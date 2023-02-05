import { apiInstance } from "./index.js";
import router from "@/router/index.js";

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
}

// [POST] 전문가 회원정보 수정

export { getUserConsultantInfo };