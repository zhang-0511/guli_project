package com.tzl.edu.controller.front;

import com.tzl.edu.service.IndexService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: guli_parent
 * @description: 前台数据
 * @author: zl
 * @create: 2022-05-25 19:45
 **/
@Slf4j
@Api(tags = "前台index页面接口")
@RestController
@RequestMapping("/edu/indexFront")
public class IndexFrontController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/index")
    @ApiOperation("获取首页的前八名热门课程和前四名热门讲师接口")
    public Result index(){
        Map<String,Object> map = indexService.indexList();
        return Result.ok().data(map);
    }
}
