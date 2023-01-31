const userSocialStore = {
  state: {
    socialUserInfo: null,
  },
  getters: {
    getSocialUserInfo(state) {
      return state.socialUserInfo;
    },
  },
  mutations: {
    SET_SOCIAL_USER_INFO(state, socialUserInfo) {
      state.socialUserInfo = socialUserInfo;
      console.log("#21# JoinStore에 정보 저장: ", state.socialUserInfo);
      // console.log("#21# Getter로도 확인: ", this.getSocialUserInfo);
    },
  },
  actions: {
    async setSocialUserInfo({ commit }, info) {
      await commit("SET_SOCIAL_USER_INFO", info);
      //   console.log("#21# user 정보 저장된거 맞냥: ", this.state.socialUserInfo);
      location.href = `${process.env.VUE_APP_BASE_URL}/join`;
    },
  },
};

export default userSocialStore;
