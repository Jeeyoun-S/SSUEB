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

export { getUserPartnerInfo };