import axios from 'axios'
import { authHeader } from '@/services/auth-header'

const API_URL = 'http://localhost:8080/member'

class MemberService {
  getAllMembers () {
    return axios.get(API_URL + '/all',
      {
        headers: authHeader()
      })
  }

  getAllRoles () {
    return axios.get(API_URL + '/roles/all',
      {
        headers: authHeader()
      })
  }

  saveMember (request) {
    return axios.post(API_URL + '/save',
      {
        id: request.id,
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

  getMembersPage (filters, currentPage, perPage) {
    return axios.get(API_URL + '/search/page',
      {
        params: {
          currentPage: currentPage,
          perPage: perPage
        },
        headers: authHeader()
      })
  }

  deleteMemberById (id) {
    console.log('Delete ' + id)
    return axios.delete(API_URL + '/delete',
      {
        params: {
          id: id
        },
        headers: authHeader()
      })
  }
}
export default new MemberService()
