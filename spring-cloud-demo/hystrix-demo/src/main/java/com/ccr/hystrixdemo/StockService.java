package com.ccr.hystrixdemo;


/**
 * 模拟库存服务
 */
public class StockService {


    String addToStock(Long orderId) {
        System.out.println("remote call stock service result is success.");
        return "remote call stock service result is success.";
    }

}
