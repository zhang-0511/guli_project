<template>
  <div class="app-container" >
    <el-form :model="banner" :rules="rules" ref="banner" label-width="120px"  size="" >
      <el-form-item label="轮播图标题" prop="title" style="width: 400px">
        <el-input v-model="banner.title"/>
      </el-form-item>

      <el-form-item label="轮播图片" prop="imageUrl">
        <el-upload class="avatar-uploader" :action="fileUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <div class="upload-inner-wrapper">
            <img v-if="dialogVisible" :src="banner.imageUrl" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </div>
        </el-upload>
        <input v-model="banner.imageUrl" hidden></input>
      </el-form-item>

      <el-form-item label="链接地址" prop="linkUrl"  style="width: 400px">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>

      <el-form-item label="轮播排序" prop="sort" >
        <el-input-number v-model="banner.sort" controls-position="right" :min="0" style="width: 300px"/>
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm('banner')">重置</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdateBanner('banner')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import banner from "@/api/cms/banner";
export default {
  name:'save',
  data() {
    return {
      banner: {},
      fileUrl: process.env.BASE_API+'/oss/fileOss/coverUpload',
      saveBtnDisabled: false, // 保存按钮是否禁用,
      dialogVisible:false,
      rules:{
        title: [
          { required: true, message: '请输入讲师名称', trigger: 'blur' }
        ],
        imageUrl: [
          { required: true, message: '请上传轮播图', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入讲师排序', trigger: 'blur' },
          { type: 'number', message: '讲师排序为数字值'}
        ],
        linkUrl: [
          { required: true, message: '请输入轮播链接地址', trigger: 'blur' }
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
        this.banner = {}
      }
    },
    handleAvatarSuccess(response, file) {
      if(response.code !== 200) {
        this.$message.error("上传失败")
        return
      }
      // 填充上传文件列表
      this.banner.imageUrl = file.response.data.url
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
      this.dialogVisible = false
    },
    saveOrUpdateBanner(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (this.banner.id){
            this.updateBanner()
          }else {
            this.saveBanner()
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
    getInfo(bannerId){
      this.dialogVisible = true
      banner.getBannerById(bannerId)
        .then(res=>{
          if (res.success === true){
            this.banner = res.data.crmBanner
          }
        }).catch(error=>{this.$message.error('获取轮播图信息失败，请联系管理员');})
    },
    saveBanner(){
      banner.saveBanner(this.banner)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
            setTimeout(() => {
              this.$router.push("/banner/list")
            }, 1000)
          }
        }).catch(error=>{this.$message.error('添加失败，请检查提交的数据是否符合规范');})
    },
    updateBanner(){
      banner.updateBanner(this.banner)
        .then(res=>{
          if (res.success === true){
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            setTimeout(() => {
              this.$router.push("/banner/list")
            }, 1000)
          }
        }).catch(error=>{this.$message.error('修改失败，请检查提交的数据是否符合规范');})
    },
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
  width: 250px;
  height: 120px;
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
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 250px;
  height: 120px;
  display: block;
}
</style>
