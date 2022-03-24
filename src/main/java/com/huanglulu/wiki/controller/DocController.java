package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.req.DocQueryReq;
import com.huanglulu.wiki.req.DocSaveReq;
import com.huanglulu.wiki.resp.CommonResp;
import com.huanglulu.wiki.resp.DocQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<PageResp<DocQueryResp>>resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<List<DocQueryResp>>resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<String>resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp();
        docService.vote(id);
        return resp;
    }


}
