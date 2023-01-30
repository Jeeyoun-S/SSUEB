<template>
  <!-- <v-btn depressed icon elevation="2" large>kakao</v-btn> -->
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
    return {
      id: process.env.VUE_APP_OAUTH_KAKAO_CLIENT,
      uri: process.env.VUE_APP_OAUTH_KAKAO_REDIRECT_URI,
    };
  },
  components: {},
  computed: {},
  methods: {
    kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${this.id}&redirect_uri=${this.uri}`
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
