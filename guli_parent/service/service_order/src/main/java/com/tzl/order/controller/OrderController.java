package com.tzl.order.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.model.entity.Order;
import com.tzl.order.service.OrderService;
import com.tzl.result.Result;
import com.tzl.utils.JwtHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Slf4j
@Api(tags = "订单接口类")
@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    @ApiOperation("/创建订单接口，返回订单号")
    public Result createOrder(
            @ApiParam(name = "courseId", value = "课程信息Id", required = true)@PathVariable("courseId") String courseId,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        String userId = JwtHelper.getUserId(token);
        String orderId = orderService.createOrder(courseId,userId);
        return Result.ok().data("orderId",orderId);
    }

    @GetMapping("/getOrderInfo/{orderId}")
    @ApiOperation("根据订单号查询订单信息")
    public Result getOrderInfo(@ApiParam(name = "orderId", value = "订单号", required = true)@PathVariable("orderId") String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("order",order);
    }

    @GetMapping("/getOrderUser/{userId}/{courseId}")
    @ApiOperation("根据用户id和课程id查询用户是否已经购买该课程")
    public Result getOrderUser(
            @ApiParam(name = "userId", value = "用户id", required = true)@PathVariable("userId") String userId,
            @ApiParam(name = "courseId", value = "课程信息Id", required = true)@PathVariable("courseId") String courseId
    ){
        String orderUser = orderService.getOrderUser(userId,courseId);
        return Result.ok().data("orderUser",orderUser);
    }
}

