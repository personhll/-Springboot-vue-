package com.huanglulu.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huanglulu.wiki.domain.Ebook;
import com.huanglulu.wiki.domain.EbookExample;
import com.huanglulu.wiki.mapper.EbookMapper;
import com.huanglulu.wiki.req.EbookReq;
import com.huanglulu.wiki.resp.EbookResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //模糊查询
        //只能根据内容查找criteria.andNameLike("%"+req.getName()+"%");
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
        //分页最基本数据：两个请求参数：pageNum，pageSize，两个返回参数：ebookList，getTotal（）
        PageHelper.startPage( req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook>pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}"+pageInfo.getTotal());
        LOG.info("总页数：{}"+pageInfo.getPages());


        //列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            //从ebook拷贝到ebookResp，（对象的复制）
//            EbookResp copyResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }
       PageResp<EbookResp> pageResp = new PageResp();
       pageResp.setTotal(pageInfo.getTotal());
       pageResp.setList(list);
       return pageResp;
    }
}
