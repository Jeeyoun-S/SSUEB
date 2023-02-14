<template>
  <v-sheet class="mx-auto" width="350">
    <v-form ref="form" lazy-validation>
      <UserJoinBasicInfo @info="updateBasicInfo"></UserJoinBasicInfo>
      <v-text-field
        v-model="info.userNickname"
        class="mb-2"
        :rules="ruleNickname"
        label="닉네임"
        variant="underlined"
        color="primary"
        maxlength="10"
        required
      ></v-text-field>
      <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
      <v-radio-group v-model="info.userAlertFlag" color="primary" inline>
        <v-label>알림방법</v-label>
        <!-- <v-radio label="카카오톡" value="0"></v-radio> -->
        <v-radio label="이메일" value="1"></v-radio>
        <v-radio label="문자" value="2"></v-radio>
      </v-radio-group>
      <v-btn
        variant="outlined"
        size="large"
        rounded="0"
        @click="validate()"
        block
        >회원가입 하기</v-btn
      >
    </v-form>
  </v-sheet>
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
        userAlertFlag: "1",
        userPhone: null,
        userIsSocialId: "0",
      },
      socialAccess: true, // for. 소셜 로그인 버튼을 통한 접근 여부판단
      provider: null, // for. 소셜 로그인 제공자 판단
    };
  },
  // #21#
  created() {
    // 회원가입 페이지 실행 시 소셜 로그인 info 적용
    this.info.userNickname = this.socialUserInfo.nickname;
    // console.log("#21# 반려인 회원가입 정보: ", this.socialUserInfo);
  },
  computed: {
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
    socialUserInfo() {
      return this.$store.getters.getSocialUserInfo;
    },
    ruleNickname() {
      return this.$store.getters.getUserRule.nickname;
    },
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid && this.phoneAuthStates) {
        joinPartner(this.info, this.socialAccess); // #21# 소셜 로그인 접근 여부 확인을 위해 코드 변경
      }
      // #21# 소셜 로그인 접근 회원가입
      // else if (
      //   !valid &&
      //   this.phoneAuthStates &&
      //   this.info.userPassword == null
      // ) {
      //   joinPartner(this.info, this.socialAccess, this.provider);
      // }
    },
    updatePhone(userPhone) {
      this.info.userPhone = userPhone;
    },
    updateBasicInfo(info) {
      this.info.id = info.id;
      this.info.userPassword = info.userPassword;
      this.info.userName = info.userName;
      this.socialAccess = info.socialAccess; // #21# false면 소셜 로그인 접근
      this.provider = info.provider; // #21# 소셜 로그인 제공자
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

<style scoped></style>
