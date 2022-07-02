package com.tzl.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.tzl.exceptionHander.GuliException;
import com.tzl.model.entity.Order;
import com.tzl.model.entity.PayLog;
import com.tzl.order.mapper.PayLogMapper;
import com.tzl.order.service.OrderService;
import com.tzl.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.order.utils.HttpClient;
import com.tzl.result.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Map<String, Object> createNative(String orderId) {
        Map<String, Object> result = new HashMap<>();

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        try {
            //封装生成二维码需要的参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderId);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "https://guli.shop/api/order/weixinPay/weixinNotify");
            map.put("trade_type", "NATIVE");

            //发送Http请求。传递xml参数。微信支付的固定地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //得到请求结果
            String content = client.getContent();

            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            result.put("out_trade_no", orderId);
            result.put("courseId", order.getCourseId());
            result.put("courseTitle", order.getCourseTitle());
            result.put("teacherName", order.getTeacherName());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", resultMap.get("result_code"));
            result.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            redisTemplate.opsForValue().set(orderId, result, 120, TimeUnit.MINUTES);
            return result;
        }catch (Exception e){
            throw new GuliException(ResultCodeEnum.PAY_ERROR);
        }
    }

    /*
        查询支付状态
     */
    @Override
    public Map<String, String> queryPayStatus(String orderId) {
        try{
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("out_trade_no", orderId);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();

            //7、返回
            return WXPayUtil.xmlToMap(xml);
        }catch (Exception e){
            throw new GuliException(ResultCodeEnum.PAY_ERROR);
        }
    }

    /*
        向支付表里面添加记录，同时更新订单的状态
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        if(order.getStatus() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
