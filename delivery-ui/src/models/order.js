export default class Order {
  constructor (id, memberId, memberFirstName, memberLastName, memberEmail, memberPhoneNumber, orderItems, status) {
    this.id = id
    this.memberId = memberId
    this.firstName = memberFirstName
    this.lastName = memberLastName
    this.email = memberEmail
    this.phoneNumber = memberPhoneNumber
    this.orderItems = orderItems
    this.status = status
  }
}
