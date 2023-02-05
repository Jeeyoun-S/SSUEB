const userInfoConsultantStore = {
  state: {
    consultantInfo: {
      id: "consultant@test.com",
      userName: null,
      userPhone: null,
      userAlertFlag: null,
      consultantIntro: null,
      consultantProfile: null,
      consultantPetType: [],
    },
    consultantRating: [
      { num: 0, cnt: 125 },
      { num: 1, cnt: 20 },
      { num: 2, cnt: 4 },
      { num: 3, cnt: 6 },
      { num: 4, cnt: 8 },
      { num: 5, cnt: 15 },
    ],
    averageRating: 3.5
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
    }
  },
  mutations: {

  },
  actions: {

  },
};

export default userInfoConsultantStore;
