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

  saveOrder (request) {
    return axios.post(API_URL + '/save',
      {
        memberId: request.memberId,
        products: request.products,
        status: 'accepted'
      },
      {
        headers: authHeader()
      })
  }
}
export default new OrderService()
