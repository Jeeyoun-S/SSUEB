<!-- <template>
  <div class="board-top border-sheet-two">
    <v-window v-model="window" show-arrows>
      <v-window-item v-for="n in length" :key="n">
        <v-card height="200px" class="d-flex justify-center align-center">
          <span class="text-h2">Card {{ n }}</span>
        </v-card>
      </v-window-item>
    </v-window>
  </div>
</template> -->
<template>
  <div class="board-top border-sheet-two">
    <v-banner>💜 인기 게시글</v-banner>
    <v-window v-model="window" show-arrows>
      <v-window-item v-for="no in board" :key="no">
        <v-card height="200px" class="d-flex justify-center align-center">
          <!-- 닉네임 -->
          <div>
            <v-text>{{ no.userNickname }}</v-text>
          </div>
          <!-- 게시글 제목 -->
          <div>
            <v-text>{{ no.boardTitle }}</v-text>
          </div>
          <!-- <v-text>[{{ no.userNickname }}]</v-text>
          <v-text>{{ no.boardTitle }}</v-text> -->
        </v-card>
      </v-window-item>
    </v-window>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const mainPageStore = "mainPageStore";

export default {
  name: "BoardTopFive",
  data: () => ({
    length: 3,
    window: 0,
  }),
  created() {
    if (localStorage.getItem("token") != null) {
      // [@Method] 좋아요 Top5 게시글 가져오기
      this.excuteGetBoard();
      console.log("#21# Front 가져온 게시글 확인: ", this.board);
    }
  },
  computed: {
    ...mapState(mainPageStore, ["board"]),
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
