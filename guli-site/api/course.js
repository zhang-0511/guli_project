import request from '@/utils/request'

const frontUrl = '/edu/courseFront'

export default {

  //获取首页轮播图数据
  pageCourseFront(page,limit,courseQueryVo){
    return request({
      url: `${frontUrl}/pageCourseFront/${page}/${limit}`,
      method: 'post',
      data: courseQueryVo
    })
  },
  //根据课程id获取课程信息
  getCourseInfoFront(courseId){
    return request({
      url: `${frontUrl}/getCourseInfoFront/${courseId}`,
      method: 'get'
    })
  },

  //获取课程信息二级列表
  getSubjectList(){
    return request({
      url: '/edu/subject/getSubjectList',
      method: 'get',
    })
  },

  updateViewCount(courseId){
    return request({
      url: `/edu/course/updateViewCount/${courseId}`,
      method: 'PUT',
    })
  }
}
