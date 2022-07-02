<template>
  <div class="app-container">
    <!--表单-->
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  :inline="true" class="demo-form-inline">
      <el-form-item label="统计日期：" prop="day">
        <el-date-picker v-model="ruleForm.day" align="left" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" :picker-options="pickerOptions" />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="btnDisabled" type="primary" @click="create('ruleForm')">生成</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import statisticsApi from "@/api/statistics/statistics";
export default {
  name: "create",
  data(){
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      ruleForm:{day: ''},
      btnDisabled:false,
      rules: {
        day: [
          {required: true, message: '请输入统计日期', trigger: 'blur'},
          { validator: this.checkday, trigger: 'blur'}
        ],
      }
    }
  },
  methods:{
    create(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          statisticsApi.countRegister(this.ruleForm.day).then(res=>{
            if (res.success === true){
              this.$message({
                message: '统计数据添加成功，统计日期为：'+this.ruleForm.day,
                type: 'success'
              });
              this.$router.push("/statistics/show")
            }
          }).catch(error=>{this.$message.error(error.message);})
        } else {
          return false;
        }
      });

    },
    checkday(rule, value, callback){
        const dateFormat =/^(\d{4})-(\d{2})-(\d{2})$/;
      if(dateFormat.test(value)){
        //true，是yyyy-MM-dd格式
        return callback()
      }else{
        //false,不是yyyy-MM-dd格式
        return callback(new Error('日期格式不正确'))
      }
    }
  },
  created() {

  }
}
</script>

<style scoped>

</style>
