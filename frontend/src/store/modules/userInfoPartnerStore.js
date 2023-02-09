const userInfoPartnerStore = {
  state: {
    partnerInfo: {
      id: null,
      userName: null,
      userPhone: null,
      userNickname: null,
      userAlertFlag: null,
    },
    petInfo: [],
    infoVer: true,
    registOpen: false,
    petImagePath: "@/image/pet/"
  },
  getters: {
    getPartnerInfo(state) {
      return state.partnerInfo;
    },
    getPetInfo(state) {
      return state.petInfo;
    },
    getInfoVersion(state) {
      return state.infoVer;
    },
    getRegistOpen(state) {
      return state.registOpen;
    },
    getPetImagePath(state) {
      return state.petImagePath;
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

      for (let i = 0; i < petInfoArray.length; i++) {
        if (petInfoArray[i].no == payload) {
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

      for (let i = 0; i < petInfoArray.length; i++) {
        if (petInfoArray[i].no == payload.no) {
          state.petInfo[i] = payload;
          break;
        }
      }
    },
    UPDATE_INFO_VERSION(state) {
      state.infoVer = !state.infoVer;
    },
    UPDATE_REGISTER_OPEN(state) {
      state.registOpen = !state.registOpen;
    }
  },
  actions: {
    getPartnerInfo({ commit }, partnerInfo) {
      partnerInfo.userAlertFlag = String(partnerInfo.userAlertFlag);
      commit("SET_PARTNER_INFO", partnerInfo);
    },
    async getPetInfo({ commit }, petInfo) {
      for (var i=0; i<petInfo.length; i++) {
        if (petInfo[i].petBirth != null) petInfo[i].petBirth = petInfo[i].petBirth.substr(0, 7);
      }
      await commit("SET_PET_INFO", petInfo);
    },
    addPetInfo({ commit }, petOneInfo) {
      commit("ADD_PET_INFO", petOneInfo);
    },
    deletePetInfo({ commit }, petNo) {
      commit("DELETE_PET_INFO", petNo);
    },
    async updatePetInfo({ commit }, petOneInfo) {
      commit("UPDATE_PET_INFO", petOneInfo);
    },
    updateInfoVersion({ commit }) {
      commit("UPDATE_INFO_VERSION");
    },
    updateRegisterOpen({ commit }) {
      commit("UPDATE_REGISTER_OPEN");
    },
  },
};

export default userInfoPartnerStore;
