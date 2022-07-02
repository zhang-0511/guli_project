package com.tzl.order.utils;

import org.joda.time.DateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单号工具类
 *
 * @author qy
 * @since 1.0
 */
public class OrderNoUtil {

    public static final String BASE_NUMBER = "0123456789";

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderNo() {
        String newDate = new DateTime().toString("yyyyMMddHHmmss");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int num = ThreadLocalRandom.current().nextInt(BASE_NUMBER.length());
            result.append(BASE_NUMBER.charAt(num));
        }
        return result.toString() + newDate;
    }
}
