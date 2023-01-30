import axios from "axios";

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
  //   headers: {
  //     "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  //   },
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
    .then(success)
    .catch(fail);
}

export { getKakaoToken, getKakaoUserInfo };
