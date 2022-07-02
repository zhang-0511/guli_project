package com.tzl.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.client.CourseClient;
import com.tzl.client.UcenterClient;
import com.tzl.model.entity.Order;
import com.tzl.model.entity.UcenterMember;
import com.tzl.model.vo.CourseInfoVo;

import com.tzl.order.mapper.OrderMapper;
import com.tzl.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseClient courseClient;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public String createOrder(String courseId, String userId) {
        Object ucenterMember = ucenterClient.getUserInfo(userId).getData().get("ucenterMember");
        String json = JSONObject.toJSONString(ucenterMember);
        UcenterMember user = JSONObject.parseObject(json, UcenterMember.class);

        Object courseInfo = courseClient.getCourseInfoFront(courseId).getData().get("courseInfo");
        String courseJson = JSONObject.toJSONString(courseInfo);
        CourseInfoVo courseInfoVo = JSONObject.parseObject(courseJson, CourseInfoVo.class);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo())
                .setCourseId(courseId)
                .setMemberId(userId)
                .setCourseCover(courseInfoVo.getCover())
                .setNickname(user.getNickname())
                .setCourseTitle(courseInfoVo.getTitle())
                .setMobile(user.getMobile())
                .setTeacherName(courseInfoVo.getTeacher().getName())
                .setTotalFee(courseInfoVo.getPrice())
                .setStatus(0)
                .setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public String getOrderUser(String userId, String courseId) {
        String orderUser = "未支付";
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",userId)
                .eq("course_id",courseId)
                .eq("status",1);
        int count = this.count(wrapper);
        if (count > 0){
            orderUser = "已支付";
        }else {
            orderUser = "未支付";
        }
        return orderUser;
    }
}
