import { duplicateId } from "@/api/userJoin";
import {
  getKakaoToken,
  getKakaoUserInfo,
  getGoogleInfo,
} from "@/api/userOAuth";
import store from "..";

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
        redirect_uri: process.env.VUE_APP_OAUTH_REDIRECT_URI,
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
    // [@Method] #Google# Google 사용자 정보 가져오기 > 회원가입 OR 로그인
    async excuteGoogleInfo({ commit }, token) {
      await getGoogleInfo(
        token,
        async ({ data }) => {
          commit;
          // console.log("#21# Google 사용자 정보: ", data);
          // console.log("#21# Google 사용자 email: ", data.email);
          const info = {
            id: data.email,
            nickname: "",
            provider: "GOOGLE",
          };

          var duplicateResult = false; // 현재 로그인한 사용자 중복 확인 (회원가입 여부 학인)
          await duplicateId(info.id).then((res) => {
            duplicateResult = res;
          });
          // * 만약 이 아이디로 회원가입 한 사용자가 없다면 > 회원가입 페이지로 이동
          if (duplicateResult == true) {
            store.dispatch("setSocialUserInfo", info); // userSocialStore에 id(email) 저장
          }
          // * 있다면 > 로그인
          else {
            // 로그인 JWT 토큰 발행 > (userStore 내 로그인 함수 호출)
            const loginInfo = {
              id: info.id,
              password: `${process.env.VUE_APP_OAUTH_GOOGLE}`,
              socialButton: 1,
            };
            store.dispatch("userStore/excuteLogin", loginInfo, { root: true });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userOAuthStore;
