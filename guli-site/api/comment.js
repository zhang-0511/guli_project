import request from '@/utils/request'

const frontUrl = '/edu/comment'

export default {

  //获取首页轮播图数据
  getCommentByid(page,limit,courseId){
    return request({
      url: `${frontUrl}/getCommentByid/${page}/${limit}`,
      method: 'get',
      params: {courseId:courseId}
    })
  },
  saveComment(comment){
    return request({
      url: `${frontUrl}/saveComment`,
      method: 'post',
      data:comment
    })
  },
  deleteById(id){
    return request({
      url: `${frontUrl}/deleteById/${id}`,
      method: 'delete'
    })
  }
}
