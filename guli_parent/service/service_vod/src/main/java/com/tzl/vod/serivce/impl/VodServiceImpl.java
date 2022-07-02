package com.tzl.vod.serivce.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tzl.exceptionHander.GuliException;
import com.tzl.result.ResultCodeEnum;
import com.tzl.vod.serivce.VodService;
import com.tzl.vod.utils.ConstantPropertiesUtils;
import com.tzl.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import java.io.InputStream;
import java.util.List;

/**
 * @program: guli_parent
 * @description: 对象存储服务层实现类
 * @author: zl
 * @create: 2022-05-12 15:29
 **/
@Slf4j
@Service
public class VodServiceImpl implements VodService {



    @Override
    public String uploadAliyunVideo(MultipartFile file) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String title = "";
        String fileName = "";
        InputStream inputStream = null;
        try {
            fileName = file.getOriginalFilename();
            inputStream = file.getInputStream();
            assert fileName != null;
            title = fileName.substring(0,fileName.lastIndexOf("."));
            log.info("title => {}",title);
            log.info("fileName => {}",fileName);
            UploadStreamRequest request = new UploadStreamRequest(keyId, keySecret, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            request.setShowWaterMark(true);
            /* 视频分类ID(可选) */
            request.setCateId(1000406773L);
            /* 模板组ID(可选) */
            request.setTemplateGroupId("ad60d10694a54baa140d9a170885468c");
            /* 存储区域(可选) */
            //request.setStorageLocation("outin-1da503f6d81111ecb75e00163e00b174.oss-cn-shanghai.aliyuncs.com");

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            log.info("RequestId => {}",response.getRequestId());//请求视频点播服务的请求ID
            String videoId= "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                log.info("videoId => {}",response.getVideoId());
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                log.info("videoId => {}",response.getVideoId());
                log.info("ErrorCode => {}",response.getCode());
                log.info("ErrorMessage => {}",response.getMessage());
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean deleteAliyunVideo(String videoSourceId) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        DefaultAcsClient client =  InitVodClient.initVodClient(keyId,keySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response;
        try {
            request.setVideoIds(videoSourceId);
            response = client.getAcsResponse(request);
            log.info("RequestId =>{} ",response.getRequestId());
            return true;
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public boolean deleteAliyunVideoBatch(List<String> videoSourceIdList) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        DefaultAcsClient client =  InitVodClient.initVodClient(keyId,keySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response;
        try {
            String videoIds = StringUtils.join(videoSourceIdList.toArray(),",");
            System.out.println(videoIds);
            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);
            log.info("RequestId =>{} ",response.getRequestId());
            return true;
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public String getPlayAuth(String videoSourceId) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String playAuth = "";
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(keyId, keySecret);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request中设置视频id
            request.setVideoId(videoSourceId);

            //调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            playAuth = response.getPlayAuth();
        }catch (Exception e){
            throw new GuliException(ResultCodeEnum.FAIL);
        }
        return playAuth;
    }
}
