<template>
  <v-form class="form" lazy-validation> 
    <v-text-field v-model="info.email" class="mb-2" :rules="rules.email" label="이메일" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.password" class="mb-2" :rules="rules.password" label="비밀번호" type="Password" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.name" class="mb-2" :rules="rules.name" label="이름" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.nickname" class="mb-2" :rules="rules.nickname" label="닉네임" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.phone" ref="userPhone" class="mb-2" :rules="rules.phone" label="휴대폰 번호" variant="underlined" color="primary" required></v-text-field>
    <v-btn variant="tonal" rounded="0" @click="phoneAuth()" :disabled="phoneDisable" block>{{ phoneButton }}</v-btn>
    <v-radio-group v-model="info.alertFlag" inline>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" size="large" rounded="0" block>회원가입 하기</v-btn>
  </v-form>
</template>

<script>
import { sendPhoneAuth } from "@/api/userJoin"

export default {
  name: 'UserJoinPartner',
  data() {
    return {
      info: {
        email: null,
        password: null,
        name: null,
        nickname: null,
        phone: null,
        alertFlag: "0",
      },
      valid: {
        email: /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/,
        password: /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/,
        name: /^[가-힣|a-z|A-Z]{1,22}$/,
        nickname: /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$/,
        phone: /^[0-9]{11}$/
      },
      rules: {
        email: [
          v => !!v || '이메일은 필수 입력 사항입니다.',
          v => this.valid.email.test(v) || '이메일 형식으로 입력해 주세요.',
          v => v.length <= 30 || '30자 이하로 입력해 주세요.',
        ],
        password: [
          v => !!v || '비밀번호는 필수 입력 사항입니다.',
          v => this.valid.password.test(v) || '영어, 숫자, 특수문자를 포함해 10~20자로 입력해 주세요.',
        ],
        name: [
          v => !!v || '이름은 필수 입력 사항입니다.',
          v => v.length <= 22 || '22자 이하로 입력해 주세요.',
          v => this.valid.name.test(v) || '숫자, 특수문자는 입력 불가능합니다.',
        ],
        nickname: [
          v => !!v || '닉네임은 필수 입력 사항입니다.',
          v => v.length <= 22 || '22자 이하로 입력해 주세요.',
          v => this.valid.nickname.test(v) || '특수문자는 입력 불가능합니다.',
        ],
        phone: [
          v => !!v || '휴대폰 번호는 필수 입력 사항입니다.',
          v => this.valid.phone.test(v) || "'-' 없이 숫자 11자리로 입력해 주세요.",
        ]
      },
      phoneDisable: true,
      isPhoneAuth: false,
      phoneButton: "휴대폰 인증"
    }
  },
  methods: {
    phoneAuth() {
      console.log("휴대폰 인증 시작");
      sendPhoneAuth({
        userPhone: this.info.phone,
      });
    }
  },
  watch: {
    info: {
      handler: function() {
        this.$refs.userPhone.validate().then(
          (v) => {
            if (v.length > 0) {
              this.phoneDisable = true;
            } else {
              this.phoneDisable = false;
            }
          }
        )
      },
      deep: true
    },
  },
}
</script>

<style scoped>
.form {
  width: 320px;
}
</style>