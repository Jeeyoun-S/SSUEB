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
        >🔔</v-card-actions
      >
    </v-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "UserMainAlert",
  data() {
    return {};
  },
  watch: {
    isLogin: function () {
      // [@Method] 권한 확인 및 유저 정보 가져오기
      this.checkAnyPermit();
    },
  },
  computed: {
    ...mapState(userStore, ["userAuth", "userInfo", "isLogin"]),
  },
  methods: {
    ...mapActions(userStore, ["checkAnyPermit"]),
  },
};
</script>

<style scoped>
.main-alert {
  height: 190px;
  width: 300px;
}
</style>
