import { login, partPermit } from "@/api/user";
// ! JWT 디코드 설치 필요: npm i vue-jwt-decode
import VueJwtDecode from "vue-jwt-decode";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isValidToken: false,
    userId: null,
    userInfo: null,
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
      console.log("#SET_USER_ID# userId 확인: ", state.userId);
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
      console.log("#SET_USER_INFO# userInfo 확인: ", state.userInfo);
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
            let email = VueJwtDecode.decode(token).sub;
            console.log("#21# token 복호화 이메일 확인: ", email);
            commit("SET_USER_ID", email);

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
      console.log("#userStore - checkAnyPermit# 모든 권한 허용 동작", commit);
    },
    // [@Method] 전문가, 관리자 권한만 허용
    async checkPartPermit({ commit }, userId) {
      console.log(
        "#userStore - checkPartPermit# 전문가, 관리자 권한만 허용 동작"
      );
      const sessionToken = sessionStorage.getItem("token");

      await partPermit(
        userId,
        sessionToken,
        ({ data }) => {
          console.log("#userStore - checkPartPermit# 성공");
          commit("SET_USER_INFO", data);
        },
        (error) => {
          console.log("#userStore - checkPartPermit# 실패");
          console.log(error);
        }
      );
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
