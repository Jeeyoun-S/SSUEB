import { apiInstance } from "./index.js";

const api = apiInstance();
async function getReply(no) {
    var result = [];
    await api.get(`${process.env.VUE_APP_API_BASE_URL}/board/reply/${no}`)
    .then((res) => {
      result = res.data;
    })
    .catch(error=>{
      console.log(error);
    })
    return await Promise.resolve(result);
}

async function createReply(no, replyContent, userId, userNickname) {
  var result = [];
  await api.post(`${process.env.VUE_APP_API_BASE_URL}/board/reply`, null, {
    params:{
      boardNo : no,
      userId: userId,
      replyContent : replyContent,
      userNickname : userNickname,
    }
  })
  .then((res) => {
    result = res.data;
  })
  .catch(error=>{
    console.log(error);
  })
  return await Promise.resolve(result);
}

async function removeReply(no) {
  await api.delete(`${process.env.VUE_APP_API_BASE_URL}/board/reply/community/${no}`)
  .then(() => {
    console.log("딜리트")
  })
  .catch(error=>{
    console.log(error);
  })
}

export {getReply, createReply, removeReply};
