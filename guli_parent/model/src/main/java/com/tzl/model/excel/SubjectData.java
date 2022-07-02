package com.tzl.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: guli_parent
 * @description: excel的实体类
 * @author: zl
 * @create: 2022-05-12 19:28
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Excel读取对象", description="课程分类的表格读取对象")
public class SubjectData {

    @ApiModelProperty(value = "一级分类")
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ApiModelProperty(value = "二级分类")
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
