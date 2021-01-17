import axios from 'axios'
import { authHeader } from './auth-header'

const API_URL = 'http://localhost:8080/api/order'

class OrderService {
  getAllOrders () {
    return axios.get(API_URL + '/all', { headers: authHeader() })
  }

  deleteAllOrders () {
    return axios.delete(API_URL + '/purge',
      {
        headers: authHeader()
      }).then(
      response => {
        console.log(response.data)
      },
      error => {
        this.message =
          (error.response && error.response.data) ||
          error.message ||
          error.toString()
      })
  }

  saveOrder (order) {
    return axios.post(API_URL + '/save',
      {
        id: order.id,
        memberId: order.memberId,
        orderItems: order.orderItems,
        status: order.status
      },
      {
        headers: authHeader()
      })
  }

  deleteOrderById (id) {
    return axios.delete(API_URL + '/delete', {
      headers: authHeader(),
      params: {
        id: id
      }
    })
  }
}
export default new OrderService()
