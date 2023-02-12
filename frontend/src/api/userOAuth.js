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
// Kakao 연결끊기를 위한 API
const kakao_api_disconnect = axios.create({
  baseURL: "https://kapi.kakao.com",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    Authorization: `Bearer ${localStorage.getItem("kakaoToken")}`,
  },
});

// #Google API#
// Google 사용자 정보를 가져오기 위한 API
const google_api_info = axios.create({
  baseURL: "https://www.googleapis.com",
});
// Google 연결끊기를 위한 API
const google_api_disconnect = axios.create({
  baseURL: "https://accounts.google.com",
  "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  Authorization: `Bearer ${localStorage.getItem("googleToken")}`,
});

// [POST] #Kakao# token 발급받기
async function getKakaoToken(kakaoInfo, success, fail) {
  // console.log("#userOAuth - api# Kakao token 발급을 위한 params: ", kakaoInfo);
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
  // console.log(
  //   "#userOAuth - api# 현재 로그인한 Kakao 정보 가져오기 위한 token: ",
  //   token
  // );
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
        provider: "KAKAO",
      };
      // console.log("#userOAuth - api# 현재 로그인한 사용자 정보: ", info);

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
        const loginInfo = {
          id: id,
          password: `${process.env.VUE_APP_OAUTH_KAKAO}`,
          socialButton: 1,
        };
        store.dispatch("userStore/excuteLogin", loginInfo, { root: true });
      }
    })
    .catch(fail);
}

// [POST] #Kakao# 연결끊기
async function withdrawalKakao(success, fail) {
  // x-www-form-urlencoded 형식으로 파라미터 보내기
  await kakao_api_disconnect.post(`v1/user/unlink`).then(success).catch(fail);
}

// [GET] #Google# 사용자 정보 요청받기
async function getGoogleInfo(token, success, fail) {
  await google_api_info
    .get(`/oauth2/v2/userinfo?access_token=${token}`, {
      headers: {
        authorization: `token ${token}`,
        accept: "application/json",
      },
    })
    .then(success)
    .catch(fail);
}

// [POST] #Google# 연결끊기
async function withdrawalGoogle(success, fail) {
  await google_api_disconnect
    .post(`/o/oauth2/revoke?token=${localStorage.getItem("googleToken")}`)
    .then(success)
    .catch(fail);
}

export {
  getKakaoToken,
  getKakaoUserInfo,
  withdrawalKakao,
  getGoogleInfo,
  withdrawalGoogle,
};
