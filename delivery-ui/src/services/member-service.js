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

  saveMember (member, newFlag) {
    console.log(member)
    return axios.post(API_URL + '/save',
      {
        id: newFlag ? null : member.id,
        name: member.name == null ? '' : member.name,
        type: member.type == null ? '' : member.type
      },
      {
        headers: authHeader()
      })
  }
}
export default new MemberService()
