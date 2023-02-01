const userInfoPartnerStore = {
  state: {
    partnerInfo: {
      id: null,
      userName: null,
      userPhone: null,
      userNickname: null,
      userAlertFlag: null
    },
    petInfo: []
  },
  getters: {
    getPartnerInfo(state) {
      return state.partnerInfo;
    },
    getPetInfo(state) {
      return state.petInfo;
    }
  },
  mutations: {
    SET_PARTNER_INFO(state, payload) {
      state.partnerInfo = payload;
    },
    SET_PET_INFO(state, payload) {
      state.petInfo = payload;
    }
  },
  actions: {
    getPartnerInfo({ commit }, partnerInfo) {
      partnerInfo.userAlertFlag = String(partnerInfo.userAlertFlag);
      commit("SET_PARTNER_INFO", partnerInfo);
    },
    getPetInfo({ commit }, petInfo) {
      commit("SET_PET_INFO", petInfo);
    }
  },
};

export default userInfoPartnerStore;
