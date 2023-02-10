import { apiInstance } from "./index.js";

const api = apiInstance();

// [GET] 공지사항 글 불러오기
async function getNoticeBoard() {
  var result = [];
  await api.get(`${process.env.VUE_APP_API_BASE_URL}/board/notice`)
  .then((res) => {
    result = res.data;
  })
  .catch()
  return await Promise.resolve(result);
}

// [Get] 상세 글 조회하기
async function getDetailBoard(no) {
  var result = [];
  await api.get(`${process.env.VUE_APP_API_BASE_URL}/board/community/${no}`)
  .then((res) => {
    result = res.data;
  })
  .catch()
  return await Promise.resolve(result);
}

export { getNoticeBoard, getDetailBoard };
