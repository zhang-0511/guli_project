import request from '@/utils/request'

const frontUrl = '/cms/bannerFront'

export default {

  //获取首页轮播图数据
  getBannerList(){
    return request({
      url: `${frontUrl}/getBannerList`,
      method: 'get'
    })
  },
  getIndexList(){
    return request({
      url: `/edu/indexFront/index`,
      method: 'get'
    })
  },
}
