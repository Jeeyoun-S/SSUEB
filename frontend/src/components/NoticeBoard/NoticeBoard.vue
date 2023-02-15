<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-alert</v-icon>
        <h2>공지사항</h2>
        <FreeBoardRegist v-if="userAuth == 'ROLE_ADMIN'"></FreeBoardRegist>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <NoticeBoardTable :boardSummaryList="boardSummaryList"></NoticeBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import FreeBoardRegist from "@/components/FreeBoard/FreeBoardRegist.vue"
import NoticeBoardTable from "@/components/NoticeBoard/NoticeBoardTable.vue"
import { getNoticeBoard } from "@/api/communityNotice.js"
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "NoticeBoard",
  computed: {
    ...mapState(userStore, ["userAuth"]),
  },
  components: {
    NoticeBoardTable,
    NowLoading,
    FreeBoardRegist
  },
  data() {
    return {
      loaded: false,
      boardSummaryList: [],
    }
  },
  methods: {
    pushNoticeList(board) {
      this.boardSummaryList.push(board);
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