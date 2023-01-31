import axios from "axios";
import { socialLogin } from "./user.js";
import { duplicateId } from "./userJoin.js";
// import router from "@/router/index.js";
// import { userJoinStore } from "@/store/modules/userJoinStore";
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

      // * 만약 이 아이디로 회원가입한 사용자가 없다면 > 회원가입 페이지로 이동
      var duplicateResult = false; // 현재 로그인한 사용자 id(email) 중복 확인 (이미 회원가입된 사용자인지 확인)
      await duplicateId(info.id).then((res) => {
        duplicateResult = res;
      });
      if (duplicateResult == true) {
        // 회원가입 store에 id, nickname 저장
        // userJoinStore.commit("SET_SOCIAL_USER_INFO", info);
        // userJoinStore.mutations("SET_SOCIAL_USER_INFO", info);
        // store.dispatch("SET_SOCIAL_USER_INFO", info);

        // store.dispatch("setSocialUserInfo", info);
        store.dispatch("setSocialUserInfo", id);
        // location.href = `${process.env.VUE_APP_BASE_URL}/join`;
        // router.push("/join");
      }
      // * 있다면 > 회원정보 업데이트
      else {
        // 현재 로그인한 사용자 정보를 Back-end로 전달 (user.js 내 socialLogin 함수 호출)
        socialLogin(info);
      }
    })
    .catch(fail);
}

export { getKakaoToken, getKakaoUserInfo };
