<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center align-center flex-column"
      width="1100" height="200" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props" @click="moveCreate()"
    >
      <img :src="require('@/assets/fail/move_dog_empty.gif')" />
      <v-card-title class="pb-5">
        {{ message }}
        새로운 상담을 신청하러 가시겠습니까?
      </v-card-title>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  name: "MoveCreateReservation",
  props: {
    message: String
  },
  methods: {
    moveCreate() {
      this.$router.push("/create-reservation");
    }
  }
}
</script>

<style scoped>
* {
  font-family: 'Hana_handwriting';
  font-size: 30px;
}
</style>