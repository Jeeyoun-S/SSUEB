import { login } from "@/api/user";
// ! JWT 디코드 설치 필요: npm i vue-jwt-decode
import VueJwtDecode from "vue-jwt-decode";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isValidToken: false,
    userId: null,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_ID: (state, userId) => {
      state.isLogin = true;
      state.userId = userId;
    },
  },
  actions: {
    // [@Method] 로그인
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
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("token", token);

            // token 복호화 > userId 저장
            console.log("#21# token 내용: ", VueJwtDecode.decode(token));
            commit("SET_USER_ID", VueJwtDecode.decode(token).sub);

            // else) 로그인 실패
          } else {
            console.log("#userStore - excuteLogin# 로그인 실패");
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] 모든 권한 허용
    async checkAnyPermit({ commit }) {
      console.log("# 모든 권한 허용: ", this.userId, commit);
    },
    // [@Method] 로그아웃
    async excuteLogout({ commit }) {
      console.log("#userStore - excuteLogout# 로그아웃 동작");
      commit("SET_IS_LOGIN", false);
      commit("SET_IS_VALID_TOKEN", false);
      sessionStorage.clear;
      //console.log("#21# sessionStorage 확인: ", sessionStorage.getItem);
    },
  },
};

export default userStore;
