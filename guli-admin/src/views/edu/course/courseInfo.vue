<template>
  <div class="app-container">
    <el-form label-width="120px" :model="courseInfo" :rules="rules" ref="courseInfo">
      <el-form-item label="课程标题" prop="title">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程分类" prop="cascade">
        <el-cascader @focus="getOneSubject"  :key="keyValue" placeholder="请选择课程分类" filterable v-model="courseInfo.cascade" :props="defaultProps" :options="subjectNestedList" clearable />
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面" prop="cover">
        <el-upload class="avatar-uploader" :action="fileUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <div class="upload-inner-wrapper">
            <img v-if="dialogVisible" :src="courseInfo.cover" class="avatar" alt=""/>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </div>
        </el-upload>

      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师" prop="teacherId">
        <el-select @focus="getTeacherList" @change="changeSelection" class="avatarClass"  v-model="courseInfo.teacherId" placeholder="请选择讲师" ref="refSelect">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id">
            <div class="option_box">
              <el-avatar class="option_img" size="small" :src="teacher.avatar"></el-avatar>
              <span v-text="teacher.name"></span>
            </div>
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时" prop="teacherId">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介" prop="description">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>


      <el-form-item label="课程价格" prop="price">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm('courseInfo')">返回列表页面</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" style="margin-top: 12px;" @click="saveOrUpdate('courseInfo')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/edu/course"
import teacher from "@/api/edu/teacher"
import subject from "@/api/edu/subject"
import Tinymce from '@/components/Tinymce'
export default {
  name: "courseInfo",
  components: { Tinymce },
  data(){
    return{
      saveBtnDisabled:false,
      courseInfo:{},
      keyValue:0,
      fileUrl: process.env.BASE_API+'/oss/fileOss/coverUpload',
      dialogVisible:false,
      dialogImageUrl: '',
      disabled: false,
      subjectNestedList: [],//一级分类列表
      teacherList: [], // 讲师列表
      defaultProps: {
        children: 'children',
        label: 'title',
        value:'id'
      },
      rules:{
        title: [
          { required: true, message: '请输入课程标题', trigger: 'blur' }
        ],
        cascade: [
          { required: true, message: '请选择课程分类', trigger: 'change' }
        ],
        teacherId: [
          { required: true, message: '请选择讲师头衔', trigger: 'change' }
        ],
        lessonNum: [
          { required: true, message: '请输入总课时', trigger: 'change' },
          { type: 'number', message: '总课时为数字值'}
        ],
        description: [
          { required: true, message: '请输入课程简介', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入课程价格', trigger: 'blur' },
          { type: 'number', message: '课程价格为数字值'}
        ]
      }
    }
  },
  methods:{
    init(){
      if(this.$route.params && this.$route.params.id){
        this.getCourseInfo(this.$route.params.id)
      }
    },
    getCourseInfo(courseId){
      course.getCourseInfo(courseId)
        .then(res=>{
          if (res.success === true){
            this.dialogVisible = true
            this.courseInfo = res.data.courseInfo
            this.teacherList[0] = this.courseInfo.teacher
            this.subjectNestedList = this.courseInfo.subjectList
            this.changeSelection(this.teacherList[0].id)
          }
        }).catch(error=>{this.$message.error('获取信息失败，请联系管理员');})
    },
    resetForm(formName) {
      this.$router.push({path:'/course/list'})
    },
    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          this.updateCourse()
        } else {
          this.$message({
            message: '提交失败，请检查填写信息',
            type: 'warning'
          });
          return false;
        }
      });
      //this.$router.push({path:'/course/chapter/1192252213659774977'})
    },
    updateCourse(){
      this.courseInfo.subjectParentId = this.courseInfo.cascade[0]
      this.courseInfo.subjectId = this.courseInfo.cascade[1]
      course.updateCourseInfo(this.courseInfo)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '修改课程信息成功!'
            });
            this.$router.push({path:'/course/list'})
          }
        })
        .catch(error=>{this.$message.error('修改失败，请检查信息是否符合要求并重新提交');})
    },
    //查询所有课程的一级分类
    getOneSubject(){
      subject.getSubjectList()
        .then(res=>{
          if (res.success === true){
            this.subjectNestedList = res.data.list
          }
        })
        .catch(error=>{this.$message.error('获取课程分类失败，请联系管理员');})
    },
    //查询所有的讲师列表
    getTeacherList(){
      teacher.getTeacherList()
        .then(res=>{
          if (res.success === true){
            this.teacherList = res.data.items
          }
        })
        .catch(error=>{this.$message.error('获取讲师列表失败，请联系管理员');})
    },
    handleAvatarSuccess(response, file) {
      if(response.code !== 200) {
        this.$message.error("上传失败")
        return
      }
      // 填充上传文件列表
      this.courseInfo.cover = file.response.data.url
      this.dialogVisible = true;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    // select标签的change事件
    changeSelection(val) {
      let optionsImg = this.teacherList,
        i = optionsImg.findIndex((item) => item.id === val);
      this.$refs["refSelect"]
        .$el.children[0]
        .children[0]
        .setAttribute(
          "style",
          `
          background: url(${optionsImg[i].avatar}) no-repeat;
          background-position: 10px center;
          background-size: 20px 20px;
          text-indent: 30px;
          `
        );
    }
  },
  watch:{
    $route(to, from) { //路由变化的方式。。路由发生变化后，就执行
      this.init()
      this.keyValue++
    }
  },
  created() {
    this.init()
  }
}
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
  <style>
  .option_box {
    display: flex;
    align-items: center;
  }

.option_img {
  width: 25px;
  height: 25px;
  margin-right: 7px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 267px;
  height: 150px;
  display: block;
}
</style>
