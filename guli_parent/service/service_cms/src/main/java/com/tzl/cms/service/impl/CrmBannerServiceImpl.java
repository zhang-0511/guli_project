package com.tzl.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.CrmBanner;
import com.tzl.cms.mapper.CrmBannerMapper;
import com.tzl.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.model.vo.BannerVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-24
 */
@Service
@SuppressWarnings("all")
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, BannerVo banner) {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create","sort");

        if (banner == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = banner.getTitle();
        String begin = banner.getBegin();
        String end = banner.getEnd();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    @Cacheable(value = "banner",key = "'selectIndexList'")
    public List<CrmBanner> getBannerList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort")
                .last("limit 5");
        return this.baseMapper.selectList(wrapper);
    }
}
