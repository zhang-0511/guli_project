<template>
    <div>
      <!-- 定义播放器dom -->
      <div id="videoPlay" class="prism-player"></div>
    </div>
</template>

<script>
import videoApi from "@/api/video";
export default {
  layout: 'video',//应用video布局
  name: "_id", playAuth: undefined,

  asyncData({ params, error }) {
    return videoApi.getPlayAuth(params.id).then(response => {
      return {
        vid: params.id,
        playAuth: response.data.data.playAuth
      }
    }).catch(error=>{this.$message.error(error.message);})
  },
  mounted() {
    new Aliplayer({
      id: 'videoPlay',
      vid: this.vid, // 视频id
      cover: 'https://guli-tzl.oss-cn-shanghai.aliyuncs.com/banner/2022/05/31/7e531e0aee3fac056b88f877ec36748b.jpg', // 封面
      playauth: this.playAuth, // 播放凭证2
      encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
      width: "100%",
      height: "650px",
      qualitySort: 'asc', // 清晰度排序
      mediaType: 'video', // 返回音频还是视频
      autoplay: false, // 自动播放
      isLive: false, // 直播
      rePlay: false, // 循环播放
      preload: true,
      controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
      useH5Prism: true, // 播放器类型：html5
    }, function(player) {
      console.log('播放器创建成功')
    })
  }
}
</script>

<style scoped>

</style>
