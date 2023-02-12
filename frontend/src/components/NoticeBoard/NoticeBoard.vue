<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-alert</v-icon>
        <h2>공지사항</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <NoticeBoardTable :boardSummaryList="boardSummaryList"></NoticeBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import NoticeBoardTable from "@/components/NoticeBoard/NoticeBoardTable.vue"
import { getNoticeBoard } from "@/api/communityNotice.js"

export default {
  name: "NoticeBoard",
  components: {
    NoticeBoardTable,
    NowLoading
  },
  data() {
    return {
      loaded: false,
      boardSummaryList: [],
    }
  },
  async created() {
    this.loaded = false;
    this.boardSummaryList = await getNoticeBoard();
    this.loaded = true;
  }
}
</script>

<style>

</style>