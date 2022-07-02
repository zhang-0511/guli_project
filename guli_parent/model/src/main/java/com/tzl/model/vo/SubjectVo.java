package com.tzl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "Subject查询对象", description = "课程信息对象封装")
public class SubjectVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	private String id;

	@ApiModelProperty(value = "课程名称")
	private String title;
}
