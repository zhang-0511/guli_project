<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt">
          <span class="success-icon"></span>
          <span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
          <span class="success-info">课程名称：{{payObj.courseTitle}}</span>
          <span class="success-info">课程讲师：{{payObj.teacherName}}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{payObj.total_fee}}</em></span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
            <qriously :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>

          </div>

        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->

      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "info",
  asyncData({params,error}) {
    return orderApi.createNative(params.orderId).then(res=>{
      if (res.data.success === true){
        return {payObj : res.data.data}
      }
    }).catch(error=>{this.$message.error(error.message);})
  },
  data(){
    return {
      interval:''
    }
  },
  methods:{
    queryPayStatus(orderId,courseId){
      orderApi.queryPayStatus(orderId,courseId).then(res=>{
        if (res.data.success === true){
          //支付成功，清除定时器
          clearInterval(this.interval)
            this.$message({
              message: '支付成功',
              type: 'success'
            });
            //跳转回到课程详情
          this.$router.push({path:'/course/'+this.payObj.courseId})
        }
      })
    }
  },
  mounted() {
    this.interval = setInterval(()=>{
      this.queryPayStatus(this.payObj.out_trade_no,this.payObj.courseId)
    },3000)
  }
}
</script>

<style scoped>

</style>
