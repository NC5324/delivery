<template>
  <div style="height: 100vh; background-color: #42b983;" id="app">
    <div>
      <b-navbar style="height: 80px" toggleable="lg" fixed="top" :variant="this.variant">
        <b-navbar-brand  href="/">
          <img width="200px" height="60px" src="https://www.pngkey.com/png/full/490-4902011_new-york-pizza-logo-png-transparent-new-york.png">
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

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

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item right>
              <b-button  @click="test">
                {{this.profilePageName}}
              </b-button>
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      profilePageName: '',
      variant: 'transparent',
      isUserScrolling: false
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
      this.profilePageName = 'Profile'
    } else {
      this.profilePageName = 'Login'
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
    handleScroll (event) {
      if (window.scrollY > 100) {
        this.variant = 'white'
      } else {
        this.variant = this.$router.currentRoute.fullPath === '/' ? 'transparent' : 'white'
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

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
@media (max-width: 768px) {
  nav.navbar {
    background: lightgray;
  }
}
.navbar-light .navbar-nav .nav-link{
  color:red!important;
}
</style>
