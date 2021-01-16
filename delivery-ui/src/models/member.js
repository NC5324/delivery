export default class Member {
  constructor (id, username, password, email, phoneNumber, firstName, lastName, roles, newRoles) {
    this.id = id
    this.username = username
    this.password = password
    this.email = email
    this.phoneNumber = phoneNumber
    this.firstName = firstName
    this.lastName = lastName
    this.roles = roles
    this.newRoles = newRoles
  }
}
