package com.tzl.client.impl;

import com.tzl.client.VodClient;
import com.tzl.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {


    @Override
    public Result deleteAliyunVideo(String videoId) {
        return Result.error().message("time out");
    }

    @Override
    public Result deleteAliyunVideoBatch(List<String> videoSourceIdList) {
        return Result.error().message("time out");
    }
}
