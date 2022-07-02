<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps align-center process-status="wait" style="margin-bottom: 40px"  :active="2" finish-status="finish">
      <el-step :title="title1" icon="el-icon-edit" description="主要填写课程的基本信息和表述信息"></el-step>
      <el-step :title="title2" icon="el-icon-edit-outline" description="填写课程的基本大纲要领"></el-step>
      <el-step :title="title3" icon="el-icon-upload" description="课程提交给管理员审核"></el-step>
    </el-steps>

    <el-form label-width="120px">
      <el-button type="primary" plain @click="dialogChapterFormVisible = true">添加章节</el-button>
      <!-- 添加和修改章节表单 -->
      <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
          <el-form-item label="章节标题">
            <el-input v-model="chapter.title"/>
          </el-form-item>
          <el-form-item label="章节排序">
            <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogChapterFormVisible = false; chapter={}">取 消</el-button>
          <el-button type="primary" @click="saveOrUpdateChapter">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 添加和修改课时表单 -->
      <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
        <el-form :model="video" label-width="120px">
          <el-form-item label="课时标题">
            <el-input v-model="video.title"/>
          </el-form-item>
          <el-form-item label="课时排序">
            <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
          </el-form-item>
          <el-form-item label="是否免费">
            <el-radio-group v-model="video.free">
              <el-radio  :label="true">免费</el-radio>
              <el-radio :label="false">默认</el-radio>
            </el-radio-group>
          </el-form-item>
          <!-- TODO -->
          <el-form-item label="上传视频">
            <el-upload :on-success="handleVodUploadSuccess" :on-remove="handleVodRemove" :before-remove="beforeVodRemove"
              :on-exceed="handleUploadExceed" :file-list="fileList" :action="actionUrl"
              :limit="1" class="upload-demo">
              <el-button size="small" type="primary">上传视频</el-button>
              <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                  支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                  GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                  MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                  SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question"/>
              </el-tooltip>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVideoFormVisible = false; video={}">取 消</el-button>
          <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 章节 -->
      <ul class="chanpterList">
        <li v-for="chapter in chapterNestedList" :key="chapter.id">
          <p>
            {{ chapter.title }}
            <span class="acts">
                <el-link type="primary" icon="el-icon-plus" @click="dialogVideoFormVisible = true; fileList=[]; chapterId = chapter.id">添加课时</el-link>
                <el-link type="primary" icon="el-icon-edit" @click="editChapter(chapter.id)">编辑</el-link>
                <el-link type="danger" icon="el-icon-delete-solid" @click="removeChapter(chapter.id)">删除</el-link>
            </span>
          </p>
          <!-- 视频 -->
          <ul class="chanpterList videoList">
            <li v-for="video in chapter.children" :key="video.id">
              <p>{{ video.title }}
                <span class="acts">
                  <el-link type="primary" icon="el-icon-edit" @click="editVideo(video.id)">编辑</el-link>
                  <el-link type="danger" icon="el-icon-delete-solid" @click="removeVideo(video.id)">删除</el-link>
                </span>
              </p>
            </li>
          </ul>
        </li>
      </ul>

      <div>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" style="margin-bottom: 20px" @click="next">保存并下一步</el-button>
      </div>
    </el-form>

  </div>
</template>

