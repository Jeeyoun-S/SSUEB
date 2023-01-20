import { login } from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginFail: false,
    userToken: null,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_FAIL: (state, isLoginFail) => {
      state.isLoginFail = isLoginFail;
    },
    SET_USER_TOKEN: (state, userToken) => {
      state.isLogin = true;
      state.userToken = userToken;
      console.log("#userStore# 현재 로그인한 사용자의 토큰: ", userToken);
    },
  },
  actions: {
    async excuteLogin({ commit }, loginInfo) {
      await login(
        loginInfo,
        ({ data }) => {
          // if) 로그인 성공
          if (data.message == "success") {
            let token = data["token"];
            console.log(
              "#userStore - excuteLogin# 로그인 성공 - token: ",
              token
            );

            // 로그인 성공에 따른 값 저장
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_FAIL", false);
            commit("SET_USER_TOKEN", token);
            // else) 로그인 실패
          } else {
            console.log("#userStore - excuteLogin# 로그인 실패");
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_FAIL", true);
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
