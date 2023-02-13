<template>
  <v-dialog v-model="modifyBoardOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn class="ma-1" :rounded="0" color="primary" size="large" v-bind="props">수정</v-btn>
    </template>
    <v-card class="pa-5">
      <v-card-title>
        <h3>게시글 수정</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="forms">
            <v-text-field v-model="modifyBoard.boardTitle" label="제목" variant="underlined"
              maxlength="100" counter="100"
            ></v-text-field>
            <v-textarea v-model="modifyBoard.boardContent" label="내용"
              variant="underlined" maxlength="500" counter="500"
              row-height="500" rows="8" auto-grow
            ></v-textarea>
            <v-file-input v-model="modifyBoard.boardFile" label="첨부파일" variant="underlined"></v-file-input>
          </v-form>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" variant="text" @click="modifyBoardOpen = false">취소</v-btn>
        <v-btn color="primary" variant="text" @click="validate()">등록</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "FreeBoardModify",
  data() {
    return {
      modifyBoardOpen: false,
      modifyBoard: {
        // 입력 받는 정보
        boardNo: 0,
        boardTitle: null,
        boardContent: null,
        boardFile: null,
        // 넣어줄 정보 -> 안 보내줘도 됨
        // userId: null,
        // userNickname: null,
        // boardFlag: null,
      },
    }
  },
  props: {
    boardDetail: Object
  },
  methods: {
    validate() {

    }
  },
  created() {
    console.log(this.boardDetail)
    this.modifyBoard.boardTitle = this.boardDetail.boardTitle;
    this.modifyBoard.boardContent = this.boardDetail.boardContent;
  }
}
</script>

<style></style>