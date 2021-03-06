package com.ccr.sericefeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过@FeignClient（“服务名”），来指定调用哪个服务
 * @author ccr12312@163.com at 2019-2-25
 */
@FeignClient(value = "service-hi",fallback = SchedulerServiceHiHystrix.class)
public interface SchedulerServiceHi {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String hi(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String hello();
}
