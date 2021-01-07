<template>
  <div style="height: 100vh; background-color: #42b983;" id="app">
    <div>
      <b-navbar style="height: 80px" toggleable="lg" fixed="top" :variant="this.variant">
        <b-navbar-brand href="/">Generic Pizza</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="/">Home</b-nav-item>
            <b-nav-item href="/about">About</b-nav-item>
            <b-nav-item href="/menu">Menu</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item right @click="test">{{this.$router.currentRoute.fullPath}}</b-nav-item>
            <b-nav-item right @click="test">{{this.profilePageName}}</b-nav-item>
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
</style>
