<template>
  <div>
    <UserLogin class="card" v-show="!isLogin"></UserLogin>
    <user-alert class="card" v-show="isLogin"></user-alert>
  </div>
  <div style="margin-top: 50px; margin-bottom: 70px">
    <v-row class="pt-3" justify="center">
      <v-col cols="3" class="mx-3">
        <v-img
          class="rounded-lg"
          :src="require('@/assets/main/wind.gif')"
        ></v-img>
      </v-col>
      <div class="col-5 main-content-card" elevation="2" style="padding: 0px">
        <div class="background-color-filter">
          <div style="margin-bottom: 30px">SSUEB</div>
          <div style="font-size: 1.8rem; font-weight: bold">아 CSS</div>
          <div style="font-size: 1.8rem; font-weight: bold">너무 싫타</div>
        </div>
      </div>
    </v-row>

    <v-row class="pt-3" justify="center">
      <div class="col-5 main-content-card" elevation="2" style="padding: 0px">
        <div class="background-color-filter" style="background-color: #2fd69c">
          <div style="margin-bottom: 30px">SSUEB</div>
          <div style="font-size: 1.8rem; font-weight: bold">언제쯤이면</div>
          <div style="font-size: 1.8rem; font-weight: bold">
            제대로 적용되나
          </div>
        </div>
      </div>
      <v-col cols="3" class="mx-3">
        <v-img
          class="rounded-lg"
          :src="require('@/assets/main/startle.gif')"
        ></v-img>
      </v-col>
    </v-row>

    <v-row class="pt-3" justify="center">
      <v-col cols="3" class="mx-3">
        <v-img
          class="rounded-lg"
          :src="require('@/assets/main/walk.gif')"
        ></v-img>
      </v-col>
      <div class="col-5 main-content-card" elevation="2" style="padding: 0px">
        <div class="background-color-filter">
          <div style="margin-bottom: 30px">SSUEB</div>
          <div style="font-size: 1.8rem; font-weight: bold">아이스티</div>
          <div style="font-size: 1.8rem; font-weight: bold">너무 마싯당</div>
        </div>
      </div>
    </v-row>
  </div>
</template>

<script>
import UserLogin from "../UserLogin/UserLogin.vue";
import UserAlert from "../UserLogin/UserAlert.vue";
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
.main-content-card {
  border-radius: 5%;
  /* background-image: url("@/assets/img/main3.jpg"); */
  box-shadow: 0px 3px 1px -2px rgb(0 0 0 / 20%),
    0px 2px 2px 0px rgb(0 0 0 / 14%), 0px 1px 5px 0px rgb(0 0 0 / 12%);
}
.background-color-filter {
  width: 100%;
  height: 100%;
  background-color: #2fbdd6;
  opacity: 0.8;
  border-radius: 5%;
  padding: 30px;
  color: white;
}
.card {
  width: 300px;
  height: 430px;
  margin-left: 285%;
  margin-bottom: 95%;
}
</style>
