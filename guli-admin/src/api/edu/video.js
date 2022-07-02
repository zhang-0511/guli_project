import request from '@/utils/request'

const frontUrl = '/edu/video'

export default {
  getVideoInfo(videoId){
    return request({
      url: `${frontUrl}/getVideoInfo/${videoId}`,
      method: 'get'
    })
  },
  saveVideo(video){
    return request({
      url: `${frontUrl}/saveVideo`,
      method: 'post',
      data:video
    })
  },
  updateVideo(video){
    return request({
      url: `${frontUrl}/updateVideo`,
      method: 'put',
      data:video
    })
  },
  //删除小节
  deleteVideo(videoId){
    return request({
      url: `${frontUrl}/deleteVideo/${videoId}`,
      method: 'delete'
    })
  },
  //删除阿里云上传的视频
  deleteAliyunVideo(videoSourceId){
    return request({
      url: `/vod/video/deleteAliyunVideo/${videoSourceId}`,
      method: 'delete'
    })
  }
}
