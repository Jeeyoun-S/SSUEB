const userJoinStore = {
  state: {
    phoneAuthMessage: "휴대폰 인증",
    phoneAuthStates: false,
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
  },
  mutations: {
    UPDATE_PHONE_AUTH_MESSAGE(state, payload) {
      state.phoneAuthMessage = payload.message;
      state.phoneAuthStates = payload.states;
    },
    UPDATE_PHONE_CONFIRM(state, payload) {
      state.phoneConfirm = payload;
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
  },
};

export default userJoinStore;
