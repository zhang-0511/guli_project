package com.tzl.model.vo;

import com.tzl.model.entity.Order;
import com.tzl.model.entity.Teacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "课程基本信息", description = "编辑课程基本信息的表单对象")
public class CourseInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	private String id;

	@ApiModelProperty(value = "课程讲师ID")
	private String teacherId;

	@ApiModelProperty(value = "课程专业ID")
	private String subjectId;

	@ApiModelProperty(value = "课程专业ID")
	private String subjectParentId;

	@ApiModelProperty(value = "课程标题")
	private String title;

	@ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
	private BigDecimal price;

	@ApiModelProperty(value = "总课时")
	private Integer lessonNum;

	@ApiModelProperty(value = "销售数量")
	private Long buyCount;

	@ApiModelProperty(value = "浏览数量")
	private Long viewCount;

	@ApiModelProperty(value = "课程封面图片路径")
	private String cover;

	@ApiModelProperty(value = "课程简介")
	private String description;

	@ApiModelProperty(value = "课程专业信息")
	private List<SubjectNestedVo> subjectList;

	@ApiModelProperty(value = "讲师信息")
	private Teacher teacher;

	@ApiModelProperty(value = "层级选择器数据")
	private List<String> cascade;

	@ApiModelProperty(value = "层级选择器数据")
	private String videoSourceId;
}
