const userJoinStore = {
  state: {
    phoneAuthMessage: "휴대폰 인증",
    phoneAuthStates: false,
    phoneAuthWarning: "휴대폰 인증을 진행해 주세요.",
    phoneConfirm: null,
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
    getPhoneAuthWarning(state) {
      return state.phoneAuthWarning;
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
    UPDATE_PHONE_WARNING(state, payload) {
      state.phoneAuthWarning = payload;
    },
  },
  actions: {
    resetPhoneAuthMessage({ commit }) {
      commit("UPDATE_PHONE_AUTH_MESSAGE", {
        message: "휴대폰 인증",
        states: false,
      });

      commit("UPDATE_PHONE_WARNING", "휴대폰 인증을 진행해 주세요.");
    },
    updatePhoneAuthMessage({ commit }, phoneNumber) {
      commit("UPDATE_PHONE_AUTH_MESSAGE", {
        message: "휴대폰 인증 완료",
        states: true,
      });
      commit("UPDATE_PHONE_CONFIRM", phoneNumber);
      commit("UPDATE_PHONE_WARNING", "");
    },
  },
};

export default userJoinStore;
