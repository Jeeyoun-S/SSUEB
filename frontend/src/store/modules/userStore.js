import { login } from "@/api/user";

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
            // else) 로그인 실패
          } else {
            console.log("#userStore - excuteLogin# 로그인 실패");
            commit("SET_IS_LOGIN", false);
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
