package com.tzl.edu.service;

import com.tzl.model.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-12
 */
public interface SubjectService extends IService<Subject> {

    public void excelUpload(MultipartFile file,SubjectService subjectService);

    public List<SubjectNestedVo> getAllOneTwoSubject();
}
