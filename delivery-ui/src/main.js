import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as axios from 'axios'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {
  BForm,
  BFormGroup,
  BFormInput,
  BButton,
  BCard,
  BCardGroup,
  BFormInvalidFeedback,
  BFormValidFeedback,
  BFormSpinbutton,
  LayoutPlugin,
  NavbarPlugin,
  CarouselPlugin, TablePlugin, PaginationPlugin
} from 'bootstrap-vue'

Vue.component('b-form-spinbutton', BFormSpinbutton)
Vue.component('b-form', BForm)
Vue.component('b-form-group', BFormGroup)
Vue.component('b-form-input', BFormInput)
Vue.component('b-form-invalid-feedback', BFormInvalidFeedback)
Vue.component('b-form-valid-feedback', BFormValidFeedback)
Vue.component('b-button', BButton)
Vue.component('b-card', BCard)
Vue.component('b-card-group', BCardGroup)

Vue.use(NavbarPlugin)
Vue.use(CarouselPlugin)
Vue.use(TablePlugin)
Vue.use(PaginationPlugin)
Vue.use(LayoutPlugin)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
