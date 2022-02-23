package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.domain.Test;
import com.huanglulu.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//RestController一般用来返回字符串
//Controller 用来返回页面
@RestController
public class TestController {

    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private TestService testService;
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
        return "hello world!"+testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "hello world! Post,"+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

}
