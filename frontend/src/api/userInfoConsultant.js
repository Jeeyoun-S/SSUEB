import { apiInstance } from "./index.js";
import router from "@/router/index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// [GET] 전문가 회원정보 조회
async function getUserConsultantInfo(id) {
  await api.get(`/user/info/consultant/${id}`)
  .then((res) => {
    if (res.data.response == "success") {
      console.log("#회원정보 조회 성공");
      const data = res.data.data;

      // 회원정보 store에 저장
      const consultantInfo = {
        id: data.id,
        userName: data.userName,
        userPhone: data.userPhone,
        userAlertFlag: data.userAlertFlag,
        consultantIntro: data.consultantIntro,
        consultantProfile: data.consultantProfile,
        consultantPetType: data.consultantPetType,
      }
      store.dispatch("updateConsultantInfo", consultantInfo);
      
      // 그래프 정보 store에 저장
      // 1. 그래프 라벨
      store.dispatch("updateGraphLabel");

      // 2. 그래프 값
      const graphInfo = {
        graphOne: [
          data.consultantReservationCount,
          data.reservationAverage,
          data.confirmCount,
          data.confirmAverage
        ],
        graphTwo: data.reservationNumber,
        graphThree: data.starNumber
      }
      store.dispatch("updateGraphInfo", graphInfo);

      // 별점 정보 store에 저장
      const starInfo = {
        averageRating: data.consultantRate,
        consultantRating: data.rate
      }
      store.dispatch("updateStarInfo", starInfo);
      // router.go(0);
    } else {
      console.log("#회원정보 조회 실패");

      Swal.fire({
        title: 'FAIL',
        text: '회원정보 조회에 실패했습니다.<br>다시 시도해 주시기 바랍니다.',
        icon: 'error'
      });
      router.push("/");
    }
  })
}

// [POST] 전문가 회원정보 수정

export { getUserConsultantInfo };