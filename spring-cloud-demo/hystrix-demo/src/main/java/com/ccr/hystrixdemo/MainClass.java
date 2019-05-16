package com.ccr.hystrixdemo;

public class MainClass {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        PayService payService = new PayService();
        StockService stockService = new StockService();

        //模拟下单
        //正常情况下
//        Long order = orderService.createOrder();
//        payService.pay(order);
        //如果stockService异常，那么tomcat线程将在这个地方阻塞，
        // 如果此时正是高并发，那么tomcat线程将会被很快耗尽，继而影响整个服务崩溃
//        stockService.addToStock(order);

        //隔离保护
        //如果创建订单的任务超过队列长度，将会返回兜底数据，调用订单服务超时也会返回兜底数据
        Long order = new OrderCreateCommand(orderService).execute();
        if(order != 0) {
            System.out.println("返回正确的订单数据");
            //下面两个服务异步调用
            new PayCommand(payService,order).queue();
            new AddToStockCommand(stockService,order).queue();
        }



    }
}
