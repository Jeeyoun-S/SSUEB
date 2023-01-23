<template>
  여기가 메인페이지
  <div class="login">
    <UserLogin v-show="!isLogin"></UserLogin>
  </div>
</template>

<script>
import UserLogin from "./UserLogin.vue";
import { mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "mainPage",
  components: {
    UserLogin,
  },
  computed: {
    ...mapState(userStore, ["isLogin"]),
  },
};
</script>

<style>
/* .login {
  margin-left: 40%;
} */
</style>
