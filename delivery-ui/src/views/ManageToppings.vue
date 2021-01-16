<template>
  <div>
    <b-form id="category and stuff">
      <div id="header" style="min-height: 20px"></div>
      <b-form-group
        v-slot="{ ariaDescribedby }"
      >
        <b-form-radio-group
          v-if="false"
          id="btn-radios-2"
          v-model="filters.type"
          :aria-describedby="ariaDescribedby"
          button-variant="outline-dark"
          name="radio-btn-outline"
          @change="searchToppings()"
          buttons
        >
          <b-form-radio class="mr-2" value="pizza">Топинги за пици</b-form-radio>
          <b-form-radio class="mr-2" value="dessert">Опции за десерти</b-form-radio>
          <b-form-radio class="mr-2" value="beverage">Опции за напитки</b-form-radio>
        </b-form-radio-group>
        <b-button class="mr-2" variant="success" @click="resetBlankTopping()" v-b-modal.newTopping>Нов топинг</b-button>
        <b-button class="mr-2" variant="primary" href="/manage/products">Управление на продукти</b-button>
      </b-form-group>
    </b-form>
    <b-table striped hover stacked="sm" :items="toppings" :fields="fields" :filters="filters">
      <template #cell(actions)="row">
        <b-button @click="row.toggleDetails" style="min-width: 7rem" class="mr-2">
          {{(row.detailsShowing ? 'Скрий' : 'Покажи')}}
        </b-button>
      </template>
      <template #row-details="row">
        <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
          <b-card id="edit" class="mb-2 mr-2 ml-2 mt-2">
            <b-form>
              <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
                <b-form-input size="lg" id="input-1" v-model="row.item.name" type="text"></b-form-input>
              </b-form-group>
              <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg" label-for="input-2">
                <b-form-input size="lg" id="input-2" v-model="row.item.type" type="text"></b-form-input>
              </b-form-group>
            </b-form>
            <template #footer>
              <b-button-group>
                <b-button class="m-2 text-light" @click="saveTopping(row.item, false, row)" variant="success">Запази промените</b-button>
                <b-button class="m-2 text-light" @click="deleteTopping(row.item.id)" variant="danger">Премахни от базата данни</b-button>
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
      @input="searchToppings"
      first-number
      last-number
      hide-ellipsis
      class="mt-4"
      size="md"
    >
    </b-pagination>
    <b-modal id="newTopping" title="Създаване на нова опция на продукт" size="lg">
      <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
        <b-card id="editNew" class="mb-2 mr-2 ml-2 mt-2">
          <b-form>
            <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
              <b-form-input size="lg" id="input-1" v-model="blankTopping.name" type="text"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg" label-for="input-2">
              <b-form-input size="lg" id="input-2" v-model="blankTopping.type" type="text"></b-form-input>
            </b-form-group>
          </b-form>
        </b-card>
      </b-card-group>
      <template #modal-footer = "{ ok, cancel }">
        <b-button variant="success" @click="saveTopping(blankTopping); ok;">
          Създай
        </b-button>
        <b-button variant="danger" @click="resetBlankTopping(); cancel;">
          Нулирай
        </b-button>
      </template>
    </b-modal>
    <div id="footer" style="min-height: 20px"></div>
  </div>
</template>

<script>
import ToppingService from '@/services/topping-service'
import Topping from '@/models/topping'
export default {
  name: 'ManageTopping',
  data () {
    return {
      successBox: '',
      currentPage: 1,
      perPage: 8,
      rows: '',
      toppings: [],
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'name', label: 'Име' },
        { key: 'type', label: 'Категория' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: {
        type: 'pizza'
      },
      blankTopping: new Topping(null, 'Нов топинг', 'pizza')
    }
  },
  mounted () {
    this.searchToppings()
  },
  methods: {
    searchToppings () {
      console.log('I have been summoned.')
      ToppingService.getAllToppings().then(
        response => {
          this.toppings = response.data
          this.rows = response.data.length
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    resetBlankTopping () {
      this.blankTopping = new Topping(null, 'Нов топинг', this.filters.type)
    },
    saveTopping (topping, newFlag, row) {
      ToppingService.saveTopping(topping, newFlag).then(
        response => {
          if (newFlag) {
            this.$router.go()
          } else {
            row.toggleDetails()
            this.showModal(response.data.message)
          }
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    deleteTopping (id) {
      ToppingService.deleteToppingById(id).then(
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
