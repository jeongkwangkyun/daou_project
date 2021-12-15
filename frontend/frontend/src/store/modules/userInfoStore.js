const userInfoStore = {
  namespaced: true,
  state: {
    userInfo: [],
  },
  getters: {
    userInfos(state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_USER_INFO(state, payload) {
      state.userInfo = payload;
    },
  },
  actions: {
    regUserInfo({ commit }, data) {
      commit("SET_USER_INFO", data);
    },
  },
};

export default userInfoStore;
