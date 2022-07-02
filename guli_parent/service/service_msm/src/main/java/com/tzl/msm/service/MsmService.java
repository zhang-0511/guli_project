package com.tzl.msm.service;


/** 短信服务的业务层接口
 * @author zyf
 * @date 2021年05月18日 15:06
 */
public interface MsmService {

    /**
     * 整合短信服务进行发送
     * @author zyf
     * @date 2021/5/18 15:35
     * @param phone 手机号
     * @param code 验证码
     * @return boolean
     */
    boolean sendCode(String phone, String code);

    /**
     * Mq发送消息
     * @author zyf
     * @date 2021/5/31 17:38
     * @param msmVo 短信实体
     * @return boolean
     */
    //boolean sendMq(MsmVo msmVo);
}
