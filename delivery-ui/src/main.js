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
  BRow,
  BCol,
  BFormSelect,
  BFormFile,
  BBadge,
  LayoutPlugin,
  NavbarPlugin,
  TabsPlugin,
  ModalPlugin,
  CarouselPlugin, TablePlugin, PaginationPlugin, CardPlugin, SidebarPlugin, AvatarPlugin, BootstrapVueIcons, JumbotronPlugin, ImagePlugin
} from 'bootstrap-vue'
import ManageFiles from '@/views/ManageFiles'

Vue.component('b-form-file', BFormFile)
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
Vue.component('b-row', BRow)
Vue.component('b-col', BCol)
Vue.component('nc-choose-files', ManageFiles)
Vue.component('b-form-select', BFormSelect)
Vue.component('b-badge', BBadge)

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
Vue.use(JumbotronPlugin)
Vue.use(ImagePlugin)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
