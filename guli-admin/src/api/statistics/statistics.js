import request from '@/utils/request'

const frontUrl = '/statistics/statisticsDaily'

export default {

  countRegister(day){
    return request({
      url: `${frontUrl}/countRegister/${day}`,
      method: 'get'
    })
  },
  showData(type,startTime,endTime){
    return request({
      url: `${frontUrl}/showData/${type}/${startTime}/${endTime}`,
      method: 'get'
    })
  }
}
