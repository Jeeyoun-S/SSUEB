import { apiInstance } from "./index.js";
import store from "@/store/index.js";

// api instance 가져오기
const api = apiInstance();

// sweetalert2 가져오기
const Swal = require("sweetalert2");

// [GET] 휴대폰 인증번호 전송
async function sendPhoneAuth(phoneNumber) {
  await api.get(`/user/phone/auth`, { params: phoneNumber }).then((res) => {
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
              String(parseInt(Swal.getTimerLeft() / (1000 * 60)) % 60).padStart(
                2,
                "0"
              ) +
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
          console.log("I was closed by the timer");
        }
      });
    }
  });
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
confirmPhoneAuth
// [GET] 휴대폰 인증번호 확인
async function confirmPhoneAuth(authNumber, userPhone) {
  await api
    .get(`/user/phone/confirm`, {
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
    });
}

// [GET] 반려인 회원가입 진행
async function joinPartner(joinRequest) {
  await api
    .post(`/user/join/partner`, JSON.stringify(joinRequest))
    .then((res) => {
      // 회원가입 성공
      if (res.data.response == "success") {
        Swal.fire(
          "회원가입 성공",
          "회원가입이 완료됐습니다. 메인페이지로 이동합니다.",
          "success"
        ).then(() => {
          if (res.data.message.includes("실패")) {
            // 로그인 실패 > 로그인 창으로 이동
          } else {
            // 로그인 성공 > 로그인 후 메인화면으로 이동
          }
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
    });
}

// [POST] 전문가 회원가입 진행
async function joinConsultant(formData) {
  formData;

  await api
  .post(`/user/join/consultant`, formData, {
    headers: {
      "Content-Type": "multipart/form-data; charset=utf-8",
    }
  }
  )
  .then((res) => {
    // 회원가입 성공
    if (res.data.response == "success") {
      Swal.fire(
        '회원가입 신청 완료',
        '가입신청이 완료됐습니다. 7일 이내에 자격 심사 후 선택하신 알림방법으로 안내드릴 예정입니다.',
        'success'
      ).then(() => {
        // 메인페이지로 이동
      })
    }
    
    // 회원가입 실패
    else {
      Swal.fire(
        '회원가입 실패',
        '회원가입을 하지 못했습니다. 다시 시도해 주시기 바랍니다.',
        'error'
      ).then(() => {
        // 회원가입 창으로 이동
      })
    }
  })
}

// [GET] 아이디 중복 확인
async function duplicateId(id) {
  var result = false;
  await api
    .get(`/user/join/duplication/id`, { params: { id: id } })
    .then((res) => {
      if (res.data.response == "success") {
        result = true;
      }
    });

  return await Promise.resolve(result);
}

export { sendPhoneAuth, joinPartner, joinConsultant, duplicateId };
