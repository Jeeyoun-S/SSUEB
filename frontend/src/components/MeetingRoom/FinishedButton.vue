<template>
  <v-btn class="ma-2" size="x-large" prepend-icon="mdi-exit-to-app"
    v-bind="props" color="primary" variant="tonal"
    @click="finishConsult"
  >
    상담 종료
  </v-btn>
</template>

<script>
export default {
  name: "FinishedButton",
  methods: {
    finishConsult() {
      this.$swal.fire(
        '상담 종료',
        '상담이 종료되었습니다.',
        'success'
      )
    }
  }
}
</script>

<style>

</style>