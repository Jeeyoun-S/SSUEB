<template>
  <div class="main-alert border-sheet-two">
    <v-card>
      <v-row justify="space-between">
        <v-col cols="7">
          <v-card-title>
            <div>
              <div><br /></div>
              <div>
                <v-text v-if="this.userAuth == 'ROLE_USER'"
                  >{{ userInfo.userNickname }} 님</v-text
                >
                <v-text v-if="this.userAuth == 'ROLE_CONSULTANT'"
                  >{{ userInfo.userName }} 님</v-text
                >
              </div>
            </div>
          </v-card-title>
        </v-col>
        <v-img
          class="shrink ma-2"
          contain
          height="120px"
          src="https://i.pinimg.com/originals/8f/10/c1/8f10c1d089561e3896d229bf673a04bc.gif"
        ></v-img>
      </v-row>
      <v-divider dark></v-divider>
      <v-card-actions class="pa-6" style="font-size: small; color: slategrey"
        >🔔 금일 예약이 {{ this.reservationCount }}건 있습니다.</v-card-actions
      >
    </v-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const userStore = "userStore";
const mainPageStore = "mainPageStore";

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
  },
};
</script>

<style scoped>
.main-alert {
  height: 190px;
  width: 300px;
}
</style>
