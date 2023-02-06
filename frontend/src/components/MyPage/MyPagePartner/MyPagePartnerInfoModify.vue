<template>
  <v-sheet class="mx-auto pa-7" width="500">
    <v-form ref="form">
      <v-text-field
        v-model="getPartnerInfo.id"
        label="이메일"
        variant="outlined"
        density="compact"
        disabled
      ></v-text-field>
      <v-text-field
        class="mb-3"
        v-model="userInfo.userName"
        label="이름"
        variant="outlined"
        density="compact"
        :rules="rules.name"
      ></v-text-field>
      <v-text-field
        class="mb-3"
        v-model="userPhone"
        label="휴대폰 번호"
        variant="outlined"
        density="compact"
        :rules="rules.phone"
      ></v-text-field>
      <v-btn
        variant="tonal"
        rounded="0"
        @click="phoneAuth()"
        :disabled="phoneDisable || phoneAuthStates"
        block
        >{{ phoneAuthMessage }}</v-btn
      >
      <div class="warning" v-if="!phoneDisable">{{ phoneAuthWarning }}</div>
      <div class="warning" v-else></div>
      <v-text-field
        class="mb-3"
        v-model="userInfo.userNickname"
        label="닉네임"
        variant="outlined"
        density="compact"
        :rules="rules.nickname"
      ></v-text-field>
      <v-text-field
        class="mb-2"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :type="showPassword ? 'text' : 'password'"
        @click:append="showPassword = !showPassword"
        v-model="userInfo.userPassword"
        label="비밀번호"
        variant="outlined"
        density="compact"
        :rules="rules.password"
      ></v-text-field>
      <v-radio-group v-model="userInfo.userAlertFlag" color="primary" density="compact" inline>
        <v-label>알림방법</v-label>
        <v-radio label="카카오톡" value="0"></v-radio>
        <v-radio label="이메일" value="1"></v-radio>
        <v-radio label="문자" value="2"></v-radio>
      </v-radio-group>
      <v-btn class="mr-3" variant="outlined" color="primary" @click="store()" rounded="0">저장</v-btn>
      <v-btn class="mr-3" variant="outlined" color="error" @click="back()" rounded="0">취소</v-btn>
    </v-form>
  </v-sheet>
</template>

<script>
import { updatePartnerInfo } from '@/api/userInfoPartner.js'
// import { sendPhoneAuth } from "@/api/userJoin"

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
      },
      phoneDisable: true,
      userPhone: null,
      showPassword: false,
    }
  },
  computed: {
    phoneAuthMessage() {
      return this.$store.getters.getPhoneAuthMessage;
    },
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
    phoneConfirm() {
      return this.$store.getters.getPhoneConfirm;
    },
    phoneAuthWarning() {
      return this.$store.getters.getPhoneAuthWarning;
    },
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
    rules() {
      return this.$store.getters.getUserRule;
    },
  },
  methods: {
    async store() {
      const { valid } = await this.$refs.form.validate();

      if (valid && this.phoneAuthStates) {

        this.userInfo.userPhone = this.userPhone;
        updatePartnerInfo(this.userInfo);
      }
    },
    back() {
      this.$store.dispatch("updateInfoVersion");
    },
    phoneAuth() {
      // sendPhoneAuth({
      //   userPhone: this.userPhone,
      // });
      this.$store.dispatch("updatePhoneAuthMessage", this.userPhone);
    },
  },
  created() {
    this.userInfo.userName = this.getPartnerInfo.userName;
    this.userPhone = this.getPartnerInfo.userPhone;
    this.userInfo.userNickname = this.getPartnerInfo.userNickname;
    this.userInfo.userAlertFlag = this.getPartnerInfo.userAlertFlag;
    // this.userInfo.userPassword = 
    this.$store.dispatch("updatePhoneAuthMessage", this.getPartnerInfo.userPhone);
  },
  watch: {
    async userPhone() {
      // 공백 제거
      this.userPhone = this.userPhone.replace(" ", "");

      // 부모 컴포넌트로 전화번호 보내기
      this.$emit("userPhone", this.userPhone);

      // 만약 휴대폰 번호 유효성 검사를 통과하지 못한다면
      if (!this.userPhone || !((/^[0-9]{11}$/).test(this.userPhone))) {
        // 인증 버튼 비활성화
        this.phoneDisable = true;
        // 인증 완료인 경우, 미완으로 변경
        if (this.phoneAuthStates) {
          this.$store.dispatch("resetPhoneAuthMessage");
        }
        // 유효성 검사를 통과한 경우
      } else {
        // 인증 번호 활성화
        this.phoneDisable = false;
        // 기존에 인증한 번호와 동일하면 인증 처리
        if (this.phoneConfirm != null && this.phoneConfirm == this.userPhone) {
          this.$store.dispatch("updatePhoneAuthMessage", this.userPhone);
        }
        // 기존에 인증한 번호와 다르면 미인증 처리
        else {
          this.$store.dispatch("resetPhoneAuthMessage");
        }
      }
    }
  },
}
</script>

<style>
.warning {
  margin: 5px 0px 15px 18px;
  font-size: 12px;
  color: #B00020;
  height: 13px;
}
</style>