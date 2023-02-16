import { apiInstance } from "./index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// [GET] 휴대폰 인증번호 전송
async function sendPhoneAuth(phoneNumber) {
  // await api.get(`/user/phone/auth`, { params: phoneNumber }).then((res) => {
  await api
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/phone/auth`, {
      params: phoneNumber,
    })
    .then((res) => {
      if (res.data.response == "success") {
        let timerInterval;

        Swal.fire({
          // 제목
          title: "휴대폰 번호 인증",
          // 내용
          html: '<input id="authNumber" class="swal2-input"><br>3분 내로 인증번호를 입력해 주세요.<br>남은 시간 <b></b>',
          // 창 제한 시간 3분 = 18000ms
          timer: 180000,
          // 제한 시간을 보여주는 바
          timerProgressBar: true,
          // 열렸을 때 시간 보여주기
          didOpen: () => {
            const b = Swal.getHtmlContainer().querySelector("b");
            timerInterval = setInterval(() => {
              b.textContent =
                String(
                  parseInt(Swal.getTimerLeft() / (1000 * 60)) % 60
                ).padStart(2, "0") +
                ":" +
                String(parseInt(Swal.getTimerLeft() / 1000) % 60).padStart(
                  2,
                  "0"
                );
            }, 100);
          },
          // 시간 지나면 창 닫힘
          willClose: () => {
            clearInterval(timerInterval);
          },
          // 확인 및 취소 버튼 노출
          confirmButtonText: "확인",
          cancelButtonText: "취소",
          showCancelButton: true,
          showCloseButton: true,
          focusConfirm: false,
          // 확인하기 버튼 눌렀을 때
          preConfirm: async () => {
            confirmPhoneAuth(
              document.getElementById("authNumber").value,
              phoneNumber.userPhone
            );
          },
        }).then((result) => {
          /* Read more about handling dismissals below */
          if (result.dismiss === Swal.DismissReason.timer) {
            // console.log("I was closed by the timer");
          }
        });
      }
    })
    .catch()
}

// [GET] 인증번호 전송 - 문자 안 보내는 테스트용
// async function sendPhoneAuth(phoneNumber) {

//   let timerInterval

//   Swal.fire({
//     // 제목
//     title: '휴대폰 번호 인증',
//     // 내용
//     html: '<input id="authNumber" class="swal2-input"><br>3분 내로 인증번호를 입력해 주세요.<br>남은 시간 <b></b>',
//     // 창 제한 시간 3분 = 18000ms
//     timer: 180000,
//     // 제한 시간을 보여주는 바
//     timerProgressBar: true,
//     // 열렸을 때 시간 보여주기
//     didOpen: () => {
//       const b = Swal.getHtmlContainer().querySelector('b');
//       timerInterval = setInterval(() => {
//         b.textContent = String(parseInt(Swal.getTimerLeft()/(1000*60))%60).padStart(2,'0')+":"+String(parseInt(Swal.getTimerLeft()/1000)%60).padStart(2,'0');
//       }, 100)
//     },
//     // 시간 지나면 창 닫힘
//     willClose: () => {
//       clearInterval(timerInterval)
//     },
//     // 확인 및 취소 버튼 노출
//     confirmButtonText: '확인',
//     cancelButtonText: '취소',
//     showCancelButton: true,
//     showCloseButton: true,
//     focusConfirm: false,
//     // 확인하기 버튼 눌렀을 때
//     preConfirm: async () => {
//       confirmPhoneAuth(document.getElementById("authNumber").value, phoneNumber.userPhone);
//     }
//   }).then((result) => {
//     /* Read more about handling dismissals below */
//     if (result.dismiss === Swal.DismissReason.timer) {
//       console.log('I was closed by the timer')
//     }
//   })
// }
// confirmPhoneAuth;
// [GET] 휴대폰 인증번호 확인
async function confirmPhoneAuth(authNumber, userPhone) {
  await api
    // .get(`/user/phone/confirm`, {
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/phone/confirm`, {
      params: {
        authNumber: authNumber,
        userPhone: userPhone,
      },
    })
    .then((res) => {
      if (res.data.response == "success") {
        Swal.fire("인증 완료", "휴대폰 인증이 완료됐습니다.", "success").then(
          () => {
            store.dispatch("updatePhoneAuthMessage", userPhone);
          }
        );
      } else {
        Swal.fire(
          "인증 실패",
          "인증 번호가 틀렸습니다. 다시 시도해 주시기 바랍니다.",
          "error"
        );
      }
    })
    .catch()
}

