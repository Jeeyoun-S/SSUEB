import {
  getKakaoToken,
  getKakaoUserInfo,
  // getNaverToken,
} from "@/api/userOAuth";
// import axios from "axios";

const userOAuthStore = {
  namespaced: true,
  state: {
    kakaoToken: null,
    naverToken: null,
  },
  getters: {},
  mutations: {
    // #Kakao# 발급받은 Kakao Token 저장
    SET_KAKAO_TOKEN: (state, kakaoToken) => {
      state.kakaoToken = kakaoToken;
      // #Kakao# 현재 로그인한 Kakao 사용자 정보 가져오기
      getKakaoUserInfo(kakaoToken);
    },
    // #Naver# 발급받은 Naver Token 저장
    SET_NAVER_TOKEN: (state, naverToken) => {
      state.naverToken = naverToken;
      console.log("#SET_NAVER_TOKEN# ", state.naverToken);
      // #Kakao# 현재 로그인한 Kakao 사용자 정보 가져오기
      // getKakaoUserInfo(naverToken);
    },
  },
  actions: {
    // [@Method] #Kakao# Kakao Token 발급받기 (Kakao 인가 코드 사용)
    async excuteKakaoToken({ commit }, code) {
      const kakaoInfo = {
        grant_type: "authorization_code",
        client_id: process.env.VUE_APP_OAUTH_KAKAO_CLIENT,
        redirect_uri: process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI,
        code: code,
        client_secret: process.env.VUE_APP_OAUTH_KAKAO_CLIENT_SECRET,
      };

      await getKakaoToken(
        kakaoInfo,
        ({ data }) => {
          // console.log(
          //   "#userOAuthStore - getKakaoToken# Kakao Token 발급 성공: ",
          //   data
          // );
          // 발급받은 access-token을 통해 현재 로그인한 사용자 정보 가져오기
          commit("SET_KAKAO_TOKEN", data.access_token);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] #Naver# Naver Token 발급받기 (Naver 인가 code, state 사용)
    async excuteNaverToken({ commit }, code) {
      commit;
      const encodeState = encodeURIComponent(
        `${process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI}`,
        "UTF-8"
      );
      location.href = `https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=${process.env.VUE_APP_OAUTH_NAVER_CLIENT}&client_secret=${process.env.VUE_APP_OAUTH_NAVER_CLIENT_SECRET}&code=${code}&state=${encodeState}`;
    },
  },
};

export default userOAuthStore;
