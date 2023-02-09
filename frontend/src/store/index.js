// for. 페이지 refresh 시 vuex store 값 유지
import Vuex from "vuex";

// ! npm i --save vuex-persistedstate 설치 필요
import createPersistedState from "vuex-persistedstate";

import userStore from "@/store/modules/userStore.js";
import communityStore from "@/store/modules/communityStore.js";
import meetingRoomStore from "@/store/modules/meetingRoomStore.js";
import reservationStore from "@/store/modules/reservationStore.js";
import userJoinStore from "@/store/modules/userJoinStore.js";
import userInfoPartnerStore from "@/store/modules/userInfoPartnerStore.js";
import userValidStore from "@/store/modules/userValidStore.js";
import userInfoConsultantStore from "@/store/modules/userInfoConsultantStore.js";

// for. OAuth 소셜 로그인
import userOAuthStore from "./modules/userOAuthStore";
import userSocialStore from "./modules/userSocialStore";

// for. 메인페이지
import mainPageStore from "./modules/mainPageStore";

// for. 페이지 refresh 시 vuex store 값 유지
// [기존 code] 주석 처리
// import { createStore } from "vuex";
// export default createStore({
//   state: {},
//   getters: {},
//   mutations: {},
//   actions: {},
//   modules: {
//     userStore: userStore,
//     communityStore: communityStore,
//     meetingRoomStore: meetingRoomStore,
//     reservationStore: reservationStore,
//   },
// });

// for. 페이지 refresh 시 vuex store 값 유지
const store = new Vuex.Store({
  modules: {
    communityStore: communityStore,
    meetingRoomStore: meetingRoomStore,
    reservationStore: reservationStore,
    // 회원 관리 관련
    userStore: userStore,
    userJoinStore: userJoinStore,
    userInfoPartnerStore: userInfoPartnerStore,
    userValidStore: userValidStore,
    userInfoConsultantStore: userInfoConsultantStore,
    // for. OAuth 소셜 로그인
    userOAuthStore: userOAuthStore,
    // for. OAuth 소셜 로그인 성공 시 user 정보를 저장하는 store
    userSocialStore: userSocialStore,
    // for. 메인페이지
    mainPageStore: mainPageStore,
  },
  // vuex plugin 명시
  plugins: [
    createPersistedState({
      // ! localStorage에 저장할 store만을 path에 등록
      //paths: ["userStore", "userJoinStore"],
      paths: ["userStore", "userSocialStore", "mainPageStore"],
    }),
  ],
});

export default store;
