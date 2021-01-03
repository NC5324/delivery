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
  BFormInvalidFeedback,
  BFormValidFeedback,
  NavbarPlugin,
  CarouselPlugin
} from 'bootstrap-vue'

Vue.component('b-form', BForm)
Vue.component('b-form-group', BFormGroup)
Vue.component('b-form-input', BFormInput)
Vue.component('b-form-invalid-feedback', BFormInvalidFeedback)
Vue.component('b-form-valid-feedback', BFormValidFeedback)
Vue.component('b-button', BButton)
Vue.component('b-card', BCard)

Vue.use(NavbarPlugin)
Vue.use(CarouselPlugin)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
