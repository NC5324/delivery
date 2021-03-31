<template>
  <!--<div style="color: green;" class="container top">
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
            <b-button class="m-2 text-light" @click="handleLogout" variant="danger">Sign out</b-button>
          </b-button-group>
        </template>
      </b-card>
    <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png">
  </div>-->
  <div>
    <div class="wrapper">
      <header>
        <img src="@/assets/brand-logo.png" alt="Brand Logo"  width="100px" height="150px">
        <div style="flex: 0 0 auto; margin-top: auto; margin-bottom: auto;">
          <h1 style="font-family: 'Roboto', sans-serif; color: #EED27F">
            SELECT <span style="color: #A6191D">*</span> FROM <span style="color: #A6191D">pizza</span><br>
            WHERE <span style="color: #A6191D">quality = "best"</span>
          </h1>
        </div>
      </header>
      <main>
        <article id="left">
          <div style="background: lightgray; width: 250px; flex: 0 1 250px; margin: 35px auto; border-radius: 50%; z-index: 999">
          </div>
        </article>
        <article id="right">
          <div id="top">
            <h2>{{ blankMember.firstName }} {{ blankMember.lastName }}</h2>
          </div>
          <div id="bot">
            <b-form>
              <h3>Name</h3>
              <b-form-input size="md" id="input-1" v-model="blankMember.firstName" type="text"></b-form-input>
              <h3>Username</h3>
              <b-form-input size="md" id="input-6" v-model="blankMember.username" type="text"></b-form-input>
              <h3>Email</h3>
              <b-form-input size="md" id="input-5" v-model="blankMember.email" type="text"></b-form-input>
              <h3>Phone number</h3>
              <b-form-input size="md" id="input-5" v-model="blankMember.phoneNumber" type="text"></b-form-input>
            </b-form>
            <b-button-group id="buttons" style="display: flex; margin-top: 10px;">
              <b-button class="text-light" @click="editProfile(blankMember)" variant="success" style="margin-right: 5px">Запази промените</b-button>
              <b-button class="text-light" @click="handleLogout" variant="danger" style="margin-left: 5px">Sign out</b-button>
            </b-button-group>
          </div>
        </article>
      </main>
    </div>
    <page-footer></page-footer>
  </div>
</template>

<script>
import Member from '@/models/member'
import MemberService from '@/services/member-service'
import PageFooter from '@/components/PageFooter'
export default {
  name: 'Profile',
  components: {
    PageFooter
  },
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
  margin-top: 100px;
}

h2{
  font-family: 'Roboto', sans-serif;
  color: white;
  font-size: 25px;
  font-weight: 500;

  text-align: left;
}

h3 {
  font-family: 'Roboto', sans-serif;
  color: white;
  font-size: 20px;
  font-weight: 400;

  padding-top: 2px;
  text-align: left;
}

header {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  padding: 30px 10%;
  margin-top: 50px;
}

main {
  flex: 1 0 auto;

  background: #E8A44A;
  padding: 0 50px;
  margin: 30px 10%;

  display: flex;
  flex-flow: row nowrap;
  position: relative;
}

#left {
  flex: 1 1 260px;
  display: flex;
  flex-flow: column nowrap;
  padding-right: 25px;
  margin-right: 25px;
  border-right: 2px solid white;
}

#right {
  flex: 1 1 744px;
  display: flex;
  flex-flow: column nowrap;
}

#top {
  flex: 0 0 25%;

  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-end;
}

#bot {
  margin-top: 50px;
  flex: 1 0 auto;
}

.wrapper {
  min-height: 100vh;
  display: flex;
  flex-flow: column nowrap;
}

@media screen and (max-width: 900px) {
  #left {
    display: none;
  }
}
</style>
