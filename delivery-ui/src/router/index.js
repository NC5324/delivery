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
import ManageFiles from '@/views/ManageFiles'
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
    path: '/manage/files',
    name: 'ManageFiles',
    component: ManageFiles
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
  // const publicPages = ['/', '/about', '/login', '/register', '/menu']
  const adminPages = ['/manage/products', '/manage/accounts', '/manage/product-options', '/manage/files']
  const moderatorPages = ['/manage/orders']
  const userPages = ['/profile']

  const adminAuthRequired = adminPages.includes(to.path)
  const modAuthRequired = moderatorPages.includes(to.path)
  const userAuthRequired = userPages.includes(to.path)

  const user = JSON.parse(localStorage.getItem('user'))

  if ((adminAuthRequired || modAuthRequired || userAuthRequired) && !user) {
    return next('/login')
  }

  if (adminAuthRequired && !user.roles.includes('ROLE_ADMIN')) {
    return next('/')
  }

  if (modAuthRequired && !user.roles.includes('ROLE_MODERATOR')) {
    return next('/')
  }

  // not really needed i think
  if (userAuthRequired && !user.roles.includes('ROLE_USER')) {
    return next('/login')
  }

  next()
})

export default router
