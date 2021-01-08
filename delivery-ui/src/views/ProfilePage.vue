<template>
  <div style="color: green;" class="container top">
    <header class="jumbotron">
      <h3>
        <strong>{{currentUser.username + '\'s'}}</strong> Profile
      </h3>
      <b-button @click="handleLogout" variant="danger">Logout</b-button>
      <b-button style="margin-left: 10px" @click="saveOrder" variant="success">Send order</b-button>
      <b-button @click="clearCart" style="margin-left: 10px" variant="dark">Clear cart</b-button>
      <p style="margin-top: 20px;">
        <strong>Token:</strong>
        {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
      </p>
      <p>
        <strong>Id:</strong>
        {{currentUser.id}}
      </p>
      <p>
        <strong>Email:</strong>
        {{currentUser.email}}
      </p>
      <strong>Authorities:</strong>
      <ul>
        <li v-for="(role,index) in currentUser.roles" :key="index">{{role}}</li>
      </ul>
      <strong>Количка:</strong>
      <ul>
        <li v-for="(product,index) in shoppingCart" :key="index"><strong style="horiz-align: center">{{product}}</strong></li>
      </ul>
      <b-card>
        <b-form>

          <b-form-group id="input-group-1" label="Потребителско име:" label-for="input-1">
            <b-form-input id="input-1"
                          v-model="currentUser.username"
                          type="text"
                          disabled
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-3" label="E-mail адрес:" label-for="input-3">
            <b-form-input
              id="input-3"
              v-model="currentUser.email"
              disabled
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-2" label="Текуща парола:" label-for="input-2">
            <b-form-input
              id="input-2"
              v-model="currentUser.password"
              type="password"
              :required="madeChanges"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-2" label="Нова парола:" label-for="input-2">
            <b-form-input
              id="input-2"
              v-model="this.newPassword"
              type="password"
              placeholder="Въведете нова парола"
              required
            ></b-form-input>
          </b-form-group>

          <b-button @click="editProfile" variant="primary">Запази промените</b-button>
        </b-form>
      </b-card>
      <h5>{{madeChanges}}</h5>
    </header>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png">
  </div>
</template>

<script>
import OrderService from '../services/order-service'
export default {
  name: 'Profile',
  newPassword: null,
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    shoppingCart () {
      return JSON.parse(localStorage.getItem('basket'))
    },
    madeChanges () {
      return this.newPassword !== null
    }
  },
  methods: {
    handleLogout () {
      this.$store.dispatch('auth/logout').then(
        () => {
          this.$router.push('/')
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
    clearCart () {
      localStorage.removeItem('basket')
      this.$router.go()
    },
    saveOrder () {
      const request = {
        memberId: this.currentUser.id,
        products: JSON.parse(localStorage.getItem('basket')),
        status: 'accepted'
      }
      OrderService.saveOrder(request).then(
        () => {
          console.log('order registered successfully')
          localStorage.removeItem('basket')
          this.$router.go()
        },
        error => {
          console.log('failed to register order')
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    editProfile () {

    }
  }
}
</script>

<style scoped>
.top{
  margin-top: 80px;
}
</style>
