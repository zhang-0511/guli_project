import request from '@/utils/request'

const frontUrl = '/edu/subject'

export default {

  //1、讲师列表，条件查询带分页
  getSubjectList(){
    return request({
      async:false,
      url: `${frontUrl}/getSubjectList`,
      method: 'get'
    })
  },
}
