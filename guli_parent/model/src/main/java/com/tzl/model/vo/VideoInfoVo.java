package com.tzl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课时基本信息", description = "编辑课时基本信息的表单对象")
@Data
public class VideoInfoVo {

	@ApiModelProperty(value = "视频ID")
	private String id;

	@ApiModelProperty(value = "节点名称")
	private String title;

	@ApiModelProperty(value = "课程ID")
	private String courseId;

	@ApiModelProperty(value = "章节ID")
	private String chapterId;

	@ApiModelProperty(value = "云端视频资源")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	private String videoOriginalName;

	@ApiModelProperty(value = "显示排序")
	private Integer sort;

	@ApiModelProperty(value = "是否可以试听：0默认 1免费")
	private Boolean free;
}
