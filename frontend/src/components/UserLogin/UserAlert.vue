<template>
  <v-card outlined>
    <v-card-title
      ><span class="headline">로그인 성공!</span> <v-spacer></v-spacer
    ></v-card-title>
    <v-card-text>
      <v-container> </v-container>
      <span>현재 Login한 사용자</span>
      <v-spacer></v-spacer>
      <span>{{ userId }}</span>
      <v-spacer></v-spacer>
      <span>#권한 허용 결과는 console로 확인가능#</span>
    </v-card-text>
    <v-card-actions>
      <v-btn width="100%" @click="checkAny">모든 권한 허용</v-btn>
    </v-card-actions>
    <v-card-actions>
      <v-btn width="100%" @click="checkPermit">전문가, 관리자 권한 허용</v-btn>
    </v-card-actions>
    <v-card-actions>
      <v-btn width="100%" @click="logout">로그아웃</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions, mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "userAlert",
  computed: {
    ...mapState(userStore, ["userId", "isLogin"]),
  },
  methods: {
    ...mapActions(userStore, [
      "checkAnyPermit",
      "checkPartPermit",
      "excuteLogout",
    ]),
    // [@Method] 모든 권한 허용
    async checkAny() {
      await this.checkAnyPermit();
    },
    // [@Method] 전문가, 관리자만 권한 허용
    async checkPermit() {
      await this.checkPartPermit(this.userId);
    },
    // [@Method] 로그아웃
    async logout() {
      await this.excuteLogout();
    },
  },
};
</script>

<style scoped></style>
