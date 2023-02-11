<template>
  <div class="board-top border-sheet-two">
    <h3 class="pa-4 pb-3">
      <v-icon class="mr-2" color="light-blue-darken-1">mdi-heart</v-icon>
      <span>인기 게시글</span>
    </h3>
    <v-divider></v-divider>
    <v-card class="pt-4" flat rounded="0">
      <v-window v-model="onboarding">
        <div class="d-flex justify-center align-center">
          <v-window-item v-for="n in board.length" :key="`card-${n}`" :value="n" @click="moveDetailBoard(board[n-1].no)">
            <v-hover v-slot="{ isHovering, props }">
              <v-card class="pa-3 mb-1 d-flex flex-column justify-center align-center"
                :elevation="isHovering ? 3 : 0" :class="{ 'on-hover': isHovering }" v-bind="props"
                style="cursor: pointer;"
                height="120" width="260" rounded="0" variant="outlined"
              >
                <v-chip size="large" color="light-blue-darken-3">
                  <v-icon>mdi-pencil</v-icon>
                  <b class="mr-1">작성자</b>{{ board[n-1].userNickname }}
                </v-chip>
                <v-card-text><h3>{{ board[n-1].boardTitle }}</h3></v-card-text>
              </v-card>
            </v-hover>
          </v-window-item>
        </div>
      </v-window>
      <v-card-actions class="justify-space-between">
        <v-btn variant="plain" icon="mdi-chevron-left" @click="prev"></v-btn>
        <v-item-group v-model="onboarding" class="text-center" mandatory>
          <v-item v-for="n in board.length" :key="`btn-${n}`" v-slot="{ isSelected, toggle }" :value="n">
            <v-btn color="light-blue-lighten-1" :variant="isSelected ? 'outlined' : 'text'" icon="mdi-record" size="28" @click="toggle"></v-btn>
          </v-item>
        </v-item-group>
        <v-btn variant="plain" icon="mdi-chevron-right" @click="next"></v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const mainPageStore = "mainPageStore";
const userStore = "userStore";

export default {
  name: "BoardTopFive",
  data: () => ({
    onboarding: 0,
  }),
  created() {
    if (this.isLogin == true) {
      // [@Method] 좋아요 Top5 게시글 가져오기
      this.excuteGetBoard();
    }
  },
  watch: {
    isLogin: function () {
      // [@Method] 좋아요 Top5 게시글 가져오기
      this.excuteGetBoard();
    },
  },
  methods: {
    ...mapActions(mainPageStore, ["excuteGetBoard"]),
    next() {
      this.onboarding = this.onboarding + 1 > this.board.length ? 1 : this.onboarding + 1
    },
    prev() {
      this.onboarding = this.onboarding - 1 <= 0 ? this.board.length : this.onboarding - 1
    },
    moveDetailBoard(no) {
      this.$router.push(`/free-board/detail/${no}`)
    }
  },
  computed: {
    ...mapState(mainPageStore, ["board"]),
    ...mapState(userStore, ["isLogin"]),
  },
};
</script>

<style scoped>
.board-top {
  height: 270px;
  width: 300px;
}
</style>
