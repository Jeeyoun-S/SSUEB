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
      <!-- isLogin : [false - 로그인 X / true - 로그인 O]-->
      <UserLogin class="card" v-show="!this.isLogin"></UserLogin>
      <UserAlert class="card" v-show="false"></UserAlert>
      <UserMainAlert v-show="this.isLogin"></UserMainAlert>
      <BoardTopFive v-show="this.isLogin"></BoardTopFive>
      <div class="main-right-item bottom">
        <MainPageChatBot></MainPageChatBot>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import UserLogin from "../UserLogin/UserLogin.vue";
import UserAlert from "../UserLogin/UserAlert.vue";
import MainPageChatBot from "@/components/MainPage/MainPageChatBot.vue";
import BoardTopFive from "@/components/MainPage/BoardTopFive.vue";
import UserMainAlert from "@/components/MainPage/UserMainAlert.vue";

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
    MainPageChatBot,
    BoardTopFive,
    UserMainAlert,
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
  align-self: start;
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
