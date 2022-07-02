import request from '@/utils/request'

const frontUrl = '/edu/chapter'

export default {
  //1、根据课程信息ID获取课程章节和课程小节
  getChapterVideo(courseId){
    return request({
      url: `${frontUrl}/getChapterVideo/${courseId}`,
      method: 'get',
    })
  },
  getChapterInfo(chapterId){
    return request({
      url: `${frontUrl}/getChapterInfo/${chapterId}`,
      method: 'get'
    })
  },
  saveChapter(chapter){
    return request({
      url: `${frontUrl}/saveChapter`,
      method: 'post',
      data:chapter
    })
  },
  updateChapter(chapter){
    return request({
      url: `${frontUrl}/updateChapter`,
      method: 'put',
      data:chapter
    })
  },
  deleteChapter(chapterId){
    return request({
      url: `${frontUrl}/deleteChapter/${chapterId}`,
      method: 'delete'
    })
  }
}
