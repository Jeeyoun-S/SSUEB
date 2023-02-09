<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-chip class="mr-3" color="primary">
          {{ String(boardDetail.no).padStart(4, '0') }}
        </v-chip>
        <h2>{{ boardDetail.boardTitle }}</h2>
      </div>
      <div class="pa-8 page-inner-items border-sheet-four">
        <v-sheet>
          {{ boardDetail.userNickname }}
          {{ boardDetail.boardWritetime }}
          {{ boardDetail.boardViews }}
          {{ boardDetail.boardHeartnum }}
          {{ boardDetail.boardContent }}
        </v-sheet>
        <v-divider class="mt-3 mb-3"></v-divider>
        <v-sheet class="max-page">
          <FreeBoardReply></FreeBoardReply>
        </v-sheet>
      </div>
    </div>
  </div>
</template>

<script>
import FreeBoardReply from "@/components/FreeBoard/FreeBoardReply.vue";

export default {
  name: "FreeBoardDetail",
  components: {
    FreeBoardReply
  },
  data() {
    return {
      loaded: true,
      boardDetail: {
        no: 5,
        userId: "",
        userNickname: "닉네임",
        boardTitle: "제목",
        boardContent: "컨텐츠",
        boardFile: "파일명",
        boardFlag: 0,
        boardHeartnum: 1,
        boardViews: 2,
        boardWritetime: "2023-01-25 16:43:28",
      }
    }
  },
  created() {
    // 게시글 상세 조회 API
    // 게시글 번호 this.$route.params.no로 조회해 boardDetail에 넣기
  }
}
</script>

<style>

</style>