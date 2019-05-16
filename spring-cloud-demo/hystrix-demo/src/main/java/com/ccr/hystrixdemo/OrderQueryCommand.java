package com.ccr.hystrixdemo;

import com.netflix.hystrix.*;

public class OrderQueryCommand extends HystrixCommand<String> {

    private OrderService orderService;

    private Long orderId;

    public OrderQueryCommand(OrderService orderService,Long orderId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryOrder"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderThread"))
                //超时时间
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000))
                //核心线程数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(3)));

        this.orderService = orderService;
        this.orderId = orderId;
    }

    @Override
    protected String run() throws Exception {
        return orderService.queryOrder(orderId);
    }

    @Override
    protected String getFallback() {
        return "返回兜底数据，默认订单{0}";
    }
}
