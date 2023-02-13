// import { joinRoomSession } from "@/api/room"

const roomStore = {
    namespaced: true,
    state: {
        onAir: false,
    },
    getters: {
    },
    mutations: {
        // UPDATE_TOKEN(state, token) {
        //     console.log(state.roomToken)
        //     state.roomToken = token;
        //     console.log("changeToken",state.roomToken)
        //   },
        UPDATE_ONAIR(state,onAir){
            state.onAir = onAir;
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
    }
}

export default roomStore