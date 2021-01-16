import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as axios from 'axios'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {
  BButton,
  BButtonGroup,
  BForm,
  BFormGroup,
  BFormInput,
  BFormInvalidFeedback,
  BFormValidFeedback,
  BFormSpinbutton,
  BFormCheckbox,
  BFormCheckboxGroup,
  BFormRadioGroup,
  BFormRadio,
  LayoutPlugin,
  NavbarPlugin,
  TabsPlugin,
  ModalPlugin,
  CarouselPlugin, TablePlugin, PaginationPlugin, CardPlugin, SidebarPlugin, AvatarPlugin, BootstrapVueIcons
} from 'bootstrap-vue'

Vue.component('b-form-spinbutton', BFormSpinbutton)
Vue.component('b-form', BForm)
Vue.component('b-form-group', BFormGroup)
Vue.component('b-form-input', BFormInput)
Vue.component('b-form-invalid-feedback', BFormInvalidFeedback)
Vue.component('b-form-valid-feedback', BFormValidFeedback)
Vue.component('b-button', BButton)
Vue.component('b-button-group', BButtonGroup)
Vue.component('b-form-checkbox-group', BFormCheckboxGroup)
Vue.component('b-form-checkbox', BFormCheckbox)
Vue.component('b-form-radio', BFormRadio)
Vue.component('b-form-radio-group', BFormRadioGroup)

Vue.use(NavbarPlugin)
Vue.use(CarouselPlugin)
Vue.use(TablePlugin)
Vue.use(PaginationPlugin)
Vue.use(LayoutPlugin)
Vue.use(TabsPlugin)
Vue.use(CardPlugin)
Vue.use(SidebarPlugin)
Vue.use(AvatarPlugin)
Vue.use(BootstrapVueIcons)
Vue.use(ModalPlugin)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
