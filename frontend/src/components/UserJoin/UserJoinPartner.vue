<template>
  <v-form class="forms" ref="form" lazy-validation>
    <UserJoinBasicInfo @info="updateBasicInfo"></UserJoinBasicInfo>
    <v-text-field
      v-model="info.userNickname"
      class="mb-2"
      :rules="ruleNickname"
      label="닉네임"
      variant="underlined"
      color="primary"
      required
    ></v-text-field>
    <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
    <v-radio-group v-model="info.userAlertFlag" color="primary" inline>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" size="large" rounded="0" @click="validate()" block
      >회원가입 하기</v-btn
    >
  </v-form>
</template>

<script>
import UserJoinPhone from "@/components/UserJoin/UserJoinPhone.vue";
import UserJoinBasicInfo from "@/components/UserJoin/UserJoinBasicInfo.vue";
import { joinPartner } from "@/api/userJoin.js";

export default {
  name: "UserJoinPartner",
  components: {
    UserJoinPhone,
    UserJoinBasicInfo,
  },
  data() {
    return {
      info: {
        id: null,
        userPassword: null,
        userName: null,
        userNickname: null,
        userAlertFlag: "0",
        userPhone: null,
      },
      ruleNickname: [
        (v) => !!v || "닉네임은 필수 입력 사항입니다.",
        (v) => v.length <= 22 || "22자 이하로 입력해 주세요.",
        (v) => this.validNickname.test(v) || "특수문자는 입력 불가능합니다.",
      ],
      validNickname: /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$/,
    };
  },
  // #21#
  created() {
    // 회원가입 페이지 실행 시 소셜 로그인 info 적용
    this.info.userNickname = this.socialUserInfo.nickname;
  },
  computed: {
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
    socialUserInfo() {
      return this.$store.getters.getSocialUserInfo;
    },
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid && this.phoneAuthStates) {
        joinPartner(this.info);
      }
    },
    updatePhone(userPhone) {
      this.info.userPhone = userPhone;
    },
    updateBasicInfo(info) {
      this.info.id = info.id;
      this.info.userPassword = info.userPassword;
      this.info.userName = info.userName;
    },
  },
  watch: {
    info: {
      async handler() {
        // 공백 제거
        if (this.info.userNickname)
          this.info.userNickname = await this.info.userNickname.replace(
            " ",
            ""
          );
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
@import "@/css/form.css";
</style>
