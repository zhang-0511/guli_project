package com.tzl.result;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 统一返回结果状态信息类
 */
@Getter
@Accessors(chain = true)
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    PARAM_ERROR( 202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),

    LOGIN_AUTH(206, "未登陆"),
    PERMISSION(207, "没有权限"),

    CODE_ERROR(208, "验证码错误"),
    SEND_CODE_ERROR(209, "验证码发送失败"),
    LOGIN_INFO_ERROR(210, "账号或密码为空"),
    LOGIN_MOBLE_ERROR(211, "账号不正确"),
    LOGIN_PASSWORD_ERROR(212, "密码不正确"),
    LOGIN_DISABLED_ERROR(213, "该用户已被禁用"),
    REGISTER_MOBLE_ERROR(214, "手机号已被使用"),
    LOGIN_AURH(215, "需要登录"),
    LOGIN_ACL(216, "没有权限"),
    TOKEN_OVERDUE( 224,"登录时间过期"),

    URL_ENCODE_ERROR( 217, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR( 218, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD( 219, "获取accessToken失败"),
    FETCH_USERINFO_ERROR( 220, "获取用户信息失败"),
    LOGIN_ERROR( 221, "登录失败"),

    PAY_RUN(222, "支付中"),
    PAY_ERROR(225, "支付失败"),
    CANCEL_ORDER_FAIL(223, "取消订单失败");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