<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
  name: "chapter",
  data(){
    return{
      title1:'填写课程基本信息',
      title2:'创建课程大纲',
      title3:'最终发布',
      saveBtnDisabled:false,
      courseId:'',
      chapterNestedList:[],
      dialogChapterFormVisible:false,
      dialogVideoFormVisible:false,
      saveVideoBtnDisabled:false,
      chapter:{},
      video:{},
      chapterId:'',
      fileList: [],//上传文件列表
      actionUrl: process.env.BASE_API+'/vod/video/uploadAliyunVideo'// 接口API地址
    }
  },
  methods:{
    /////////////////////////////////////////章节操作/////////////////////////////////////////////////////////////////
    getChapterVideo(){
      chapter.getChapterVideo(this.courseId)
        .then(res=>{
          if(res.success === true){
            this.chapterNestedList = res.data.list
          }
        })
        .catch(error=>{this.$message.error('加载失败，请联系管理员');})
    },
    saveOrUpdateChapter(){
      if (this.chapter.id){
        this.updateChapter()
      }else {
        this.saveChapter()
      }
    },
    saveChapter(){
      this.chapter.courseId = this.courseId
      chapter.saveChapter(this.chapter)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            this.helpSave()
          }
        }).catch(error=>{this.$message.error('提交课程章节信息失败，请检查信息是否符合要求并重新提交');})
    },
    helpSave(){
      this.dialogChapterFormVisible = false// 如果保存成功则关闭对话框
      this.getChapterVideo()// 刷新列表
      this.chapter.title = ''// 重置章节标题
      this.chapter.sort = 0// 重置章节标题
      this.saveBtnDisabled = false
    },
    updateChapter(){
      this.chapter.courseId = this.courseId
      chapter.updateChapter(this.chapter)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            this.helpSave()
          }
        }).catch(error=>{this.$message.error('提交课程章节信息失败，请检查信息是否符合要求并重新提交');})
    },
    editChapter(chapterId){
      this.dialogChapterFormVisible = true
      chapter.getChapterInfo(chapterId)
        .then(res=>{
          if (res.success === true){
            this.chapter = res.data.chapter
          }
        }).catch(error=>{this.$message.error('获取课程章节信息失败，请联系管理员');})
    },
    removeChapter(chapterId){
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        chapter.deleteChapter(chapterId)
          .then(res=>{
            if (res.success === true){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.helpSave()
            }
          }).catch(error=>{this.$message.error('删除课程章节信息失败，请联系管理员');})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    /////////////////////////////////////////小节操作/////////////////////////////////////////////////////////////////
    saveOrUpdateVideo(){
      if (this.video.id){
        this.updateVideo()
      }else {
        this.saveVideo()
      }
    },
    editVideo(videoId){
      this.dialogVideoFormVisible = true
      video.getVideoInfo(videoId)
        .then(res=>{
          if (res.success === true){
            this.video = res.data.videoInfoVo
            this.fileList = [{'name': this.video.videoOriginalName}]
          }
        }).catch(error=>{this.$message.error('获取课程小节信息失败，请联系管理员');})
    },
    removeVideo(videoId){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        video.deleteVideo(videoId).then(res=>{
            if (res.success === true){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.helpSaveVideo()
            }
        }).catch(error=>{this.$message.error('删除课程小节失败，请联系管理员');})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    updateVideo(){
      this.video.courseId = this.courseId
      video.updateVideo(this.video).then(res=>{
        if (res.success === true){
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          this.helpSaveVideo()
        }
      }).catch(error=>{this.$message.error('提交课程小节信息失败，请检查信息是否符合要求并重新提交');})
    },
    saveVideo(){
     this.video.courseId = this.courseId
      this.video.chapterId = this.chapterId
      video.saveVideo(this.video)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            this.helpSaveVideo()
          }
        }).catch(error=>{this.$message.error('提交课程小节信息失败，请检查信息是否符合要求并重新提交');})
    },
    //成功回调
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.videoId
      this.video.videoOriginalName = file.name
    },
//视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },
    handleVodRemove(file, fileList){
      let videoSourceId = file.response.data.videoId
      video.deleteAliyunVideo(videoSourceId).then(res=>{
        if (res.success === true){
          this.$message({
            message: '移除视频成功',
            type: 'success'
          });
          this.fileList = []
          this.video.videoSourceId = ''
          this.video.videoOriginalName = ''
        }
      }).catch(error=>{this.$message.error('删除视频失败，请刷新页面重试');})
    },
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    next() {
      //this.$router.push({path:'/course/publish/1525799184847101954'})
      this.$router.push({path:'/course/publish/'+this.courseId})
    },
    previous(){
      this.$router.push({path:'/course/info/'+this.courseId})
    },
    init(){
      this.title1='已完成'
      this.title2='进行中'
      if(this.$route.params && this.$route.params.id){
        this.courseId = this.$route.params.id
        this.getChapterVideo()
      }else {
        this.list = []
      }
    },
    helpSaveVideo() {
      this.dialogVideoFormVisible = false// 如果保存成功则关闭对话框
      this.getChapterVideo()// 刷新列表
      this.video.title = ''// 重置章节标题
      this.video.sort = 0// 重置章节标题
      this.video.videoSourceId = ''// 重置视频资源id
      this.video.videoOriginalName = ''// 重置视频资源id
      this.saveVideoBtnDisabled = false
    }
  },
  watch:{
    $route(to, from) { //路由变化的方式。。路由发生变化后，就执行
      this.init()
    }
  },
  created() {
    this.init()
  }
}
</script>

<style scoped>
.chanpterList{
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>
