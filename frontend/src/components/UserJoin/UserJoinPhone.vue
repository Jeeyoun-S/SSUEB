<template>
  <v-text-field
    v-model="userPhone"
    class="mb-2"
    :rules="phoneRules"
    label="휴대폰 번호"
    variant="underlined"
    color="primary"
    required
  ></v-text-field>
  <v-btn
    variant="tonal"
    rounded="0"
    @click="phoneAuth()"
    :disabled="phoneDisable || phoneAuthStates"
    block
    >{{ phoneAuthMessage }}</v-btn
  >
  <div class="warnings" v-if="!phoneDisable">{{ phoneAuthWarning }}</div>
  <div class="warnings" v-else></div>
</template>

<script>
// import { sendPhoneAuth } from "@/api/userJoin"

export default {
  name: "UserJoinPhone",
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
    phoneRules() {
      return this.$store.getters.getUserRule.phone;
    }
  },
  emits: ["userPhone"],
  data() {
    return {
      userPhone: null,
      phoneDisable: true,
    };
  },
  methods: {
    phoneAuth() {
      // sendPhoneAuth({
      //   userPhone: this.userPhone,
      // });
      this.$store.dispatch("updatePhoneAuthMessage", this.userPhone);
    },
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
};
</script>

<style scoped>
.warnings {
  margin-top: 10px;
  font-size: 12px;
  color: #B00020;
  height: 13px;
}
</style>
