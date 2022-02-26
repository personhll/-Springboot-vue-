package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.req.EbookQueryReq;
import com.huanglulu.wiki.req.EbookSaveReq;
import com.huanglulu.wiki.resp.CommonResp;
import com.huanglulu.wiki.resp.EbookQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<PageResp<EbookQueryResp>>resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

}
