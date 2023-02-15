import { apiInstance } from "./index.js";

const api = apiInstance();

// [DELETE] 매칭 삭제
async function deleteMatching(no) {
  var result = false;
  await api
  .delete(`${process.env.VUE_APP_API_BASE_URL}/reservation/matching/${no}`)
  .then(() => {
    result = true;
  })
  .catch()
  return await Promise.resolve(result);
}

export { deleteMatching };