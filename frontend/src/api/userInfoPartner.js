import router from "@/router/index.js";
import { apiInstance } from "./index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// const token = sessionStorage.getItem("token"); // #21#
const token = localStorage.getItem("token");

// [GET] 반려인 회원정보 조회
async function getUserPartnerInfo(id) {
  await api
    // .get(`/user/info/partner/${id}`) // #21# 기존 코드 주석처리
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/info/partner/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#회원정보 조회 성공");

        store.dispatch("getPartnerInfo", res.data.data.userInfo);
        const petInfo = res.data.data.petInfo;
        if (petInfo != null)
          store.dispatch("getPetInfo", res.data.data.petInfo);
      } else {
        console.log("#회원정보 조회 실패");

        Swal.fire({
          title: "FAIL",
          text: "회원정보 조회에 실패했습니다. 다시 시도해 주시기 바랍니다.",
          icon: "error",
        });
        router.push("/");
      }
    })
    .catch(() => {
      console.log("#회원정보 조회 오류");
    });
}

// [POST] 반려인 회원 정보 수정
async function updatePartnerInfo(partnerInfo) {
  partnerInfo.id = store.getters.getPartnerInfo.id;

  api
    .post(`${process.env.VUE_APP_API_BASE_URL}/user/info/partner`, partnerInfo)
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#회원정보 수정 성공");

        store.dispatch("getPartnerInfo", partnerInfo);
        store.dispatch("updateInfoVersion");

        // 수정된 회원정보 반영 (for. store 저장, 메인페이지 표시) > 권한검증을 통해 회원정보 get
        store.dispatch("userStore/checkAnyPermit", null, { root: true });
      } else {
        console.log("#회원정보 수정 실패");

        Swal.fire({
          title: "FAIL",
          text: "회원정보 수정에 실패했습니다. 다시 시도해 주시기 바랍니다.",
          icon: "error",
        });
      }
    });
}

// [POST] 반려동물 등록
async function registerPetInfo(petInfo, id) {
  var value = {};
  value.result = true;
  value.petInfo = null;

  for (let key of petInfo.keys()) {
    console.log(key, ":", petInfo.get(key));
  }

  await api
    // #21# 기존 code 주석 처리
    // .post(`/user/pet/${id}`, petInfo, {
    //   headers: {
    //     "Content-Type": "multipart/form-data; charset=utf-8;",
    //   },
    // })
    .post(`${process.env.VUE_APP_API_BASE_URL}/user/pet/${id}`, petInfo, {
      headers: {
        "Content-Type": "multipart/form-data; charset=utf-8;",
        Authorization: `Bearer ${token}`,
      },
    })
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#반려동물 등록 성공");

        Swal.fire({
          title: "SUCCESS",
          text: "반려동물을 등록했습니다.",
          icon: "success",
        });

        value.result = false;
        value.petInfo = res.data.data;
        console.log(petInfo);
        store.dispatch("addPetInfo", res.data.data);
      } else {
        console.log("#반려동물 등록 실패");

        Swal.fire({
          title: "FAIL",
          text: "반려동물을 등록에 실패했습니다. 등록 정보를 다시 확인해 주세요.",
          icon: "error",
        });
      }
    });

  return await Promise.resolve(value);
}

// [PUT] 반려동물 수정
async function modifyPetInfo(petInfo, petNo) {
  var petFormData = new FormData();

  for (var key in petInfo) {
    if (petInfo[key] != null) {
      if (key == "petImage" && petInfo[key].length >= 1) {
        petFormData.append(key, petInfo[key][0]);
      }
      // else if (key == "petBirth") petFormData.append(key, petInfo[key] + "-01");
      else petFormData.append(key, petInfo[key]);
    }
  }
  petFormData.append("no", petNo);
  var result = true;

  await api
    .put(`${process.env.VUE_APP_API_BASE_URL}/user/pet/${petNo}`, petFormData, {
      headers: {
        "Content-Type": "multipart/form-data; charset=utf-8;",
      },
    })
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#반려동물 수정 성공");

        Swal.fire({
          title: "SUCCESS",
          text: "반려동물 정보를 수정했습니다.",
          icon: "success",
        });

        result = false;

        const newModifyPetInfo = {};
        for (var key in petInfo) {
          newModifyPetInfo[key] = petInfo[key];
          console.log(key, petInfo[key]);
        }

        newModifyPetInfo.no = petNo;
        newModifyPetInfo.petImage = res.data.data.petImage;
        console.log("수정 후", newModifyPetInfo);
        store.dispatch("updatePetInfo", newModifyPetInfo);
      } else {
        console.log("#반려동물 수정 실패");
      }
    });

  return await Promise.resolve(result);
}

// [DELETE] 반려동물 삭제
async function removePetInfo(petNo) {
  await api
    .delete(`${process.env.VUE_APP_API_BASE_URL}/user/pet/${petNo}`)
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#반려동물 삭제 성공");

        store.dispatch("deletePetInfo", petNo);
      } else {
        console.log("#반려동물 삭제 실패");
      }
    });
}

// [POST] 회원정보 수정 전 비밀번호 확인
async function checkPassword(id) {
  const { value: password } = await Swal.fire({
    title: "비밀번호 확인",
    input: "password",
    inputLabel: "회원 확인을 위해 비밀번호를 입력해 주세요.",
    inputPlaceholder: "비밀번호를 입력해 주세요.",
  });

  console.log("비밀번호 확인을 위해 보내는 정보", id, password);
  await api
    .post(`${process.env.VUE_APP_API_BASE_URL}/user/info/password`, {
      id: id,
      password: password,
    })
    .then((res) => {
      if (res.data.response == "success") {
        console.log("#비밀번호 확인 성공");

        store.dispatch("updateInfoVersion");
      } else {
        console.log("#비밀번호 확인 실패");

        Swal.fire({
          icon: "error",
          title: "FAIL",
          text: "비밀번호가 틀렸습니다.",
        });
      }
    });
}

export {
  getUserPartnerInfo,
  registerPetInfo,
  modifyPetInfo,
  removePetInfo,
  checkPassword,
  updatePartnerInfo,
};
