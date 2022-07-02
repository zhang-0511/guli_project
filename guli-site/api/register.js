import request from '@/utils/request'

const frontUrl = '/cms/bannerFront'

export default {

  //发送手机验证码
  sendCode(phone){
    return request({
      url: `/msm/send/sendCode/${phone}`,
      method: 'get'
    })
  },

  //用户注册
  register(registerVo){
    return request({
      url: `/user/ucenterMember/register`,
      method: 'post',
      data:registerVo
    })
  },
  getLoginParam(){
    return request({
      url: `/user/wxLogin/getLoginParam`,
      method: 'get'
    })
  },
}
