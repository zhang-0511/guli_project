package com.tzl.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
public interface OrderService extends IService<Order> {

    public String createOrder(String courseId, String userId);

    public String getOrderUser(String userId, String courseId);
}
