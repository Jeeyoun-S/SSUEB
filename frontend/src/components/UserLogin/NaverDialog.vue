<template>
  <div class="sns-login">
    <img
      :src="require('@/assets/login/naver_login_square.png')"
      @click="kakaoLogin"
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
    naverLogin() {},
  },
};
</script>

<style></style>
