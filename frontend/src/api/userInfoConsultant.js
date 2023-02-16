import { apiInstance } from "./index.js";
import router from "@/router/index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// [GET] 전문가 회원정보 조회
async function getUserConsultantInfo(id) {
  var result = true;
  // await api.get(`/user/info/consultant/${id}`)
  await api
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/info/consultant/${id}`)
    .then((res) => {
      if (res.data.response == "success") {
        // console.log("#회원정보 조회 성공");
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
        };
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
            data.confirmAverage,
          ],
          graphTwo: data.reservationNumber,
          graphThree: data.starNumber,
        };
        store.dispatch("updateGraphInfo", graphInfo);

        // 별점 정보 store에 저장
        const starInfo = {
          averageRating: data.consultantRate,
          consultantRating: data.rate,
        };
        store.dispatch("updateStarInfo", starInfo);
        result = true;
      } else {
        // console.log("#회원정보 조회 실패");

        Swal.fire({
          title: "FAIL",
          text: "회원정보 조회에 실패했습니다. 다시 시도해 주시기 바랍니다.",
          icon: "error",
        });
        router.push("/");
      }
    })
    .catch();
  return await Promise.resolve(result);
}

// [POST] 전문가 회원정보 수정
async function updateConsultantInfo(consultantInfo) {

  const petType = [0, 0, 0, 0, 0, 0];
  for (var i in consultantInfo.consultantPetType) {
    petType[consultantInfo.consultantPetType[i]] = 1;
    // console.log(i, petType[i])
  }
  consultantInfo.consultantPetType = petType.join('');
  // console.log("consultantInfo", petType);
  // console.log("consultantInfo", consultantInfo);

  await api
    // .post(`user/info/consultant`, consultantInfo, {
    .post(
      `${process.env.VUE_APP_API_BASE_URL}/user/info/consultant`,
      consultantInfo,
      {
        headers: {
          "Content-Type": "multipart/form-data; charset=utf-8;",
        },
      }
    )
    .then((res) => {
      if (res.data.response == "success") {
        // console.log("#회원정보 수정 성공");

        consultantInfo.consultantProfile = res.data.message;

        // console.log(consultantInfo);

        delete consultantInfo.userPassword;
        delete consultantInfo.deleteProfile;

        const petType = Array.from(consultantInfo.consultantPetType);
        const petTypeList = [];
        for (var i = 0; i < 6; i++) {
          if (petType[i] == 1) {
            petTypeList.push(i);
          }
        }
        consultantInfo.consultantPetType = petTypeList;

        store.dispatch("updateConsultantUserInfo", consultantInfo);
        store.dispatch("updateInfoVersion");

        // 수정된 회원정보 반영 (for. store 저장, 메인페이지 표시) > 권한검증을 통해 회원정보 get
        store.dispatch("userStore/checkAnyPermit", null, { root: true });
      } else {
        // console.log("#회원정보 수정 실패");

        Swal.fire({
          title: "FAIL",
          text: "회원정보 수정에 실패했습니다. 다시 시도해 주시기 바랍니다.",
          icon: "error",
        });
      }
    })
    .catch();
}

export { getUserConsultantInfo, updateConsultantInfo };
