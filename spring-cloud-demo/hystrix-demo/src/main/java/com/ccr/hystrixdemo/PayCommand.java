package com.ccr.hystrixdemo;

import com.netflix.hystrix.*;

public class PayCommand extends HystrixCommand<String> {

    private PayService payService;

    private Long orderId;

    public PayCommand(PayService payService,Long orderId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryOrder"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderThread"))
                //超时时间
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000))
                //核心线程数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(3)));

        this.payService = payService;
        this.orderId = orderId;
    }

    @Override
    protected String run() throws Exception {
        return payService.pay(orderId);
    }

    @Override
    protected String getFallback() {
        return "支付失败....";
    }
}
