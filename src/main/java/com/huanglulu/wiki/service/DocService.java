package com.huanglulu.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huanglulu.wiki.domain.Doc;
import com.huanglulu.wiki.domain.DocExample;
import com.huanglulu.wiki.mapper.DocMapper;
import com.huanglulu.wiki.req.DocQueryReq;
import com.huanglulu.wiki.req.DocSaveReq;
import com.huanglulu.wiki.resp.DocQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.util.CopyUtil;
import com.huanglulu.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

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

    public List<DocQueryResp> all(){
        DocExample docExample = new DocExample();
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

    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req,Doc.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        }else{
            //更新
            docMapper.updateByPrimaryKey(doc);
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
}
