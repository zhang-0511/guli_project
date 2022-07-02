<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" :model="searchObj" :rules="rules" ref="ruleForm" class="demo-form-inline">

      <el-form-item label="请选择统计对象：" prop="type">
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num"/>
          <el-option label="学员注册数统计" value="register_num"/>
          <el-option label="课程播放数统计" value="video_view_num"/>
          <el-option label="每日课程数统计" value="course_num"/>
          <el-option label="每日课程购买量" value="buy_count"/>
          <el-option label="每日课程浏览量" value="view_count"/>
        </el-select>
      </el-form-item>

      <el-form-item label="选择统计日期：" prop="value2">
        <el-date-picker v-model="searchObj.value2" type="daterange" align="right" value-format="yyyy-MM-dd"
            unlink-panels range-separator="至" style="width: 400px" start-placeholder="开始日期"
            end-placeholder="结束日期" :picker-options="pickerOptions" />

      </el-form-item>

      <el-form-item>
        <el-button :disabled="btnDisabled" type="primary" icon="el-icon-search" @click="showChart('ruleForm')">查询</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>

    </el-form>

    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import statisticsApi from "@/api/statistics/statistics";
export default {
  name: "show",
  data(){
    return{

      btnDisabled: false,
      searchObj:{
        type: '',
        begin: '',
        end: '',
        value2:''
      },
      title: '',
      xData: [],
      yData: [],
      rules: {
        type: [
          { required: true, message: '请选择统计对象', trigger: 'change' }
        ],
        begin: [
          { required: true, message: '请选择统计的开始日期', trigger: 'change' },
          { validator: this.checkday, trigger: 'change'}
        ],
        end: [
          {required: true, message: '请选择统计的截至日期', trigger: 'change'},
          { validator: this.checkday, trigger: 'change'}
        ],
        value2: [
          {required: true, message: '请选择统计的开始结束日期', trigger: 'change'},
          { validator: this.checkday, trigger: 'change'}
        ],
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      }
    }
  },
  methods:{
    showChart(formName) {
      this.searchObj.begin = this.searchObj.value2[0]
      this.searchObj.end = this.searchObj.value2[1]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.initChartData()
          this.setChart()
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    checkday(rule, value, callback){
      const dateFormat =/^(\d{4})-(\d{2})-(\d{2})$/;
      let begin = value[1]
      let end = value[1]
      if(dateFormat.test(begin) && dateFormat.test(end)){
        //true，是yyyy-MM-dd格式
        return callback()
      }else{
        //false,不是yyyy-MM-dd格式
        return callback(new Error('日期格式不正确'))
      }
    },
    // 准备图表数据
    initChartData() {
      statisticsApi.showData(this.searchObj.type,this.searchObj.begin,this.searchObj.end).then(res=>{
        if (res.success === true){
          // 数据
          this.yData = res.data.numList
          // 横轴时间
          this.xData = res.data.dateList

          // 当前统计类别
          switch (this.searchObj.type) {
            case 'register_num':
              this.title = '学员注册数统计'
              break
            case 'login_num':
              this.title = '学员登录数统计'
              break
            case 'video_view_num':
              this.title = '课程播放数统计'
              break
            case 'course_num':
              this.title = '每日课程数统计'
              break
            case 'buy_count':
              this.title = '每日课程购买量'
              break
            case 'view_count':
              this.title = '每日课程浏览量'
              break
          }

          this.setChart()
        }
      }).catch(error=>{this.$message.error(error.message);})
    },

    setChart(){
      // 基于准备好的dom，初始化echarts实例
      const chartDom = document.getElementById('chart');
      const myChart = echarts.init(chartDom);
      let option;

      // 指定图表的配置项和数据
      option = {
        title: {
          text: this.title
        },
        tooltip: {
          trigger: 'axis'
        },
        // x轴是类目轴（离散数据）,必须通过data设置类目数据
        xAxis: {
          type: 'category',
          data: this.xData
        },
        // y轴是数据轴（连续数据）
        yAxis: {
          type: 'value'
        },
        // 系列列表。每个系列通过 type 决定自己的图表类型
        series: [{
          // 系列中的数据内容数组
          data: this.yData,
          // 折线图
          type: 'line'
        }],
        dataZoom: [{
          show: true,
          height: 30,
          xAxisIndex: [
            0
          ],
          bottom: 30,
          start: 10,
          end: 80,
          handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
          handleSize: '110%',
          handleStyle: {
            color: '#d3dee5'

          },
          textStyle: {
            color: '#fff'
          },
          borderColor: '#90979c'
        },
          {
            type: 'inside',
            show: true,
            height: 15,
            start: 1,
            end: 35
          }]
      };

      option && myChart.setOption(option);
    }
  },
  created() {

  }
}
</script>

<style scoped>

</style>
