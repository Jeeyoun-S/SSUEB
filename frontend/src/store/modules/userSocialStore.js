const userSocialStore = {
  state: {
    socialUserInfo: {
      id: null,
      nickname: null,
      provider: null,
    },
  },
  getters: {
    getSocialUserInfo(state) {
      return state.socialUserInfo;
    },
  },
  mutations: {
    SET_SOCIAL_USER_INFO(state, socialUserInfo) {
      state.socialUserInfo = socialUserInfo;
      // console.log("#21# JoinStore에 정보 저장: ", state.socialUserInfo);
    },
  },
  actions: {
    // [@Method] 소셜 로그인 성공 시 user 정보(email, nickname) 저장
    async setSocialUserInfo({ commit }, info) {
      await commit("SET_SOCIAL_USER_INFO", info);
      //   console.log("#userSocialStore# 소셜 로그인 user 정보 저장: ", this.state.socialUserInfo);
      location.href = `${process.env.VUE_APP_BASE_URL}/join`;
    },
    // [@Method] 소셜 로그인 user 정보(email, nickname) 초기화
    initSocialUserInfo({ commit }) {
      commit("SET_SOCIAL_USER_INFO", "");
    },
  },
};

export default userSocialStore;
