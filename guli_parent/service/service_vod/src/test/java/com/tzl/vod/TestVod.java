package com.tzl.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.HashMap;
import java.util.List;

/**
 * @program: guli_parent
 * @description: 测试阿里视频上传
 * @author: zl
 * @create: 2022-05-20 18:50
 **/
public class TestVod {

    public static void main(String[] args) {
        getPlaAddress();
    }

    public static void testUploadVideo(){

        //1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "testVideo";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "C:\\Users\\tzl\\Downloads\\谷粒学苑\\test.flv";
        //本地文件上传
        UploadVideoRequest request = new UploadVideoRequest("LTAI4Fz7nLmqqW1yrHUngfae", "81ZPJTux48voQswGj93yWbyBbJH2tq", title, fileName);

        /* 模板组ID(可选) */
        request.setTemplateGroupId("ad60d10694a54baa140d9a170885468c");
        /* 视频分类ID(可选) */
        request.setCateId(1000406773L);
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        request.setIsShowWaterMark(true);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }

    public static void getPlaAddress(){
        //根据视频Id获取视频播放地址
        DefaultAcsClient client = InitObject.initVodClient("LTAI4Fz7nLmqqW1yrHUngfae", "81ZPJTux48voQswGj93yWbyBbJH2tq");

        //创建获取视频地址的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request中设置视频id
        request.setVideoId("9b04b628f19c4e6b8aec8f0f47a72818");

        //调用初始化对象中的方法 ，传递request，获取数据
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        String playAuth = response.getPlayAuth();
        System.out.println("playAuth =====>"+playAuth);
    }

    public static  void  getPlayUrl(){
        //根据视频Id获取视频播放地址
        DefaultAcsClient client = InitObject.initVodClient("LTAI4Fz7nLmqqW1yrHUngfae", "81ZPJTux48voQswGj93yWbyBbJH2tq");

        //创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request中设置视频id
        request.setVideoId("06c800cf14d547e194ff3f4d1bd77418");

        //调用初始化对象中的方法 ，传递request，获取数据
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        playInfoList.forEach(playInfo -> {
            System.out.println("PlayInfo =>"+playInfo.getPlayURL());
        });
        System.out.println("title => "+response.getVideoBase().getTitle());
    }
}
