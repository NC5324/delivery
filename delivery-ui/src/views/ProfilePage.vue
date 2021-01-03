<template>
  <div class="top">
    <b-button @click="handleLogout" variant="danger">Logout</b-button>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  methods: {
    handleLogout () {
      this.$store.dispatch('auth/logout').then(
        () => {
          this.$router.push('/')
          this.$router.go()
        },
        error => {
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    }
  }
}
</script>

<style scoped>
.top{
  margin-top: 100px;
}
</style>
