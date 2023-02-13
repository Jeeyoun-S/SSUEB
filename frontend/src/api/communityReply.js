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
  console.log(no, replyContent, userId, userNickname)
  await api.post(`${process.env.VUE_APP_API_BASE_URL}/board/reply`, null, {
    params:{
      boardNo : no,
      userId: userId,
      replyContent : replyContent,
      userNickname : userNickname,
    }
  })
  return await Promise.resolve(result);
}


export {getReply, createReply};
