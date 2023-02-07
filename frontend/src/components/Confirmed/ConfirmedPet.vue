<template>
  <div width="260" height="250">
    <v-row class = "top-row">
        <img
        class="image"
        width="80"
        :src="require('@/assets/placeholder/placeholder_dog.png')"
      />
    </v-row>
    <br />
    <div class="text-center">
    <h3>로이 (8세)</h3>
    <h4>강아지 - 이탈리안 그레이하운드</h4>
    <br />
    <p>노견이 이렇게 귀여워도 되는건가</p>
    </div>
  </div>
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
