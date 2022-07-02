<template>
  <div class="app-container" >
    <el-form :model="teacher" :rules="rules" ref="teacher" label-width="120px"  size="" >
      <el-form-item label="讲师名称" prop="name">
        <el-input v-model="teacher.name" style="width: 300px;"/>
      </el-form-item>
      <!-- 讲师头像： -->
      <el-form-item label="讲师头像" prop="avatar">
        <el-upload class="avatar-uploader" :action="fileUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <div class="upload-inner-wrapper">
            <img v-if="dialogVisible" :src="teacher.avatar" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </div>
        </el-upload>
      </el-form-item>

      <el-form-item label="讲师排序" prop="sort">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0" style="width: 300px"/>
      </el-form-item>
      <el-form-item label="讲师头衔" prop="level">
        <el-select v-model="teacher.level" clearable placeholder="请选择" style="width: 300px">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历" prop="career">
        <el-input v-model="teacher.career" style="width: 300px"/>
      </el-form-item>
      <el-form-item label="讲师简介" prop="intro">
        <el-input v-model="teacher.intro" :rows="5" type="textarea" style="width: 300px"/>
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm('teacher')">重置</el-button>
        <el-button @click="setCokkie">设置token</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('teacher')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import cookie from 'js-cookie'
export default {
  name:'save',
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: '',
        career: '',
        intro: '',
        avatar: ''
      },
      fileUrl: process.env.BASE_API+'/oss/fileOss/avatarUpload',
      saveBtnDisabled: false, // 保存按钮是否禁用,
      dialogVisible:false,
      rules:{
        name: [
          { required: true, message: '请输入讲师名称', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入讲师排序', trigger: 'blur' },
          { type: 'number', message: '讲师排序为数字值'}
        ],
        level: [
          { required: true, message: '请选择讲师头衔', trigger: 'change' }
        ],
        career: [
          { required: true, message: '请输入讲师资历', trigger: 'blur' }
        ],
        intro: [
          { required: true, message: '请输入讲师简介', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(){
      if(this.$route.params && this.$route.params.id){
        this.dialogVisible = true
        this.getInfo(this.$route.params.id)
      }else {
        this.dialogVisible = false
        this.teacher = {}
      }
    },
    handleAvatarSuccess(response, file) {
      if(response.code !== 200) {
        this.$message.error("上传失败")
        return
      }
      // 填充上传文件列表
      this.teacher.avatar = file.response.data.url
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if(this.teacher.id){
            this.updateTeacher()
          }else {
            this.saveData()
          }
        } else {
          this.$message({
            message: '提交失败，请检查填写信息',
            type: 'warning'
          });
          return false;
        }
      });

    },
    // 保存
    saveData() {
      teacherApi.addTeacher(this.teacher)
        .then(res =>{
          if (res.success === true) {
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
            setTimeout(() => {
              this.$router.push("/teacher/list")
            }, 1000)
          }
        })
        .catch(error=>{this.$message.error('添加失败！');this.saveBtnDisabled = false})
    },
    getInfo(id){
      teacherApi.findByIdTeacher(id)
        .then(res =>{
          if(res.success === true){
            this.teacher = res.data.teacher
          }
        })
        .catch(error=>{this.$message.error('加载失败，请联系管理员');})
    },
    updateTeacher(){
      teacherApi.updateTeacher(this.teacher)
        .then(res =>{
          if (res.success === true) {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            setTimeout(() => {
              this.$router.push("/teacher/list")
            }, 1000)
          }
        })
        .catch(error=>{this.$message.error('修改失败！');this.saveBtnDisabled = false})
    },
    setCokkie(){
      cookie.set('name',JSON.stringify('token'),{domain:'localhost'})
    }
  },
  watch: { //监听
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

</style>
<style>
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
  width: 100px;
  height: 100px;
  display: block;
}
</style>
