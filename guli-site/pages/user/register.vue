<template>
  <client-only>
  <div class="main">
    <div class="title">
      <a href="/user/login">
        <i class="iconfont icon-huiyuandenglu" style="font-size:20px;vertical-align: middle"/>
        登录
      </a>
      <span>·</span>
      <a class="active" href="/user/register">
        <i class="iconfont icon-zhucedenglu" style="font-size:20px;vertical-align: middle"/>
        注册
      </a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="registerVo" :rules="rules">

        <el-form-item class="input-prepend restyle" prop="nickname">
          <div>
            <el-input type="text" placeholder="你的昵称" v-model="registerVo.nickname"/>
            <i class="iconfont icon-nicheng"/>
          </div>
        </el-form-item>

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
      <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li>
            <el-link @click="showWx" target="_blank"><i class="iconfont icon-weixin"/></el-link>
          </li>
        </ul>
      </div>

    </div>
  </div>
  </client-only>
</template>

<script>
import "assets/css/sign.css";
import register from '@/api/register'
import cookie from 'js-cookie'
export default {
  layout: 'sign',
  data() {
    return {
      registerVo: {
        mobile: '',
        code: '',
        nickname: '',
        password: '',
        newPassword:''
      },
      sending: false,      //是否发送验证码
      second: 60,        //倒计时间
      codeTest: '获取验证码',
      isPhone: true,
      rules: {
        nickname: [
          { required: true, message: '请输入你的昵称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
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
      this.$router.push({name:'weixin-wxLogin',params:{isLogin : '0'}})
    },
    getCodeFun() {
      console.log(111)
      if (this.isPhone){
        this.sending = true
        register.sendCode(this.registerVo.mobile)
          .then(res=>{
            if (res.data.success === true){
              this.$message({
                message: '发送手机验证码成功',
                type: 'success'
              });
              this.timeDown()
            }
          }).catch(error=>{this.sending = false;this.$message.error(error.message);})
      }else {
        this.$message({
          message: '你的手机号格式不正确，请检查手机号是否输入正确',
          type: 'warning'
        });
      }

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
        .then(res=>{

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
        this.isPhone = false
        return callback(new Error('手机号码格式不正确'))
      }else {
        this.isPhone = true
        return callback()
      }

    },
    validatePass2(rule, value, callback){
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerVo.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
  }
}
</script>

<style scoped>
.el-form-item{
  margin-bottom:0
}
</style>
