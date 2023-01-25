import { apiInstance } from "./index.js";

const api = apiInstance();

// [GET] 휴대폰 인증번호 전송
async function sendPhoneAuth(phoneNumber) {
  await api
  .get(`/user/phone/auth`, JSON.stringify(phoneNumber))
  .then((res) => {
    if (res.data.response == "success") {
      let timerInterval

      this.$swal.fire({
        title: '인증 번호를 입력해 주세요.',
        html: 'I will close in <b></b> milliseconds.<br><input id="swal-input1" class="swal2-input">',
        timer: 2000,
        timerProgressBar: true,
        didOpen: () => {
          this.$swal.showLoading()
          const b = this.$swal.getHtmlContainer().querySelector('b')
          timerInterval = setInterval(() => {
          b.textContent = this.$swal.getTimerLeft()
          }, 100)
        },
        willClose: () => {
          clearInterval(timerInterval)
        }
      }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === this.$swal.DismissReason.timer) {
          console.log('I was closed by the timer')
        }
      })
    }
  })
}

export { sendPhoneAuth };