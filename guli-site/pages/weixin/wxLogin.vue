<template>
  <client-only>
    <div class="main">
      <div class="title" v-if="showTitle">
        <a class="active">
          <i class="iconfont icon-shoujihao" style="font-size:20px;vertical-align: middle"/>
          {{ wxLoginText }}
        </a>
      </div>

      <div class="sign-up-container" v-if="dialogAtrr.showLoginType === 'phone'">
        <el-form ref="userForm" :model="registerVo" :rules="rules">

          <el-form-item class="input-prepend restyle no-radius" prop="mobile">
            <div>
              <el-input type="text" placeholder="手机号" v-model="registerVo.mobile"/>
              <i class="iconfont icon-phone"/>
            </div>
          </el-form-item>

          <el-form-item class="input-prepend restyle no-radius" prop="code">
            <div style="width: 100%;display: block;float: left;position: relative">
              <el-input type="text" placeholder="验证码" v-model.number="registerVo.code"/>
              <i class="iconfont icon-yanzhengma"></i>
            </div>
            <div class="btn" style="position:absolute;right: 0;top: 6px;width: 40%;">
              <!--            <a href="javascript:" type="button" @click="getCodeFun()" :value="codeTest" style="border: none;background-color: none">{{codeTest}}</a>-->
              <el-button type="primary" :disabled="sending" size="mini" @click="getCodeFun()" round>{{codeTest}}</el-button>
            </div>
          </el-form-item>

          <el-form-item class="input-prepend" prop="password">
            <div>
              <el-input type="password" placeholder="请输入密码" v-model="registerVo.password"/>
              <i class="iconfont icon-mima"/>
            </div>
          </el-form-item>

          <el-form-item class="input-prepend newPassword" prop="newPassword">
            <div>
              <el-input type="password" placeholder="再输入一次密码" v-model="registerVo.newPassword"/>
              <i class="iconfont icon-mima"/>
            </div>
          </el-form-item>

          <div class="btn">
            <input type="button" class="sign-up-button" value="注册" @click="submitRegister()">
          </div>
          <p class="sign-up-msg">
            点击 “注册” 即表示您同意并愿意遵守简书
            <br>
            <a target="_blank" href="https://www.jianshu.com/p/c44d171298ce">用户协议</a>
            和
            <a target="_blank" href="https://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
          </p>
        </el-form>
        <!-- 更多注册方式 -->
      </div>
      <!-- 微信登录 #start -->
      <div class="operate-view" v-if="dialogAtrr.showLoginType === 'weixin'">
        <div class="wrapper wechat" style="height: 400px">
          <div>
            <div id="weixinLogin"></div>
          </div>
          <div class="bottom wechat">
            <el-link type="primary" @click="phoneLogin()">
              <i class="iconfont icon-denglushoujidenglu" style="font-size:20px;vertical-align: middle" />{{ showPhoneText }}
            </el-link>
          </div>
        </div>
      </div>
      <!-- 微信登录 #end -->
    </div>
  </client-only>
</template>

<script>
import "assets/css/sign.css";
import register from '@/api/register'
import cookie from 'js-cookie'
import Vue from 'vue'
export default {
  name:'wxLogin',
  layout: 'sign',
  data() {
    return {
      registerVo: {
        mobile: '',
        code: '',
        password: '',
        newPassword:''
      },
      showTitle:false,
      wxLoginText:'微信登录',
      showPhoneText:'',
      dialogAtrr:{
        showLoginType:'weixin'
      },
      sending: false,      //是否发送验证码
      second: 60,        //倒计时间
      codeTest: '获取验证码',
      rules: {
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: this.checkPhone, trigger: 'blur'}
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { type: 'number', message: '验证码为数字值'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码的长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: this.validatePass2, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    showWx(){
      this.dialogAtrr.showLoginType = 'weixin'
      register.getLoginParam()
        .then(res=>{
          var obj = new WxLogin({
            self_redirect:true,
            id:"weixinLogin",
            appid: res.data.data.appid,
            scope: res.data.data.scope,
            redirect_uri: res.data.data.redirect_uri,
            state: res.data.data.state,
            style: "black",
            href: ""
          });
        })
    },
    phoneLogin() {
      this.registerVo = {}
      if (this.isLogin === '1'){
        this.$router.push({path:'/user/login'})
      }else {
        this.$router.push({path:'/user/register'})
      }

    },
    getCodeFun() {
      this.sending = true
      register.sendCode(this.registerVo.mobile)
        .then(res=>{
          if (res.data.success === true){
            this.$message({
              message: '发送手机验证码成功',
              type: 'success'
            });
            this.sending = false
            this.timeDown()
          }
        }).catch(error=>{this.$message.error(error.message);})
      this.timeDown()
    },
    timeDown() {
      let result = setInterval(() => {
        --this.second;
        this.codeTest = this.second +'秒后重试'
        if (this.second < 1) {
          clearInterval(result);
          this.sending = false;
          this.second = 60;
          this.codeTest = "获取验证码"
        }
      }, 1000);
    },
    submitRegister() {
      register.register(this.registerVo)
        .then(res =>{
          if (res.data.success === true){
            this.$message({
              message: '注册成功，自动登录中',
              type: 'success'
            });
            let token = res.data.data.token
            this.loginInfo = res.data.data.loginInfo
            cookie.set('guli_token',token,{domain:'localhost'})
            cookie.set('guli_user',JSON.stringify(this.loginInfo),{domain:'localhost'})
            setTimeout(() => {
              this.$router.push({path: '/'})
            }, 1000)
          }
        }).catch(error=>{this.$message.error(error.message);})
    },
    checkPhone (rule, value, callback) {
      if (!(/^1[34578]\d{9}$/.test(value))) {
        return callback(new Error('手机号码格式不正确'))
      }
      return callback()
    },
    validatePass2(rule, value, callback){
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerVo.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    },
    loginCallback(map){
      map = JSON.parse(map)
      if (map.isLogin === 0){
        this.registerVo = map.userInfo
        this.dialogAtrr.showLoginType = 'phone'
        this.showTitle = true
        this.wxLoginText = '绑定手机号'
      }else if (map.isLogin === 1){
        let token = map.token
        let userInfo = map.userInfo
        cookie.set('guli_token',token,{domain:'localhost'})
        cookie.set('guli_user',JSON.stringify(userInfo),{domain:'localhost'})
        this.$message({
          message: '登录成功',
          type: 'success'
        });
        this.$router.push({path: '/'})
      }
    },
  },
  created() {
    if (this.$route.params.isLogin === '1'){
      this.showPhoneText = '手机号验证码登录'
      this.isLogin = '1'
    }else {
      this.showPhoneText = '手机号验证码注册'
      this.isLogin = '0'
    }
    this.showWx()
  },
  mounted() {
    //注册全局登录事件
     window.loginEvent = new Vue()
    // 微信登录回调处理
    let self = this;
    window["loginCallback"] = (map) => {
      self.loginCallback(map);
    }
  }
}
</script>

<style scoped>
.el-form-item{
  margin-bottom:0
}
</style>
