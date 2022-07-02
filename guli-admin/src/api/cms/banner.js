import request from '@/utils/request'

const frontUrl = '/cms/bannerAdmin'

export default {

  //分页查询banner
  pageBanner(page,limit,bannerVo){
    return request({
      url: `${frontUrl}/pageBanner/${page}/${limit}`,
      method: 'post',
      data:bannerVo
    })
  },
  //添加banner
  saveBanner(crmBanner){
    return request({
      url: `${frontUrl}/saveBanner`,
      method: 'post',
      data:crmBanner
    })
  },
  //根据id获取banner
  getBannerById(bannerId){
    return request({
      url: `${frontUrl}/getBannerById/${bannerId}`,
      method: 'get',
    })
  },
  //修改banner信息
  updateBanner(crmBanner){
    return request({
      url: `${frontUrl}/updateBanner`,
      method: 'put',
      data:crmBanner
    })
  },
  //根据Id删除Banner
  deleteBannerById(bannerId){
    return request({
      url: `${frontUrl}/deleteBannerById/${bannerId}`,
      method: 'delete',
    })
  }
}
