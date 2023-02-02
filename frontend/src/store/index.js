// for. 페이지 refresh 시 vuex store 값 유지
import Vuex from "vuex";

// ! npm i --save vuex-persistedstate 설치 필요
import createPersistedState from "vuex-persistedstate";

import userStore from "@/store/modules/userStore.js";
import communityStore from "@/store/modules/communityStore.js";
import meetingRoomStore from "@/store/modules/meetingRoomStore.js";
import reservationStore from "@/store/modules/reservationStore.js";
import userJoinStore from "@/store/modules/userJoinStore.js";

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
    userStore: userStore,
    communityStore: communityStore,
    meetingRoomStore: meetingRoomStore,
    reservationStore: reservationStore,
    userJoinStore: userJoinStore
  },
  // vuex plugin 명시
  plugins: [
    createPersistedState({
      // ! localStorage에 저장할 store만을 path에 등록
      paths: ["userStore"],
    }),
  ],
});

export default store;
