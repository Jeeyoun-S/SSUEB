<template>
  <v-form class="form">
    {{ valid.email.test(info.email) }}
    {{ info.email }}
    <v-text-field
      v-model="info.email"
      class="mb-2"
      :rules="rules.email"
      label="이메일"
      variant="underlined"
      color="primary"
      required
    ></v-text-field>
    <v-text-field
      v-model="info.password"
      class="mb-2"
      label="비밀번호"
      variant="underlined"
      color="primary"
    ></v-text-field>
    <v-text-field
      v-model="info.name"
      class="mb-2"
      label="이름"
      variant="underlined"
      color="primary"
    ></v-text-field>
    <v-text-field
      v-model="info.nickname"
      class="mb-2"
      label="닉네임"
      variant="underlined"
      color="primary"
    ></v-text-field>
    <v-text-field
      v-model="info.phone"
      class="mb-2"
      label="휴대폰 번호"
      variant="underlined"
      color="primary"
    ></v-text-field>
    <v-btn variant="outlined" size="large" rounded="0" block
      >회원가입 신청</v-btn
    >
  </v-form>
</template>

<script>
export default {
  name: "UserJoinPartner",
  data() {
    return {
      info: {
        email: null,
        password: null,
        name: null,
        nickname: null,
        phone: null,
      },
      valid: {
        email: /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{2,4}$/,
        password:
          /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/,
        name: /^[가-힣|a-z|A-Z]{1,22}$/,
        nickname: /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$/,
        phoen: /^[0-9]{11}$/,
      },
      rules: {
        email: [
          (v) => !!v || "이메일은 필수 입력 사항입니다.",
          (v) =>
            this.valid.email.test(v) ||
            "30자 이하 이메일 형식으로 입력해 주세요.",
        ],
      },
    };
  },
};
</script>

<style scoped>
.form {
  width: 280px;
}
</style>
