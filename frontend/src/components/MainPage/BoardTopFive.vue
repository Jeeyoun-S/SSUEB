<template>
  <div class="board-top border-sheet-two">
    <h3 class="pa-4 pb-1">
      <v-icon class="mr-2" color="yellow-darken-3">mdi-heart</v-icon>
      <span>인기 게시글</span>
    </h3>
    <v-card height="180" flat rounded="0">
      <v-window v-model="window">
        <!-- <template v-slot:prev="{ props }">
          <v-btn
            icon="mdi-chevron-left"
            variant="text"
            color="light-blue-darken-3"
            @click="props.onClick"
          >
          </v-btn>
        </template>
        <template v-slot:next="{ props }">
          <v-btn
            icon="mdi-chevron-right"
            variant="text"s
            color="light-blue-darken-3"
            @click="props.onClick"
          >
          </v-btn>
        </template> -->
        <div class="d-flex justify-center align-center">
          <v-window-item v-for="no in board" :key="no">
            <v-card elevation="0" height="120" width="220" class="pa-3 d-flex flex-column justify-start align-center">
              <v-chip size="large" color="light-blue-darken-3">
                <v-icon>mdi-pencil</v-icon>
                <b class="mr-1">작성자</b>{{ no.userNickname }}
              </v-chip>
              <v-sheet height="100">
                <v-card-text>{{ no.boardTitle }}</v-card-text>
              </v-sheet>
            </v-card>
          </v-window-item>
        </div>
      </v-window>
      <v-card-actions class="justify-space-between">
        <v-btn
          variant="plain"
          icon="mdi-chevron-left"
          @click="prev"
        ></v-btn>
        <v-item-group
          v-model="window"
          class="text-center"
          mandatory
        >
          <v-item
            v-for="n in board.length"
            :key="`btn-${n}`"
            v-slot="{ isSelected, toggle }"
            :value="n"
          >
            <v-btn
              :variant="isSelected ? 'outlined' : 'text'"
              icon="mdi-record"
              @click="toggle"
            ></v-btn>
          </v-item>
        </v-item-group>
        <v-btn
          variant="plain"
          icon="mdi-chevron-right"
          @click="next"
        ></v-btn>
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
    window: 0,
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
    next () {
      this.window = this.window + 1 > this.board.length ? 1 : this.window + 1
    },
    prev () {
      this.window = this.window - 1 <= 0 ? this.board.length : this.window - 1
    },
  },
  computed: {
    ...mapState(mainPageStore, ["board"]),
    ...mapState(userStore, ["isLogin"]),
  },
};
</script>

<style scoped>
.board-top {
  height: 250px;
  width: 300px;
}
</style>
