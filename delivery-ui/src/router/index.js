import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Profile from '../views/ProfilePage'
import Register from '../views/Register'
import About from '../views/About'
import Menu from '@/views/Menu'
import ManageProducts from '@/views/ManageProducts'
import ManageAccounts from '@/views/ManageAccounts'
import ManageOrders from '@/views/ManageOrders'
import ManageToppings from '@/views/ManageToppings'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/menu',
    name: 'Menu',
    component: Menu
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/manage/products',
    name: 'ManageProduct',
    component: ManageProducts
  },
  {
    path: '/manage/orders',
    name: 'Manage Orders',
    component: ManageOrders
  },
  {
    path: '/manage/accounts',
    name: 'Manage Accounts',
    component: ManageAccounts
  },
  {
    path: '/manage/product-options',
    name: 'ManageTopping',
    component: ManageToppings
  },
  {
    path: '*',
    redirect: '/'
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/', '/about', '/login', '/register', '/menu']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')
  if (authRequired && !loggedIn) {
    return next('/login')
  }
  next()
})

export default router
