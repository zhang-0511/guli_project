package com.tzl.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.BannerVo;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-24
 */
public interface CrmBannerService extends IService<CrmBanner> {

    public void pageBanner(Page<CrmBanner> pageParam, BannerVo banner);

    public List<CrmBanner> getBannerList();
}
