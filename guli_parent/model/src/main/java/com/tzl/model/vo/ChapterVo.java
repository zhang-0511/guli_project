package com.tzl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Accessors(chain = true)
@ApiModel(value = "章节信息Vo层",description = "章节信息的对象封装")
public class ChapterVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "章节id")
	private String id;

	@ApiModelProperty(value = "章节名称")
	private String title;

	private List<VideoVo> children = new ArrayList<>();
}
