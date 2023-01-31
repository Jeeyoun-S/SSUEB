const userJoinStore = {
  // namespaced: true, // #21# - 하면 store.dispatch가 안먹음
  state: {
    phoneAuthMessage: "휴대폰 인증",
    phoneAuthStates: false,
    phoneConfirm: null,
    socialUserInfo: null, // #21#
  },
  getters: {
    getPhoneAuthMessage(state) {
      return state.phoneAuthMessage;
    },
    getPhoneAuthStates(state) {
      return state.phoneAuthStates;
    },
    getPhoneConfirm(state) {
      return state.phoneConfirm;
    },
    // #21#
    getSocialUserInfo(state) {
      return state.socialUserInfo;
    },
  },
  mutations: {
    UPDATE_PHONE_AUTH_MESSAGE(state, payload) {
      state.phoneAuthMessage = payload.message;
      state.phoneAuthStates = payload.states;
    },
    UPDATE_PHONE_CONFIRM(state, payload) {
      state.phoneConfirm = payload;
    },
    // #21#
    SET_SOCIAL_USER_INFO(state, socialUserInfo) {
      state.socialUserInfo = socialUserInfo;
      console.log("#21# JoinStore에 정보 저장: ", state.socialUserInfo);
      // console.log("#21# Getter로도 확인: ", this.getSocialUserInfo);
    },
  },
  actions: {
    resetPhoneAuthMessage({ commit }) {
      commit("UPDATE_PHONE_AUTH_MESSAGE", {
        message: "휴대폰 인증",
        states: false,
      });
    },
    updatePhoneAuthMessage({ commit }, phoneNumber) {
      commit("UPDATE_PHONE_AUTH_MESSAGE", {
        message: "휴대폰 인증 완료",
        states: true,
      });
      commit("UPDATE_PHONE_CONFIRM", phoneNumber);
    },
    // #21#
    async setSocialUserInfo(context, info) {
      await context.commit("SET_SOCIAL_USER_INFO", info);
      console.log("#21# user 정보 저장된거 맞냥: ", context.state.socialUserInfo);
      location.href = `${process.env.VUE_APP_BASE_URL}/join`;
    },
  },
};

export default userJoinStore;
