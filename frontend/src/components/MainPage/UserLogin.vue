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
              label="이메일"
              v-model="loginInfo.id"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field
              label="비밀번호"
              type="Password"
              v-model="loginInfo.password"
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
