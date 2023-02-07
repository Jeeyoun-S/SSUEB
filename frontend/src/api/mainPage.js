import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 메인페이지 - 좋아요 Top 5 게시글 가져오기
async function getTopFiveBoard(success, fail) {
  await api.get(`/board/popular`).then(success).catch(fail);
}

export { getTopFiveBoard };
