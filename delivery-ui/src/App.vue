<template>
  <div style="min-height: 100vh; min-width: 100vw; background-image: url(https://images.pexels.com/photos/326333/pexels-photo-326333.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260);" id="app">
    <b-navbar sticky toggleable="lg" :variant="this.nav_bg_variant">
      <b-navbar-brand  href="/">
        <img width="200px" height="60px" src="https://www.pngkey.com/png/full/490-4902011_new-york-pizza-logo-png-transparent-new-york.png" alt="brandLogo">
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse">
      </b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!--Left aligned nav items-->
        <b-navbar-nav>
          <b-nav-item href="/"><b-button>Начало</b-button></b-nav-item>
          <b-nav-item href="/about"><b-button>За нас</b-button></b-nav-item>
          <b-nav-item href="/menu"><b-button>Меню</b-button> </b-nav-item>
        </b-navbar-nav>

        <!--Right aligned nav items-->
      </b-collapse>
      <b-navbar-nav class="ml-auto">
        <b-nav-item right><b-avatar :size="40" icon="basket2" v-b-toggle.sidebar-right></b-avatar></b-nav-item>
        <b-nav-item right><b-avatar :size="40" button @click="test"></b-avatar></b-nav-item>
      </b-navbar-nav>
    </b-navbar>
    <router-view/>
    <b-sidebar @shown="onBasketShow" @hidden="onBasketHide"  id="sidebar-right" title="Количка" right bg-variant="dark" text-variant="light">
      <template #footer="{ hide }">
        <div class="d-flex bg-dark text-light align-items-center px-3 py-2">
          <strong class="mr-auto">Обща цена: {{totalPrice}} лв.</strong>
          <b-button size="sm" @click="hide">Close</b-button>
        </div>
      </template>
      <b-card-group v-for="item in products" :key="item" class="px-3 py-2">
        <b-card bg-variant="white" text-variant="dark" :header="item.name" class="text-center mt-3">
          <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit. {{item.toppings}}</b-card-text>
          <template #footer>
            <strong class="mr-auto">Цена: {{item.price}} лв.</strong>
            <b-avatar button @click="removeFromCart(item.id)" class="ml-5" icon="x-circle"></b-avatar>
          </template>
        </b-card>
      </b-card-group>
    </b-sidebar>
  </div>
</template>

<script>
import ProductService from './services/product-service'
export default {
  data () {
    return {
      profilePageName: '',
      nav_bg_variant: '',
      products: [{
        id: '',
        name: '',
        price: 0,
        imgSource: '',
        type: ''
      }],
      totalPrice: 0,
      show: false
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    },
    validation () {
      return this.profilePageName === 'Profile'
    }
  },
  created () {
    if (this.loggedIn) {
      this.profilePageName = 'Профил'
    } else {
      this.profilePageName = 'Вход'
    }
    this.onBasketShow()
    window.addEventListener('scroll', this.handleScroll)
    this.nav_bg_variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
  },
  methods: {
    test () {
      if (this.validation) {
        this.$router.push('/profile')
      } else {
        this.$router.push('/login')
      }
    },
    handleScroll () {
      if (window.scrollY > 50) {
        this.nav_bg_variant = 'white'
      } else {
        this.nav_bg_variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
      }
    },
    onBasketShow () {
      if (localStorage.getItem('basket')) {
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
