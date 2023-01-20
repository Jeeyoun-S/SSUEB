import { createStore } from 'vuex'

import userStore from '@/store/modules/userStore.js'
import communityStore from '@/store/modules/communityStore.js'
import meetingRoomStore from '@/store/modules/meetingRoomStore.js'
import reservationStore from '@/store/modules/reservationStore.js'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    userStore: userStore,
    communityStore: communityStore,
    meetingRoomStore: meetingRoomStore,
    reservationStore: reservationStore
  }
})
