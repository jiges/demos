package com.ccr.sericefeign;

import org.springframework.stereotype.Component;

/**
 * @author ccr12312@163.com at 2019-5-15
 */
@Component
public class SchedulerServiceHiHystrix  implements SchedulerServiceHi{
    @Override
    public String hi(String name) {
        return "sorry,server is disabled...";
    }

    @Override
    public String hello() {
        return "sorry,server is disabled...";
    }
}
