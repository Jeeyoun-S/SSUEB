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
    },
    ADD_PET_INFO(state, payload) {
      state.petInfo.push(payload);
    },
    DELETE_PET_INFO(state, payload) {
      const petInfoArray = state.petInfo;

      for (let i=0; i<petInfoArray.length; i++) {
        if (petInfoArray[i].no == payload)  {
          state.petInfo.splice(i, 1);
          break;
        }
      }
    },
    UPDATE_PET_INFO(state, payload) {
      for (var key in payload) {
        console.log(key, payload[key]);
      }

      const petInfoArray = state.petInfo;

      for (let i=0; i<petInfoArray.length; i++) {
        if (petInfoArray[i].no == payload.no)  {
          state.petInfo[i] = payload;
          break;
        }
      }
    }
  },
  actions: {
    getPartnerInfo({ commit }, partnerInfo) {
      partnerInfo.userAlertFlag = String(partnerInfo.userAlertFlag);
      commit("SET_PARTNER_INFO", partnerInfo);
    },
    getPetInfo({ commit }, petInfo) {
      commit("SET_PET_INFO", petInfo);
    },
    addPetInfo({ commit }, petOneInfo) {
      commit("ADD_PET_INFO", petOneInfo);
    },
    deletePetInfo({ commit }, petNo) {
      commit("DELETE_PET_INFO", petNo);
    },
    async updatePetInfo({ commit }, petOneInfo) {
      commit("UPDATE_PET_INFO", petOneInfo);
    }
  },
};

export default userInfoPartnerStore;
