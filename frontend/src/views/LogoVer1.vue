<template>
  <div class="logo-ver1">
    <img :src="logoImageVer1" />
  </div>
</template>

<script>
export default {
  name: 'LogoVer1',
  data() {
    return {
      logoImageVer1: require('@/assets/logo/logo_ver1.png')
    }
  }
}
</script>

<style scoped>
@import "@/css/logo.css";
</style>