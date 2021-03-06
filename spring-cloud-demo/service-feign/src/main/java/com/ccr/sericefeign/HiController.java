package com.ccr.sericefeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在Web层的controller层，对外暴露一个"/hi"的API接口，通过上面定义的Feign客户端SchedualServiceHi 来消费服务
 * @author ccr12312@163.com at 2019-2-25
 */
@RestController
public class HiController {

    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    SchedulerServiceHi schedulerServiceHi;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        System.out.println("calling feign service...");
        return schedulerServiceHi.hi( name );
    }

    @GetMapping(value = "/hello")
    public String sayHi() {
        System.out.println("calling feign service...");
        return schedulerServiceHi.hello();
    }

}
