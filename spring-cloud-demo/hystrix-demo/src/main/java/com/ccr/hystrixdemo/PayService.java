package com.ccr.hystrixdemo;

/**
 * 模拟支付服务
 */
public class PayService {

    String pay(Long orderId) {
        System.out.println("remote call pay service,result is success");
        return "remote call pay service,result is success";
    }
}
