<template>
  <!-- #21# disabled 추가: 소셜 로그인을 통해 접근한 경우 아이디, 비밀번호 비활성화 -->
  <v-text-field
    v-model="info.id"
    class="mb-2"
    :rules="rules.email"
    label="이메일"
    variant="underlined"
    color="primary"
    :disabled="!info.socialAccess"
    required
  ></v-text-field>
  <!-- #21# disabled 추가: 소셜 로그인을 통해 접근한 경우 아이디, 비밀번호 비활성화 -->
  <v-text-field
    v-model="info.userPassword"
    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
    :type="showPassword ? 'text' : 'password'"
    @click:append="showPassword = !showPassword"
    class="mb-2"
    :rules="rules.password"
    label="비밀번호"
    variant="underlined"
    color="primary"
    :disabled="!info.socialAccess"
    required
  ></v-text-field>
  <v-text-field
    v-model="info.userName"
    class="mb-2"
    :rules="rules.name"
    label="이름"
    variant="underlined"
    color="primary"
    required
  ></v-text-field>
</template>

<script>
import { duplicateId } from "@/api/userJoin.js";

export default {
  name: "UserJoinBasicInfo",
  data() {
    return {
      info: {
        id: null,
        userPassword: null,
        userName: null,
        socialAccess: true, // #21#
      },
      valid: {
        email: /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/,
        password:
          /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/,
        name: /^[가-힣|a-z|A-Z]{1,22}$/,
      },
      rules: {
        email: [
          (v) => !!v || "이메일은 필수 입력 사항입니다.",
          (v) => this.valid.email.test(v) || "이메일 형식으로 입력해 주세요.",
          (v) => v.length <= 30 || "30자 이하로 입력해 주세요.",
          () => this.duplicateIdCheck || "중복된 이메일입니다.",
        ],
        password: [
          (v) => !!v || "비밀번호는 필수 입력 사항입니다.",
          (v) =>
            this.valid.password.test(v) ||
            "영어, 숫자, 특수문자를 포함해 10~20자로 입력해 주세요.",
        ],
        name: [
          (v) => !!v || "이름은 필수 입력 사항입니다.",
          (v) => v.length <= 22 || "22자 이하로 입력해 주세요.",
          (v) =>
            this.valid.name.test(v) || "숫자, 특수문자는 입력 불가능합니다.",
        ],
      },
      duplicateIdCheck: false,
      lastCheckId: null,
      showPassword: false,
    };
  },
  emits: ["info"],
  watch: {
    info: {
      async handler() {
        // 부모 컴포넌트로 정보 보내기
        this.$emit("info", this.info);

        // 공백 제거
        if (this.info.id) this.info.id = await this.info.id.replace(" ", "");
        if (this.info.userPassword)
          this.info.userPassword = await this.info.userPassword.replace(
            " ",
            ""
          );
        if (this.info.userName)
          this.info.userName = await this.info.userName.replace(" ", "");

        // 아이디 중복 확인
        if (
          this.valid.email.test(this.info.id) &&
          this.lastCheckId != this.info.id
        ) {
          duplicateId(this.info.id).then((res) => {
            this.duplicateIdCheck = res;
            this.lastCheckId = this.info.id;
            // rule을 한 번 더 확인할 수 있도록 id 값에 변화 주기
            this.info.id = this.info.id + " ";
          });
        }
      },
      deep: true,
    },
  },
  // computed: {
  //   // ...mapState(userJoinStore, ["socialUserInfo"]), // #21#
  //   socialUserInfo() {
  //     return this.$store.getters.getSocialUserInfo;
  //     // return this.$store.state.socialUserInfo;
  //   },
  // },
  created() {
    // 소셜 로그인을 통해 회원가입 페이지로 접근 하였다면 > 소셜 로그인 info 적용
    if (this.socialUserInfo != null) {
      this.info.id = this.socialUserInfo.id;
      // 비밀번호 입력칸 비활성화
      // if (this.socialUserInfo.id != null) this.socialAccess = false;
      if (this.socialUserInfo.id != null) this.info.socialAccess = false;
    }
  },
  computed: {
    socialUserInfo() {
      return this.$store.getters.getSocialUserInfo;
    },
  },
};
</script>

<style></style>
