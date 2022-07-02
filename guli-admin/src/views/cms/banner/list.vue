<template>
  <div class="app-container">
    <div style="margin: 1vh auto 0;width: 100%">
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="轮播标题：">
          <el-input v-model="bannerVo.title" placeholder="轮播标题"/>
        </el-form-item>

        <el-form-item label="选择时间：" >
          <el-date-picker v-model="value1" align="right" type="datetimerange" start-placeholder="选择开始时间" end-placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00']"></el-date-picker>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
    </div>

    <el-table v-loading="listLoading" :data="list" element-loading-text="数据加载中"  border fit highlight-current-row row-class-name="myClassList">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="image_url" label="轮播图信息" width="400" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.imageUrl" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
            </div>
          </div>
        </template>

      </el-table-column>

      <el-table-column prop="linkUrl" label="链接地址" align="center"></el-table-column>

      <el-table-column prop="sort" label="排序"  align="center"></el-table-column>

      <el-table-column prop="gmtCreate" label="添加时间"  align="center"></el-table-column>

      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <router-link :to="'/banner/edit/'+scope.row.id">
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
import banner from "@/api/cms/banner";
export default {
  name: 'list',
  data() {
    return {
      value1:'',
      listLoading: true, // 是否显示loading信息
      page:1,  //当前页
      limit:5, //每页显示记录数
      total:0, //总记录数
      bannerVo:{},  //条件查询参数
      list:[],  //查询出结果
    }
  },
  methods: {
    fetchData(page){
      this.bannerVo.begin = this.value1[0]
      this.bannerVo.end = this.value1[1]
      this.getList(page,this.limit)
    },
    handleSizeChange(limit){
      this.getList(this.page,limit)
    },
    resetData() {
      this.value1 = ''
      this.bannerVo = {}
      this.fetchData()
    },
    getList(page = 1,limit = 5){
      this.page = page
      this.limit = limit
      this.listLoading = true
      banner.pageBanner(this.page,this.limit,this.bannerVo)
        .then(res =>{
          if (res.success === true) {
            this.list = res.data.rows
            this.total = res.data.total
          }
          this.listLoading = false
        }).catch(error=>{this.$message.error('加载失败，请联系管理员');})
    },
    removeDataById(bannerId){
      this.$confirm('此操作将永久删除该轮播图信息信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        banner.deleteBannerById(bannerId)
          .then(res =>{
            if (res.success === true) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.fetchData(this.page)
            }
          }).catch(error=>{this.$message.error('删除失败，请联系管理员');})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },

  },

  created() {
    this.getList()
  }
}
</script>

<style scoped>
.myClassList .info {
  width: 400px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 250px;
  height: 120px;
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
  height: 100%;
}
.myClassList td .info .title {
  width: 150px;
  float: left;
  height: 90px;
}
.myClassList td .info .title a {
  font-size: 18px;
  text-align: center;
  display: block;
  /*height: 50px;*/
  line-height: 90px;
  overflow: hidden;
  color: #00baf2;
}
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
  width: 250px;
  height: 130px;
  display: block;
}
</style>
