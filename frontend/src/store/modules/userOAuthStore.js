import { getKakaoToken } from "@/api/userOAuth";

const userOAuthStore = {
  namespaced: true,
  state: {
    kakaoToken: null,
  },
  getters: {},
  mutations: {
    SET_KAKAO_TOKEN: (state, kakaoToken) => {
      state.kakaoToken = kakaoToken;
      console.log("#SET_KAKAO_TOKEN# kakaoToken 확인: ", state.kakaoToken);
    },
  },
  actions: {
    // [@Method] Kakao Token 발급받기
    async excuteKakaoToken({ commit }, code) {
      commit;
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
            "#userOAuthStore - getKakaoToken# 발급받은 kakao token 확인: ",
            data
          );
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userOAuthStore;
