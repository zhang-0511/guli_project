<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">{{subject.oneName}}</a>
        \
        <span class="c-333 fsize14">{{subject.twoName}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseInfo.cover" style="width: 640px;height: 357px;" :alt="courseInfo.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ courseInfo.title }}</span>
            </h2>
            <section class="c-attr-jg" v-if="Number(courseInfo.price) === 0">
              <b class="c-yellow" style="font-size:24px;">免费</b>
            </section>
            <section class="c-attr-jg" v-else>
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;" >￥{{courseInfo.price}}</b>
            </section>

            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{ teacher.name }}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#" >收藏</a>
              </span>
            </section>
            <section class="c-attr-mt">
              <a  @click.prevent="toPlayer" target="_blank" title="立即观看" v-if="Number(courseInfo.price) === 0" class="comm-btn c-btn-3"  >立即观看</a>
              <a  @click.prevent="toPlayer" target="_blank" title="立即观看" v-else-if="orderUser === '已支付'" class="comm-btn c-btn-3" style="width: 200px">已购买，立即观看</a>
              <a href="#" title="立即购买" v-else class="comm-btn c-btn-3" @click.prevent="createOrders" >立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ courseInfo.buyCount }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ courseInfo.lessonNum }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ courseInfo.viewCount }}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseInfo.description">
                         {{courseInfo.description}}
                      </p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <li class="lh-menu-stair" v-for="(chapterVo,index) in chapterVoList" :key="index">
                            <a href="javascript: void(0)" :title="chapterVo.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapterVo.title}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30" v-for="videoVo in chapterVo.children" :key="videoVo.id">
                                <a @click.prevent="toPlayer2(videoVo.videoSourceId)" :title="videoVo.title" target="_blank">
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{ videoVo.title }}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: 70px;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="teacher.avatar" width="50" height="50" :alt="teacher.name">
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{ teacher.name }}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{teacher.career}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
    <div class="mt50 commentHtml"><div>
      <h6 class="c-c-content c-infor-title" id="i-art-comment">
        <span class="commentTitle">课程评论</span>
      </h6>
      <section class="lh-bj-list pr mt20 replyhtml">
        <ul>
          <li class="unBr">
            <aside class="noter-pic">
              <img width="50" height="50" class="picImg" src="~/assets/img/avatar-boy.gif" alt="">
            </aside>
            <div class="of">
              <section class="n-reply-wrap">
                <fieldset>
                  <textarea name="" v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                </fieldset>
                <p class="of mt5 tar pl10 pr10">
                  <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                  <el-button type="primary" @click="addComment()" size="mini" round>发布评论</el-button>
                </p>
              </section>
            </div>
          </li>
        </ul>
      </section>
      <section class="">
        <section class="question-list lh-bj-list pr">
          <ul class="pr10">
            <li v-for="(comment,index) in commentData.items" v-bind:key="index">
              <aside class="noter-pic">
                <img width="50" height="50" class="picImg" :src="comment.avatar">
              </aside>
              <div class="of">
                    <span class="fl">
                    <font class="fsize12 c-blue">
                      {{comment.nickname}}</font>
                    <font class="fsize12 c-999 ml5">评论：</font></span>
              </div>
              <div class="noter-txt mt5">
                <p>{{comment.content}}</p>
                <p></p>
              </div>
              <div class="of mt5">
                <el-link v-if="userId===comment.memberId" type="danger" style="position: absolute;right:0;bottom: 38%" @click="removeComment(comment.id)">删除评论</el-link>
                <span class="fr"><font class="fsize12 c-999 ml5">{{comment.gmtCreate}}</font></span>
              </div>
            </li>

          </ul>
        </section>
      </section>

      <!-- 公共分页 开始 -->
      <div class="paging">
        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
        <a
          :class="{undisable: !commentData.hasPrevious}"
          href="#"
          title="首页"
          @click.prevent="gotoPage(1)">首</a>
        <a
          :class="{undisable: !commentData.hasPrevious}"
          href="#" v-if="commentData.hasPrevious"
          title="前一页"
          @click.prevent="gotoPage(commentData.current-1)">&lt;</a>
        <a
          v-for="page in commentData.pages"
          :key="page"
          :class="{current: commentData.current == page, undisable: commentData.current == page}"
          :title="'第'+page+'页'"
          href="#"
          @click.prevent="gotoPage(page)">{{ page }}</a>
        <a
          :class="{undisable: !commentData.hasNext}"
          href="#" v-if="commentData.hasNext"
          title="后一页"
          @click.prevent="gotoPage(commentData.current+1)">&gt;</a>
        <a
          :class="{undisable: !commentData.hasNext}"
          href="#"
          title="末页"
          @click.prevent="gotoPage(commentData.pages)">末</a>
        <div class="clear"/>
      </div>
      <!-- 公共分页 结束 -->
    </div>
    </div>
  </div>
