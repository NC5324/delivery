<template>
  <div style="display: flex; flex-direction: column; min-height: 100vh;">
    <header>
      <img src="@/assets/brand-logo.png" alt="Brand Logo"  width="100px" height="150px">
      <div style="flex: 0 0 auto; margin-top: auto; margin-bottom: auto;">
        <h1 style="font-family: 'Roboto', sans-serif; color: #EED27F">
          SELECT <span style="color: #A6191D">*</span> FROM <span style="color: #A6191D">pizza</span><br>
          WHERE <span style="color: #A6191D">quality = "best"</span>
        </h1>
      </div>
    </header>
    <section class="thumbnail">
      <b-carousel
        id="carousel-fade"
        :interval=interval
        style="text-shadow: 0px 0px 2px #000;"
        fade
        indicators
      >
        <b-carousel-slide
          caption="The best pizza in the world!"
          img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron10-1450x600.jpg"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Our dough rises 87 days in a special environment!"
          img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron9_eq0Of8AMTw2mdEYBqwdO-1450x600.jpg"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="You wouldn't want to miss our amazing deals! If you have any questions call Kole."
          img-src="https://irp-cdn.multiscreensite.com/96de223d/dms3rep/multi/desktop/jumbotron6_9SkyhFecTAKDGx3JtpYa-1450x600.jpg"
        ></b-carousel-slide>
        <img height="20px" src="https://i.stack.imgur.com/Vkq2a.png" alt="">
      </b-carousel>
    </section>
    <main>
      <div class="about">
        <h2 style="text-align: left;">$.get('api/about')</h2>
        <p style="text-align: left; font-family: 'Roboto', sans-serif; color: white;">
          Bacon ipsum dolor amet fatback doner tri-tip leberkas ham beef ribs frankfurter bacon pig salami cupim turkey t-bone ground round tail.
          Landjaeger pork belly bresaola, brisket jerky chislic ball tip flankpancetta. Flank kevin doner beef, hamburger corned beef tongue swine pastrami. Brisket landjaeger cow meatloaf shankle filet mignon corned beefbiltong.
        </p>
      </div>
    </main>
    <footer>
      <h2 style="text-align: center; border-bottom: 3px solid white;">ORDER NOW: +359 XX XXXX XXX</h2>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      slide: 0,
      sliding: null,
      interval: 4000
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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

*::selection {
  background: gray;
}

h2{
  font-family: 'Roboto', sans-serif;
  color: white;
  font-size: 25px;
  font-weight: 500;

  padding-bottom: 20px;
  padding-top: 20px;
}

p {
  font-family: 'Roboto', sans-serif;
  color: white;
  line-height: 27px;
  font-size: 20px;
  font-weight: 400;
}

header {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  padding: 30px 10%;
  margin-top: 50px;
}

footer {
  flex: 1 0 300px;
  background: #E8A44A;
  padding: 0 10%;
}

main {
  background: #E8A44A;
  padding: 80px 10% 100px;
  margin-bottom: 30px;

  display: flex;
  flex-flow: column wrap;
}

.about {
  margin-bottom: auto;
  margin-top: auto;
}

.thumbnail {
  padding: 0 10%;
  background: url("../assets/vectorpaint.svg") left bottom no-repeat;
  background-size: 100% 75%;
}

.thumbnail * {
  border: 3px solid white;
}
</style>
