<template>
  <v-dialog v-model="dialog" transition="dialog-bottom-transition" width="900">
    <v-card>
      <v-toolbar
        class="pa-1 pr-3 pl-3"
        color="primary"
        :title="boardDetail.boardTitle"
      >
      </v-toolbar>
      <v-sheet class="pa-5 d-flex flex-column justify-space-between" min-height="300">
        <v-card-item class="pb-5">
          <v-chip>{{ boardDetail.boardWritetime }}</v-chip>
          <v-card-text>{{ boardDetail.boardContent }}</v-card-text>
        </v-card-item>
        <v-card-actions class="justify-end">
          <v-btn variant="text" @click="dialog= false">닫기</v-btn>
        </v-card-actions>
      </v-sheet>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "NoticeBoardDetail",
  data() {
    return {
      dialog: false,
      // boardNo: null,
      boardDetail: {
        no: 5,
        boardTitle: "제목",
        boardContent: "컨텐츠",
        boardWritetime: "2023-01-25 16:43:28",
      },
    }
  },
}
</script>

<style>

</style>