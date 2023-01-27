<template>
  <v-card outlined>
    <!-- title -->
    <v-card-title class="title"
      ><span class="headline">로그인</span></v-card-title
    >
    <!-- container -->
    <v-container>
      <v-text-field
        v-model="loginInfo.id"
        :rules="rules.id"
        label="이메일"
        required
      ></v-text-field>
      <v-text-field
        v-model="loginInfo.password"
        :rules="rules.password"
        type="Password"
        label="비밀번호"
        required
      ></v-text-field>
      <v-btn width="100%" elevation="3" outline block @click="login"
        >로그인</v-btn
      >
    </v-container>
    <v-card-text>간편 로그인 및 회원가입 --------------</v-card-text>
    <!-- 소셜 로그인 -->
    <v-card-actions>
      <kakao-dialog></kakao-dialog>
      <naver-dialog></naver-dialog>
    </v-card-actions>
    <v-btn text elevation="0" class="joinBtn"><a href="http://localhost:8081/join">회원가입</a></v-btn>
  </v-card>
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
  components: { KakaoDialog, NaverDialog },
  computed: {
    ...mapState(userStore, ["isLogin", "userToken"]),
  },
  methods: {
    ...mapActions(userStore, ["excuteLogin"]),
    // [@Method] 로그인
    async login() {
      console.log(
        "#21# loginInfo 값 확인: ",
        this.loginInfo.id,
        this.loginInfo.password
      );
      await this.excuteLogin(this.loginInfo);

      // 로그인 실패 시 alert창 띄우기
      if (!this.isLogin) {
        this.$swal.fire(
          "FAIL",
          "아이디와 비밀번호를 다시 입력해주세요.",
          "warning"
        );
      }
    },
  },
};
</script>

<style scoped>
.title {
  margin-left: 37%;
  margin-top: 5;
}
.joinBtn {
  margin-left: 70%;
  margin-top: 5%;
}
</style>
