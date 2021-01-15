import axios from 'axios'

const API_URL = 'http://localhost:8080/product'

class ProductService {
  getAllProducts () {
    return axios.get(API_URL + '/all')
  }

  getProductsPage (filters, currentPage, perPage) {
    return axios.get(API_URL + '/search/page',
      {
        params: {
          name: filters.name == null ? '' : filters.name,
          description: filters.description == null ? '' : filters.description,
          type: filters.type == null ? '' : filters.type,
          currentPage: currentPage,
          perPage: perPage
        }
      })
  }

  getProductById (id) {
    return axios.get(API_URL + '/search/id',
      {
        params: {
          id: id
        }
      })
  }

  deleteProductById (id) {
    console.log(id)
    return axios.delete(API_URL + '/delete',
      {
        params: {
          id: id
        }
      })
  }

  saveProduct (product) {
    console.log(product)
    return axios.post(API_URL + '/save',
      {
        id: product.id,
        name: product.name == null ? '' : product.name,
        type: product.type == null ? '' : product.type,
        price: product.price,
        imgSource: product.imgSource,
        toppings: product.newToppings
      })
  }
}
export default new ProductService()
