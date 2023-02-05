<template>
  <div class="main-page">
    <div class="main-center">
      <div class="main-center-item first">
        <img class="image" :src="require('@/assets/main/wind.gif')" />
        <div class="explain one">
          <h2>SSUEB's Service</h2>
          <p>무슨 내용 적을지 고민해 봅시다.</p>
        </div>
      </div>
      <div class="main-center-item second">
        <div class="explain two">
          <h2>SSUEB's User</h2>
          <p>무슨 내용 적을지 고민해 봅시다.</p>
        </div>
        <img class="image" :src="require('@/assets/main/startle.gif')" />
      </div>
      <div class="main-center-item third">
        <img class="image" :src="require('@/assets/main/walk.gif')" />
        <div class="explain three">
          <h2>SSUEB's Happiness</h2>
          <p>무슨 내용 적을지 고민해 봅시다.</p>
        </div>
      </div>
    </div>
    <div class="main-right">
      <div class="main-right-item top">
        <UserLogin class="card" v-show="!isLogin"></UserLogin>
        <UserAlert class="card" v-show="isLogin"></UserAlert>
      </div>
      <div class="main-right-item border-sheet-two bottom"></div>
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
import { mapState, mapActions } from "vuex";
import UserLogin from "../UserLogin/UserLogin.vue";
import UserAlert from "../UserLogin/UserAlert.vue";

const userStore = "userStore";
const userOAuthStore = "userOAuthStore";

export default {
  name: "mainPage",
  data() {
    return {
      kakaoCode: null,
      googleToken: null,
    };
  },
  created() {
    // #OAuth - 인가 code 받기 (Google, Kakao)
    // i) Kakao 인가 code
    this.kakaoCode = this.$route.query.code;
    if (this.kakaoCode != null) {
      // console.log("#21# kakao 인가 code 확인: ", this.kakaoCode);
      this.kakao();
    }
    // ii) Google access_token
    else {
      const url = new URL(window.location.href);
      const hash = url.hash;
      if (hash) {
        this.googleToken = hash.split("=")[1].split("&")[0];
        // console.log("#21# google access_token 확인: ", this.googleToken);
        this.google();
      }
    }
  },
  components: {
    UserLogin,
    UserAlert,
  },
  computed: {
    ...mapState(userStore, ["isLogin"]),
  },
  methods: {
    ...mapActions(userOAuthStore, ["excuteKakaoToken", "excuteGoogleInfo"]),

    // #OAuth - Kakao# 받은 인가 코드를 사용하여 Kakao Token 발급요청
    async kakao() {
      await this.excuteKakaoToken(this.kakaoCode);
      this.kakaoCode = null; // 받은 인가 code 초기화
    },
    // #OAuth - Google# 받은 access_token을 사용하여 사용자 정보 요청
    async google() {
      await this.excuteGoogleInfo(this.googleToken);
      this.googleToken = null; // 받은 access_token 초기화
    },
  },
};
</script>

<style scoped>
.main-page {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
}
.main-right {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 300px;
  height: 654.4px;
}
.main-right .main-right-item.bottom {
  width: 300px;
  height: 130px;
}
.main-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 730px;
  height: 654.4px;
  margin-right: 40px;
}
.main-center .main-center-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 730px;
  height: 200px;
}
/* .main-page .main-center .main-center-item.first,
.second {
  margin-bottom: 2.6%;
} */
.main-center .main-center-item img {
  width: 200px;
  height: 200px;

  border-radius: 10px;
  object-fit: cover;
}
.main-center .main-center-item .explain {
  width: 500px;
  height: 200px;

  border-radius: 10px;
  padding: 35px;
}
.main-center .main-center-item .explain h2 {
  font-size: 30px;
  font-family: "Patua One", cursive;
  font-weight: lighter;
}
.main-center .main-center-item .explain.one {
  background-color: #e8ebf6;
}
.main-center .main-center-item .explain.two {
  color: white;
  background-color: #3d6ec5;
}
.main-page .main-center .main-center-item .explain.three {
  background-color: #e3e3e3;
}
</style>
