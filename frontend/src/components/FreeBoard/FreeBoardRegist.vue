<template>
  <v-dialog v-model="registBoardOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn class="ma-2" rounded="pill" v-bind="props"
        prepend-icon="mdi-plus" color="primary" variant="tonal"
      >
        글 등록
      </v-btn>
    </template>
    <v-card class="pa-5">
      <v-card-title>
        <h3>게시글 등록</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="forms">
            <v-text-field v-model="newBoard.boardTitle" label="제목" variant="underlined"
              maxlength="100" counter="100"
            ></v-text-field>
            <v-textarea v-model="newBoard.boardContent" label="내용"
              variant="underlined" maxlength="500" counter="500"
              row-height="500" rows="8" auto-grow
            ></v-textarea>
            <v-file-input v-model="boardFile" label="첨부파일" variant="underlined"></v-file-input>
          </v-form>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" variant="text" @click="registBoardOpen = false">취소</v-btn>
        <v-btn color="primary" variant="text" @click="validate()">등록</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { apiInstance } from "@/api/index.js";
import { mapState } from "vuex";
const userStore = "userStore";
export default {
  name: "FreeBoardRegist",
  computed: {
    ...mapState(userStore, ["userId", "userinfo", "userAuth"]),
  },
  data() {
    return {
      registBoardOpen: false,
      boardFile: null,
      newBoard: {
        // 입력 받는 정보
        boardTitle: null,
        boardContent: null,
        // 넣어줄 정보
        userId: this.userId,
        userNickname: "임시닉네임",//this.userinfo.usreNickname,//바로 못받아오는듯?
        boardFlag: this.userAuth=="ROLE_ADMIN"?0:this.userAuth=="ROLE_CONSULTANT"?1:2,
      },
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
        ]
      }
    }
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.forms.validate();
      if (valid) {
        const frm = new FormData();

        this.newBoard.userId = this.userId;
        frm.append("board",  new Blob([ JSON.stringify(this.newBoard) ], {type : "application/json"}));
        frm.append("file", this.boardFile);
        console.log(this.boardFile)
        console.log(this.newBoard);
        console.log(frm.getAll("file"))
        const api = apiInstance();
        api.post(process.env.VUE_APP_API_BASE_URL+`/board/community`, frm, {
          headers: {'Content-Type': 'multipart/form-data'}
        }).then(() => {
          this.$swal.fire(
            '게시글 등록 완료',
            '신규 게시글 등록이 완료되었습니다.',
            'success'
          )
        }).catch(error => {
          console.log(error.message)
          return;
        })
      }

      this.registBoardOpen = false;
    }
  }
}
</script>

<style></style>