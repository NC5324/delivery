<template>
  <div class="center-screen top">
    <b-card style="min-width: 50%" class="login-card">
      <b-form :state="loginSuccess" v-if="show">
        <b-form-group id="input-group-1" label="Потребителско име:" label-for="input-1">
          <b-form-input id="input-1"
            v-model="user.username"
            type="text"
            placeholder="Въведете потребителско име"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="Парола:" label-for="input-2">
          <b-form-input
            id="input-2"
            v-model="user.password"
            type="password"
            placeholder="Въведете парола"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-invalid-feedback style="margin-bottom: 10px; font-weight: bold;" :state="loginSuccess">
          Bad credentials. Try again.
        </b-form-invalid-feedback>
        <b-form-valid-feedback>
        </b-form-valid-feedback>
        <b-button @click="onSubmit" variant="primary">Вход</b-button>
        <b-button style="margin-left: 10px;" @click="requestRegister" variant="primary">Регистрация</b-button>
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
      show: true,
      loginSuccess: null
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
    },
    requestRegister () {
      this.$router.push('/register')
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
  margin-top: 80px;
}
</style>
