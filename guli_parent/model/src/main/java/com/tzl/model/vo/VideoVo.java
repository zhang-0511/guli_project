package com.tzl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "课时信息Vo层",description = "课时信息的对象封装")
public class VideoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课时id")
	private String id;

	@ApiModelProperty(value = "课时名称")
	private String title;

	@ApiModelProperty(value = "课时是否免费")
	private Boolean free;

	@ApiModelProperty(value = "云端视频资源")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	private String videoOriginalName;
}
