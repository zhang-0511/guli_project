package com.tzl.vod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: guli_parent
 * @description: 读取配置文件的属性工具类
 * @author: zl
 * @create: 2022-05-12 15:11
 **/
@Data
@Component
public class ConstantPropertiesUtils implements InitializingBean {



    @Value("${aliyun.vod.file.keyId}")
    private String keyId;

    @Value("${aliyun.vod.file.keySecret}")
    private String keySecret;

    public static String KEY_ID;
    public static String KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;

    }
}
