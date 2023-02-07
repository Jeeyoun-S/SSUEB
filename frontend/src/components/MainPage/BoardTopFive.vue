<template>
  <div class="board-top border-sheet-two">
    <v-banner>💜 인기 게시글</v-banner>
    <v-window v-model="window" show-arrows>
      <v-window-item v-for="no in board" :key="no">
        <v-card height="225px" class="d-flex justify-center align-center">
          <!-- 닉네임 -->
          <div>
            <v-text>{{ no.userNickname }}</v-text>
          </div>
          <!-- 게시글 제목 -->
          <div>
            <v-text>{{ no.boardTitle }}</v-text>
          </div>
        </v-card>
      </v-window-item>
    </v-window>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const mainPageStore = "mainPageStore";
const userStore = "userStore";

export default {
  name: "BoardTopFive",
  data: () => ({
    window: 0,
  }),
  watch: {
    isLogin: function () {
      // [@Method] 좋아요 Top5 게시글 가져오기
      this.excuteGetBoard();
    },
  },
  computed: {
    ...mapState(mainPageStore, ["board"]),
    ...mapState(userStore, ["isLogin"]),
  },
  methods: {
    ...mapActions(mainPageStore, ["excuteGetBoard"]),
  },
};
// board 5개 가져와서 for에 넣기
</script>

<style scoped>
.board-top {
  height: 290px;
  width: 300px;
}
</style>
