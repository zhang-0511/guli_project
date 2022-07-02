package com.tzl.vod;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @program: guli_parent
 * @description: 创建上传视频初始化
 * @author: zl
 * @create: 2022-05-20 18:44
 **/
public class InitObject {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret)throws ClientException {

        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }
}
