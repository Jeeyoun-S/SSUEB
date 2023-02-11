<template>
  <div
    class="pa-7 d-flex flex-column justify-space-between main-alert border-sheet-two"
  >
    <div>
      <h2>
        <p>안녕하세요</p>
        <p v-if="this.userAuth == 'ROLE_USER'">
          {{ userInfo.userNickname }} 님
        </p>
        <p v-if="this.userAuth == 'ROLE_CONSULTANT'">
          {{ userInfo.userName }} 님
        </p>
      </h2>
    </div>
    <div>
      <v-alert density="compact" color="light-blue-darken-1">
        <v-icon class="pb-1" color="white" size="20">mdi-bell</v-icon>
        금일 예약이 {{ this.reservationCount }}건 있습니다.
      </v-alert>
      <!-- #21# -->
      <v-btn @click="sendKakaoTalk">Kakao</v-btn>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
// import UserLogout from "../MyPage/UserLogout.vue";

const userStore = "userStore";
const mainPageStore = "mainPageStore";
const userOAuthStore = "userOAuthStore"; // #21#

export default {
  name: "UserMainAlert",
  data() {
    return {};
  },
  components: {},
  // watch: {
  //   isLogin: function () {
  //     console.log("#21# 권한 확인 및 유저 정보 가져오기 동작");
  //     // [@Method] 권한 확인 및 유저 정보 가져오기
  //     this.checkAnyPermit();
  //     // [@Method] 금일 예약 건 수 가져오기
  //     this.excuteGetReservationCount();
  //   },
  // },
  computed: {
    ...mapState(userStore, ["userAuth", "userInfo", "isLogin"]),
    ...mapState(mainPageStore, ["reservationCount"]),
  },
  methods: {
    ...mapActions(userStore, ["checkAnyPermit"]),
    ...mapActions(mainPageStore, ["excuteGetReservationCount"]),
    // #21#
    ...mapActions(userOAuthStore, ["excutedSendKakaoMessage"]),
    sendKakaoTalk() {
      this.excutedSendKakaoMessage();
    },
  },
};
</script>

<style scoped>
.main-alert {
  height: 230px;
  width: 300px;
  background-image: url("https://i.pinimg.com/originals/8f/10/c1/8f10c1d089561e3896d229bf673a04bc.gif");
  background-size: 150px;
  background-position-x: right;
}
</style>
