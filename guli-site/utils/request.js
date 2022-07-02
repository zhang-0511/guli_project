import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://127.0.0.1:8008', // api 的 base_url
  timeout: 5000 // 请求超时时间
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    //debugger
    if (cookie.get('guli_token')) {
      config.headers['token'] = cookie.get('guli_token');
    }

    return config
  },
  err => {
    return Promise.reject(err);
  })
// http response 拦截器
service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code === 224) {
      // 返回 错误代码-1 清除ticket信息并跳转到登录页面
      cookie.remove('guli_token')
      cookie.remove('guli_user')
      window.location.href="/login"
      Message({
        message: response.data.message,
        type: 'error',
        duration: 5 * 1000
      })
    }else{
      if (response.data.code !== 200) {
        //25000：订单支付中，不做任何提示
        if(response.data.code !== 222) {
          Message({
            message: response.data.message || 'error',
            type: 'error',
            duration: 5 * 1000
          })
        }
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error)   // 返回接口返回的错误信息
  });


export default service
