<template>

  <div>
    <!-- 幻灯片 开始 -->
    <div  v-swiper:mySwiper="swiperOption" v-if="bannerList.length">
      <div class="swiper-wrapper">
        <div v-for="(banner,index) in bannerList" :key="index" class="swiper-slide" style="background: #040B1B;">
          <img :src="banner.imageUrl" :alt="banner.title" style="width: 1519px;height: 480px">
        </div>
      </div>
      <div class="swiper-pagination swiper-pagination-white"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
    </div>
    <!-- 幻灯片 结束 -->

    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="(course,index) in courseList" :key="index">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img :src="course.cover" class="img-responsive" :alt="course.title" style="width: 267px;height: 150px;">
                      <div class="cc-mask">
                        <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{ course.title }}</a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green" v-if="Number(course.price) === 0">
                        <i class="c-fff fsize12 f-fA">免费</i>
                      </span>
                      <span class="fr jgTag bg-green" v-else>
                        <i class="c-fff fsize12 f-fA">需购买</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{course.buyCount}}人学习</i>
                        |
                        <i class="c-999 f-fA">{{course.viewCount}}浏览</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="#" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">名师大咖</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="(teacher,index) in teacherList" :key="index">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/'+teacher.id" title="姚晨">
                        <img :alt="teacher.name" :src="teacher.avatar" style="width: 141px;height: 141px;">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/teacher/'+teacher.id" :title="teacher.name" class="fsize18 c-666">{{ teacher.name }}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{ teacher.career }}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">
                       {{ teacher.intro }}
                      </p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="#" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>

<script>
import index from '@/api/index'

export default {

  data () {
    return {
     swiperOption: {
        loop:true,
        pagination: {
          el: '.swiper-pagination',//分页的dom节点
        },
        navigation: {
          nextEl: '.swiper-button-next',//下一页dom节点
          prevEl: '.swiper-button-prev'//前一页dom节点
        },
       initialSlide: 0,
       speed: 400,
       autoplay: {
         delay: 3000,
         disableOnInteraction: false
       },
        grabCursor: true,
       setWrapperSize: true,
       autoHeight: true,
       uniqueNavElements: true,
       preventInteractionOnTransition: false,
       allowSilderPrev: true,
       allowSilderNext: true,
       scrollbar:'.swiper-scrollbar',//滚动条
       mousewheelControl: true,
       observer: true,
       observeParents: true,
       debugger: true,
      },
      bannerList:[],
      teacherList:[],
      courseList:[]
    }
  },
  methods:{
    getBannerList() {
      index.getBannerList()
        .then(res=>{
          if (res.data.success === true){
            this.bannerList = res.data.data.list
          }
        }).catch(error=>{this.$message.error(error.message);})
    },
    getIndexList(){
      index.getIndexList()
       .then(res=>{
         if (res.data.success === true){
           this.teacherList = res.data.data.teacherList
           this.courseList = res.data.data.courseList
         }
       }).catch(error=>{this.$message.error(error.message);})
    },
  },
  created() {
    this.getBannerList()
    this.getIndexList()
  },

}
</script>
<style>
.swiperBox {
  position: relative;
}
</style>
