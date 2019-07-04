package com.yinchuan.yunshu.service;

import com.yinchuan.yunshu.config.Order;
import com.yinchuan.yunshu.config.Storage;
import com.yinchuan.yunshu.repository.OrderRepository;
import com.yinchuan.yunshu.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orders;

    @Autowired
    public StorageRepository goods;

    public Order findById(int id){
        return orders.findById(id);
    }

    public List<Order> findBySellerId(int sellerId){
        return orders.findBySellerId(sellerId);
    }

    public List<Order> findByBuyerId(int buyerId){
        return orders.findByBuyerId(buyerId);
    }

    public List<Order> findByStatus(int status){
        return orders.findByStatus(status);
    }

    public List<Order> findAll(){
        return orders.findAll();
    }

    public List<Order> findAllMyOrders(int userId, int userType){
        if(userType ==2) {
            List<Order> ordersAsBuyer = this.orders.findByBuyerId(userId);
            List<Order> ordersAsSeller = this.orders.findBySellerId(userId);
            ordersAsBuyer.addAll(ordersAsSeller);

            return ordersAsBuyer;
        }
        else if(userType ==3){
            return this.orders.findByDeliverymanId(userId);
        }
        else{
            return this.orders.findAll();
        }
    }

    public String createOrder(Order order, int id, int userId, int targetId){
        if(order==null)
            return "order could not be null./n";
        else {
            Storage targetGood = this.goods.findByIdAndUserId(id, userId);
            if(goods != null){
                targetGood.setUserId(targetId);
                this.orders.save(order);
                return "Order " + order.getId() + "has been created!";
            }
            else{
                return "订单创建失败";
            }
        }
    }

    public ResponseEntity<?> receiveOrder(int buyerId, int goodId){
        Order order = this.orders.findByBuyerIdAndGoodId(buyerId, goodId);
        if(order==null)
            return new ResponseEntity<>("No order found", HttpStatus.BAD_REQUEST);
        else {
            int deliverymanId = order.getDeliverymanId();
            Storage targetGood = this.goods.findByIdAndUserId(goodId, deliverymanId);
            if(targetGood != null){
                targetGood.setUserId(buyerId);
                this.goods.save(targetGood);
                order.setStatus(2);
                this.orders.save(order);
                return new ResponseEntity<>("SUCCESS\n"+targetGood.toString()+"\n"+order.toString(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
