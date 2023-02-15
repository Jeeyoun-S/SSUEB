<template>
  <div class="star">
    <div class="star-inner">
      <div class="star-average">
        <v-icon v-for="(value, index) in average" :key="index" size="55" color="yellow-darken-3">
          {{ value }}
        </v-icon>
        <span>{{ Math.round(getAverageRating * 10) / 10 }}</span>
      </div>
    </div>
    <div class="star-inner">
      <div class="star-each">
        <div class="star-each-item" v-for="rate in getConsultantRating.slice(0, 3)" :key="rate.num">
          <v-rating
            class="pr-2"
            v-model="rate.num"
            empty-icon="mdi-star-outline" full-icon="mdi-star" half-icon="mdi-star-half"
            color="yellow-darken-3" density="compact"
            half-increments readonly
          ></v-rating>
          <span>{{ rate.cnt }}</span>
        </div>
      </div>
      <div class="star-each">
        <div class="star-each-item" v-for="rate in getConsultantRating.slice(3, 6)" :key="rate.num">
          <v-rating
            class="pr-2"
            v-model="rate.num"
            empty-icon="mdi-star-outline" full-icon="mdi-star" half-icon="mdi-star-half"
            color="yellow-darken-3" density="compact"
            half-increments readonly
          ></v-rating>
          <span>{{ rate.cnt }}</span>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "MyPageConsultantStar",
  data() {
    return {
      average: ["mdi-star-outline", "mdi-star-outline", "mdi-star-outline", "mdi-star-outline", "mdi-star-outline"]
    }
  },
  computed: {
    getConsultantRating() {
      return this.$store.getters.getConsultantRating;
    },
    getAverageRating() {
      return this.$store.getters.getAverageRating;
    }
  },
  created() {
    const rating = this.getAverageRating * 10;
    var a = parseInt(rating / 10);
    var b = parseInt(rating % 10);

    for (let i=0; i<5 ; i++) {
      if (a > 0) {
        this.average[i] = "mdi-star";
        a -= 1;
      } else if (b > 0) {
        this.average[i] = "mdi-star-half-full";
        b = 0;
      } else {
        this.average[i] = "mdi-star-outline"
      }
    }
  }
}
</script>

<style>
.star {
  display: flex;
  flex-direction: row;

  padding: 20px;
}
.star .star-inner {
  display: flex;
  flex-direction: column;
}
.star .star-inner .star-average {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  
  padding-right: 20px;
  height: 100%;
}
.star .star-inner .star-average span {
  font-weight: 800;
  font-size: 30px;
  padding-left: 10px;
}
.star .star-inner .star-each {
  display: flex;
  flex-direction: row;
}
.star .star-inner .star-each .star-each-item {
  width: 200px;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: left;
  padding: 10px;
}
</style>