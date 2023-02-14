<template>
  <v-dialog v-model="modifyBoardOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn class="ma-1" :rounded="0" color="primary" size="large" v-bind="props"
        @click="updateData"
      >수정</v-btn>
    </template>
    <v-card class="pa-5">
      <v-progress-circular class="align-self-center" v-if="!loaded"
        :size="50"
        color="primary"
        indeterminate
      ></v-progress-circular>
      <div v-else>
        <v-card-title>
          <h3>게시글 수정</h3>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="forms">
              <v-text-field v-model="modifyBoard.boardTitle" label="제목" variant="underlined"
                maxlength="100" counter="100" :rules="boardRules.boardTitle"
              ></v-text-field>
              <v-textarea v-model="modifyBoard.boardContent" label="내용"
                variant="underlined" maxlength="500" counter="500"
                row-height="500" rows="8" auto-grow :rules="boardRules.boardContent"
              ></v-textarea>
              <v-file-input v-model="boardFile" label="첨부파일" variant="underlined" :rules="boardRules.boardFile"></v-file-input>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" variant="text" @click="modifyBoardOpen = false">취소</v-btn>
          <v-btn color="primary" variant="text" @click="validate()">등록</v-btn>
        </v-card-actions>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import { putBoard } from "@/api/communityFree.js"
export default {
  name: "FreeBoardModify",
  data() {
    return {
      modifyBoardOpen: false,
      boardFile: null,
      modifyBoard: {
        // 입력 받는 정보
        no: 0,
        boardTitle: null,
        boardContent: null,
        // 넣어줄 정보 -> 안 보내줘도 됨
        // userId: null,
        // userNickname: null,
        // boardFlag: null,
      },
      loaded: false,
      boardRules: {
        boardTitle: [
          (v) => (v != null && v.length > 0) || "제목 입력은 필수입니다.",
          (v) => v.length <= 100 || "100자 이하로 입력해 주세요.",
        ],
        boardContent: [
          (v) => (v != null && v.length > 0) || "내용 입력은 필수입니다.",
          (v) => v.length <= 500 || "500자 이하로 입력해 주세요.",
        ],
        boardFile: [
          (v) => v == null || v.length <= 1 || "첨부파일은 최대 1개까지만 첨부 가능합니다.",
          (v) => v == null || v[0].size <= 5000000 || "첨부 파일 크기는 최대 5MB까지만 가능합니다.",
        ]
      },
    }
  },
  emits: ["updateBoardDetail"],
  props: {
    boardDetail: Object
  },
  methods: {
    async validate() {
      this.modifyBoard.boardNo = this.boardDetail.no;
      console.log(this.modifyBoard, this.boardFile)
      await putBoard(this.modifyBoard, this.boardFile)
      .then((res) => {
        if (res != null) {
          this.$emit("updateBoardDetail", res);
          console.log(res);
          this.modifyBoardOpen = false;
          this.$swal.fire(
            '게시글 수정 완료',
            '게시글을 수정했습니다.',
            'success'
          )
        }
      })
    },
    async updateData() {
      this.loaded = false;
      this.modifyBoard.no = this.boardDetail.no;
      this.modifyBoard.boardTitle = await this.boardDetail.boardTitle;
      this.modifyBoard.boardContent = await this.boardDetail.boardContent;
      if (this.boardDetail.boardFile != null) {
        
        const imageUrl = `${process.env.VUE_APP_FILE_PATH_BOARD}${this.boardDetail.boardFile}`;
        console.log("image", imageUrl);
        const response = await fetch(imageUrl, {mode: 'no-cors'});
        console.log("response", response);
        const blob = await response.blob();
        console.log("blob", blob);
        const file = new File([blob], this.boardDetail.boardFile.substring(37), {type: blob.type});
        this.boardFile = [file];
      }
      this.loaded = true;
    }
  },
}
</script>

<style></style>