package com.yinchuan.yunshu.controller;

import com.yinchuan.yunshu.config.Order;
import com.yinchuan.yunshu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    //== filed ==
    private OrderService orderService;

    //== constructor ==
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("orders")
    public List<Order> findAll(){
        return orderService.findAll();
    }

    @GetMapping("orders/findAllMyOrders")
    public List<Order> findAllMyOrders(@RequestParam int userId, int userType){
        return orderService.findAllMyOrders(userId, userType);
    }

    @PostMapping("orders/createOrder")
    public String createOrder(@RequestParam int id, int userId, int targetId,  @RequestBody Order order){
        return orderService.createOrder(order, id, userId, targetId);
    }

    @PostMapping("orders/receiveOrder")
    public ResponseEntity<?> receiveOrder(@RequestParam int buyerId, int goodId){
        return orderService.receiveOrder(buyerId, goodId);
    }
}
