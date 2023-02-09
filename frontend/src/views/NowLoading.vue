<template>
  <div class="loading">
    <v-progress-circular
      :size="70"
      :width="7"
      color="primary"
      indeterminate
    ></v-progress-circular>
    <span>Loading</span>
  </div>
</template>

<script>
export default {
  name: "NowLoading"
}
</script>

<style>
.loading {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 600px;
  padding: 20px;
}
.loading span {
  font-size: 50px;
  font-weight: 800;
}
</style>