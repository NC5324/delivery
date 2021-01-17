import axios from 'axios'
import { authHeader } from '@/services/auth-header'

const API_URL = 'http://localhost:8080/api/files'

class UploadFilesService {
  upload (file) {
    const formData = new FormData()
    formData.append('file', file)
    const headers = {
      Authorization: authHeader().Authorization,
      'Content-Type': 'multipart/form-data'
    }
    return axios.post(API_URL + '/upload', formData, {
      headers
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

  deleteFileByName (name) {
    return axios.delete(API_URL + '/delete', {
      headers: authHeader(),
      params: {
        name: name
      }
    })
  }
}
export default new UploadFilesService()
