<template>
  <div>
        <b-container style="background-image: url(https://images.pexels.com/photos/129731/pexels-photo-129731.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260)" class="center-screen">
          <b-tabs pills content-class="mt-3">
          <b-tab title="Пици" active>
          <b-card-group>
            <div v-for="(product, index) in products" :key="product">
              <b-card
                :title="product.name"
                :img-src="product.imgSource"
                img-alt="Image"
                img-top
                img-height="200rem"
                tag="article"
                style="min-width: 20rem ;max-width: 20rem; min-height: 25rem;"
                class="mb-2 mr-2 ml-2 mt-2"
                border-variant="dark"
              >

                <b-card-text>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                  ut labore et dolore magna aliqua. Pellentesque elit ullamcorper dignissim cras.
                </b-card-text>
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
          </b-tab>
          <b-tab title="Десерти"><p>Това да не ви е сладкарница</p></b-tab>
          <b-tab title="За пиене"><p>За пиене: вода от чешмата 1лв./200мл.</p></b-tab>
          </b-tabs>
        </b-container>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png">
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
        name: 'name',
        type: 'type'
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
.top{
  margin-top: 85px;
}
.center-screen {
  display: flex;
  flex-direction: column;
}
</style>
