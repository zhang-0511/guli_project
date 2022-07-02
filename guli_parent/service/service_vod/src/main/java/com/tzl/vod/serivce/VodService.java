package com.tzl.vod.serivce;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    public String uploadAliyunVideo(MultipartFile file);

    public boolean deleteAliyunVideo(String videoSourceId);

    public boolean deleteAliyunVideoBatch(List<String> videoSourceIdList);

    public String getPlayAuth(String videoSourceId);
}
