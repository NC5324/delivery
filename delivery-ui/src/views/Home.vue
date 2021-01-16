<template>
  <div>
    <b-carousel
      id="carousel-fade"
      style="text-shadow: 0px 0px 2px #000"
      fade
      indicators
    >
      <b-carousel-slide
        caption="Second Slide"
        img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron9_eq0Of8AMTw2mdEYBqwdO-1450x600.jpg"
      ></b-carousel-slide>
      <b-carousel-slide
        caption="First slide"
        img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron10-1450x600.jpg"
      ></b-carousel-slide>
      <b-carousel-slide
        caption="Third Slide"
        img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron6_9SkyhFecTAKDGx3JtpYa-1450x600.jpg"
      ></b-carousel-slide>
      <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png" alt="">
    </b-carousel>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      slide: 0,
      sliding: null
    }
  },
  methods: {
    onSlideStart (slide) {
      this.sliding = true
    },
    onSlideEnd (slide) {
      this.sliding = false
    }
  }
}
</script>

<style scoped>
</style>
