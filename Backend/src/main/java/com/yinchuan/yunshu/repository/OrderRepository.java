package com.yinchuan.yunshu.repository;

import com.yinchuan.yunshu.config.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface for accessing user table
 * @author Jonah Luo
 *
 */

public interface OrderRepository extends CrudRepository<Order, Integer> {
    /**
     * Gets user from the table by id
     * @param id The id of the user
     * @return User object
     */
    Order findById(int id);

    List<Order> findBySellerId(int sellerId);

    List<Order> findByBuyerId(int buyerId);

    Order findByBuyerIdAndGoodId(int buyerId, int goodId);

    List<Order> findByDeliverymanId(int deliverymanId);

    List<Order> findByStatus(int status);
    /**
     * Gets all users from the table
     */
    List<Order> findAll();
}
