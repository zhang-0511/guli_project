package com.tzl.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.entity.PayLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}
