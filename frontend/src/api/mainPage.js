import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 메인페이지 - 좋아요 Top 5 게시글 가져오기
async function getTopFiveBoard(success, fail) {
  // await api.get(`/board/popular`).then(success).catch(fail);
  await api
    .get(`${process.env.VUE_APP_API_BASE_URL}/board/popular`)
    .then(success)
    .catch(fail);
}

// [GET] 메인페이지 - 현재 로그인한 사용자의 금일 예약 상담 건 수 조회
async function getReservationCount(id, success, fail) {
  // await api.get(`/user/logout/alert/${id}`).then(success).catch(fail);
  await api
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/logout/alert/${id}`)
    .then(success)
    .catch(fail);
}

export { getTopFiveBoard, getReservationCount };
