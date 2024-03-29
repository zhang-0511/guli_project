import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '谷粒学院后台首页', icon: 'dashboard', noCache: true }
    }]
  },
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/list',
    name: '讲师管理',
    meta: { title: '讲师管理', icon: 'teacher' },
    children: [
      {
        path: 'list',
        name: '讲师列表',
        component: () => import('@/views/edu/teacher/list'),
        meta: { title: '讲师列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加讲师',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '添加讲师', icon: 'teacher_edit' }
      },
      {
        path: 'edit/:id',
        name: '修改讲师',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '修改讲师', icon: 'teacher_edit' },
        hidden:true
      }
    ]
  },
  {
    path: '/subject',
    component: Layout,
    redirect: '/subject/list',
    name: '课程分类管理',
    meta: { title: '课程分类管理', icon: 'subject' },
    children: [
      {
        path: 'list',
        name: '课程分类列表',
        component: () => import('@/views/edu/subject/list'),
        meta: { title: '课程分类列表', icon: 'list2' }
      },
      {
        path: 'save',
        name: '添加课程分类',
        component: () => import('@/views/edu/subject/save'),
        meta: { title: '添加课程信息', icon: 'excel2' }
      }
    ]
  },
  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '课程管理',
    meta: { title: '课程管理', icon: 'subject2' },
    children: [
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/edu/course/list'),
        meta: { title: '课程列表', icon: 'showCourse' }
      },
      {
        path: 'info',
        name: '课程发布',
        component: () => import('@/views/edu/course/info'),
        meta: { title: '课程发布', icon: 'imputCourse' }
      },
      {
        path: 'info/:id',
        name: '课程发布',
        component: () => import('@/views/edu/course/info'),
        meta: { title: '课程发布', icon: 'imputCourse' },
        hidden:true
      },
      {
        path: 'courseInfo/:id',
        name: '编辑课程信息',
        component: () => import('@/views/edu/course/courseInfo'),
        meta: { title: '编辑课程信息', icon: 'imputCourse' },
        hidden:true
      },
      {
        path: 'chapter/:id',
        name: '课程发布',
        component: () => import('@/views/edu/course/chapter'),
        meta: { title: '课程发布', icon: 'imputCourse' },
        hidden:true
      },
      {
        path: 'chapterInfo/:id',
        name: '编辑课程大概',
        component: () => import('@/views/edu/course/chapterInfo'),
        meta: { title: '编辑课程大概', icon: 'imputCourse' },
        hidden:true
      },
      {
        path: 'publish/:id',
        name: '课程发布',
        component: () => import('@/views/edu/course/publish'),
        meta: { title: '课程发布', icon: 'imputCourse' },
        hidden:true
      }
    ]
  },
  {
    path: '/banner',
    component: Layout,
    redirect: '/banner/list',
    name: '轮播图管理',
    meta: { title: '轮播图管理', icon: 'banner' },
    children: [
      {
        path: 'list',
        name: '轮播图列表',
        component: () => import('@/views/cms/banner/list'),
        meta: { title: '轮播图列表', icon: 'bannerList' }
      },
      {
        path: 'save',
        name: '添加轮播图',
        component: () => import('@/views/cms/banner/save'),
        meta: { title: '添加轮播图', icon: 'editBanner' }
      },
      {
        path: 'edit/:id',
        name: '修改轮播图',
        component: () => import('@/views/cms/banner/save'),
        meta: { title: '修改轮播图', icon: 'editBanner' },
        hidden:true
      }
    ]
  },
  {
    path: '/statistics',
    component: Layout,
    redirect: '/statistics/create',
    name: '统计分析',
    meta: { title: '统计分析', icon: 'banner' },
    children: [
      {
        path: 'create',
        name: '生成数据',
        component: () => import('@/views/statistics/create'),
        meta: { title: '生成数据', icon: 'bannerList' }
      },
      {
        path: 'show',
        name: '图表显示',
        component: () => import('@/views/statistics/show'),
        meta: { title: '图表显示', icon: 'editBanner' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
