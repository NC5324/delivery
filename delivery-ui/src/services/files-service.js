import axios from 'axios'
import { authHeader } from '@/services/auth-header'

const API_URL = 'http://localhost:8080/api/files'

class UploadFilesService {
  upload (file, onUploadProgress) {
    const formData = new FormData()
    formData.append('file', file)
    const headers = {
      Authorization: authHeader().Authorization,
      'Content-Type': 'multipart/form-data'
    }
    return axios.post(API_URL + '/upload', formData, {
      headers,
      onUploadProgress
    })
  }

  getFiles () {
    const headers = {
      Authorization: authHeader().Authorization,
      'Content-Type': 'multipart/form-data'
    }
    return axios.get(API_URL + '/all',
      {
        headers
      })
  }

  getFilesPage (filters, currentPage, perPage) {
    return axios.get(API_URL + '/search/page',
      {
        params: {
          type: filters.type == null ? '' : filters.type,
          currentPage: currentPage,
          perPage: perPage
        },
        headers: {
          Authorization: authHeader().Authorization,
          'Content-Type': 'multipart/form-data'
        }
      })
  }

  getFilesCount () {
    const headers = {
      Authorization: authHeader().Authorization,
      'Content-Type': 'multipart/form-data'
    }
    axios.get(API_URL + '/all',
      {
        headers
      }).then(
      response => {
        return response.data.length
      })
  }
}
export default new UploadFilesService()
