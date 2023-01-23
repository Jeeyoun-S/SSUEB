import { login } from "@/api/user";
// ! JWT 디코드 설치 필요: npm i vue-jwt-decode
import VueJwtDecode from "vue-jwt-decode";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    userToken: null,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_USER_TOKEN: (state, userToken) => {
      state.isLogin = true;
      state.userToken = userToken;
    },
  },
  actions: {
    async excuteLogin({ commit }, loginInfo) {
      await login(
        loginInfo,
        ({ data }) => {
          // if) 로그인 성공
          if (data.response == "success") {
            let token = data["token"];
            console.log(
              "#userStore - excuteLogin# 로그인 성공 - token: ",
              token
            );

            // 로그인 성공에 따른 값 저장
            commit("SET_IS_LOGIN", true);
            commit("SET_USER_TOKEN", token);

            // token 복호화
            console.log("#21# token 내용: ", VueJwtDecode.decode(token));

            // else) 로그인 실패
          } else {
            console.log("#userStore - excuteLogin# 로그인 실패");
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_TOKEN", null);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userStore;
