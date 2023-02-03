import router from "@/router/index.js";
import { apiInstance } from "./index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// [GET] 반려인 회원정보 조회
async function getUserPartnerInfo(id) {
  await api.get(`/user/info/partner/${id}`)
  .then((res) => {
    if (res.data.response == "success") {
      console.log("#회원정보 조회 성공");
      
      store.dispatch("getPartnerInfo", res.data.data.userInfo);
      const petInfo = res.data.data.petInfo;
      if (petInfo != null) store.dispatch("getPetInfo", res.data.data.petInfo);

    } else {
      console.log("#회원정보 조회 실패");

      Swal.fire({
        title: 'FAIL',
        text: '회원정보 조회에 실패했습니다. 다시 시도해 주시기 바랍니다.',
        icon: 'error'
      });
      router.push("/");
    }
  })
  .catch(() => {
    console.log("#회원정보 조회 오류");
  })
}

// [POST] 반려동물 등록
async function registerPetInfo(petInfo, id) {
  var result = true;

  for (let key of petInfo.keys()) {
    console.log(key, ":", petInfo.get(key));
  }

  await api.post(`/user/pet/${id}`, petInfo, {
    headers: {
      "Content-Type": "multipart/form-data; charset=utf-8;",
    },
  }).then((res) => {
    if (res.data.response == "success") {
      console.log("#반려동물 등록 성공");
      
      Swal.fire({
        title: 'SUCCESS',
        text: '반려동물을 등록했습니다.',
        icon: 'success'
      });

      result = false;
      console.log(petInfo);
      store.dispatch("addPetInfo", res.data.data);

    } else {
      console.log("#반려동물 등록 실패");
      
      Swal.fire({
        title: 'FAIL',
        text: '반려동물을 등록에 실패했습니다. 등록 정보를 다시 확인해 주세요.',
        icon: 'error'
      });
    }
  })

  return await Promise.resolve(result);
}

// [PUT] 반려동물 수정
async function modifyPetInfo(petInfo, petNo) {
  var petFormData = new FormData();

  for (var key in petInfo) {

    if (petInfo[key] != null) {
      if (key == "petImage" && petInfo[key].length >= 1) {
        petFormData.append(key, petInfo[key][0]);
      }
      else if (key == "petBirth") petFormData.append(key, petInfo[key] + "-01")
      else petFormData.append(key, petInfo[key]);
    }
  }
  petFormData.append("no", petNo);
  var result = true;
  
  await api.put(`/user/pet/${petNo}`, petFormData, {
    headers: {
      "Content-Type": "multipart/form-data; charset=utf-8;",
    },
  })
  .then((res) => {
    if (res.data.response == "success") {
      console.log("#반려동물 수정 성공");

      Swal.fire({
        title: 'SUCCESS',
        text: '반려동물 정보를 수정했습니다.',
        icon: 'success'
      });

      result = false;
      petInfo.no = petNo;
      store.dispatch("updatePetInfo", petInfo);

    } else {
      console.log("#반려동물 수정 실패");
    }
  })

  return await Promise.resolve(result);
}

// [DELETE] 반려동물 삭제
async function removePetInfo(petNo) {
  await api.delete(`/user/pet/${petNo}`)
  .then((res) => {
    if (res.data.response == "success") {
      console.log("#반려동물 삭제 성공");
      
      store.dispatch("deletePetInfo", petNo);
    } else {
      console.log("#반려동물 삭제 실패");
    }
  })
}

// [POST] 회원정보 수정 전 비밀번호 확인
async function checkPassword() {
  await api.post(`/user/info/password`)
  .then((res) => {
    if (res.data.response == "success") {
      console.log("#반려동물 삭제 성공");
      
    } else {
      console.log("#반려동물 삭제 실패");
    }
  })
}

export { getUserPartnerInfo, registerPetInfo, modifyPetInfo, removePetInfo, checkPassword };