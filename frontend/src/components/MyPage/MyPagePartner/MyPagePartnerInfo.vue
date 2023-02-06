<template>
  <v-sheet class="mx-auto pa-7" width="500">
    <v-text-field
      v-model="getPartnerInfo.id"
      label="이메일"
      variant="outlined"
      density="compact"
      readonly
    ></v-text-field>
    <v-text-field
      v-model="getPartnerInfo.userName"
      label="이름"
      variant="outlined"
      density="compact"
      readonly
    ></v-text-field>
    <v-text-field
      v-model="getPartnerInfo.userPhone"
      label="휴대폰 번호"
      variant="outlined"
      density="compact"
      readonly
    ></v-text-field>
    <v-text-field
      v-model="getPartnerInfo.userNickname"
      label="닉네임"
      variant="outlined"
      density="compact"
      readonly
    ></v-text-field>
    <v-radio-group
      v-model="getPartnerInfo.userAlertFlag"
      color="primary"
      density="compact"
      inline
      readonly
    >
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <UserLogout></UserLogout>
    <v-btn
      class="mr-3"
      variant="outlined"
      rounded="0"
      @click="modifyPartnerInfo()"
      >회원 정보 수정</v-btn
    >
    <v-btn
      class="mr-3"
      variant="outlined"
      color="error"
      rounded="0"
      @click="withdrawal"
      >탈퇴</v-btn
    >
  </v-sheet>
</template>

<script>
import { checkPassword } from "@/api/userInfoPartner.js";
import UserLogout from "@/components/MyPage/UserLogout.vue";
import { mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "MyPagePartnerInfo",
  components: {
    UserLogout,
  },
  data() {
    return {
      userInfo: {
        id: "ssafy@ssafy.com",
        userName: "김싸피",
        userPhone: "010-8888-8888",
        userNickname: "인싸피",
        userAlertFlag: "0",
      },
    };
  },
  computed: {
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
    socialUserInfo() {
      return this.$store.getters.getSocialUserInfo.id;
    },
  },
  methods: {
    ...mapActions(userStore, ["excuteWithdrawal"]),
    modifyPartnerInfo() {
      if (this.socialUserInfo != null) {
        this.$store.dispatch("updateInfoVersion");
      } else {
        checkPassword(this.getPartnerInfo.id);
      }
    },
    // [@Method] 회원 탈퇴
    async withdrawal() {
      await this.excuteWithdrawal();
    },
  },
};
</script>

<style></style>
