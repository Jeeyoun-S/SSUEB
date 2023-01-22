<template>
  <v-card class="login">
    <v-card-title
      ><span class="headline">로그인</span> <v-spacer></v-spacer
    ></v-card-title>
    <v-card-tex>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="loginInfo.id"
              :rules="rules.id"
              label="이메일"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field
              v-model="loginInfo.password"
              :rules="rules.password"
              type="Password"
              label="비밀번호"
              required
            ></v-text-field>
          </v-col>
          <v-col v-if="isLoginFail"
            >아이디 또는 비밀번호를 다시 입력해주세요</v-col
          >
        </v-row>
      </v-container>
    </v-card-tex>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn width="100%" @click="login">로그인</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "UserLogin",
  data() {
    return {
      isLoginFail: false,
      loginInfo: {
        id: null,
        password: null,
      },
      valid: {
        email: /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/,
        password:
          /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/,
      },
      rules: {
        id: [
          (v) => !!v || "아이디은 필수 입력 사항입니다.",
          (v) => this.valid.email.test(v) || "이메일 형식으로 입력해 주세요.",
          (v) => v.length <= 30 || "30자 이하로 입력해 주세요.",
        ],
        password: [
          (v) => !!v || "비밀번호는 필수 입력 사항입니다.",
          (v) =>
            this.valid.password.test(v) ||
            "영어, 숫자, 특수문자를 포함해 10~20자로 입력해 주세요.",
        ],
      },
    };
  },
  components: {},
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginFail", "userToken"]),
  },
  methods: {
    ...mapActions(userStore, ["excuteLogin"]),
    // 로그인 동작
    async login() {
      console.log(
        "#21# loginInfo 값 확인: ",
        this.loginInfo.id,
        this.loginInfo.password
      );
      await this.excuteLogin(this.loginInfo);
    },
  },
};
</script>

<style scoped>
.login {
  width: 280px;
}
</style>
