package com.tzl.client.impl;

import com.tzl.client.UcenterClient;
import com.tzl.result.Result;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {

    @Override
    public Result getUserInfo(String id) {
        return Result.error().message("time out");
    }

    @Override
    public Result countRegister(String day) {
        return Result.error().message("time out");
    }
}
