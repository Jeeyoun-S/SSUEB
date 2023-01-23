<template>
  여기가 메인페이지
  <div class="login">
    <UserLogin v-show="!isLogin"></UserLogin>
    <user-alert v-show="isLogin"></user-alert>
  </div>
</template>

<script>
import UserLogin from "./UserLogin.vue";
import UserAlert from "./UserAlert.vue";
import { mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "mainPage",
  components: {
    UserLogin,
    UserAlert,
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
