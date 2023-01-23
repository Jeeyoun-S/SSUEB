import { apiInstance } from "./index.js";

const api = apiInstance();

// 로그인
async function login(loginInfo, success, fail) {
  console.log("#api# 로그인 params: ", loginInfo);
  await api
    .post(`/user/login/`, JSON.stringify(loginInfo))
    .then(success)
    .catch(fail);
}

export { login };
