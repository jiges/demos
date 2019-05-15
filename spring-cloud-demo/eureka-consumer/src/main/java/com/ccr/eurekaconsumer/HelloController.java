package com.ccr.eurekaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccr12312@163.com at 2019-1-23
 */
@RestController
public class HelloController {

    @Autowired
    SimpleService simpleService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return simpleService.getInfo(name);
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return simpleService.getHelloInfo();
    }
}
