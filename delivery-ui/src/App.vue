<template>
  <div style="min-height: 100vh; min-width: 100vw; background-image: url(https://images.pexels.com/photos/326333/pexels-photo-326333.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260);" id="app">
      <b-navbar toggleable="lg" :variant="this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'">
        <b-navbar-brand  href="/">
          <img width="200px" height="60px" src="https://www.pngkey.com/png/full/490-4902011_new-york-pizza-logo-png-transparent-new-york.png">
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse">

        </b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="/">
              <b-button>Начало</b-button>
            </b-nav-item>
            <b-nav-item href="/about">
              <b-button>За нас</b-button>
            </b-nav-item>
            <b-nav-item href="/menu">
              <b-button>Меню</b-button>
            </b-nav-item>
          </b-navbar-nav>

          <!--Right aligned nav items-->
          <b-navbar-nav class="ml-auto">
            <b-nav-item right>
              <b-button  @click="test">
                {{this.profilePageName}}
              </b-button>
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      profilePageName: '',
      variant: 'transparent'
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
    window.addEventListener('scroll', this.handleScroll)
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
        this.variant = 'white'
      } else {
        this.variant = this.$router.currentRoute.fullPath === '/' ? 'white' : 'transparent'
      }
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
