package com.tzl.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 读取配置文件中的 短信服务的配置
 * @author zyf
 * @date 2021年05月18日 14:58
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.sms.regionId}")
    private String regionId;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.secret}")
    private String secret;


    public static String REGION_ID;
    public static String ACCESS_KEY_ID;
    public static String SECRET;

    /**
     *
     * @author zyf
     * @date 2021/5/18 14:59
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_ID = regionId;
        ACCESS_KEY_ID = accessKeyId;
        SECRET = secret;
    }
}
