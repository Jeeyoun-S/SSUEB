<template>
  <v-btn
    depressed
    elevation="2"
    large
    class="kakaoBtn"
    color="yellow"
    @click="kakaoLogin"
    >KAKAO</v-btn
  >
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
    kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.VUE_APP_OAUTH_KAKAO_CLIENT}&redirect_uri=${process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI}`
      );
    },
  },
};
</script>

<style>
.kakaoBtn {
  margin-left: 22%;
}
</style>
