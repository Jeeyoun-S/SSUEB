<template>
  여기가 메인페이지
  <div class="login">
    <UserLogin></UserLogin>
  </div>
</template>

<script>
import UserLogin from "./UserLogin.vue";

export default {
  name: "mainPage",
  components: {
    UserLogin,
  },
};
</script>

<style>
.login {
  margin-left: 1000;
}
</style>
