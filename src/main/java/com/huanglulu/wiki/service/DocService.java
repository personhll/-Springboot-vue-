package com.huanglulu.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huanglulu.wiki.domain.Content;
import com.huanglulu.wiki.domain.Doc;
import com.huanglulu.wiki.domain.DocExample;
import com.huanglulu.wiki.exception.BusinessException;
import com.huanglulu.wiki.exception.BusinessExceptionCode;
import com.huanglulu.wiki.mapper.ContentMapper;
import com.huanglulu.wiki.mapper.DocMapper;
import com.huanglulu.wiki.mapper.DocMapperCust;
import com.huanglulu.wiki.req.DocQueryReq;
import com.huanglulu.wiki.req.DocSaveReq;
import com.huanglulu.wiki.resp.DocQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.util.CopyUtil;
import com.huanglulu.wiki.util.RedisUtil;
import com.huanglulu.wiki.util.RequestContext;
import com.huanglulu.wiki.util.SnowFlake;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

    @Resource
    public WsService wsService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    public PageResp<DocQueryResp> list(DocQueryReq req){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();

        //分页最基本数据：两个请求参数：pageNum，pageSize，两个返回参数：docList，getTotal（）
        PageHelper.startPage( req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc>pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}"+pageInfo.getTotal());
        LOG.info("总页数：{}"+pageInfo.getPages());


        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
       PageResp<DocQueryResp> pageResp = new PageResp();
       pageResp.setTotal(pageInfo.getTotal());
       pageResp.setList(list);
       return pageResp;
    }

    public List<DocQueryResp> all(Long ebookId){
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }


    /**
     * 保存
     * @param req
     */
    @Transactional
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req,Doc.class);
        Content content = CopyUtil.copy(req,Content.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        }else{
            //更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);

            if(count == 0){
                contentMapper.insert(content);
            }

        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(List<String> ids){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }


    /**
     * @param id
     */
    public String findContent(Long id){
        Content content = contentMapper.selectByPrimaryKey(id);
        //文档阅读数加一
        docMapperCust.increaseViewCount(id);
        if(ObjectUtils.isEmpty(content)){
            return "";
        }else{
            return content.getContent();
        }
    }

    /**
     * 点赞
     * @param id
     */
    public void vote(Long id){
//        docMapperCust.increaseVoteCount(id);
        //远程IP+doc.id作为key24小时不能重复
        String ip = RequestContext.getRemoteAddr();
        if(redisUtil.validateRepeat("DOC_VOTE_"+id+"_"+ip,5000)){
            docMapperCust.increaseVoteCount(id);
        }else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        //推送消息
        Doc docDb = docMapper.selectByPrimaryKey(id);
        String logId = MDC.get("LOG_ID");
//        wsService.sendInfo("【"+docDb.getName()+"】被点赞！",logId);
        rocketMQTemplate.convertAndSend("VOTE_TOPIC","【"+docDb.getName()+"】被点赞！");
    }

    public void updateEbookInfo(){
        docMapperCust.updateEbookInfo();
    }
}
