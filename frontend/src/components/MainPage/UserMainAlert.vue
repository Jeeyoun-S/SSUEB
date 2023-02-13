<template>
  <div class="pa-7 d-flex flex-column justify-space-between main-alert border-sheet-two">
    <div>
      <h2>
        <p>안녕하세요</p>
        <p v-if="this.userAuth == 'ROLE_USER'">{{ userInfo.userNickname }} 님</p>
        <p v-else-if="this.userAuth == 'ROLE_CONSULTANT'">{{ userInfo.userName }} 님</p>
        <p v-else-if="this.userAuth == 'ROLE_ADMIN'">관리자 님</p>
      </h2>
    </div>
    <div class="align-self-end" v-if="this.userAuth == 'ROLE_ADMIN'">
      <UserLogout block></UserLogout>
    </div>
    <div v-else>
      <v-btn prepend-icon="mdi-bell" color="light-blue-darken-2" size="large"
        @click="moveConfirmed" block variant="outlined" rounded="0"
      >
        금일 예약이 {{ this.reservationCount }}건 있습니다.
      </v-btn>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import UserLogout from "@/components/MyPage/UserLogout.vue";

const userStore = "userStore";
const mainPageStore = "mainPageStore";

export default {
  name: "UserMainAlert",
  data() {
    return {
      
    };
  },
  components: {
    UserLogout
  },
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
    moveConfirmed() {
      this.$router.push("/confirmed");
    }
  },
};
</script>

<style scoped>
.main-alert {
  height: 210px;
  width: 300px;
  background-image: url("https://i.pinimg.com/originals/8f/10/c1/8f10c1d089561e3896d229bf673a04bc.gif");
  background-size: 150px;
  background-position-x: right;
}
</style>
