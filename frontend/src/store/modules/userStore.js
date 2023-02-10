import { login, anyPermit, partPermit, withdrawal } from "@/api/user";
import VueJwtDecode from "vue-jwt-decode"; // ! JWT 디코드 설치 필요: npm i vue-jwt-decode
import store from "@/store/index.js";
// import router from "@/router/index.js";

// sweetalert2 가져오기
const Swal = require("sweetalert2");

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isValidToken: false,
    userId: null,
    userInfo: null, // 권한 검증 후 받아오는 user 정보를 저장하기 위한 state
    userAuth: null, // 현재 로그인한 사용자의 권한
  },
  getters: {
    getIsLogin: (state) => {
      return state.isLogin;
    },
    getUserId: (state) => {
      return state.userId;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
      console.log("#SET_IS_LOGIN# isLogin 확인: ", state.isLogin);
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_ID: (state, userId) => {
      //state.isLogin = true;
      state.userId = userId;
      console.log("#SET_USER_ID# userId 확인: ", state.userId);
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
      console.log("#SET_USER_INFO# userInfo 확인: ", state.userInfo);
    },
    SET_USER_AUTH: (state, userAuth) => {
      state.userAuth = userAuth;
      console.log(
        "#SET_USER_AUTH# 현재 로그인한 사용자의 권한 확인: ",
        state.userAuth
      );
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
            // 로그인 성공에 따른 값(로그인 여부, 토큰 여부, 권한) 저장
            let token = data["token"];
            // console.log(
            //   "#userStore - excuteLogin# 로그인 성공 - token: ",
            //   token
            // );
            // token 복호화 > id(email), 권한 저장
            let email = VueJwtDecode.decode(token).sub;
            commit("SET_USER_ID", email);
            commit("SET_USER_AUTH", VueJwtDecode.decode(token).auth);
            //sessionStorage.setItem("token", token);
            localStorage.setItem("token", token);
            commit("SET_IS_VALID_TOKEN", true);
            commit("SET_IS_LOGIN", true);

            // 로그인 성공에 따른 메인페이지 정보 가져오기
            // [@Method] 권한 확인 및 유저 정보 가져오기
            store.dispatch("userStore/checkAnyPermit", null, { root: true });
            // [@Method] 금일 예약 건 수 가져오기
            store.dispatch("mainPageStore/excuteGetReservationCount", null, {
              root: true,
            });

            // 로그인 성공 alert창 출력
            const id = email.split("@");
            Swal.fire("SSEUB", `${id[0]} 님 환영합니다!`, "success");

            // else) 로그인 실패
          } else {
            // console.log("#userStore - excuteLogin# 로그인 실패");
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_VALID_TOKEN", false);
            commit("SET_USER_ID", null);
            commit("SET_USER_INFO", null);
            commit("SET_USER_AUTH", null);

            // 로그인 실패 시 실패원인에 따른 alert창 출력
            Swal.fire("로그인 실패", `${data.message}`, "error");
          }
        },
        (error) => {
          console.log(error);
        }
      );

      return await Promise.resolve();
    },
    // [@Method] Kakao, Naver 소셜 로그인 성공 시 저장
    excuteSocialLogin({ commit }) {
      commit("SET_IS_LOGIN", true);
      commit("SET_IS_VALID_TOKEN", true);
    },
    // [@Method] 모든 권한 허용
    async checkAnyPermit({ commit }) {
      console.log("#userStore - checkAnyPermit# 모든 권한 허용 동작");
      const token = localStorage.getItem("token");

      await anyPermit(
        token,
        ({ data }) => {
          console.log("#userStore - checkAnyPermit# 성공");
          commit("SET_USER_INFO", data);
        },
        (error) => {
          console.log("#userStore - checkAnyPermit# 실패");
          console.log(error);
        }
      );
    },
    // [@Method] 전문가, 관리자 권한만 허용
    async checkPartPermit({ commit }, userId) {
      console.log(
        "#userStore - checkPartPermit# 전문가, 관리자 권한만 허용 동작"
      );
      const token = localStorage.getItem("token");

      await partPermit(
        userId,
        token,
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
      commit("SET_USER_AUTH", null);
      commit("SET_USER_ID", null);
      commit("SET_USER_INFO", "");
      window.localStorage.clear();

      // userSocialStore에 저장된 소셜 로그인 정보(email, nickname) 초기화
      store.dispatch("initSocialUserInfo");

      // mainPageStore에 저장된 정보 초기화
      store.dispatch("mainPageStore/initMainPageStore", null, { root: true });

      // router.push("/");
      location.href = `${process.env.VUE_APP_BASE_URL}`;
    },
    // [@Method] 회원 탈퇴
    async excuteWithdrawal(context) {
      // # 소셜 로그인(Kakao, Google) 회원탈퇴 진행
      store.dispatch("userOAuthStore/excuteWithdrawalKakao");
      store.dispatch("userOAuthStore/excuteWithdrawalGoogle");

      const info = {
        id: context.state.userId,
      };
      await withdrawal(
        info,
        ({ data }) => {
          // i) 회원탈퇴 성공
          if (data.response == "success") {
            console.log("#userStore# 회원탈퇴 성공: ", data);
            Swal.fire("SSEUB", `${data.message}`, "success");

            // 로그아웃 처리
            store.dispatch("userStore/excuteLogout", null, { root: true });
          }
          // ii) 회원탈퇴 실패
          else {
            console.log("#userStore# 회원탈퇴 실패: ", data);
            Swal.fire("SSEUB", `${data.message}`, "error");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] 회원가입 직후 로그인 성공 SET
    setAutoLogin({ commit }, res) {
      // console.log("#userStore# 회원가입 직후 로그인 성공 response: ", res);

      // 로그인 성공에 따른 값(로그인 여부, 토큰 여부, 권한) 저장
      let token = res.data.token;
      // token 복호화 > id(email), 권한 저장
      let email = VueJwtDecode.decode(token).sub;
      commit("SET_USER_ID", email);
      commit("SET_USER_AUTH", VueJwtDecode.decode(token).auth);
      // sessionStorage.setItem("token", token);
      localStorage.setItem("token", token);
      commit("SET_IS_VALID_TOKEN", true);
      commit("SET_IS_LOGIN", true);

      // 로그인 성공에 따른 메인페이지 정보 가져오기
      // [@Method] 권한 확인 및 유저 정보 가져오기
      store.dispatch("userStore/checkAnyPermit", null, { root: true });
      // [@Method] 금일 예약 건 수 가져오기
      store.dispatch("mainPageStore/excuteGetReservationCount", null, {
        root: true,
      });

      // 로그인 성공 alert창 출력
      const id = email.split("@");
      Swal.fire("SSEUB", `${id[0]} 님 환영합니다!`, "success");

      // 페이지 이동
      // location.href = process.env.VUE_APP_BASE_URL;
    },
    // [@Method] 메인 페이지로 이동
    moveMainPage() {
      location.href = process.env.VUE_APP_BASE_URL;
    },
  },
};

export default userStore;
