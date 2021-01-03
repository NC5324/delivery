import axios from 'axios'

const AUTH_URL = 'http://localhost:8080/api/auth'

class AuthService {
  login (user) {
    return axios.post(AUTH_URL + '/login',
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
    return axios.post(AUTH_URL + '/signup',
      {
        username: user.username,
        password: user.password,
        email: user.email
      })
  }
}
export default new AuthService()
