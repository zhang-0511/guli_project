package com.tzl.oss.serivce.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.tzl.oss.serivce.OssService;
import com.tzl.oss.utils.ConstantPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @program: guli_parent
 * @description: 对象存储服务层实现类
 * @author: zl
 * @create: 2022-05-12 15:29
 **/
@Slf4j
@Service
public class OssServiceImpl implements OssService {


    @Override
    public String pictureUpload(MultipartFile file,String name) {
        //获取配置信息
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //创建OSS示例
        OSS ossClient = new OSSClientBuilder().build(endPoint,keyId,keySecret);
        try {

            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取file名称
            String fileName = file.getOriginalFilename();

            //生成随机性唯一值，使用uuid，添加到名称里面
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            if (fileName != null && fileName.length() < 20) {
                fileName = uuid + fileName;
            }

            //按照当前日期，创建文件夹，上传到创建的文件夹里
            String timeUrl = new DateTime().toString("yyyy/MM/dd");

            fileName = name+"/"+timeUrl+"/"+fileName;

            /*
                调用oss方法上传文件
                1、第一个参数，bucketName
                2、第二个参数：上传到oss文件的路径和文件的名称
             */
            ossClient.putObject(bucketName,fileName,inputStream);
            log.info("fileName => {}",fileName);
            //获取url路径
            String url;
            url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            log.info("url =>{}",url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭oss
            ossClient.shutdown();
        }
        return "";
    }
}
