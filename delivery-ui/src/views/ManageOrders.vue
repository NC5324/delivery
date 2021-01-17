<template>
  <div>
    <b-table style="text-align: left" stacked="lg" striped hover :items="orders" :fields="fields">
      <template #cell(orderItems)="orderItemRow">
        <table style="width: 100%;">
          <tr>
            <th>Име</th>
            <th>Количество</th>
          </tr>
          <tr v-for="(item, index) in orderItemRow.item.orderItems" :key="index">
            <td>{{item.product.name}}</td>
            <td>{{item.quantity}}</td>
          </tr>
        </table>
      </template>
      <template #cell(status)="statusRow">
        <b-form-select v-model="statusRow.item.status" :options="statusOptions" @change="updateStatus(statusRow.item)"></b-form-select>
      </template>
      <template #cell(actions)="actionRow">
        <b-button style="width: 100%" variant="danger" @click="deleteOrder(actionRow.item.id)">Изтрий</b-button>
      </template>
    </b-table>
    <div v-if="dataIsEmpty">
      <strong>Не съществуват поръчки</strong>
    </div>
  </div>
</template>

<script>
import OrderService from '../services/order-service'
import Order from '../models/order'
export default {
  name: 'ManageOrders',
  data () {
    return {
      orders: [],
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'orderItems', label: 'Продукти' },
        { key: 'firstName', label: 'Име' },
        { key: 'lastName', label: 'Фамилия' },
        { key: 'email', label: 'E-mail' },
        { key: 'phoneNumber', label: 'Телефон' },
        { key: 'status', label: 'Статус' },
        { key: 'actions', label: 'Действия' }
      ],
      statusOptions: ['Приета', 'Изпратена', 'Получена', 'Отказана']
    }
  },
  mounted () {
    this.getAllOrders()
  },
  computed: {
    dataIsEmpty () {
      return !this.orders || this.orders.length === 0
    }
  },
  methods: {
    getAllOrders () {
      OrderService.getAllOrders().then(
        response => {
          this.orders = []
          this.pushAll(this.orders, response.data)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    updateStatus (item) {
      console.log(item)
      OrderService.saveOrder(item).then(
        response => {
          console.log(response.data)
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    deleteOrder (id) {
      OrderService.deleteOrderById(id).then(
        response => {
          console.log(response.data)
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
    pushAll (target, input) {
      for (let i = 0; i < input.length; i++) {
        const o = input[i]
        const member = o.member
        target.push(new Order(o.id, member.id, member.firstName, member.lastName, member.email, member.phoneNumber, o.orderItems, o.status))
      }
    }
  }

}
</script>

<style scoped>

</style>
