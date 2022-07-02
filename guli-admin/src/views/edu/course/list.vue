<template>
  <div class="app-container">
    <div style="width: 100%">
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">

        <el-form-item label="讲师名称：">
          <el-input v-model="course.title" placeholder="课程名称" style="width: 205px" />
        </el-form-item>
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师：">
          <el-select @change="changeSelection" class="avatarClass"  v-model="course.teacherId" placeholder="请选择讲师" ref="refSelect">
            <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id">
              <div class="option_box">
                <el-avatar class="option_img" size="small" :src="teacher.avatar"></el-avatar>
                <span v-text="teacher.name"></span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>

        <!-- 一级分类 -->
        <el-form-item label="课程类别：">
          <el-select v-model="course.subjectParentId" placeholder="请选择" @change="subjectLevelOneChanged" clearable >
            <el-option v-for="subject in subjectNestedList" :key="subject.id" :label="subject.title" :value="subject.id"/>
          </el-select>
          <!-- 二级分类 -->
          <el-select v-model="course.subjectId" placeholder="请选择" clearable>
            <el-option v-for="(subject,index) in subSubjectList" :key="index" :label="subject.title" :value="subject.id"  />
          </el-select>
        </el-form-item>

        <el-form-item label="是否免费：">
          <el-select v-model="course.isFree" placeholder="请选择" clearable>
            <el-option label="免费" value="0">免费</el-option>
            <el-option label="收费" value="1">收费</el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="课程状态：">
          <el-select v-model="course.status" placeholder="请选择" clearable>
            <el-option label="已发布" value="Normal">已发布</el-option>
            <el-option label="未发布" value="Draft">未发布</el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="选择时间：" >
          <el-date-picker style="width: 408px;" v-model="value1" align="right" type="datetimerange" start-placeholder="选择开始时间" end-placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00']"></el-date-picker>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
    </div>


    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="数据加载中" border fit highlight-current-row row-class-name="myClassList">

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="课程信息" width="450" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>

        </template>
      </el-table-column>


      <el-table-column label="讲师信息" align="center" width="180">
        <template slot-scope="scope">
          <div style="margin-left: 20px">
            <el-avatar size="medium" :src="scope.row.avatar" />
            <div style="float: right;margin-right: 40px">
              <span style="line-height: 40px">{{ scope.row.name }}</span>
            </div>
          </div>
        </template>

      </el-table-column>

      <el-table-column label="课程类别名称" width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.subjectLevelOne }}—{{ scope.row.subjectLevelTwo }}
        </template>
      </el-table-column>

      <el-table-column label="价格" width="120" align="center" >
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>

      <el-table-column prop="buyCount" label="付费学员" width="120" align="center" >
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人
        </template>
      </el-table-column>

      <el-table-column label="课程状态" width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column label="创建时间" width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center" fixed="right">
        <template slot-scope="scope">
          <router-link :to="'/course/courseInfo/'+scope.row.id">
            <el-link type="primary" style="font-size: 7px" icon="el-icon-edit">编辑课程信息</el-link>
          </router-link>
          <router-link :to="'/course/chapterInfo/'+scope.row.id">
            <el-link type="primary" style="font-size: 7px" icon="el-icon-edit">编辑课程大纲</el-link>
          </router-link>
          <el-link type="danger" style="font-size: 7px" icon="el-icon-delete-solid" @click="removeDataById(scope.row.id)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" :hide-on-single-page="false"
                   style="padding: 30px 0; text-align: center;" :page-sizes="[5, 10, 50, 100]"
                   layout="total, sizes, prev, pager, next, jumper"  @size-change="handleSizeChange" @current-change="fetchData"/>
  </div>
</template>

<script>
import course from '@/api/edu/course'
import teacher from "@/api/edu/teacher";
import subject from "@/api/edu/subject";
export default {
  name: 'list',
  data() {
    return {
      subjectNestedList: [],//一级分类列表
      subSubjectList: [],//二级分类列表
      teacherList:{},
      value1:'',
      listLoading: true, // 是否显示loading信息
      page:1,  //当前页
      limit:5, //每页显示记录数
      total:0, //总记录数
      course:{
        subjectId: '',//二级分类id
        subjectParentId:'',//一级分类id
        teacherId: '',
      },  //条件查询参数
      list:[]  //查询出结果
    }
  },
  methods: {
    fetchData(page){
      if (this.value1 !== undefined && this.value1.length > 0){
        this.course.begin = this.value1[0]
        this.course.end = this.value1[1]
      }
     this.getList(page,this.limit)
    },
    handleSizeChange(limit){
      this.getList(this.page,limit)
    },
    resetData() {
      this.value1 = ''
      this.course = {}
      if (this.$refs["refSelect"]) {
        this.$refs["refSelect"].$el.children[0].children[0].setAttribute("style", ``);
      }
      this.fetchData()
    },
    getList(page = 1,limit = 5){
      this.page = page
      this.limit = limit
      this.listLoading = true
      course.pageCourse(this.page,this.limit,this.course)
        .then(res=>{
          if (res.success === true){
            this.list = res.data.rows
            this.total = res.data.total
          }
          this.listLoading = false
        }).catch(error=>{this.$message.error('课程列表加载失败，请联系管理员');})
    },
    removeDataById(courseId){
      this.$confirm('此操作将永久删除课程的所有信息，包括课程章节课程小节和视频, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        course.removeCourse(courseId).then(res=>{
          if (res.success === true ){
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.fetchData(this.page)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
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
    subjectLevelOneChanged(value) {
      this.course.subjectId = ''
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === value) {
          this.subSubjectList = this.subjectNestedList[i].children

        }
      }
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
  created() {
    this.getList()
    this.getTeacherList()
    this.getOneSubject()
  }
}
</script>

<style scoped>
.myClassList .info {
  width: 450px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}
.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.myClassList .info .pic img {
  display: block;
  width: 100%;
}
.myClassList td .info .title {
  width: 300px;
  float: right;
  height: 90px;
}
.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 60px;
  overflow: hidden;
  color: #00baf2;
}
.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
</style>
<style lang="css"> .el-tooltip__popper{ max-width:50% } </style>
