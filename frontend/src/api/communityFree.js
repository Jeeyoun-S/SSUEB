import { apiInstance } from "./index.js";

const api = apiInstance();
async function getFreeBoard() {
    var result = [];
    await api.get(`${process.env.VUE_APP_API_BASE_URL}/board/community`)
    .then((res) => {
      result = res.data;
    })
    .catch()
    return await Promise.resolve(result);
}

async function getHeartWhether(no, id) {
  var result = false;
  await api.post(`${process.env.VUE_APP_API_BASE_URL}/board/heart/whether`, null, {
    params:{
      boardNo: no,
      userId : id,
    }
  })
  .then((res) => {
    result = res.data;
  })
  .catch()
  return await Promise.resolve(result);
}

export {getFreeBoard, getHeartWhether};
