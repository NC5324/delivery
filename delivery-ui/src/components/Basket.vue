<template>
  <b-sidebar @shown="onBasketShow" @hidden="onBasketHide" id="sidebar-basket" title="Количка" right shadow>
    <div class="px-3 py-2">
      <div v-for="(orderItem, index) in orderItems" :key="index">
        <b-card :title="getProductById(orderItem.productId).name" bg-variant="white" text-variant="dark" class="text-center mt-3">
          <template #header>
            <b-card-img :src="getProductById(orderItem.productId).imgSource" alt="image" top>
            </b-card-img>
          </template>
          <template #default>
            <b-card-text style="text-align: left">
              <strong>Топинги: </strong>
              <div v-for="(topping, index) in getProductById(orderItem.productId).toppings" :key="index">
                {{topping.name}}
              </div>
            </b-card-text>
          </template>
          <template #footer>
            <b-button style="width: 100%;" class="m-2">Количество: {{orderItem.quantity}}</b-button>
            <b-button style="width: 100%;" class="m-2">Цена на 1: {{getProductById(orderItem.productId).price}} лв.</b-button>
            <b-button style="width: 100%;" class="m-2">Цена: {{getProductById(orderItem.productId).price * orderItem.quantity}} лв.</b-button>
            <b-button style="width: 100%;" class="m-2" variant="danger" @click="removeFromCart(orderItem.productId)"><b-icon-x-circle></b-icon-x-circle> Премахни </b-button>
          </template>
        </b-card>
      </div>
    </div>
    <template #footer>
      <div class="d-flex bg-dark text-light align-items-center px-3 py-2">
        <strong class="mr-auto">Обща цена: {{calculatePrice}} лв.</strong>
        <b-button variant="success" @click="saveOrder">Поръчай</b-button>
      </div>
    </template>
  </b-sidebar>
</template>

<script>
import ProductService from '../services/product-service'
import Order from '@/models/order'
import OrderService from '@/services/order-service'
export default {
  name: 'Basket',
  data () {
    return {
      orderItems: []
    }
  },
  computed: {
    calculatePrice () {
      this.validateBasket()
      const basket = JSON.parse(localStorage.getItem('basket'))
      let price = 0
      for (let i = 0; i < basket.orderItems.length; i++) {
        const oi = basket.orderItems[i]
        price = price + this.getProductById(oi.productId).price
      }

      return price
    }
  },
  methods: {
    getProductById (id) {
      this.loadProductById(id)
      return JSON.parse(localStorage.getItem('product' + id))
    },
    onBasketShow () {
      this.loadBasket()
      localStorage.setItem('pog', 'POGGERS')
    },
    onBasketHide () {
      localStorage.removeItem('pog')
    },
    loadBasket () {
      this.validateBasket()
      const basket = JSON.parse(localStorage.getItem('basket'))
      this.orderItems = basket.orderItems
    },
    validateBasket () {
      let basket = JSON.parse(localStorage.getItem('basket'))
      if (!basket) {
        basket = new Order()
        basket.orderItems = []
        localStorage.setItem('basket', JSON.stringify(basket))
      }
      this.orderItems = basket.orderItems
    },
    loadProductById (productId) {
      ProductService.getProductById(productId).then(
        (response) => {
          localStorage.setItem('product' + productId, JSON.stringify(response.data))
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    removeFromCart (productId) {
      const basket = JSON.parse(localStorage.getItem('basket'))
      let index = null
      for (let i = 0; i < basket.orderItems.length; i++) {
        if (basket.orderItems[i].productId === productId) {
          index = i
        }
      }

      const items = basket.orderItems
      if (index !== null) {
        items.splice(index, 1)
      }

      localStorage.setItem('basket', JSON.stringify(basket))
      this.loadBasket()
    },
    saveOrder () {
      const basket = JSON.parse(localStorage.getItem('basket'))
      const user = JSON.parse(localStorage.getItem('user'))
      if (!user) {
        this.$router.push('/login')
      } else {
        basket.memberId = user.id
        basket.firstName = user.firstName
        basket.lastName = user.lastName
        basket.email = user.email
        basket.phoneNumber = user.phoneNumber
        basket.status = 'Приета'
      }
      const order = new Order(null, basket.memberId, basket.firstName, basket.lastName, basket.email, basket.phoneNumber, basket.orderItems, basket.status)
      console.log(order)
      OrderService.saveOrder(order).then(
        (response) => {
          localStorage.removeItem('basket')
          this.$root.$emit('bv::toggle::collapse', 'sidebar-basket')
          this.showModal(response.data)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    showModal (message) {
      this.successBox = ''
      this.$bvModal.msgBoxOk(message, {
        title: 'Съобщение',
        size: 'sm',
        buttonSize: 'sm',
        okVariant: 'success',
        headerClass: 'p-2 border-bottom-0',
        footerClass: 'p-2 border-top-0',
        centered: true
      })
    }
  }
}
</script>

<style scoped>

</style>
