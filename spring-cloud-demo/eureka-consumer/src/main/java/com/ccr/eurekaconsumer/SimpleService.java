package com.ccr.eurekaconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 在hiService方法上加上@HystrixCommand注解。该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，熔断方法直接返回了一个字符串
 * @author ccr12312@163.com at 2019-1-23
 */
@Service
public class SimpleService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String getInfo(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name,String.class);
    }

    @HystrixCommand(fallbackMethod = "helloError")
    public String getHelloInfo() {
        return restTemplate.getForObject("http://SERVICE-HI/hello",String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }

    public String helloError() {
        return "hello server is disabled,sorry,error!";
    }
}
