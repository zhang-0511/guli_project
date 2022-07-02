package com.tzl.msm.utils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/** 生成短信验证码的工具类
 * @author zyf
 * @date 2021年05月18日 15:18
 */
public class RandomUtil {

    public static final String BASE_NUMBER = "0123456789";

    public static String getSixBitRandom() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int num = ThreadLocalRandom.current().nextInt(BASE_NUMBER.length());
            sb.append(BASE_NUMBER.charAt(num));
        }
        return sb.toString();
    }

    public static String getFourBitRandom() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int num = ThreadLocalRandom.current().nextInt(BASE_NUMBER.length());
            sb.append(BASE_NUMBER.charAt(num));
        }
        return sb.toString();
    }

}
