<template>
  <div class="main-page">
    <div class="main-center">
      <div class="main-center-item first">
        <v-sheet color="white" elevation="1" height="200" width="780">
          <v-img
            class="rounded-lg image"
            :src="require('@/assets/main/wind.gif')"
          ></v-img>
        </v-sheet>
      </div>
      <div class="main-center-item second">
        <v-sheet color="white" elevation="1" height="200" width="780">
          <v-img
            class="rounded-lg image"
            :src="require('@/assets/main/startle.gif')"
          ></v-img>
        </v-sheet>
      </div>
      <div class="main-center-item third">
        <v-sheet color="white" elevation="1" height="200" width="780">
          <v-img
            class="rounded-lg image"
            :src="require('@/assets/main/walk.gif')"
          ></v-img>
        </v-sheet>
      </div>
    </div>
    <div class="main-right">
      <div class="main-right-item top">
        <UserLogin class="card" v-show="!isLogin"></UserLogin>
        <UserAlert class="card" v-show="isLogin"></UserAlert>
      </div>
      <div class="main-right-item bottom">
        <v-sheet color="white" elevation="1" height="200" width="300"></v-sheet>
      </div>
    </div>
  </div>

  <!-- <div v-show="false" style="margin-top: 50px; margin-bottom: 70px">
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
  </div> -->
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

<style scoped>
.main-page {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  justify-content: flex-start;
}
.main-page .main-right {
  /* display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: right;

  margin-right: 50px; */
}
.main-page .main-right .main-right-item {
  /* margin-bottom: 1%; */
}
.main-page .main-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 780px;
  margin-right: 50px;
}

.main-page .main-center .main-center-item.first,
.second {
  margin-bottom: 2.6%;
}
.main-page .main-center .main-center-item .image {
  width: 200px;
  height: 200px;
  background-size: cover;
}
</style>
