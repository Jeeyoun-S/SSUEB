<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class=" page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-bulletin-board</v-icon>
        <h2>자유게시판</h2>
        <FreeBoardRegist></FreeBoardRegist>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <FreeBoardTable></FreeBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import FreeBoardTable from "@/components/FreeBoard/FreeBoardTable.vue"
import FreeBoardRegist from "@/components/FreeBoard/FreeBoardRegist.vue"
import NowLoading from '@/views/NowLoading.vue';

export default {
  name: "FreeBoard",
  components: {
    FreeBoardTable,
    FreeBoardRegist,
    NowLoading
  },
  data() {
    return {
      loaded: true
    }
  },
  created() {
    
  }
}
</script>

<style>

</style>