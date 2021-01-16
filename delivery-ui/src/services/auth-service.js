import axios from 'axios'
import { authHeader } from '@/services/auth-header'

const API_URL = 'http://localhost:8080/api/auth'

class AuthService {
  login (user) {
    return axios.post(API_URL + '/login',
      {
        username: user.username,
        password: user.password
      })
      .then(
        response => {
          if (response.data.accessToken) {
            localStorage.setItem('user', JSON.stringify(response.data))
          }

          return response.data
        }
      )
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post(API_URL + '/signup',
      {
        username: user.username,
        password: user.password,
        email: user.email
      })
  }

  adminRegister (request) {
    return axios.post(API_URL + '/admin/signup',
      {
        username: request.username,
        password: request.password,
        firstName: request.firstName,
        lastName: request.lastName,
        phoneNumber: request.phoneNumber,
        email: request.email,
        roles: request.newRoles
      },
      {
        headers: authHeader()
      })
  }
}
export default new AuthService()
