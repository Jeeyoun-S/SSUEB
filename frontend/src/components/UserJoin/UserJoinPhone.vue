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
  <v-checkbox
    class="none"
    v-model="phoneAuthStates"
    :rules="[
      (phoneAuthStates) => !!phoneAuthStates || '휴대폰 인증을 진행해 주세요.',
    ]"
    label="Do you agree?"
  ></v-checkbox>
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
  },
  emits: ["userPhone"],
  data() {
    return {
      userPhone: null,
      phoneDisable: true,
      phoneValid: /^[0-9]{11}$/,
      phoneRules: [
        (v) => !!v || "휴대폰 번호는 필수 입력 사항입니다.",
        (v) =>
          this.phoneValid.test(v) || "'-' 없이 숫자 11자리로 입력해 주세요.",
      ],
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
      if (!this.userPhone || !this.phoneValid.test(this.userPhone)) {
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

      // Vuetify 내부 validate 사용, 자동완성 사용 시 오류
      // await this.$refs.userPhone.validate().then(
      //   (v) => {
      //     if (v.length > 0) {
      //       this.phoneDisable = true;
      //     } else {
      //       this.phoneDisable = false;
      //     }
      //   }
      // )
    },
  },
};
</script>

<style scoped></style>
