package com.ccr.hystrixdemo;


/**
 * 模拟订单服务
 */
public class OrderService {

    String queryOrder(Long id) {
        System.out.println("remote call order service queryOrder,result is " + id);
        return "remote call order service queryOrder,result is " + id;
    }

    Long createOrder() {
        System.out.println("remote call order service createOrder,result is success");
        return 100L;
    }
}
