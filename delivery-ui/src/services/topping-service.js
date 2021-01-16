import axios from 'axios'
import { authHeader } from '@/services/auth-header'

const API_URL = 'http://localhost:8080/topping'

class ToppingService {
  getAllToppings () {
    return axios.get(API_URL + '/all',
      {
        headers: authHeader()
      })
  }

  getToppingsPage (filters, currentPage, perPage) {
    return axios.get(API_URL + '/search/page',
      {
        params: {
          name: filters.name == null ? '' : filters.name,
          type: filters.type == null ? '' : filters.type,
          currentPage: currentPage,
          perPage: perPage
        },
        headers: authHeader()
      })
  }

  deleteToppingById (id) {
    console.log('Delete ' + id)
    return axios.delete(API_URL + '/delete',
      {
        params: {
          id: id
        },
        headers: authHeader()
      })
  }

  saveTopping (topping, newFlag) {
    console.log(topping)
    return axios.post(API_URL + '/save',
      {
        id: newFlag ? null : topping.id,
        name: topping.name == null ? '' : topping.name,
        type: topping.type == null ? '' : topping.type
      },
      {
        headers: authHeader()
      })
  }
}
export default new ToppingService()
