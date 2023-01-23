<template>
  여기가 메인페이지
  <div>
    <UserLogin class="card" v-show="!isLogin"></UserLogin>
    <user-alert class="card" v-show="isLogin"></user-alert>
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
.card {
  width: 341px;
  height: 438px;
}
</style>
