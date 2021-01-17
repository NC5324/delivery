<template>
  <div style="min-height: 100vh; min-width: 100vw; background-image: url(https://cdn.hipwallpaper.com/i/3/15/dqIeNr.jpg); background-repeat: no-repeat; background-size: 100% 100%" id="app">
    <b-navbar sticky toggleable="xl" variant="white">
      <b-navbar-brand  href="/">
        <img width="200px" height="60px" src="https://www.pngkey.com/png/full/490-4902011_new-york-pizza-logo-png-transparent-new-york.png" alt="brandLogo">
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse">
      </b-navbar-toggle>

      <b-collapse class="ml-auto" id="nav-collapse" is-nav>
        <!--Left aligned nav items-->
        <b-navbar-nav>
          <b-nav-item href="/"><b-button variant="dark">Начало</b-button></b-nav-item>
          <b-nav-item href="/about"><b-button variant="dark">За нас</b-button></b-nav-item>
          <b-nav-item href="/menu"><b-button variant="dark">Меню</b-button> </b-nav-item>
          <b-nav-item right v-if="showModeratorBoards"><b-button variant="dark">Управление на поръчки</b-button></b-nav-item>
          <b-nav-item href="/manage/products" right v-if="showAdminBoards"><b-button variant="dark">Управление на продукти</b-button></b-nav-item>
          <b-nav-item href="/manage/accounts" right v-if="showAdminBoards"><b-button variant="dark">Управление на акаунти</b-button></b-nav-item>
          <b-nav-item href="/manage/files" right v-if="showAdminBoards"><b-button variant="dark">Управление на файлове</b-button></b-nav-item>
        </b-navbar-nav>
      </b-collapse>

      <b-navbar-toggle class="ml-auto" target="nav-collapse2" is-nav>
      </b-navbar-toggle>

        <!--Right aligned nav items-->
      <b-collapse id="nav-collapse2" is-nav>
        <b-navbar-nav class="ml-auto" v-if="!currentUser">
          <b-nav-item right v-b-toggle.sidebar-right><b-button variant="dark"><b-icon-basket2></b-icon-basket2> Количка </b-button></b-nav-item>
          <b-nav-item right href="/login"><b-button variant="dark"><b-icon-door-closed></b-icon-door-closed> Вход </b-button></b-nav-item>
          <b-nav-item right href="/register"><b-button variant="dark"><b-icon-key></b-icon-key> Регистрация </b-button></b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto" v-if="currentUser">
          <b-nav-item right v-b-toggle.sidebar-right><b-button variant="dark"><b-icon-basket2></b-icon-basket2> Количка </b-button></b-nav-item>
          <b-nav-item right href="/profile"><b-button variant="dark"><b-icon-person></b-icon-person>Профил</b-button></b-nav-item>
          <b-nav-item right><b-button @click="logOut" variant="dark"><b-icon-door-open></b-icon-door-open>Изход</b-button></b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <div>
      <router-view/>
    </div>
    <b-sidebar v-if="false" @shown="onBasketShow" @hidden="onBasketHide"  id="sidebar-right" title="Количка" right bg-variant="dark" text-variant="light">
      <template #footer="{ hide }">
        <div class="d-flex bg-dark text-light align-items-center px-3 py-2">
          <strong class="mr-auto">Обща цена: {{totalPrice}} лв.</strong>
          <b-button size="sm" @click="hide">Close</b-button>
        </div>
      </template>
      <div v-if="!initial">
        <b-card-group v-for="item in products" :key="item" class="px-3 py-2">
          <b-card bg-variant="white" text-variant="dark" :header="item.name" class="text-center mt-3">
            <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit. {{item.toppings}}</b-card-text>
            <template #footer>
              <strong class="mr-auto">Цена: {{item.price}} лв.</strong>
              <b-avatar button @click="removeFromCart(item.id)" class="ml-5" icon="x-circle"></b-avatar>
            </template>
          </b-card>
        </b-card-group>
      </div>
      <div v-else>
        <strong class="mr-auto">Количката е празна!</strong>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import ProductService from './services/product-service'
export default {
  data () {
    return {
      nav_bg_variant: 'white',
      products: [{
        id: '',
        name: '',
        price: 0,
        imgSource: '',
        type: ''
      }],
      totalPrice: 0,
      show: false,
      initial: true
    }
  },
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
    this.nav_bg_variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
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
    this.onBasketShow()
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
    onBasketShow () {
      if (localStorage.getItem('basket')) {
        this.initial = false
        const items = JSON.parse(localStorage.getItem('basket'))
        for (let i = 0; i < items.length; i++) {
          ProductService.getProductById(items[i].productId).then(
            response => {
              if (!this.products[i]) {
                this.products.push(response.data.product)
              }
            },
            error => {
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString()
            })
        }
      }
      this.calculatePrice()
    },
    onBasketHide () {
    },
    calculatePrice () {
      this.totalPrice = 0
      for (let i = 0; i < this.products.length; i++) {
        this.totalPrice += this.products[i].price
      }
    },
    removeFromCart (id) {
      let indexOfResult
      let result = this.products.filter(obj => {
        indexOfResult = this.products.indexOf(obj)
        return obj.id === id
      })
      if (result) {
        this.products.splice(indexOfResult, 1)
      }
      const items = JSON.parse(localStorage.getItem('basket'))
      result = null
      result = items.filter(obj => {
        indexOfResult = items.indexOf(obj)
        return obj.productId === id
      })
      console.log(result)
      if (result) {
        items.splice(indexOfResult, 1)
        localStorage.setItem('basket', JSON.stringify(items))
      }
      this.onBasketShow()
    },
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/')
      this.$router.go()
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
</style>
