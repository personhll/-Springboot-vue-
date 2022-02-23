package com.huanglulu.wiki.service;

import com.huanglulu.wiki.domain.Ebook;
import com.huanglulu.wiki.domain.EbookExample;
import com.huanglulu.wiki.mapper.EbookMapper;
import com.huanglulu.wiki.req.EbookReq;
import com.huanglulu.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            //从ebook拷贝到ebookResp，（对象的复制）
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }
        return respList;
    }
}
