package com.huanglulu.wiki.controller;

import com.huanglulu.wiki.domain.Test;
import com.huanglulu.wiki.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

//RestController一般用来返回字符串
//Controller 用来返回页面
@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private RedisTemplate redisTemplate;

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

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value){
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable Long key){
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}",key, object);
        return object;
    }

}
