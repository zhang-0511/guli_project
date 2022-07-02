package com.tzl.order.controller;


import com.tzl.client.CourseClient;
import com.tzl.order.service.PayLogService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-31
 */
@Slf4j
@Api(tags = "支付日志表")
@RestController
@RequestMapping("/order/payLog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private CourseClient courseClient;

    @GetMapping("/createNative/{orderId}")
    @ApiOperation("生成订单二维码")
    public Result createNative(@ApiParam(name = "orderId", value = "订单号", required = true)@PathVariable("orderId") String orderId){
        Map<String,Object> map = payLogService.createNative(orderId);
        return Result.ok().data(map);
    }

    @GetMapping("/queryPayStatus/{orderId}/{courseId}")
    @ApiOperation("查询订单是否支付成功")
    public Result queryPayStatus(@ApiParam(name = "orderId", value = "订单号", required = true)@PathVariable("orderId") String orderId,@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        Map<String,String> map = payLogService.queryPayStatus(orderId);
        System.out.println(map);
        if (map == null){
            return Result.error().message("支付出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            courseClient.updateBuyCount(courseId);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(222).message("支付中");
    }
}

