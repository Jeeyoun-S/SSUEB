import axios from "axios";
import { duplicateId } from "./userJoin.js";
import store from "@/store/index.js";

// #Kakao API#
// Kakao Token을 발급받기 위한 API
const kakao_api_auth = axios.create({
  baseURL: "https://kauth.kakao.com",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  },
});
// Kakao 사용자 정보를 가져오기 위한 API
const kakao_api_info = axios.create({
  baseURL: "https://kapi.kakao.com",
});

// [POST] #Kakao# token 발급받기
async function getKakaoToken(kakaoInfo, success, fail) {
  console.log("#userOAuth - api# Kakao token 발급을 위한 params: ", kakaoInfo);

  // x-www-form-urlencoded 형식으로 파라미터 보내기
  const params = {
    grant_type: kakaoInfo.grant_type,
    client_id: kakaoInfo.client_id,
    redirect_uri: kakaoInfo.redirect_uri,
    code: kakaoInfo.code,
    client_secret: kakaoInfo.client_secret,
  };

  await kakao_api_auth.post(`/oauth/token`, params).then(success).catch(fail);
}

// [GET] #Kakao# 현재 로그인한 Kakao 사용자 정보 가져오기
async function getKakaoUserInfo(token, success, fail) {
  console.log(
    "#userOAuth - api# 현재 로그인한 Kakao 정보 가져오기 위한 token: ",
    token
  );
  // const kakaoToken = token;

  await kakao_api_info
    .get(`/v2/user/me`, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        Authorization: `Bearer ${token}`,
      },
    })
    .then(async (success) => {
      const nickname = success.data.properties.nickname;
      const id = success.data.kakao_account.email;
      const info = {
        id: id,
        nickname: nickname,
      };
      console.log("#userOAuth - api# 현재 로그인한 사용자 정보: ", info);

      var duplicateResult = false; // 현재 로그인한 사용자 id(email) 중복 확인 (이미 회원가입된 사용자인지 확인)
      await duplicateId(info.id).then((res) => {
        duplicateResult = res;
      });
      // * 만약 이 아이디로 회원가입한 사용자가 없다면 > 회원가입 페이지로 이동
      if (duplicateResult == true) {
        // 소셜 로그인 유저 정보(userSocialStore) store에 id, nickname 저장
        store.dispatch("setSocialUserInfo", info);
      }
      // * 있다면 > 로그인
      else {
        // 로그인 JWT 토큰 발행 > (userStore 내 로그인 함수 호출)
        // Kakao: email(id) + client-secret 키로 비밀번호 생성
        const kakaoId = id.substring(0, 6);
        const kakaoKey =
          process.env.VUE_APP_OAUTH_KAKAO_CLIENT_SECRET.substring(0, 6);
        const kakaoPassword = kakaoId + kakaoKey + "#1";
        const loginInfo = {
          id: id,
          password: kakaoPassword,
        };
        store.dispatch("userStore/excuteLogin", loginInfo, { root: true });
      }
    })
    .catch(fail);
}

export { getKakaoToken, getKakaoUserInfo };
