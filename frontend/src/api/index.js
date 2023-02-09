import axios from "axios";

// local vue api axios instance
function apiInstance() {
  const instance = axios.create({
    // # CORS 오류 해결을 위하여 baseURL 주석 처리 (vue.config.js 파일 참고)
    // baseURL: "http://localhost:8080/api",
    // baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  // # axios 실행 전 request(요청)을 interceptor하여 header에 jwt token 넣기
  instance.interceptors.request.use(
    (config) => {
      // console.log("#21# api token 확인: ", localStorage.getItem("token"));
      config.headers.Authorization = `Bearer ${localStorage.getItem("token")}`;
      config.headers["Access-Control-Allow-Origin"] = "*";
      return config;
    },
    (error) => {
      return console.log("#axios# ", error);
    }
  );

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
