<template>
  <v-card class="card">
    <v-card-title
      ><span class="headline">로그인 성공!</span> <v-spacer></v-spacer
    ></v-card-title>
    <v-card-tex>
      <v-container> </v-container>
    </v-card-tex>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn width="100%" @click="checkAny">모든 권한 허용</v-btn>
    </v-card-actions>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn width="100%" @click="checkPermit">전문가, 관리자 권한 허용</v-btn>
    </v-card-actions>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn width="100%" @click="logout">로그아웃</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions, mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "UserAlert",
  data() {
    return {
      userId: null,
    };
  },
  components: {},
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  methods: {
    ...mapActions(userStore, ["checkAnyPermit", "excuteLogout"]),
    // [@Method] 모든 권한 허용
    async checkAny() {
      await this.checkAnyPermit();
    },
    // [@Method] 전문가, 관리자만 권한 허용
    async checkPermit() {
      console.log("#UserAlert 페이지# 전문가, 관리자 권한 허용 실행");
      //await this.excuteLogin(this.loginInfo);
    },
    // [@Method] 로그아웃
    async logout() {
      await this.excuteLogout();
    },
  },
};
</script>

<style></style>
