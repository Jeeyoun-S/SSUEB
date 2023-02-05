<template>
  <div class="sns-login">
    <img
      :src="require('@/assets/login/naver_login_square.png')"
      @click="naverLogin"
    />
  </div>
</template>

<script>
export default {
  name: "NaverDialog",
  data() {
    return {};
  },
  components: {},
  computed: {},
  methods: {
    // Naver 로그인 연동 동의 요청
    naverLogin() {
      const encodeState = encodeURIComponent(
        `${process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI}`,
        "UTF-8"
      );
      window.location.replace(
        `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${process.env.VUE_APP_OAUTH_NAVER_CLIENT}&state=${encodeState}&redirect_uri=${process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI}`
      );
    },
  },
};
</script>

<style></style>
