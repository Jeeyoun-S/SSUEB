const userInfoConsultantStore = {
  state: {
    consultantInfo: {
      id: null,
      userName: null,
      userPhone: null,
      userAlertFlag: 0,
      consultantIntro: null,
      consultantProfile: null,
      consultantPetType: [],
    },
    consultantRating: [
      { num: 0, cnt: 0 },
      { num: 1, cnt: 0 },
      { num: 2, cnt: 0 },
      { num: 3, cnt: 0 },
      { num: 4, cnt: 0 },
      { num: 5, cnt: 0 },
    ],
    averageRating: 0,
    graphLabels: ["", "", "", "", "", ""],
    graphOne: [0, 0, 0, 0],
    graphTwo: [0, 0, 0, 0, 0, 0],
    graphThree: [0, 0, 0, 0, 0, 0]
  },
  getters: {
    getConsultantInfo(state) {
      return state.consultantInfo;
    },
    getConsultantRating(state) {
      return state.consultantRating;
    },
    getAverageRating(state) {
      return state.averageRating;
    },
    getGraphOne(state) {
      return state.graphOne;
    },
    getGraphTwo(state) {
      return state.graphTwo;
    },
    getGraphThree(state) {
      return state.graphThree;
    },
    getGraphLabels(state) {
      return state.graphLabels;
    }
  },
  mutations: {
    UPDATE_CONSULTANT_INFO(state, payload) {
      state.consultantInfo.id = payload.id;
      state.consultantInfo.userName = payload.userName;
      state.consultantInfo.userPhone = payload.userPhone;
      state.consultantInfo.userAlertFlag = String(payload.userAlertFlag);
      state.consultantInfo.consultantIntro = payload.consultantIntro;
      state.consultantInfo.consultantProfile = payload.consultantProfile;
      const petType = Array.from(payload.consultantPetType);
      for (var i=0; i<6; i++) {
        if (petType[i] == 1) {
          state.consultantInfo.consultantPetType.push(i);
        }
      }
    },
    UPDATE_GRAPH_INFO(state, payload) {
      state.graphOne = payload.graphOne;

      for (var i=0; i<6; i++) {
        for (var j=0; j<payload.graphTwo.length; j++) {
          const valueTwo = payload.graphTwo[j][state.graphLabels[i]];
          if (typeof valueTwo != "undefined") state.graphTwo[i] = valueTwo;
        }
        for (var k=0; k<payload.graphThree.length; k++) {
          const valueThree = payload.graphThree[k][state.graphLabels[i]];
          if (typeof valueThree != "undefined") state.graphThree[i] = valueThree;
        }
      }
    },
    UPDATE_STAR_INFO(state, payload) {
      // 평균값 넣어주기
      state.averageRating = payload.averageRating;
      // 각 별점 개수 넣기
      for (var i=0; i<payload.consultantRating.length; i++) {
        const value = payload.consultantRating[i];
        state.consultantRating[value.starNum].cnt = value.starCnt;
      }
    },
    UPDATE_GRAPH_LABEL(state) {
      for (var i=1; i<7; i++) {
        var date = new Date(new Date().setMonth(new Date().getMonth() - i));
        state.graphLabels[6-i] = date.getFullYear()+"-"+(date.getMonth()+1);
      }
    }
  },
  actions: {
    updateConsultantInfo({ commit }, data) {
      commit('UPDATE_CONSULTANT_INFO', data);
    },
    updateStarInfo({ commit }, data) {
      commit('UPDATE_STAR_INFO', data);
    },
    updateGraphInfo({ commit }, data) {
      commit('UPDATE_GRAPH_INFO', data);
    },
    updateGraphLabel({ commit }) {
      commit('UPDATE_GRAPH_LABEL');
    }
  },
};

export default userInfoConsultantStore;