// [GET] 반려인 회원가입 진행
async function joinPartner(joinRequest, socialAccess, provider) {
  // ! 소셜 로그인을 통한 회원가입 접근 (비밀번호 없음)
  if (socialAccess == false) {
    if (provider == "KAKAO") {
      joinRequest.userPassword = `${process.env.VUE_APP_OAUTH_KAKAO}`;
    } else {
      joinRequest.userPassword = `${process.env.VUE_APP_OAUTH_GOOGLE}`;
    }
    joinRequest.userIsSocialId = 1; // 소셜 로그인 ID 체크
  }

  await api
    .post(
      `${process.env.VUE_APP_API_BASE_URL}/user/join/partner`,
      JSON.stringify(joinRequest)
    )
    .then((res) => {
      // 회원가입 성공
      if (res.data.response == "success") {
        Swal.fire(
          "회원가입 성공",
          "회원가입이 완료됐습니다. 메인페이지로 이동합니다.",
          "success"
        ).then(async () => {
          if (res.data.message.includes("실패")) {
            // 로그인 실패 > 로그인 창으로 이동
            // console.log("#userJoin - api# 회원가입 후 로그인 실패 res: ", res);
            location.href = process.env.VUE_APP_BASE_URL;
          } else {
            // 로그인 성공 > 로그인 후 메인화면으로 이동
            // console.log(
            //   "#userJoin - api# [반려인] 회원가입 후 로그인 성공 res: ",
            //   res
            // );
            await store.dispatch("userStore/setAutoLogin", res, { root: true });
            await store.dispatch("userStore/moveMainPage", null, {
              root: true,
            });
          }
          // location.href = process.env.VUE_APP_BASE_URL;
        });
      }
      // 회원가입 실패
      else {
        Swal.fire(
          "회원가입 실패",
          "회원가입을 하지 못했습니다. 다시 시도해 주시기 바랍니다.",
          "error"
        ).then(() => {
          // 회원가입 창으로 이동
        });
      }
    })
    .catch()
}

// [POST] 전문가 회원가입 진행
async function joinConsultant(formData, socialAccess, provider) {
  // formData;
  // ! 소셜 로그인을 통한 회원가입 접근
  if (socialAccess == false) {
    if (provider == "KAKAO") {
      formData.set("userPassword", `${process.env.VUE_APP_OAUTH_KAKAO}`);
    } else {
      formData.set("userPassword", `${process.env.VUE_APP_OAUTH_GOOGLE}`);
    }
    formData.append("userIsSocialId", 1); // 소셜 로그인 ID 체크
  }

  await api
    // .post(`/user/join/consultant`, formData, {
    .post(
      `${process.env.VUE_APP_API_BASE_URL}/user/join/consultant`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data; charset=utf-8;",
        },
      }
    )
    .then((res) => {
      // 회원가입 성공
      if (res.data.response == "success") {
        Swal.fire(
          "회원가입 신청 완료",
          "가입신청이 완료됐습니다.<br>7일 이내에 자격 심사 후<br>선택하신 알림방법으로 안내드릴 예정입니다.",
          "success"
        ).then(async () => {
          // 메인페이지로 이동
          // console.log("#userJoin - api# [전문가] 회원가입 후 로그인 성공 res: ", res);
          await store.dispatch("userStore/setAutoLogin", res, { root: true });
          await store.dispatch("userStore/moveMainPage", null, {
            root: true,
          });
        });
        // 메인페이지로 이동
        location.href = process.env.VUE_APP_BASE_URL;
      }

      // 회원가입 실패
      else {
        Swal.fire(
          "회원가입 실패",
          "회원가입을 하지 못했습니다. 다시 시도해 주시기 바랍니다.",
          "error"
        ).then(() => {
          // 회원가입 창으로 이동
          location.href = process.env.VUE_APP_BASE_URL;
        });
      }
    })
    .catch()
}

// [GET] 아이디 중복 확인
async function duplicateId(id) {
  var result = false;
  await api
    // .get(`/user/join/duplication/id`, { params: { id: id } })
    .get(`${process.env.VUE_APP_API_BASE_URL}/user/join/duplication/id`, {
      params: { id: id },
    })
    .then((res) => {
      if (res.data.response == "success") {
        result = true;
      }
    })
    .catch()

  return await Promise.resolve(result);
}

export { sendPhoneAuth, joinPartner, joinConsultant, duplicateId };
