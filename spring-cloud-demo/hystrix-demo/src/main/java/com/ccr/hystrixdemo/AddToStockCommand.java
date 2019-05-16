package com.ccr.hystrixdemo;

import com.netflix.hystrix.*;

public class AddToStockCommand extends HystrixCommand<String> {

    private StockService stockService;

    private Long orderId;

    public AddToStockCommand(StockService stockService,Long orderId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryOrder"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderThread"))
                //超时时间
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000))
                //核心线程数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(3)));

        this.stockService = stockService;
        this.orderId = orderId;
    }

    @Override
    protected String run() throws Exception {
        return stockService.addToStock(orderId);
    }

    @Override
    protected String getFallback() {
        return "添加库存失败....";
    }
}
