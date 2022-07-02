import request from '@/utils/request'

const frontUrl = '/vod/video'

export default {

  //播放视频
  getPlayAuth(videoSourceId){
    return request({
      url: `${frontUrl}/getPlayAuth/${videoSourceId}`,
      method: 'get'
    })
  },
  updatePlayCount(videoSourceId){
    return request({
      url: `/edu/video/updatePlayCount/${videoSourceId}`,
      method: 'put'
    })
  }
}
