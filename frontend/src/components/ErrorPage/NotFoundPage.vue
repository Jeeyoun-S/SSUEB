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
  margin-right: 30%;
  /* margin-bottom: 20%; */
}
.errorImg {
  margin-left: 30%;
  width: 100%;
}
</style>
