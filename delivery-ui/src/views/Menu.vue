<template>
  <div id="content">
    <b-container style="background-image: url(https://images.unsplash.com/photo-1524365521362-0a59611ecbf1?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=634&q=80)" class="center-screen">
      <b-form-group
        v-slot="{ ariaDescribedby }"
      >
        <b-form-radio-group
          id="btn-radios-2"
          v-model="filters.type"
          :aria-describedby="ariaDescribedby"
          button-variant="outline-dark"
          name="radio-btn-outline"
          @change="searchProducts()"
          buttons
        >
          <b-form-radio class="mr-2" value="pizza">Пици</b-form-radio>
          <b-form-radio class="mr-2" value="dessert">Десерти</b-form-radio>
          <b-form-radio class="mr-2" value="beverage">Напитки</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
      <b-card-group>
        <div v-for="(product, index) in products" :key="product">
          <b-card
            :title="product.name"
            style="min-width: 22rem ;max-width: 22rem; min-height: 36rem;"
            class="mb-2 mr-2 ml-2 mt-2"
          >
            <template #header>
              <b-card-img :src="product.imgSource ? product.imgSource : 'http://www.dominos.bg/gallery/fmobile/1266medium.png'" alt="image" top>
              </b-card-img>
            </template>
            <template #default>
              <b-card-text style="text-align: left">
                <strong>Топинги: </strong>
                <div v-for="topping in product.toppings" :key="topping">
                  {{topping.name}}
                </div>
              </b-card-text>
            </template>
            <template #footer>
              <b-form-spinbutton @change="setQuantity(index)" v-model="quantity[index]" inline size="sm" ></b-form-spinbutton>
              <b-button @click="addToCart(product.id, index)" style="margin-left: 1rem; margin-bottom: 3px;" size="sm" variant="success">Добави в количката</b-button>
            </template>
          </b-card>
        </div>
      </b-card-group>
      <b-pagination
        pills
        align="fill"
        v-model="currentPage"
        :per-page="perPage"
        :total-rows="rows"
        @input="searchProducts"
        first-number
        last-number
        hide-ellipsis
        class="mt-4"
        size="lg"
      >
      </b-pagination>
    </b-container>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png" alt="">
  </div>
</template>

<script>
import ProductService from '../services/product-service'
import OrderItem from '../models/orderItem'
export default {
  name: 'Menu',
  data () {
    return {
      message: '',
      currentPage: 1,
      perPage: 8,
      rows: '',
      quantity: [],
      products: [{
        id: '',
        name: '',
        imgSource: '',
        type: ''
      }],
      fields: [
        { key: 'name', label: 'Име' },
        { key: 'type', label: 'Тип на продукт' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: [{
        type: 'pizza'
      }]
    }
  },
  mounted () {
    this.searchProducts()
    for (let i = 0; i < this.perPage; i++) {
      this.quantity.push(1)
    }
  },
  methods: {
    searchProducts () {
      console.log(this.filters)
      ProductService.getProductsPage(this.filters, this.currentPage, this.perPage).then(
        response => {
          this.products = response.data.products
          this.rows = response.data.totalItems
          window.scrollTo(0, 0)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    addToCart (id, index) {
      let items = JSON.parse(localStorage.getItem('basket'))
      if (!items) {
        items = []
      }
      items.push(new OrderItem(id, this.quantity[index]))
      localStorage.setItem('basket', JSON.stringify(items))
    },
    setQuantity (index) {
      this.quantity[index]++
    }
  }
}
</script>

<style scoped>
.center-screen {
  display: flex;
  flex-direction: column;
}
</style>
