<template>
  <div style="color: green;" class="container top">
      <b-card id="editNew" class="mb-2 mr-2 ml-2 mt-2">
        <b-form>
          <b-form-group id="input-group-1" label="Име : " label-align="left" label-for="input-1">
            <b-form-input size="md" id="input-1" v-model="blankMember.firstName" type="text"></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-2" label="Фамилия: " label-align="left" label-for="input-2">
            <b-form-input size="md" id="input-2" v-model="blankMember.lastName" type="text"></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-6" label="Потребителско име: " label-align="left" label-for="input-6">
            <b-form-input size="md" id="input-6" v-model="blankMember.username" type="text"></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-5" label="E-mail адрес: " label-align="left" label-for="input-4">
            <b-form-input size="md" id="input-5" v-model="blankMember.email" type="text"></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-5" label="Телефон: " label-align="left" label-for="input-5">
            <b-form-input size="md" id="input-5" v-model="blankMember.phoneNumber" type="text"></b-form-input>
          </b-form-group>
        </b-form>
        <template #footer>
          <b-button-group>
            <b-button class="m-2 text-light" @click="editProfile(blankMember)" variant="success">Запази промените</b-button>
          </b-button-group>
        </template>
      </b-card>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png">
  </div>
</template>

<script>
import Member from '@/models/member'
import MemberService from '@/services/member-service'
export default {
  name: 'Profile',
  data () {
    return {
      blankMember: new Member(this.$store.state.auth.user.id, this.$store.state.auth.user.username, this.$store.state.auth.user.password, this.$store.state.auth.user.email, this.$store.state.auth.user.phoneNumber, this.$store.state.auth.user.firstName, this.$store.state.auth.user.lastName)
    }
  },
  mounted () {
    this.resetBlankMember()
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  methods: {
    handleLogout () {
      this.$store.dispatch('auth/logout').then(
        () => {
          this.$router.push('/login')
          this.$router.go()
        },
        error => {
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    editProfile (member) {
      MemberService.saveMember(member).then(
        response => {
          this.showModal(response.data.message)
          this.handleLogout()
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    showModal (message) {
      this.successBox = ''
      this.$bvModal.msgBoxOk(message, {
        title: 'Съобщение',
        size: 'sm',
        buttonSize: 'sm',
        okVariant: 'success',
        headerClass: 'p-2 border-bottom-0',
        footerClass: 'p-2 border-top-0',
        centered: true
      })
    },
    resetBlankMember () {
      this.blankMember = new Member(this.$store.state.auth.user.id, this.$store.state.auth.user.username, this.$store.state.auth.user.password, this.$store.state.auth.user.email, this.$store.state.auth.user.phoneNumber, this.$store.state.auth.user.firstName, this.$store.state.auth.user.lastName)
    }
  }
}
</script>

<style scoped>
.top{
  margin-top: 80px;
}
</style>
