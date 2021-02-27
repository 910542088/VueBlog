import Vue from 'vue'
import VueRouter from 'vue-router'
import BlogDetail from '../views/BlogDetail'
import BlogEdit from "../views/BlogEdit"
import Blogs from "../views/Blogs"
import Login from "../views/Login"
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component:Blogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:BlogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/:BlogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  }
]

const router = new VueRouter({
  mode:'history',
  routes
})

export default router
