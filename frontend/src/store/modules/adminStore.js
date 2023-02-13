const adminStore = {
  state: {
    consultantDetail: {
      id: null
    }
  },
  getters: {
    getConsultantDetail(state) {
      return state.consultantDetail;
    }
  },
  mutations: {
    UPDATE_CONSULTANT_DETAIL(state, payload) {
      state.consultantDetail = payload;
    }
  },
  actions: {
    updateConsultantDetail({ commit }, consultantDetail) {
      commit("UPDATE_CONSULTANT_DETAIL", consultantDetail);
    }
  }
}

export default adminStore