package com.tzl.cms.controller;


import com.tzl.cms.service.CrmBannerService;
import com.tzl.model.entity.CrmBanner;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-24
 */
@Slf4j
@Api(tags = "普通用户首页banner表接口")
@RestController
@RequestMapping("/cms/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("查询所有的轮播图信息")
    @GetMapping("/getBannerList")
    public Result getBannerList(){
        List<CrmBanner> list = crmBannerService.getBannerList();
        return Result.ok().data("list",list);
    }




}

