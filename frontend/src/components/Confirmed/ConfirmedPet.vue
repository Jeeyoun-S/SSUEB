<template>
  <v-card class="pt-2" width="220" height="320" elevation="0">
    <div class="d-flex align-center flex-column">
      <img
        class="image"
        width="80"
        :src="require('@/assets/placeholder/placeholder_dog.png')"
      />
      <v-card-title class="pb-0">로이 (8세)</v-card-title>
      <v-card-subtitle>강아지</v-card-subtitle>
      <v-card-subtitle>이탈리안 그레이하운드</v-card-subtitle>
    </div>
    <v-card-text>
      노견이 이렇게 귀여워도 되는건가
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "ConfirmedPet",
};
</script>

<style scoped>
.top-row {
    display: flex;
    justify-content: space-around;
}
</style>
