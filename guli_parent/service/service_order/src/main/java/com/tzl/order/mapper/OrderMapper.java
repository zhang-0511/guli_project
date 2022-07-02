package com.tzl.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
