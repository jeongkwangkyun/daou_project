const paymentStore = {
  namespaced: true,
  state: {
    payment_list: [],
  },
  getters: {
    payments(state) {
      return state.payment_list;
    },
  },
  mutations: {
    SET_PAYMENT_LIST(state, payload) {
      state.payment_list = payload;
    },
  },
  actions: {
    regPayment({ commit }, data) {
      commit("SET_PAYMENT_LIST", data);
    },
  },
};

export default paymentStore;
