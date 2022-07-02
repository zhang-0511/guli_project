<template>
  <div class="app-container">
    <el-form style="margin: 3vh auto 0" label-width="180px">
      <el-form-item  label="输入关键字进行过滤：">
        <el-input placeholder="输入关键字进行过滤"  style="width: 200px" v-model="filterText"></el-input>
      </el-form-item>

    </el-form>

    <el-tree accordion class="filter-tree" :data="list" :props="defaultProps" :filter-node-method="filterNode" ref="tree"></el-tree>
  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {
  name: 'list',
  data() {
    return {
      list:[],  //查询出结果
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'title',
        value:'id'
    }
  }
},
methods: {
  getList(){
    subject.getSubjectList()
      .then(res=>{
        if (res.success === true){
          this.list = res.data.list
        }
      })
      .catch(error=>{this.$message.error('加载失败，请联系管理员');})
  },
  filterNode(value, data) {
    if (!value) return true
    return data.label.toLowerCase().indexOf(value.toLowerCase()) !== -1
  }
},
watch: {
  filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
  }
}
</script>

<style scoped>

</style>
<style lang="css"> .el-tooltip__popper{ max-width:50% } </style>
