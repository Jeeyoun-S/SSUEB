<template>
  <div class="create-matching border-sheet-four">
    <div class="create-matching-title border-sheet-four">
      <v-icon class="mr-2" size="x-large">mdi-paw</v-icon>
      <h2>반려인에게 상담 제안 보내기</h2>
    </div>
    <div class="create-matching-item border-sheet-four">
      <CreateCard v-for="n in 8" :key="n"></CreateCard>
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
@import "@/css/create-matching.css";
</style>
