// import { joinRoomSession } from "@/api/room"

const roomStore = {
    namespaced: true,
    state: {
        onAir: false,
        reservation: {}
    },
    getters: {
    },
    mutations: {
        // UPDATE_TOKEN(state, token) {
        //     console.log(state.roomToken)
        //     state.roomToken = token;
        //     console.log("changeToken",state.roomToken)
        //   },
        UPDATE_ONAIR(state, onAir) {
            state.onAir = onAir;
        },
        UPDATE_RESERVATION(state, payload) {
            state.reservation = payload;
        }
    },
    actions: {
        // joinRoomAction({commit},loginInfo){

        //     joinRoomSession(loginInfo,
        //         (data)=>{
        //             commit("UPDATE_TOKEN",data.data);
        //         }, (error)=>{
        //             console.log(error);

        //         }
        //         )

        // }

        async setMeetingReservation({ commit }, reservation) {
            console.log("되니?", reservation);
            await commit("UPDATE_RESERVATION", reservation);
            console.log("되니?");
            return await Promise.resolve(true);
            // location.href = `${process.env.VUE_APP_BASE_URL}/meeting-room`;
        }
    }
}

export default roomStore