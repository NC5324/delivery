<template>
  <div>
    <b-table striped hover :items="products" :fields="fields" :filters="filters"></b-table>
  </div>
</template>

<script>
import ProductService from "@/services/product-service";

export default {
  name: 'ManageProduct',
  data() {
    return {
      currentPage: 1,
      perPage: 8,
      rows: '',
      products: [{
        id: '',
        name: '',
        imgSource: '',
        type: ''
      }],
      fields: [
        { key: 'name', label: 'Име' },
        { key: 'type', label: 'Тип на продукт' },
        { key: 'toppings', label: 'Съдържа' },
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
  methods : {
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
    }
  }

}
</script>

<style scoped>

</style>
