<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="谷粒学院">
            <img src="@/assets/img/logo.png" width="200" alt="谷粒学院">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link to="/article" tag="li" active-class="current">
              <a>文章</a>
            </router-link>
            <router-link to="/qa" tag="li" active-class="current">
              <a>问答</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!userinfo.id" id="no-login">
              <a href="/user/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/user/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
<!--            <li v-if="userinfo.id" id="is-login-one" class="mr10">
              <a id="headerMsgCountId" href="#" title="消息">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>-->
            <li v-if="userinfo.id" id="is-login-two" class="h-r-user">
<!--              <a href="/ucenter" title>
                <img :src="userinfo.avatar" width="30" height="30" class="vam picImg" alt>
                <span id="userName" class="vam disIb">{{ userinfo.nickname }}</span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>-->
              <el-dropdown  @command="loginMenu">
                <span class="el-dropdown-link">
                   <a href="#" title>
                    <img :src="userinfo.avatar" width="30" height="30" class="vam picImg" alt>
                    <span id="userName" class="vam disIb">{{ userinfo.nickname }}</span>
                  </a>
                </span>
                <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
                  <el-dropdown-item command="/user/userInfo">个人中心</el-dropdown-item>
                  <el-dropdown-item command="/user/updatePassword">修改密码</el-dropdown-item>
                  <el-dropdown-item command="/logout" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </li>

            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" style="width: 200px" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit"  class="s-btn">
                  <em class="icon18" style="margin-left: 30px">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->

    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>联系电话：17612944805</span>
                <span>Email：2878932916@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2022课程版权均归天姿学院所有 京ICP备5201314号</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="@/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="@/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "@/assets/css/sign.css";
import '~/assets/css/reset.css'
import '~/assets/css/theme.css'
import '~/assets/css/global.css'
import '~/assets/css/web.css'
import '~/assets/css/base.css'
import '~/assets/css/activity_tab.css'
import '~/assets/css/bottom_rec.css'
import '~/assets/css/nice_select.css'
import '~/assets/css/order.css'
import '~/assets/css/swiper-3.3.1.min.css'
import "~/assets/css/pages-weixinpay.css"
import cookie from 'js-cookie'
export default {
  data(){
    return{
      token:'',
      userinfo:{}
    }
  },
  methods:{
    showInfo(){
      let user = cookie.get('guli_user')
      if (user!==undefined && user !== 'undefined'){
        this.userinfo = JSON.parse(user)
      }
    },
    logout(){
      cookie.remove('guli_user')
      cookie.remove('guli_token')
      window.location.href = '/'
    },
    loginMenu(command) {
      if('/logout' === command) {
        cookie.remove('guli_user')
        cookie.remove('guli_token')
        window.location.href = '/'
      } else {
        window.location.href = command
      }
    },
  },
  created() {
    this.showInfo()
  }
};
</script>
