import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 로그인
async function login(loginInfo, success, fail) {
  console.log("#user - api# 로그인 params: ", loginInfo);
  await api
    .post(`/user/login/`, JSON.stringify(loginInfo))
    .then(success)
    .catch(fail);
}

// [GET] 전문가, 관리자 권한만 허용
async function partPermit(userId, success, fail) {
  console.log("#user - api# 일부 권한 허용 params: ", userId);
  await api.get(`/user/auth/permit/${userId}`).then(success).catch(fail);
}

export { login, partPermit };
