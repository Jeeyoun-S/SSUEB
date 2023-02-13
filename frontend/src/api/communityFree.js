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

async function pushHeart(boardNo, id, isHeart){
  var result = [];
  await api.post(`${process.env.VUE_APP_API_BASE_URL}/board/heart`, null, {
    params:{
      boardNo: boardNo,
      userId : id,
      flag : isHeart,
    }
  })
  .then((res) => {
    result = res.data;
  })
  .catch()
  return await Promise.resolve(result);
}

async function deleteBoard(boardNo){
  await api.delete(`${process.env.VUE_APP_API_BASE_URL}/board/community/${boardNo}`)
  .then(() => {
    console.log("삭제");
  })
}

export {getFreeBoard, getHeartWhether, pushHeart, deleteBoard};
