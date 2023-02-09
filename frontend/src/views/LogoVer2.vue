<template>
  <div class="logo-ver2">
    <a href="/"><img :src="logoImageVer2" /></a>
  </div>
</template>

<script>
export default {
  name: 'LogoVer2',
  data() {
    return {
      logoImageVer2: require('@/assets/logo/logo_ver2.png')
    }
  }
}
</script>

<style scoped>
@import "@/css/logo.css";
</style>