<template>
  <div class="logo">
    <div class="logo-inner">
      <img class="big" :src="logoImage" />
      <h1>SSUEB</h1>
    </div>
  </div>
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