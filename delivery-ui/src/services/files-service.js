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
    console.log(headers)
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
    console.log(headers)
    return axios.get(API_URL + '/all',
      {
        headers
      })
  }
}
export default new UploadFilesService()
