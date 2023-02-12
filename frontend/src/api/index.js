import axios from "axios";

// local vue api axios instance
function apiInstance() {
  const instance = axios.create({
    // # CORS 오류 해결을 위하여 baseURL 주석 처리 (vue.config.js 파일 참고)
    // baseURL: "http://localhost:8080/api",
    // baseURL: process.env.VUE_APP_API_BASE_URL + '/api',
    baseURL: '/api',
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

// form-data 형식 axios instance
// function apiFormInstance() {
//   const instance = axios.create({
//     baseURL: process.env.VUE_APP_API_BASE_URL,
//     headers: {
//       "Content-type": "multipart/form-data",
//     },
//   });
//   return instance;
// }

export { apiInstance };
