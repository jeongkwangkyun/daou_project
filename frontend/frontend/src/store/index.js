import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import paymentStore from "@/store/modules/paymentStore.js";
import userInfoStore from "@/store/modules/userInfoStore.js";
const store = new Vuex.Store({
  modules: {
    paymentStore,
    userInfoStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
