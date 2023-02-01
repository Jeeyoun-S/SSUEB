import { getKakaoToken, getKakaoUserInfo } from "@/api/userOAuth";

const userOAuthStore = {
  namespaced: true,
  state: {
    kakaoToken: null,
  },
  getters: {},
  mutations: {
    // #Kakao# 발급받은 Kakao Token 저장
    SET_KAKAO_TOKEN: (state, kakaoToken) => {
      state.kakaoToken = kakaoToken;
      // #Kakao# 현재 로그인한 Kakao 사용자 정보 가져오기
      getKakaoUserInfo(kakaoToken);
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
          console.log(
            "#userOAuthStore - getKakaoToken# Kakao Token 발급 성공: ",
            data
          );
          // 발급받은 access-token을 통해 현재 로그인한 사용자 정보 가져오기
          commit("SET_KAKAO_TOKEN", data.access_token);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userOAuthStore;
