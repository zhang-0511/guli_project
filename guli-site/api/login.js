import request from '@/utils/request'


export default {

  //登录
  login(loginVo){
    return request({
      url: `/user/ucenterMember/login`,
      method: 'post',
      data:loginVo
    })
  },
  //根据token获取用户信息
  getUserInfo(){
    return request({
      url: `/user/ucenterMember/getUserInfo`,
      method: 'get'
    })
  },
}
