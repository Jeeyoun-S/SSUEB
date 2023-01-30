import axios from "axios";

// const kakao_api = "https://kauth.kakao.com";
const kakao_api = axios.create({
  baseURL: "https://kauth.kakao.com",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
  },
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

  await kakao_api.post(`/oauth/token`, params).then(success).catch(fail);
}

export { getKakaoToken };
