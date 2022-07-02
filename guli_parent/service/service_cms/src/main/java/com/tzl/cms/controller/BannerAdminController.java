package com.tzl.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.cms.service.CrmBannerService;
import com.tzl.model.entity.CrmBanner;
import com.tzl.model.vo.BannerVo;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "管理员首页banner表接口")
@RestController
@RequestMapping("/cms/bannerAdmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;


    @ApiOperation("分页查询banner")
    @PostMapping("/pageBanner/{page}/{limit}")
    public Result pageBanner(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit,
            @ApiParam(name = "bannerVo",value = "条件查询实体类") @RequestBody(required = false) BannerVo bannerVo
    ){
        Page<CrmBanner> pageParam = new Page<>(page, limit);

        crmBannerService.pageBanner(pageParam, bannerVo);

        List<CrmBanner> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加banner")
    @PostMapping("/saveBanner")
    public Result saveBanner(@ApiParam(name = "crmBanner",value = "banner信息",required = true) @RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据Id获取Banner")
    @GetMapping("/getBannerById/{bannerId}")
    public Result getBannerById(@ApiParam(name = "bannerId",value = "bannerId",required = true) @PathVariable("bannerId")String bannerId){
        CrmBanner crmBanner = crmBannerService.getById(bannerId);
        return Result.ok().data("crmBanner",crmBanner);
    }

    @ApiOperation("修改banner信息")
    @PutMapping("/updateBanner")
    public Result updateBanner(@ApiParam(name = "crmBanner",value = "banner信息",required = true) @RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.updateById(crmBanner);
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据Id删除Banner")
    @DeleteMapping("/deleteBannerById/{bannerId}")
    public Result deleteBannerById(@ApiParam(name = "bannerId",value = "bannerId",required = true) @PathVariable("bannerId")String bannerId){
        boolean b = crmBannerService.removeById(bannerId);
        if (b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

