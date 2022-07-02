package com.tzl.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.tzl.exceptionHander.GuliException;
import com.tzl.model.entity.UcenterMember;
import com.tzl.result.Result;
import com.tzl.result.ResultCodeEnum;
import com.tzl.user.service.UcenterMemberService;
import com.tzl.user.utils.ConstantWxPropertiesUtils;
import com.tzl.user.utils.HttpClientUtils;
import com.tzl.utils.JwtHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: guli_parent
 * @description: 微信扫码登录
 * @author: zl
 * @create: 2022-05-28 10:07
 **/
@Slf4j
@Api(tags = "微信登录接口类")
//@RestController
@Controller
@RequestMapping("/user/wxLogin")
@SuppressWarnings("all")
public class WxLoginController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    @GetMapping("/getLoginParam")
    @ApiOperation("获取微信二维码参数")
    @ResponseBody
    public Result getLoginParam(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
        map.put("scope", "snsapi_login");
        String redirectUrl = ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(ResultCodeEnum.URL_ENCODE_ERROR);
        }
        map.put("redirect_uri", redirectUrl);
        map.put("state",String.valueOf(System.currentTimeMillis()));

        return Result.ok().data(map);
    }


    @ApiOperation(value = "微信扫描后进行回调方法")
    @GetMapping("/callback")
    public String callback(String code,String state){
        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {

            throw new GuliException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        //拿着code、微信id和密钥，请求微信固定地址，得到两个值
        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessToken = new StringBuffer();
        baseAccessToken.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessToken.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET, code);
        //使用HttpClientUtils 去请求这个地址
        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        //字符串返回获取两个值，openId和access_token
        JSONObject jsonObject = JSONObject.parseObject(accessTokenInfo);
        if(jsonObject.getString("errcode") != null){
            throw new GuliException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");

        //判断数据库中是否存在微信扫描人的信息， 根据openid判断
        UcenterMember userInfo = ucenterMemberService.selectWxInfoOpenid(openid);

        Map<String, Object> map = new HashMap<>();

        if (userInfo != null){
            if (!StringUtils.isEmpty(userInfo.getMobile()) && !StringUtils.isEmpty(userInfo.getPassword())){
                // jwt生成token生成
                String token = JwtHelper.createToken(userInfo.getId(), userInfo.getMobile());
                try {
                    userInfo.setNickname(URLEncoder.encode(userInfo.getNickname(), String.valueOf(StandardCharsets.UTF_8)));
                } catch (UnsupportedEncodingException e) {
                    throw new GuliException(ResultCodeEnum.URL_ENCODE_ERROR);
                }
                //返回信息， 返回登录用户名，返回token
                map.put("token",token);
                map.put("userInfo",userInfo);
                map.put("isLogin",1);
            }else {
                userInfo.setLoginTime(new Date());
                ucenterMemberService.updateById(userInfo);
                map.put("isLogin",0);
            }
        }else {
            //通过openId和access_token 访问微信地址
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String resultInfo = null;

            try {
                resultInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            //获取用户信息
            JSONObject resultUserInfoJson  = JSONObject.parseObject(resultInfo);
            if(resultUserInfoJson.getString("errcode") != null){
                throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            String nickname = resultUserInfoJson.getString("nickname"); //昵称
            try {
                nickname = URLEncoder.encode(nickname, String.valueOf(StandardCharsets.UTF_8));
            } catch (UnsupportedEncodingException e) {
                throw new GuliException(ResultCodeEnum.URL_ENCODE_ERROR);
            }
            String headimgurl = resultUserInfoJson.getString("headimgurl"); //头像
            String sex = resultUserInfoJson.getString("sex");
            userInfo = new UcenterMember();
            userInfo.setNickname(nickname)
                    .setOpenid(openid)
                    .setAvatar(headimgurl)
                    .setSex(Integer.valueOf(sex));
            map.put("isLogin",0);
            map.put("userInfo",userInfo);
        }
        String jsonMap = JSONObject.toJSONString(map);

        return "redirect:http://localhost:3000/weixin/callback?map="+jsonMap;
    }
}
