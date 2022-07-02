package com.tzl.oss.serivce;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    public String pictureUpload(MultipartFile file,String fileName);

}
