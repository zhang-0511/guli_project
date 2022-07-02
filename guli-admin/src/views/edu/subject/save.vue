<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item  label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/subject.xls'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload class="upload-demo" ref="upload" :auto-upload="true" :on-success="fileUploadSuccess" drag
                   :on-error="fileUploadError" accept="application/vnd.ms-excel"
                   :action="fileUrl" multiple name="file">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500m</div>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {
  name:'save',
  data() {
    return {
      fileUrl:process.env.BASE_API+'/edu/subject/addSubject'
    }
  },
  methods: {
    fileUploadSuccess(response) {
      if (response.success === true){
        this.$message({
          type: 'success',
          message: '添加课程分类成功!'
        });
         setTimeout(() => {
           this.$router.push("/subject/list")
        }, 1000)
      }else {
        this.$message.error('上传失败，请检查Excel表格是否符合规范，再刷新页面重新上传');
      }
    },
    fileUploadError(response) {
      if (response.success === false){
        this.$message.error('上传失败，请检查Excel表格是否符合规范，再重新上传');
      }
    }
  },
  watch: { //监听

  },
  created() {

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
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
