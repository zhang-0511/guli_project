<template>
  <div class="app-container">
    <div style="margin: 3vh auto 0;text-align:center;width: 100%">
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="讲师名：">
          <el-input v-model="teacherVo.name" placeholder="讲师名"/>
        </el-form-item>

        <el-form-item label="讲师头衔：">
          <el-select v-model="teacherVo.level" clearable placeholder="讲师头衔">
            <el-option :value="1" label="高级讲师"/>
            <el-option :value="2" label="首席讲师"/>
          </el-select>
        </el-form-item>

        <el-form-item label="选择时间：" >
<!--          <el-date-picker v-model="teacherVo.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>
          <el-date-picker v-model="teacherVo.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>-->
          <el-date-picker v-model="value1" align="right" type="datetimerange" start-placeholder="选择开始时间" end-placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00']"></el-date-picker>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
    </div>


    <el-table v-loading="listLoading" :data="list" element-loading-text="数据加载中" fit highlight-current-row style="width: 100%;">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="120" align="center"></el-table-column>

      <el-table-column prop="avatar" label="头像" width="120" align="center">
        <template slot-scope="scope">
          <el-avatar size="large" :src="scope.row.avatar"></el-avatar>
        </template>

      </el-table-column>

      <el-table-column label="头衔" width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="career" label="讲师资历" width="200" align="center"></el-table-column>

      <el-table-column prop="intro" label="讲师简介" width="500"  align="center" show-overflow-tooltip></el-table-column>

      <el-table-column prop="gmtCreate" label="添加时间" width="200" align="center"></el-table-column>

      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-link type="primary" icon="el-icon-edit">修改</el-link>
          </router-link>

          <el-link type="danger" icon="el-icon-delete-solid" @click="removeDataById(scope.row.id)">删除</el-link>
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
import teacher from '@/api/edu/teacher'
export default {
  name: 'list',
  data() {
    return {
      value1:'',
      listLoading: true, // 是否显示loading信息
      page:1,  //当前页
      limit:5, //每页显示记录数
      total:0, //总记录数
      teacherVo:{},  //条件查询参数
      list:[]  //查询出结果
    }
  },
  methods: {
    fetchData(page){
      this.teacherVo.begin = this.value1[0]
      this.teacherVo.end = this.value1[1]
      this.getList(page,this.limit)
    },
    handleSizeChange(limit){
      this.getList(this.page,limit)
    },
    resetData() {
      this.value1 = ''
      this.teacherVo = {}
      this.fetchData()
    },
    getList(page = 1,limit = 5){
      this.page = page
      this.limit = limit
      this.listLoading = true
      teacher.pageTeacherQuery(this.page,this.limit,this.teacherVo)
        .then(res =>{
          if (res.success === true) {
            this.list = res.data.rows
            this.total = res.data.total
          }
          this.listLoading = false
        }).catch(error=>{this.$message.error('加载失败，请联系管理员');})
    },
    removeDataById(id){
      this.$confirm('此操作将永久删除该讲师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacher.removeById(id)
          .then(res =>{
            if (res.success === true) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.fetchData(this.page)
            }
          })
          .catch(error=>{this.$message.error('加载失败，请联系管理员');})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    }
  },

  created() {
    this.getList()
  }
}
</script>

<style scoped>

</style>
<style lang="css"> .el-tooltip__popper{ max-width:50% } </style>
