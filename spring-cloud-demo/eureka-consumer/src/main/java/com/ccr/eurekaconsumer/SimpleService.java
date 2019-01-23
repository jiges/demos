package com.ccr.eurekaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author ccr12312@163.com at 2019-1-23
 */
@Service
public class SimpleService {

    @Autowired
    private RestTemplate restTemplate;

    public String getInfo(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name,String.class);
    }
}
