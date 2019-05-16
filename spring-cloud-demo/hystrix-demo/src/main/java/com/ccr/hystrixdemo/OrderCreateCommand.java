package com.ccr.hystrixdemo;

import com.netflix.hystrix.*;

public class OrderCreateCommand extends HystrixCommand<Long>   {

    private OrderService orderService;

    public OrderCreateCommand(OrderService orderService) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryOrder"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderThread"))
                //超时时间
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000))
                //核心线程数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(3)));

        this.orderService = orderService;
    }

    @Override
    protected Long run() throws Exception {
        return orderService.createOrder();
    }

    @Override
    protected Long getFallback() {
        System.out.println("创建订单失败....");
        return 0L;
    }

}
