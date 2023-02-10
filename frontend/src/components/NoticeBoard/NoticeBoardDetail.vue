<template>
  <v-dialog v-model="dialog" transition="dialog-bottom-transition" width="900">
    <v-card>
      <v-toolbar
        class="pa-1"
        color="primary"
        :title="boardDetail.boardTitle"
      >
      </v-toolbar>
      <v-card-item>
        <v-chip>{{ boardDetail.boardWritetime }}</v-chip>
      </v-card-item>
      <v-card-text>
        {{ boardDetail.boardContent }}
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn variant="text" @click="dialog= false">닫기</v-btn>
      </v-card-actions>
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