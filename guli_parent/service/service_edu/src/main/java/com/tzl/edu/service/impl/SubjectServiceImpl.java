package com.tzl.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.model.entity.Subject;
import com.tzl.model.excel.SubjectData;
import com.tzl.model.vo.SubjectNestedVo;
import com.tzl.model.vo.SubjectVo;
import com.tzl.edu.listener.SubjectExcelListener;
import com.tzl.edu.mapper.SubjectMapper;
import com.tzl.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-12
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void excelUpload(MultipartFile file,SubjectService subjectService) {
        InputStream in = null;
        try {
            //获取文件输入流
            in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<SubjectNestedVo> getAllOneTwoSubject() {
        //1、先查询出一级分类的
        QueryWrapper<Subject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id",0);
        List<Subject> oneSubjectList = this.baseMapper.selectList(oneWrapper);

        //2、再查出来二级分类的
        QueryWrapper<Subject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id",0);
        List<Subject> twoSubjectList = this.baseMapper.selectList(twoWrapper);

        //3、封装一级分类
        List<SubjectNestedVo> finalList = new ArrayList<>();
        if (oneSubjectList != null && oneSubjectList.size() > 0) {
            oneSubjectList.forEach(s -> {
                SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
                BeanUtils.copyProperties(s,subjectNestedVo);

                //4、封装二级分类
                List<SubjectVo> subjectVoList = new ArrayList<>();
                if (twoSubjectList != null && twoSubjectList.size() > 0) {
                    twoSubjectList.forEach(t->{
                        if(s.getId().equalsIgnoreCase(t.getParentId())){
                            SubjectVo subjectVo = new SubjectVo();
                            BeanUtils.copyProperties(t,subjectVo);
                            subjectVoList.add(subjectVo);
                        }
                    });
                }
                subjectNestedVo.setChildren(subjectVoList);
                finalList.add(subjectNestedVo);
            });
        }

        return finalList;
    }
}
