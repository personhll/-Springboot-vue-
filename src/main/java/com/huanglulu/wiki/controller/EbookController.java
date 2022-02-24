package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.req.EbookReq;
import com.huanglulu.wiki.resp.CommonResp;
import com.huanglulu.wiki.resp.EbookResp;
import com.huanglulu.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


    @GetMapping("/list")
    public CommonResp list(EbookReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<List<EbookResp>>resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

}