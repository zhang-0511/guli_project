package com.tzl.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.entity.UcenterMember;
import com.tzl.model.vo.LoginVo;
import com.tzl.model.vo.RegisterVo;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-26
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    public Map<String, Object> login(LoginVo loginVo);

    public Map<String, Object> register(RegisterVo registerVo);

    public UcenterMember getloginInfo(String token);

    public UcenterMember selectWxInfoOpenid(String openid);

    public Map<String,Object> countRegister(String day);
}
