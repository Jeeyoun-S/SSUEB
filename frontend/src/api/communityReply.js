import { apiInstance } from "./index.js";

const api = apiInstance();
async function getReply(no) {
    var result = [];
    await api.get(`${process.env.VUE_APP_API_BASE_URL}/board/community/${no}`)
    .then((res) => {
      result = res.data;
    })
    .catch()
    return await Promise.resolve(result);
}


export {getReply};
