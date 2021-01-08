<template>
  <div class="center-screen" style="margin-top: 80px">
    <b-card style="margin-top: 20px; max-width: 100vh; min-width: 50vh">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
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

        <b-form-group id="input-group-3" label="E-mail адрес:" label-for="input-3">
          <b-form-input
            id="input-3"
            v-model="user.email"
            :state = "isEmailValid"
            placeholder="Въведете е-mail"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-valid-feedback>
        </b-form-valid-feedback>
        <b-button type="submit" variant="primary">Регистрация</b-button>
        <b-button type="reset" variant="primary" style="margin-left: 10px;">Изчисти</b-button>
        <b-button @click="cancel" variant="primary" style="margin-left: 10px;">Отказ</b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import User from '../models/user'

export default {
  name: 'Profile',
  data () {
    return {
      user: new User('', '', ''),
      show: true,
      reg: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    },
    isEmailValid () {
      return (this.user.email === '') ? null : (this.reg.test(this.user.email))
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
      if (!this.isEmailValid) {
        return
      }
      this.$store.dispatch('auth/register', this.user).then(
        () => {
          this.$router.push('/login')
          this.$router.go()
        },
        error => {
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    onReset (event) {
      event.preventDefault()
      // Reset our form values
      this.user.email = ''
      this.user.username = ''
      this.user.password = ''
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    cancel () {
      this.$router.push('/login')
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
</style>
