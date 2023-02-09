<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-send</v-icon>
        <h2>내가 보낸 상담 제안 확인하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <SendMatchingCard></SendMatchingCard>
      </div>
    </div>
  </div>
</template>

<script>
import SendMatchingCard from '@/components/SendMatching/SendMatchingCard.vue';

export default {
  name: "SendMatching",
  components: {
    SendMatchingCard
  }
};
</script>

<style scoped>

</style>