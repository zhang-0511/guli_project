import request from '@/utils/request'

const frontUrl = '/edu/teacherFront'

export default {

  //获取讲师分页列表
  pageTeacherFront(page,limit){
    return request({
      url: `${frontUrl}/pageTeacherFront/${page}/${limit}`,
      method: 'get'
    })
  },
  //根据讲师id查询讲师
  findByIdTeacher(id){
    return request({
      url: `${frontUrl}/getTeacherInfoById/${id}`,
      method: 'get'
    })
  }
}
