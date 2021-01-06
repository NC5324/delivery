<template>
  <div class="top" id="wrapper">
    <div>
      <h3>{{message}}</h3>
      <b-table striped responsive
               id="personTable"
               :bordered=true
               table-variant="primary"
               :items="products"
               :fields="fields"
               :current-page="currentPage"
               :per-page="0">

        <template slot="top-row" slot-scope="{ fields }">
          <td v-for="(field,index) in fields" :key="field.id">
            <div v-if="index === fields.length - 1">
              <b-button size="sm" @click="searchProducts">
                Search
              </b-button>
            </div>
            <div v-else>
              <input v-model="filters[field.key]">
            </div>
          </td>
        </template>

        <template v-slot:cell(actions)="row">
          <b-button @click="addToCart(row.item.id)" size="sm">Add to cart</b-button>
        </template>
      </b-table>

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
    </div>
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
      perPage: 2,
      products: [{
        id: '',
        name: '',
        description: '',
        type: ''
      }],
      fields: [
        { key: 'name', label: 'Име' },
        { key: 'description', label: 'Описание' },
        { key: 'type', label: 'Тип на продукт' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: [{
        name: 'name',
        description: 'description',
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
  margin-top: 100px;
}
</style>
