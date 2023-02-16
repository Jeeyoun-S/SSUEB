import { apiInstance } from "./index.js";

const api = apiInstance();

// [PUT] 리뷰 작성
async function registReview(review) {
  var result = false;
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/review`, null, { params: review })
  .then(() => {
    result = true;
  })
  .catch()
  return Promise.resolve(result);
}

// [PUT] 상담 완료 (미완)
async function finishConsult(no) {
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/finish/${no}`)
}

// [PUT] 진단서 작성
async function registDignosis(dignosis) {
  // console.log(dignosis)
  var result = false;
  await api
  .put(`${process.env.VUE_APP_API_BASE_URL}/reservation/dignosis`, null, { params: dignosis })
  .then(() => {
    result = true;
  })
  .catch()
  return Promise.resolve(result);
}

export { registReview, finishConsult, registDignosis };