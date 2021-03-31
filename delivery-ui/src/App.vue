<template>
  <div id="app">
    <b-navbar fixed="top" class="navbar">
      <b-navbar-nav class="navbar-left" style="margin-left: 10%">
        <b-nav-item href="/"><span class="nav-item1">Home</span></b-nav-item>
        <b-nav-item href="/menu"><span class="nav-item1">Menu</span></b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto navbar-right">
        <b-nav-item href="/manage/products" v-if="showAdminBoards"><span class="nav-item1">Manage Products</span></b-nav-item>
        <b-nav-item href="/manage/accounts" v-if="showAdminBoards"><span class="nav-item1">Manage Profiles</span></b-nav-item>
        <b-nav-item href="/manage/files" v-if="showAdminBoards"><span class="nav-item1">Manage Files</span></b-nav-item>
        <b-nav-item href="/manage/orders" v-if="showModeratorBoards"><span class="nav-item1">Manage Orders</span></b-nav-item>
        <div v-b-toggle.sidebar-basket class="nav-item1" style="display: flex; flex-direction: column; justify-content: space-around">
          <img src="@/assets/basket.svg" alt="Basket" width="30px" height="30px">
        </div>
        <div v-on:click="redirect('/login')" class="nav-item1" style="margin-left: 10px; display: flex; flex-direction: column; justify-content: space-around; cursor: pointer;">
          <img src="@/assets/profile.svg" alt="Profile" width="30px" height="30px">
        </div>
      </b-navbar-nav>
    </b-navbar>
    <div>
      <router-view/>
    </div>
    <Basket></Basket>
  </div>
</template>

<script>
import Order from '@/models/order'
import Basket from '@/components/Basket'
export default {
  components: { Basket },
  data () {
    return {
      basket: new Order(),
      totalPrice: 0,
      show: false,
      initial: true
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    },
    currentUser () {
      return this.$store.state.auth.user
    },
    showAdminBoards () {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN')
      }
      return false
    },
    showModeratorBoards () {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_MODERATOR')
      }
      return false
    }
  },
  created () {
    window.addEventListener('scroll', this.handleScroll)
    this.nav_bg_variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
  },
  methods: {
    handleScroll () {
      if (window.scrollY > 50) {
        this.nav_bg_variant = 'white'
      } else {
        this.nav_bg_variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
      }
    },
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/')
      this.$router.go()
    },
    redirect (target) {
      this.$router.push(target)
    },
    wideEnough () {
      return window.innerWidth > 900
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

::-webkit-scrollbar{
  display: none;
}

.navbar {
  background: #E8A44A;
}

.navbar-left {
  padding-left: 10%;
}

.navbar-right {
  padding-right: 10%;
}

.nav-item1 {
  color: white;
  font-weight: bold;
}
.nav-item1:hover {
  border-bottom: 2px solid white;
}
</style>
