package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.req.UserQueryReq;
import com.huanglulu.wiki.req.UserSaveReq;
import com.huanglulu.wiki.resp.CommonResp;
import com.huanglulu.wiki.resp.UserQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp<PageResp<UserQueryResp>>resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){

        //在Commonresp里放一些前端需要的通用属性
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

}