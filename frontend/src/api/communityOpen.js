import { apiInstance } from "./index.js";

const api = apiInstance();

// [GET] 공개된 상담 기록 보기
async function getOpenBoard() {
  var result = [];
  await api.get(`${process.env.VUE_APP_API_BASE_URL}/reservation/open`)
  .then((res) => {
    result = res.data;
  })
  .catch()
  return await Promise.resolve(result);
}

export { getOpenBoard };
