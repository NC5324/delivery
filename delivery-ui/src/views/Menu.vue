<template>
    <div class="top">
      <b-container style="height:  background-color: antiquewhite">
        <b-card-group>
          <div v-for="product in products" :key="product">
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
                <b-button @click="addToCart(product.id)" style="width: 100%; height: 100%" variant="success">Add to cart</b-button>
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
</style>
