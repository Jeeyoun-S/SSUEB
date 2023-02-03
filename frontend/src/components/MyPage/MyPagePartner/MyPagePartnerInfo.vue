<template>
  <v-sheet class="mypage-info-item" height="300" width="500" elevation="2" rounded>
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
  </v-sheet>
  <v-sheet class="mypage-info-item" height="200" width="500" elevation="2" rounded>
    <v-radio-group v-model="getPartnerInfo.userAlertFlag" color="primary" density="compact" inline readonly>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" color="primary" rounded="0" block>로그아웃</v-btn>
    <v-btn variant="outlined" rounded="0" @click="modifyPartnerInfo()" block>회원 정보 수정</v-btn>
    <v-btn variant="outlined" color="error" rounded="0" block>회원탈퇴</v-btn>
  </v-sheet>
</template>

<script>
import { checkPassword } from "@/api/userInfoPartner.js";

export default {
  name: 'MyPagePartnerInfo',
  data() {
    return {
      userInfo: {
        id: "ssafy@ssafy.com",
        userName: "김싸피",
        userPhone: "010-8888-8888",
        userNickname: "인싸피",
        userAlertFlag: "0"
      }
    }
  },
  computed: {
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
  },
  methods: {
    modifyPartnerInfo() {
      checkPassword(this.getPartnerInfo.id);
    }
  }
}
</script>

<style>

</style>