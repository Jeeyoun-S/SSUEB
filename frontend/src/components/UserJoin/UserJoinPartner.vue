<template>
  <v-form class="form" ref="form" lazy-validation> 
    <v-text-field v-model="info.id" class="mb-2" :rules="rules.email" label="이메일" variant="underlined" color="primary" required></v-text-field>
    {{ duplicateIdCheck }}
    <v-text-field v-model="info.userPassword" class="mb-2" :rules="rules.password" label="비밀번호" type="Password" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.userName" class="mb-2" :rules="rules.name" label="이름" variant="underlined" color="primary" required></v-text-field>
    <v-text-field v-model="info.userNickname" class="mb-2" :rules="rules.nickname" label="닉네임" variant="underlined" color="primary" required></v-text-field>
    <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
    <v-radio-group v-model="info.userAlertFlag" inline>
      알림수신방법
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" size="large" rounded="0" @click="validate()" block>회원가입 하기</v-btn>
  </v-form>
</template>

<script>
import UserJoinPhone from '@/components/UserJoin/UserJoinPhone.vue'
import { duplicateId, joinPartner } from '@/api/userJoin.js'

export default {
  name: 'UserJoinPartner',
  components: {
    UserJoinPhone
  },
  data() {
    return {
      info: {
        id: null,
        userPassword: null,
        userName: null,
        userNickname: null,
        userAlertFlag: "0",
        userPhone: null
      },
      valid: {
        email: /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/,
        password: /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/,
        name: /^[가-힣|a-z|A-Z]{1,22}$/,
        nickname: /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$/,
      },
      rules: {
        email: [
          v => !!v || '이메일은 필수 입력 사항입니다.',
          v => this.valid.email.test(v) || '이메일 형식으로 입력해 주세요.',
          v => v.length <= 30 || '30자 이하로 입력해 주세요.',
          () => this.duplicateIdCheck || '중복된 이메일입니다.'
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
        ]
      },
      duplicateIdCheck: false
    }
  },
  watch: {
    info: {
      async handler() {
        console.log("watch 호출")
        
        // 공백 제거
        if (this.info.id) this.info.id = await this.info.id.replace(" ", "");
        if (this.info.userPassword) this.info.userPassword = await this.info.userPassword.replace(" ", "");
        if (this.info.userName) this.info.userName = await this.info.userName.replace(" ", "");
        if (this.info.userNickname) this.info.userNickname = await this.info.userNickname.replace(" ", "");
        
        // 아이디 중복 확인
        if (this.valid.email.test(this.info.id)) {
          duplicateId(this.info.id).then((res) => {
            this.duplicateIdCheck = res;
            this.info.id = this.info.id + " ";
          })
        }
      },
      deep: true
    }
  },
  computed: {
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid && this.phoneAuthStates) {
        joinPartner(this.info);
      }
    },
    updatePhone(userPhone) {
      this.info.userPhone = userPhone;
    }, 
  },
}
</script>

<style scoped>
.form {
  width: 320px;
}
</style>