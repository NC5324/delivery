import axios from 'axios'
import { authHeader } from './auth-header'

const API_URL = 'http://localhost:8080/member'

class UserService {

  saveUser (user) {
    return axios.post( API_URL + '/save',
      {
        memberRequest: user
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
}

export default new UserService()
