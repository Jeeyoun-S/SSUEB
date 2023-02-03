<template>
  <v-sheet class="mypage-info-item" height="300" width="500" elevation="2" rounded>
    <div>회원정보 수정 컴포넌트</div>
    <v-text-field
        v-model="getPartnerInfo.id"
        label="이메일"
        variant="outlined"
        density="compact"
        disabled
    ></v-text-field>
    <v-text-field
        v-model="userInfo.userName"
        label="이름"
        variant="outlined"
        density="compact"
    ></v-text-field>
    <v-text-field
      v-model="userInfo.userPhone"
      label="휴대폰 번호"
      variant="outlined"
      density="compact"
    ></v-text-field>
    <v-text-field
      v-model="userInfo.userNickname"
      label="닉네임"
      variant="outlined"
      density="compact"
    ></v-text-field>
  </v-sheet>
  <v-sheet class="mypage-info-item" height="200" width="500" elevation="2" rounded>
    <v-text-field
      v-model="userInfo.userPassword"
      label="비밀번호"
      variant="outlined"
      density="compact"
    ></v-text-field>
    <v-radio-group v-model="userInfo.userAlertFlag" color="primary" density="compact" inline>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" color="primary" @click="store()" rounded="0">저장</v-btn>
    <v-btn variant="outlined" color="error" @click="back()" rounded="0">취소</v-btn>
  </v-sheet>
</template>

<script>
import { updatePartnerInfo } from '@/api/userInfoPartner.js'

export default {
  name: "MyPagePartnerInfoModify",
  data() {
    return {
      userInfo: {
        userName: null,
        userPhone: null,
        userNickname: null,
        userAlertFlag: null,
        userPassword: null
      }
    }
  },
  computed: {
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
  },
  methods: {
    store() {
      console.log("보내기전 ", this.userInfo);
      updatePartnerInfo(this.userInfo);
    },
    back() {
      this.$store.dispatch("updateInfoVersion");
    }
  },
  created() {
    this.userInfo.userName = this.getPartnerInfo.userName;
    this.userInfo.userPhone = this.getPartnerInfo.userPhone;
    this.userInfo.userNickname = this.getPartnerInfo.userNickname;
    this.userInfo.userAlertFlag = this.getPartnerInfo.userAlertFlag;
    // this.userInfo.userPassword = 
  }
}
</script>

<style>

</style>