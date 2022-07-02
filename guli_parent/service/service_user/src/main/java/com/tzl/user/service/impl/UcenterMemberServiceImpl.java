package com.tzl.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.exceptionHander.GuliException;
import com.tzl.model.entity.UcenterMember;
import com.tzl.model.vo.LoginVo;
import com.tzl.model.vo.RegisterVo;
import com.tzl.result.ResultCodeEnum;
import com.tzl.user.mapper.UcenterMemberMapper;
import com.tzl.user.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.utils.JwtHelper;
import com.tzl.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        Map<String, Object> map = new HashMap<>();
        //从loginVo中获取输入的手机,密码和验证码
        String phone = loginVo.getMobile();
        String password = loginVo.getPassword();

        //判断手机号，密码和验证码是否为空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember ucenterMember = this.baseMapper.selectOne(wrapper);

        if (ucenterMember == null){
            throw new GuliException(ResultCodeEnum.LOGIN_INFO_ERROR);
        }

        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GuliException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        if (ucenterMember.getIsDisabled()){
            throw new GuliException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }


        ucenterMember.setLoginTime(new Date());
        this.updateById(ucenterMember);

        // jwt生成token生成
        String token = JwtHelper.createToken(ucenterMember.getId(), phone);
        //返回信息， 返回登录用户名，返回token
        map.put("token",token);
        map.put("loginInfo",ucenterMember);
        return map;
    }

    @Override
    public Map<String, Object> register(RegisterVo registerVo) {
        Map<String, Object> map = new HashMap<>();

        String nickname = registerVo.getNickname();
        String phone = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        String avatar = registerVo.getAvatar();
        String openid = registerVo.getOpenid();
        //校验参数
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        //校验校验验证码
        //从redis获取发送的验证码
        /*String phoneCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(phoneCode)) {
            throw new GuliException(ResultCodeEnum.CODE_ERROR);
        }*/
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", phone));
        if(count > 0) {
            throw new GuliException(ResultCodeEnum.REGISTER_MOBLE_ERROR);

        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setNickname(nickname)
                .setMobile(phone)
                .setPassword(MD5.encrypt(password))
                .setIsDisabled(false);
        if (!StringUtils.isEmpty(openid)){
            ucenterMember.setOpenid(openid);
        }
        if (!StringUtils.isEmpty(avatar)){
            ucenterMember.setAvatar(avatar);
        }else {
            ucenterMember.setAvatar("https://guli-tzl.oss-cn-shanghai.aliyuncs.com/avatar/2022/05/12/de39dd32f23e4fefbdfe8879f6888092avatar.jpg");
        }
        ucenterMember.setLoginTime(new Date());
        this.save(ucenterMember);
        // jwt生成token生成
        String token = JwtHelper.createToken(ucenterMember.getId(), phone);
        //返回信息， 返回登录用户名，返回token
        map.put("loginInfo",ucenterMember);
        map.put("token",token);
        return map;
    }

    @Override
    @Cacheable(value = "user",key = "'userInfo'")
    public UcenterMember getloginInfo(String token) {
        String userId = JwtHelper.getUserId(token);
        return this.getById(userId);
    }

    /**
     * 判断数据库中是否存在微信扫描人的信息， 根据openid判断
     * @author zyf
     * @date 2021/5/19 17:58
     * @param openid  openid
     * @return com.zyf.model.user.UserInfo
     */
    @Override
    public UcenterMember selectWxInfoOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember userInfo = baseMapper.selectOne(wrapper);
        return userInfo;
    }

    @Override
    public Map<String,Object> countRegister(String day) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<UcenterMember> registerWrapper = new QueryWrapper<>();
        registerWrapper.eq("date(gmt_create)",day);
        Integer registerNum = baseMapper.selectCount(registerWrapper);

        QueryWrapper<UcenterMember> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq("date(login_time)",day);
        Integer loginNum = baseMapper.selectCount(loginWrapper);
        if (registerNum == null || registerNum < 0){
            registerNum = 0;
        }
        if (loginNum == null || loginNum < 0){
            loginNum = 0;
        }
        map.put("registerNum",registerNum);
        map.put("loginNum",loginNum);
        return map;
    }
}
