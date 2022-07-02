import request from '@/utils/request'

const frontUrl = '/edu/course'

export default {
  //1、添加课程信息
  saveCourseInfo(courseInfoVo){
    return request({
      url: `${frontUrl}/saveCourseInfo`,
      method: 'post',
      data:courseInfoVo
    })
  },
  //根据id获取课程信息
  getCourseInfo(courseId){
    return request({
      url: `${frontUrl}/getCourseInfo/${courseId}`,
      method: 'get'
    })
  },
  //根据id修改课程信息
  updateCourseInfo(courseInfoVo){
    return request({
      url: `${frontUrl}/updateCourseInfo`,
      method: 'put',
      data:courseInfoVo
    })
  },
  //根据ID获取课程发布信息
  getCoursePublishVoById(courseId){
    return request({
      url: `${frontUrl}/getCoursePublish/${courseId}`,
      method: 'get'
    })
  },
  //最终发布课程
  coursePublish(courseId){
    return request({
      url: `${frontUrl}/coursePublish/${courseId}`,
      method: 'put'
    })
  },
  //分页查询课程数据
  pageCourse(page,limit,course){
    return request({
      url: `${frontUrl}/pageCourse/${page}/${limit}`,
      method: 'post',
      data:course
    })
  },
  removeCourse(courseId){
    return request({
      url: `${frontUrl}/deleteCourseById/${courseId}`,
      method: 'delete'
    })
  }
}
