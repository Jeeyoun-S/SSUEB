<template>
  <div class="main-error">
    <img
      class="errorImg"
      :src="require('@/assets/fail/universeNotFound.gif')"
    />
  </div>
</template>

<style scoped>
.main-error {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 600px;
}
.errorImg {
  object-fit: cover;
  height: 300px;
  width: 600px;
  object-position: 30% 32%;
}
</style>