</template>

<script>
import courseApi from "@/api/course";
import commentApi from "@/api/comment";
import orderApi from "@/api/order";
import videoApi from "@/api/video";
import cookie from 'js-cookie'
export default {
  data(){
    return {
      courseInfo:{},
      courseId:'',
      teacher:{},
      chapterVoList:[],
      subject:{},
      page:1,
      limit:4,
      total:10,
      commentData:{},
      comment:{content:'', courseId:''},
      userId:'',
      orderUser:'',
      videoSourceId:''
    }
  },
  methods:{
    getCourseInfo(){
      courseApi.getCourseInfoFront(this.courseId)
        .then(res=>{
          if (res.data.success === true){
            this.courseInfo = res.data.data.courseInfo
            this.videoSourceId = res.data.data.courseInfo.videoSourceId
            this.teacher = res.data.data.courseInfo.teacher
            this.chapterVoList = res.data.data.chapterVoList
            this.subject.oneName = res.data.data.courseInfo.subjectList[0].title
            this.subject.twoName = res.data.data.courseInfo.subjectList[0].children[0].title
          }
        }).catch(error=>{this.$message.error(error.message);})

    },
    getOrderUser(){
      let user = cookie.get('guli_user')
      if (user != null && user !== 'undefined'){
        user = JSON.parse(user)
        let userId = user.id
          orderApi.getOrderUser(userId,this.courseId).then(res=>{
            if (res.data.success === true){
              this.orderUser = res.data.data.orderUser
            }
          })
      }
    },
    initComment(){
      commentApi.getCommentByid(this.page,this.limit,this.courseId)
        .then(res =>{
          if (res.data.success === true){
            this.commentData = res.data.data
          }
        }).catch(error=>{this.$message.error(error.message);})
    },
    addComment(){
      this.comment.courseId = this.courseId
      this.comment.teacherId = this.teacher.id
      commentApi.saveComment(this.comment).then(res=>{
          if (res.data.success === true){
            this.comment.content = ''
            this.initComment()
            this.$message({
              message: '发表评论成功',
              type: 'success'
            });
          }
        }).catch(error=>{this.$message.error(error.message);})
    },
    removeComment(id){
      this.$confirm('确定删除该条评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        commentApi.deleteById(id)
          .then(res=>{
            if (res.data.success === true){
              this.comment.content = ''
              this.initComment()
              this.$message({
                message: '删除评论成功',
                type: 'success'
              });
            }
          }).catch(error=>{this.$message.error(error.message);})

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    gotoPage(page){
      commentApi.getCommentByid(page, this.limit,this.courseId).then(response => {
        this.commentData = response.data.data
      })
    },
    getUserId(){
      let user = cookie.get('guli_user')
      if (user != null && user !== 'undefined'){
        user = JSON.parse(user)
        this.userId = user.id
      }
    },
    createOrders(){
      let user = cookie.get('guli_user')
      if (user != null && user !== 'undefined') {
        orderApi.createOrder(this.courseId).then(res => {
          if (res.data.success === true) {
            let orderId = res.data.data.orderId
            this.$router.push({name: 'orders-info', params: {orderId: orderId}})
          }
        }).catch(error => {
          this.$message.error(error.message);
        })
      }else {
        this.$confirm('请登录后再进行课程购买, 是否跳转登录页面?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          this.$router.push({name: 'user-login'})
        }).catch(() => {

        });
      }
    },
    toPlayer(){
      videoApi.updatePlayCount(this.videoSourceId)
      const strUrl = '/player/'+this.videoSourceId
      let a = document.createElement("a");
      document.body.appendChild(a);
      a.style = "display: none";
      a.target = "_blank";
      a.href = strUrl;
      a.click();
      document.body.removeChild(a);
      //this.$router.push({path: '/player/'+this.videoSourceId})
    },
    toPlayer2(videoSourceId){
      videoApi.updatePlayCount(videoSourceId)
      const strUrl = '/player/'+videoSourceId
      let a = document.createElement("a");
      document.body.appendChild(a);
      a.style = "display: none";
      a.target = "_blank";
      a.href = strUrl;
      a.click();
      document.body.removeChild(a);
      //this.$router.push({path: '/player/'+videoSourceId})
    }
  },
  created() {
    if (this.$route.params.id){
        this.courseId = this.$route.params.id
        this.getCourseInfo()
        this.initComment()
        this.getUserId()
        this.getOrderUser()
    }
  },
  asyncData({params,error}) {
    return courseApi.updateViewCount(params.id).then(res=> {
      if (res.data.success === true) {
        return {payObj: res.data.data}
      }
    })
  }
};
</script>
