import { apiInstance } from "./index.js";

const api = apiInstance();

// 로그인
// async function login(user, success, fail) {
//   console.log("## 로그인 params: ", user);
//   await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
// }
async function login(loginInfo, success, fail) {
  console.log("#api# 로그인 params: ", loginInfo);
  await api
    .post(`/user/login/auth`, JSON.stringify(loginInfo))
    .then(success)
    .catch(fail);
}

export { login };
