import { createStore } from 'vuex'

declare let SessionStorage: any;
const USER = "USER";

const store = createStore({
  state: {
    user: SessionStorage.get(USER) || {}
  },
  //同步
  mutations: {
    setUser (state, user){
      state.user = user;
      SessionStorage.set(USER,user);
    }
  },
  //异步
  actions: {
  },
  modules: {
  }
});

export default store;
