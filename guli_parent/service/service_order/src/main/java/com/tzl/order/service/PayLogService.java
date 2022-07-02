package com.tzl.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
public interface PayLogService extends IService<PayLog> {

    public Map<String, Object> createNative(String orderId);

    Map<String, String> queryPayStatus(String orderId);

    void updateOrderStatus(Map<String, String> map);
}
