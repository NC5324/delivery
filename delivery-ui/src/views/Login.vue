<template>
  <div class="center-screen top">
    <b-card class="login-card">
      <b-form @submit="onSubmit" :state="loginSuccess" v-if="show">
        <b-form-group
          id="input-group-1"
          label="Username:"
          label-for="input-1"
        >
          <b-form-input id="input-1"
            v-model="user.username"
            type="text"
            placeholder="Enter username"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="Password:" label-for="input-2">
          <b-form-input
            id="input-2"
            v-model="user.password"
            type="password"
            placeholder="Enter password"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-invalid-feedback style="margin-bottom: 10px; font-style: bold;" :state="loginSuccess">
          Bad credentials. Try again.
        </b-form-invalid-feedback>
        <b-form-valid-feedback>
        </b-form-valid-feedback>
        <b-button type="submit" variant="primary">Login</b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import User from '../models/user'

export default {
  name: 'Login',
  data () {
    return {
      user: new User('', ''),
      message: '',
      show: true,
      loginSuccess: true
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  created () {
    if (this.loggedIn) {
      this.$router.push('/profile')
    }
  },
  methods: {
    onSubmit (event) {
      event.preventDefault()
      this.$store.dispatch('auth/login', this.user).then(
        () => {
          this.$router.push('/profile')
          this.$router.go()
        },
        error => {
          this.loginSuccess = false
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    }
  }
}
</script>

<style scoped>
.center-screen {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  min-height: 30vh;
}
.login-card{
  max-width: 50vh;
}
.top {
  margin-top: 100px;
}
</style>
