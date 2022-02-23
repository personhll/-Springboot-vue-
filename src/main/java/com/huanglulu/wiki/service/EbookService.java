package com.huanglulu.wiki.service;

import com.huanglulu.wiki.domain.Ebook;
import com.huanglulu.wiki.domain.EbookExample;
import com.huanglulu.wiki.mapper.EbookMapper;
import com.huanglulu.wiki.req.EbookReq;
import com.huanglulu.wiki.resp.EbookResp;
import com.huanglulu.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //模糊查询
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        //列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            //从ebook拷贝到ebookResp，（对象的复制）
//            EbookResp copyResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }
       return list;
    }
}
