<template>
  <div class="sns-login">
    <img
      :src="require('@/assets/login/google_logo.png')"
      @click="googleLogin"
    />
  </div>
</template>

<script>
export default {
  name: "GoogleDialog",
  data() {
    return {};
  },
  components: {},
  computed: {},
  methods: {
    // Google 로그인 연동 동의 요청
    googleLogin() {
      window.location.replace(
        `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.VUE_APP_OAUTH_GOOGLE_CLIENT}&response_type=token&redirect_uri=${process.env.VUE_APP_OAUTH_REDIRECT_URI}&scope=https://www.googleapis.com/auth/userinfo.email`
      );
    },
  },
};
</script>

<style></style>
