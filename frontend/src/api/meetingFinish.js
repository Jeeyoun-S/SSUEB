import { apiInstance } from "./index.js";

const api = apiInstance();

// [PUT] 리뷰 작성
async function registReview(review) {
  review
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/review`, review)
}

// [PUT] 상담 완료
async function finishConsult(no) {
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/finish/${no}`)
}

// [PUT] 진단서 작성
async function registDignosis(dignosis) {
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/dignosis`, dignosis)
}

export { registReview, finishConsult, registDignosis };