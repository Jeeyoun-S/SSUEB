<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-view-grid-plus</v-icon>
        <h2>반려인에게 상담 제안 보내기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <CreateCard v-for="n in 8" :key="n"></CreateCard>
      </div>
    </div>
    </div>
</template>

<script>
import CreateCard from "@/components/CreateMatching/CreateCard.vue";

export default {
  name: "CreateMatching",
  components: {
    CreateCard,
  },
};
</script>

<style scoped>
</style>
