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
        socialAccess: true, // for. 소셜 로그인 버튼을 통한 접근 여부판단
        provider: null, // for. 소셜 로그인 제공자 판단
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
          (/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/).test(this.info.id) &&
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
  computed: {
    socialUserInfo() {
      return this.$store.getters.getSocialUserInfo;
    },
    rules() {
      return this.$store.getters.getUserRule;
    },
  },
  created() {
    // 소셜 로그인을 통해 회원가입 페이지로 접근 하였다면 > 소셜 로그인 info 적용
    this.info.id = this.socialUserInfo.id;
    this.info.provider = this.socialUserInfo.provider;
    // 비밀번호 입력칸 비활성화
    if (this.socialUserInfo.id != null) this.info.socialAccess = false;

    if (this.rules.email.length <= 3) {
      this.rules.email.push(() => this.duplicateIdCheck || "중복된 이메일입니다.");
    }
    console.log(this.rules.email);
  },
};
</script>

<style></style>
