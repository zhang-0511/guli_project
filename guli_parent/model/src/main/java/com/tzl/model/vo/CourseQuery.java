package com.tzl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程名称")
	private String title;

	@ApiModelProperty(value = "讲师id")
	private String teacherId;

	@ApiModelProperty(value = "一级类别id")
	private String subjectParentId;

	@ApiModelProperty(value = "二级类别id")
	private String subjectId;

	@ApiModelProperty(value = "是否免费")
	private Integer isFree;

	@ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
	private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

	@ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
	private String end;

	@ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
	private String status;

}
