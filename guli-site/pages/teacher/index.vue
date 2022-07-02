<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
          <!-- <c:forEach var="subject" items="${subjectList }">
                            <a id="${subject.subjectId}" title="${subject.subjectName }" href="javascript:void(0)" onclick="submitForm(${subject.subjectId})">${subject.subjectName }</a>
          </c:forEach>-->
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="teacherData.total === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="i-teacher-list" v-else-if="teacherData.total > 0">
            <ul class="of">
              <li v-for="(teacher,index) in teacherData.items" :key="index">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank">
                      <img :src="teacher.avatar" alt style="width: 100px;height: 100px; margin-left: 25px">
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank" class="fsize18 c-666">{{ teacher.name }}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">
                      {{teacher.intro}}
                    </span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{ teacher.career }}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !teacherData.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !teacherData.hasPrevious}"
              href="#" v-if="teacherData.hasPrevious"
              title="前一页"
              @click.prevent="gotoPage(teacherData.current-1)" >&lt;</a>
            <a
              v-for="page in teacherData.pages"
              :key="page"
              :class="{current: teacherData.current == page, undisable: teacherData.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>

            <a
              :class="{undisable: !teacherData.hasNext}"
              :style="{'pointer-events: none ': !teacherData.hasNext}"
              href="#" v-if="teacherData.hasNext"
              title="后一页"
              @click.prevent="gotoPage(teacherData.current+1)">&gt;</a>
            <a
              :class="{undisable: !teacherData.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(teacherData.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->

      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
import teacherApi from '@/api/teacher'
export default {
  asyncData({params,error}) {
    return teacherApi.pageTeacherFront(1,8).then(res=>{
      if(res.data.success === true){
        return {teacherData: res.data.data}
      }
    }).catch(error=>{this.$message.error(error.message);})
  },
  methods: {
    gotoPage(page){
      teacherApi.pageTeacherFront(page, 8).then(response => {
        this.teacherData = response.data.data
      })
    }
  }
};
</script>
