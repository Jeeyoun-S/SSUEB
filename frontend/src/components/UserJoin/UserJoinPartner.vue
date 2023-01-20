<template>
  <v-form class="form"> 
    {{ info.email }}
    <v-text-field v-model="info.email" class="mb-2" label="이메일" variant="underlined" color="primary"></v-text-field>
    <v-text-field v-model="info.password" class="mb-2" label="비밀번호" variant="underlined" color="primary"></v-text-field>
    <v-text-field v-model="info.name" class="mb-2" label="이름" variant="underlined" color="primary"></v-text-field>
    <v-text-field v-model="info.nickname" class="mb-2" label="닉네임" variant="underlined" color="primary"></v-text-field>
    <v-text-field v-model="info.phone" class="mb-2" label="휴대폰 번호" variant="underlined" color="primary"></v-text-field>
    <v-btn variant="outlined" size="large" rounded="0" block>회원가입 신청</v-btn>
  </v-form>
</template>

<script>
export default {
  name: 'UserJoinPartner',
  data() {
    return {
      info: {
        email: null,
        password: null,
        name: null,
        nickname: null,
        phone: null
      }
    }
  },
  computed: {
    rules() {
      const rules = "";
      return rules;
    }
  }
}
</script>

<style scoped>
.form {
  width: 280px;
}
</style>