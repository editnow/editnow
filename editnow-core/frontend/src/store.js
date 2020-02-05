import Vue from 'vue'
import Vuex from 'vuex'
import userApi from './components/api/user-api'
import Cookies from 'js-cookie'

const USER_UUID = 'userUUID';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        userName: null,
        userUUID: null
    },
    mutations: {
        loadedFromCookie(state, userUUIDFromCookie) {
            state.userUUID = userUUIDFromCookie;
        },
        setGeneratedUUID(state, userUUIDFromServer) {
            state.userUUID = userUUIDFromServer;
        }
    },
    actions: {
        loadUUIDFromCookie(context) {
            return new Promise((resolve, reject) => {
                const userUUID = Cookies.get(USER_UUID);
                if (userUUID) {
                    context.commit('loadedFromCookie', userUUID);
                    resolve()
                } else {
                    reject()
                }
            })
        },
        generateUUID({commit}) {
            userApi.generateUUID()
              .then(response => {
                  const generatedUUID = response.data;
                  commit('setGeneratedUUID', generatedUUID);
                  Cookies.set(USER_UUID, generatedUUID, { expires: 365 })
              })
        }
    },
    getters: {
        userUUID: state => state.userUUID
    }
})