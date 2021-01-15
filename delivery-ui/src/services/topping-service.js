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
}
export default new ToppingService()
