<template>
    <!-- header -->
    <div>
    </div>
    <!-- footer -->
</template>
<script>
export default {
  data() {
    return {
    }
  },
  mounted() {
    if (this.$route.query.map){
      let map = this.$route.query.map
      // 调用父vue方法
      window.parent['loginCallback'](map)
    }
  }
}
</script>
