package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.req.CategoryQueryReq;
import com.huanglulu.wiki.req.CategorySaveReq;
import com.huanglulu.wiki.resp.CommonResp;
import com.huanglulu.wiki.resp.CategoryQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<PageResp<CategoryQueryResp>>resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }

}
