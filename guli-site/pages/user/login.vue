<template>
  <client-only>
  <div class="main">
    <div class="title">
      <a class="active" href="/user/login">
        <i class="iconfont icon-huiyuandenglu" style="font-size:20px;vertical-align: middle"/>
        登录
      </a>
      <span>·</span>
      <a href="/user/register">
        <i class="iconfont icon-zhucedenglu" style="font-size:20px;vertical-align: middle"/>
        注册
      </a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user" :rules="rules">

        <el-form-item class="input-prepend restyle" prop="mobile">
          <div>
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" >
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
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
import 'assets/css/sign.css'
import 'assets/css/iconfont.css'
import cookie from 'js-cookie'
import login from '@/api/login'
export default {
  name: 'login',
  layout: 'sign',
  data () {
    return {
      user:{
        mobile:'',
        password:''
      },
      loginInfo:{},
      rules: {
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: this.checkPhone, trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码的长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitLogin(){
      login.login(this.user)
        .then(res=>{
          if (res.data.success === true){
            let token = res.data.data.token
            this.loginInfo = res.data.data.loginInfo
            cookie.set('guli_token',token,{domain:'localhost'})
            cookie.set('guli_user',JSON.stringify(this.loginInfo),{domain:'localhost'})
            this.$message({
              message: '登录成功',
              type: 'success'
            });
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
    showWx(){
      this.$router.push({name:'weixin-wxLogin',params:{isLogin : '1'}})
    },
  }
}
</script>

<style scoped>
.el-form-item__error{
  z-index: 9999999;
}
</style>
