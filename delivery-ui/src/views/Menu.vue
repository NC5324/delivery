<template>
    <div class="top">
      <b-container style="background-color: antiquewhite">
        <b-card-group columns>
          <div v-for="product in products" :key="product">
            <b-card
              :title="product.name"
              :img-src="product.imgSource"
              img-alt="Image"
              img-top
              img-height="200rem"
              tag="article"
              style="min-width: 80% ;max-width: 80%; min-height: 50%; max-height: 50%"
              class="mb-2 ml-2 mr-2 mt-2"
              footer-tag="footer"
              border-variant="dark"
            >

              <b-card-text>
                {{ product.toppings }}
              </b-card-text>
              <template #footer>
                <b-button @click="addToCart(product.id)" style="width: 100%" variant="success">Add to cart</b-button>
              </template>
            </b-card>
          </div>
        </b-card-group>

        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          @input="searchProducts"
          aria-controls="personTable"
          first-text="First"
          prev-text="Prev"
          next-text="Next"
          last-text="Last"
          class="mt-4"
          align="center"
          size="lg"
        >
        </b-pagination>
      </b-container>
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
      rows: '',
      perPage: 6,
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
  },
  methods: {
    searchProducts () {
      console.log(this.filters)
      ProductService.getProductsPage(this.filters, this.currentPage, this.perPage).then(
        response => {
          this.products = response.data.products
          this.rows = response.data.totalItems
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    addToCart (id) {
      let items = JSON.parse(localStorage.getItem('basket'))
      if (!items) {
        items = []
      }
      items.push(new OrderItem(id, 1))
      localStorage.setItem('basket', JSON.stringify(items))
    }
  }
}
</script>

<style scoped>
.top{
  margin-top: 80px;
}
.center-screen {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  min-height: 30vh;
  margin-left: 10vw;
  margin-right: 10vw;
  max-width: 80vw;
  border: thin solid black;
}
.navbar-light .navbar-nav .nav-link{
  color:white!important
}
</style>
