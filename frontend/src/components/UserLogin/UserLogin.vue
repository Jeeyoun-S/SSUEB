<template>
  <div class="login border-sheet-two">
    <!-- title -->
    <h2>로그인</h2>

    <!-- container -->
    <v-sheet class="mx-auto" width="250">
      <v-form ref="form">
        <v-text-field
          v-model="loginInfo.id"
          :rules="rules.id"
          label="이메일"
          variant="outlined"
          required
          class="pb-2"
        ></v-text-field>
        <v-text-field
          v-model="loginInfo.password"
          :rules="rules.password"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword ? 'text' : 'password'"
          @click:append="showPassword = !showPassword"
          label="비밀번호"
          variant="outlined"
          required
          class="pb-2"
        ></v-text-field>
        <v-btn variant="outlined" block @click="login" rounded="0" size="large"
          >로그인</v-btn
        >
      </v-form>
    </v-sheet>

    <!-- 소셜 로그인 -->
    <div class="social-login">
      <div>간편 로그인 및 회원가입 -------------</div>
      <div class="icon">
        <kakao-dialog></kakao-dialog>
        <naver-dialog></naver-dialog>
      </div>
      <v-btn
        color="primary"
        variant="outlined"
        @click="moveJoin()"
        rounded="0"
        width="100%"
        >이메일로 회원가입하러 가기</v-btn
      >
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import KakaoDialog from "./KakaoDialog.vue";
import NaverDialog from "./NaverDialog.vue";

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
          (v) => !!v || "아이디는 필수 입력 사항입니다.",
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
      showPassword: false,
    };
  },
  components: { KakaoDialog, NaverDialog },
  computed: {
    ...mapState(userStore, ["isLogin", "userToken"]),
  },
  methods: {
    ...mapActions(userStore, ["excuteLogin"]),
    // [@Method] 로그인
    async login() {
      await this.$refs.form.validate();

      console.log(
        "#21# loginInfo 값 확인: ",
        this.loginInfo.id,
        this.loginInfo.password
      );
      await this.excuteLogin(this.loginInfo);
    },
    moveJoin() {
      location.href = "http://localhost:8081/join";
    },
  },
};
</script>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;

  padding: 15px;
  width: 300px;
  height: 500px;
}
.login .social-login {
  width: 250px;
  height: 160px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
.login .social-login .icon {
  width: 110px;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
</style>
