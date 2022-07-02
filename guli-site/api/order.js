import request from '@/utils/request'

const frontUrl = '/order/order'

export default {

  //创建订单信息
  createOrder(courseId){
    return request({
      url: `${frontUrl}/createOrder/${courseId}`,
      method: 'post'
    })
  },
  //得到订单信息
  getOrderInfo(orderId){
    return request({
      url: `${frontUrl}/getOrderInfo/${orderId}`,
      method: 'get'
    })
  },
  //生成支付二维码
  createNative(orderId){
    return request({
      url: `/order/payLog/createNative/${orderId}`,
      method: 'get'
    })
  },
  //查询订单是否支付成功
  queryPayStatus(orderId,courseId){
    return request({
      url: `/order/payLog/queryPayStatus/${orderId}/${courseId}`,
      method: 'get'
    })
  },
  //查询用户是否已经支付过该课程
  getOrderUser(userId,courseId){
    return request({
      url: `${frontUrl}/getOrderUser/${userId}/${courseId}`,
      method: 'get'
    })
  }
}
