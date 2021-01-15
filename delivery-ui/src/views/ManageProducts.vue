<template>
  <div>
    <b-table striped hover stacked="sm" :items="products" :fields="fields" :filters="filters">
      <template #cell(toppings)="row">
        <div v-for="(topping, index) in row.item.toppings" :key="topping" style="display: inline">
          {{topping.name + (index &lt; (row.item.toppings.length - 1) ? ', ' : '')}}
        </div>
      </template>
      <template #cell(actions)="row">
        <b-button size="sm" @click="row.toggleDetails" style="min-width: 5rem" class="mr-2">
          {{ row.detailsShowing ? 'Скрий' : 'Покажи'}}
        </b-button>
      </template>
      <template #row-details="row">
        <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
          <b-card id="preview"
            :title="row.item.name"
            :img-src="row.item.imgSource ? row.item.imgSource : 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png'"
            img-alt="Image"
            img-top
            style="min-width: 22rem ;max-width: 22rem; min-height: 30rem;"
            class="mb-2 mr-2 ml-2 mt-2"
          >
            <b-card-text>
              <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua. Pellentesque elit ullamcorper dignissim cras.
              </p>
              <strong>Toppings: {{row.item.toppings}}</strong>
            </b-card-text>
          </b-card>
          <b-card id="edit" class="mb-2 mr-2 ml-2 mt-2">
            <b-form>
              <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
                <b-form-input id="input-1" v-model="row.item.name" type="text" disabled></b-form-input>
              </b-form-group>
              <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg"  label-for="input-2">
                <b-form-input id="input-2" v-model="row.item.type" type="text" disabled></b-form-input>
              </b-form-group>
              <b-form-group id="input-group-4" label="Топинги: " label-align="left" label-size="lg" v-slot="{ ariaDescribedby }">
                <b-form-checkbox-group
                  v-model="row.item.newToppings"
                  id="checkboxes-4"
                  :aria-describedby="ariaDescribedby"
                  size="lg"
                  v-for="topping in allToppings" :key="topping"
                  stacked
                  align="left"
                >
                  <b-form-checkbox :value="topping">{{topping.name}}</b-form-checkbox>
                </b-form-checkbox-group>
              </b-form-group>
            </b-form>
            <template #footer>
              <b-button-group>
                <b-button class="m-2 text-light" @click="saveProduct(row.item)" variant="success">Запази промените</b-button>
                <b-button class="m-2 text-light" variant="warning">Откажи промените</b-button>
                <b-button class="m-2 text-light" variant="danger">Премахни от базата данни</b-button>
              </b-button-group>
            </template>
          </b-card>
        </b-card-group>
      </template>
    </b-table>
    <b-pagination
      align="fill"
      v-model="currentPage"
      :per-page="perPage"
      :total-rows="rows"
      @input="searchProducts"
      first-number
      last-number
      hide-ellipsis
      class="mt-4"
      size="md"
      variant="transparent"
    >
    </b-pagination>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png" alt="">
  </div>
</template>

<script>
import ProductService from '@/services/product-service'
import ToppingService from '@/services/topping-service'
import Product from '@/models/product'
export default {
  name: 'ManageProduct',
  data () {
    return {
      successBox: '',
      currentPage: 1,
      perPage: 8,
      rows: '',
      products: [],
      allToppings: [
        {
          id: 0,
          name: '',
          type: ''
        }],
      fields: [
        { key: 'name', label: 'Име' },
        { key: 'type', label: 'Категория' },
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
    this.getAllToppings()
    this.searchProducts()
  },
  methods: {
    searchProducts () {
      console.log(this.filters)
      ProductService.getProductsPage(this.filters, this.currentPage, this.perPage).then(
        response => {
          this.products = []
          this.pushAll(this.products, response.data.products)
          this.rows = response.data.totalItems
          // window.scrollTo(0, 0)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    getAllToppings () {
      ToppingService.getAllToppings().then(
        response => {
          this.allToppings = response.data
          console.log(this.allToppings)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    print (something) {
      console.log(something)
    },
    pushAll (target, input) {
      for (let i = 0; i < input.length; i++) {
        const p = input[i]
        target.push(new Product(p.id, p.name, p.price, p.type, p.imgSource, p.toppings, p.toppings))
      }
    },
    saveProduct (product) {
      ProductService.saveProduct(product).then(
        () => {
          this.searchProducts()
          this.successBox = ''
          this.$bvModal.msgBoxOk('Data was submitted successfully', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
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
