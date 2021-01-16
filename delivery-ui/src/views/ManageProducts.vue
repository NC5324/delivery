<template>
  <div>
    <b-form>
      <div id="header" style="min-height: 20px"></div>
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
        <b-button class="mr-2" variant="success" @click="resetBlankProduct()" v-b-modal.newProduct>Нов продукт</b-button>
        <b-button class="mr-2" variant="primary" href="/manage/product-options">Управление на опции</b-button>
      </b-form-group>
    </b-form>
    <b-table striped hover stacked="sm" :items="products" :fields="fields" :filters="filters">
      <template #cell(toppings)="row">
        <div v-for="(topping, index) in row.item.newToppings" :key="topping" style="display: inline">
          {{topping.name + (index &lt; (row.item.newToppings.length - 1) ? ', ' : '')}}
        </div>
      </template>
      <template #cell(actions)="row">
        <b-button @click="row.toggleDetails" style="min-width: 7rem" class="mr-2">
          {{(row.detailsShowing ? 'Скрий' : 'Покажи')}}
        </b-button>
      </template>
      <template #row-details="row">
        <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
          <b-card id="preview"
            :title="row.item.name"
            style="max-width: 35rem; min-height: 36rem;"
            class="mb-2 mr-2 ml-2 mt-2"
          >
            <template #header>
              <b-card-img :src="row.item.imgSource ? row.item.imgSource :
               filters.type === 'beverage' ? 'https://cdn.iconscout.com/icon/free/png-256/beverage-1468789-1242983.png'
               : (filters.type === 'pizza' ? 'http://www.dominos.bg/gallery/fmobile/1266medium.png'
               : 'https://iconarchive.com/download/i109888/lemon-liu/recipes/recipe-dessert-cake.ico')" alt="image" top >
              </b-card-img>
            </template>
            <template #default>
              <b-card-text style="text-align: left">
                <strong>Топинги: </strong>
                <div v-for="topping in row.item.newToppings" :key="topping">
                  {{topping.name}}
                </div>
              </b-card-text>
            </template>
            <template #footer>
              {{row.item.price + ' лв.'}}
            </template>
          </b-card>
          <b-card id="edit" class="mb-2 mr-2 ml-2 mt-2">
            <b-form>
              <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
                <b-form-input size="lg" id="input-1" v-model="row.item.name" type="text"></b-form-input>
              </b-form-group>
              <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg" label-for="input-2">
                <b-form-input size="lg" id="input-2" v-model="row.item.type" type="text" disabled></b-form-input>
              </b-form-group>
              <b-form-group id="input-group-3" label="Цена на продукт: " label-align="left" label-size="lg" label-for="input-3">
                <b-form-input min="0" size="lg" id="input-3" v-model="row.item.price" type="number"></b-form-input>
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
                <b-button class="m-2 text-light" @click="saveProduct(row.item, false)" variant="success">Запази промените</b-button>
                <b-button class="m-2 text-light" @click="deleteProduct(row.item.id)" variant="danger">Премахни от базата данни</b-button>
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
      >
      </b-pagination>
    <b-modal id="newProduct" title="Създаване на нов продукт" size="lg">
      <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
        <b-card id="previewNew"
                :title="blankProduct.name"
                style="max-width: 22rem; min-height: 36rem;"
                class="mb-2 mr-2 ml-2 mt-2"
        >
          <template #header>
            <b-card-img :src="blankProduct.imgSource ? blankProduct.imgSource :
               filters.type === 'beverage' ? 'https://cdn.iconscout.com/icon/free/png-256/beverage-1468789-1242983.png'
               : (filters.type === 'pizza' ? 'http://www.dominos.bg/gallery/fmobile/1266medium.png'
               : 'https://iconarchive.com/download/i109888/lemon-liu/recipes/recipe-dessert-cake.ico')" alt="image" top >
            </b-card-img>
          </template>
          <template #default>
            <b-card-text style="text-align: left">
              <strong>Топинги: </strong>
              <div v-for="topping in blankProduct.newToppings" :key="topping">
                {{topping.name}}
              </div>
            </b-card-text>
          </template>
          <template #footer>
            {{ blankProduct.price + ' лв.' }}
          </template>
        </b-card>
        <b-card id="editNew" class="mb-2 mr-2 ml-2 mt-2">
          <b-form>
            <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
              <b-form-input size="lg" id="input-1" v-model="blankProduct.name" type="text"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg" label-for="input-2">
              <b-form-input size="lg" id="input-2" v-model="blankProduct.type" type="text" disabled></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-3" label="Цена на продукт: " label-align="left" label-size="lg" label-for="input-3">
              <b-form-input min="0" size="lg" id="input-3" v-model="blankProduct.price" type="number"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-4" label="Топинги: " label-align="left" label-size="lg" v-slot="{ ariaDescribedby }">
              <b-form-checkbox-group
                v-model="blankProduct.newToppings"
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
        </b-card>
      </b-card-group>
      <template #modal-footer = "{ ok, cancel }">
        <b-button variant="success" @click="saveProduct(blankProduct); ok;">
          Създай
        </b-button>
        <b-button variant="danger" @click="resetBlankProduct(); cancel;">
          Нулирай
        </b-button>
      </template>
    </b-modal>
    <div id="footer" style="min-height: 20px"></div>
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
      currentPage: 1,
      perPage: 8,
      rows: '',
      products: [],
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'name', label: 'Име' },
        { key: 'type', label: 'Категория' },
        { key: 'toppings', label: 'Съдържа' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: {
        type: 'pizza'
      },
      allToppings: [
        {
          id: 0,
          name: '',
          type: ''
        }
      ],
      blankProduct: new Product(null, 'Нов продукт', 0, '', null, [], [])
    }
  },
  mounted () {
    this.getAllToppings()
    this.searchProducts()
  },
  methods: {
    searchProducts () {
      console.log('I have been summoned.')
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
    resetBlankProduct () {
      this.blankProduct = new Product(null, 'Нов продукт', 0, this.filters.type, null, [], [])
    },
    pushAll (target, input) {
      for (let i = 0; i < input.length; i++) {
        const p = input[i]
        target.push(new Product(p.id, p.name, p.price, p.type, p.imgSource, p.toppings, p.toppings))
      }
    },
    saveProduct (product, newFlag) {
      ProductService.saveProduct(product, newFlag).then(
        () => {
          this.$router.go()
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    deleteProduct (id) {
      ProductService.deleteProductById(id).then(
        () => {
          this.$router.go()
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
