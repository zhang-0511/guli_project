package com.tzl.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tzl.exceptionHander.GuliException;
import com.tzl.msm.service.MsmService;
import com.tzl.msm.utils.ConstantPropertiesUtils;
import com.tzl.result.ResultCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** 短信服务 业务层实现类
 * @author zyf
 * @date 2021年05月18日 15:06
 */
@Service
public class MsmServiceImpl implements MsmService {


    /**
     * 整合短信服务进行发送
     * @author zyf
     * @date 2021/5/18 15:35
     * @param phone 手机号
     * @param code 验证码
     * @return boolean
     */
    @Override
    public boolean sendCode(String phone, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            return false;
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret =ConstantPropertiesUtils.SECRET;//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);

        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);

        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setSysMethod(MethodType.POST);

        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phone);

        //必填:短信签名-可在短信控制台中找到
        request.setSignName("乐优商城");

        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_205436421");

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        String json = JSONObject.toJSONString(map);
        request.setTemplateParam(json);

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(ResultCodeEnum.SEND_CODE_ERROR);
        }
        if (Objects.requireNonNull(sendSmsResponse).getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            return true;
        }
        return false;
    }

    /**
     * Mq发送消息
     * @author zyf
     * @date 2021/5/31 17:38
     * @param msmVo 短信实体
     * @return boolean
     */
    /*@Override
    public boolean sendMq(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getPhone())){
            boolean b = this.send(msmVo.getPhone(), msmVo.getParam());
            return b;
        }
        return false;
    }

    private boolean send(String phone, Map<String, Object> paramMap) {
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        //整合阿里云短信服务，发送短信
        DefaultProfile profile = DefaultProfile.getProfile(ConstantPropertiesUtils.REGION_ID,
                ConstantPropertiesUtils.ACCESS_KEY_ID,
                ConstantPropertiesUtils.SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //手机号
        request.putQueryParameter("PhoneNumbers",phone);
        //签名名称
        request.putQueryParameter("SignName","乐优商城");
        //短信模板code
        request.putQueryParameter("TemplateCode","SMS_205436421");
        //验证码  使用json格式   {"code":"123456"}
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(paramMap));

        //调用方法进行短信发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
