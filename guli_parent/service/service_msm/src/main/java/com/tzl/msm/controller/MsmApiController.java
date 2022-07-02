package com.tzl.msm.controller;


import com.tzl.msm.service.MsmService;
import com.tzl.msm.utils.RandomUtil;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/** 发送短信服务 api
 * @author zyf
 * @date 2021年05月18日 15:04
 */
@Slf4j
@Api("短信服务")
@RestController
@RequestMapping("/msm/send")
public class MsmApiController {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MsmService msmService;

    @ApiOperation(value = "发送手机验证码")
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@ApiParam(name = "phone",value = "用户手机号",required = true) @PathVariable("phone") String phone){
       //从redis中获取验证码，如果获取到，删除后重新生成
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            redisTemplate.delete(phone);
        }
        //如果从redis获取不到，生成验证码，通过整合短信服务进行发送，生成的验证码放在redis中，设置有效时间
        String newCode = RandomUtil.getSixBitRandom();
        boolean isSend = msmService.sendCode(phone,newCode);
        if (isSend){
            redisTemplate.opsForValue().set(phone,newCode,5, TimeUnit.MINUTES);
            return Result.ok();
        }else {
            return Result.error().message("发送短信失败");
        }
    }
}
