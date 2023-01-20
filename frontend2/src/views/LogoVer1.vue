<template>
  <v-sheet elevation="2" class="mx-auto sheet logo" height="290" width="370" rounded>
    <div class="logo-inner">
      <img class="big" :src="logoImage" />
      <h1>SSUEB</h1>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: 'LogoVer1',
  data() {
    return {
      logoImage: require('@/assets/logo_crop.jpg')
    }
  }
}
</script>

<style scoped>
@import "@/css/logo.css";
</style>