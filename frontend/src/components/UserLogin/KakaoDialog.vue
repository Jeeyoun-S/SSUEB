<template>
  <div class="sns-login">
    <img :src="require('@/assets/login/kakao_logo.png')" @click="kakaoLogin" alt="google login" />
  </div>
</template>

<script>
export default {
  name: "KakaoDialog",
  data() {
    return {};
  },
  components: {},
  computed: {},
  methods: {
    // Kakao 인가 code 받기 (userOAuthStore로 전달)
    kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.VUE_APP_OAUTH_KAKAO_CLIENT}&redirect_uri=${process.env.VUE_APP_OAUTH_REDIRECT_URI}`
      );
    },
  },
};
</script>

<style>
.sns-login img {
  width: 45px;
  height: 45px;
}
.sns-login img:hover {
  cursor: pointer;
}
</style>
