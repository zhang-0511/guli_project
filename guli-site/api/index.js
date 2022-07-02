import request from '@/utils/request'


export default {

  //获取首页轮播图数据
  getBannerList(){
    return request({
      url: `/cms/bannerFront/getBannerList`,
      method: 'get'
    })
  },
  getIndexList(){
    return request({
      url: `/edu/indexFront/index`,
      method: 'get'
    })
  }
}
