import request from '@/utils/request'

const frontUrl = '/edu/teacher'

export default {

  //1、讲师列表，条件查询带分页
  pageTeacherQuery(page,limit,teacherVo){
    return request({
      url: `${frontUrl}/pageTeacherQuery/${page}/${limit}`,
      method: 'post',
      data:teacherVo
    })
  },
  //根据Id删除讲师
  removeById(id){
    return request({
      url: `${frontUrl}/${id}`,
      method: 'delete'
    })
  },
  //添加讲师
  addTeacher(teacher){
    return request({
      url: `${frontUrl}/addTeacher`,
      method: 'post',
      data:teacher
    })
  },
  //根据id查询讲师
  findByIdTeacher(id){
    return request({
      url: `${frontUrl}/findByIdTeacher/${id}`,
      method: 'get'
    })
  },
  //修改讲师信息
  updateTeacher(teacher){
    return request({
      url: `${frontUrl}/updateTeacher`,
      method: 'put',
      data:teacher
    })
  },
  //查询所有讲师接口
  getTeacherList(){
    return request({
      url: `${frontUrl}/getTeacherList`,
      method: 'get'
    })
  },
}
