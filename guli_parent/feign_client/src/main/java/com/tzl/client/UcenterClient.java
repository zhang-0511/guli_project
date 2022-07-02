package com.tzl.client;


import com.tzl.client.impl.UcenterClientImpl;
import com.tzl.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name="service-user",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据token字符串获取用户信息
    @GetMapping("/user/ucenterMember/getUserInfo/{id}")
    @ApiOperation("根据用户id获取用户信息")
    public Result getUserInfo(@PathVariable("id") String id);


    @GetMapping("/user/ucenterMember/countRegister/{day}")
    @ApiOperation("/统计某一天的注册人数")
    public Result countRegister(@ApiParam(name = "day",value = "用户id") @PathVariable("day") String day);

}
