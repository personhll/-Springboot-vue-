package com.huanglulu.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//RestController一般用来返回字符串
//Controller 用来返回页面
@RestController
public class TestController {

    /**
     * GET,,POST,PUT,DELETE
     *RequestMapping支持所有
     * status= 405，浏览器直接访问地址，发送的是GET请求 ，而接口写的是POST
     * @RequestMapping(value = "/user/1",method = RequestMethod.GET)  请求访问userid=1
     * /user?id=1
     * /user/1
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "hello world!";
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "hello world! Post,"+name;
    }
}
