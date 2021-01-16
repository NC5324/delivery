<template>
  <div>
    <b-form id="category and stuff">
      <b-button class="mr-2 mb-2 mt-2" variant="success" @click="resetBlankMember()" v-b-modal.newMember>Нов профил</b-button>
    </b-form>
    <b-table striped hover stacked="sm" :items="members" :fields="fields" :filters="filters">
      <template #cell(actions)="row">
        <b-button @click="row.toggleDetails" style="min-width: 7rem" class="mr-2">
          {{(row.detailsShowing ? 'Скрий' : 'Покажи')}}
        </b-button>
      </template>
      <template #row-details="row">
        <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
          <b-card id="preview"
                  :title="row.item.firstName + row.item.lastName"
                  style="max-width: 35rem; min-height: 36rem;"
                  class="mb-2 mr-2 ml-2 mt-2"
          >
            <template #header>
              <b-card-img src="https://secure.gravatar.com/avatar/246e93e60153c5aee1f1e5dd8ecd9e3b?s=256&d=mm&r=g" alt="profile pic" top >
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
      @input="searchMembers"
      first-number
      last-number
      hide-ellipsis
      class="mt-4"
      size="md"
    >
    </b-pagination>
    <b-modal v-if="ready" id="newMember" title="Създаване на нова опция на продукт" size="lg">
      <b-card-group style="margin-bottom: 0.5rem; margin-left: 0.5rem; margin-right: 0.5rem">
        <b-card id="editNew" class="mb-2 mr-2 ml-2 mt-2">
          <b-form>
            <b-form-group id="input-group-1" label="Име на продукт: " label-align="left" label-size="lg" label-for="input-1">
              <b-form-input size="lg" id="input-1" v-model="blankMember.name" type="text"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-2" label="Категория на продукт: " label-align="left" label-size="lg" label-for="input-2">
              <b-form-input size="lg" id="input-2" v-model="blankMember.type" type="text"></b-form-input>
            </b-form-group>
          </b-form>
        </b-card>
      </b-card-group>
      <template #modal-footer = "{ ok, cancel }">
        <b-button variant="success" @click="saveMember(blankMember); ok;">
          Създай
        </b-button>
        <b-button variant="danger" @click="resetBlankMember(); cancel;">
          Нулирай
        </b-button>
      </template>
    </b-modal>
    <div id="footer" style="min-height: 20px"></div>
  </div>
</template>

<script>
import MemberService from '@/services/member-service'
import Member from '@/models/member'
export default {
  name: 'ManageMember',
  data () {
    return {
      ready: false,
      successBox: '',
      currentPage: 1,
      perPage: 8,
      rows: '',
      members: [],
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'firstName', label: 'Име' },
        { key: 'lastName', label: 'Фамилия' },
        { key: 'username', label: 'Потребителско име' },
        { key: 'password', label: 'Парола' },
        { key: 'email', label: 'e-mail адрес' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: {
        type: null
      },
      blankMember: new Member(null, 'foobar', '', 'foo@bar.com', '0000000000', 'Foo', 'Bar')
    }
  },
  mounted () {
    this.searchMembers()
  },
  methods: {
    searchMembers () {
      console.log('I have been summoned.')
      MemberService.getAllMembers().then(
        response => {
          this.members = response.data
          this.rows = response.data.length
          this.perPage = response.data.length
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    resetBlankMember () {
      this.blankMember = new Member(null, 'foobar', '', 'foo@bar.com', '0000000000', 'Foo', 'Bar')
    },
    saveMember (member, newFlag, row) {
      MemberService.saveMember(member, newFlag).then(
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
    deleteMember (id) {
      MemberService.deleteMemberById(id).then(
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
