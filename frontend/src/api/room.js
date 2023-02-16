import { apiInstance } from "./index.js";
// import { BASE_APP_URL } from "@/uilts.js";
// 배포시 ${}를사용해야함.
const token = localStorage.getItem("token");
const api = apiInstance();
async function joinRoomSession(roomId, success, fail) {
    //나중에 수정. login 정보와 기타등등을 넘겨야함.
    // console.log(loginInfo);d
    await api
      .post(`${process.env.VUE_APP_API_BASE_URL}/room/session`, {customSessionId: roomId},{  
      // .post(`api/room/session`, {customSessionId: roomId},{  
          headers: {
            Authorization: `Bearer ${token}`
          },
      }) 
      .then(success)
      .catch(fail);
  } 

async function finishRoom(roomId, success,fail){
  await api
  .post(`${process.env.VUE_APP_API_BASE_URL}/reservation/finish/${roomId}`,{  
  // .put(`api/reservation/finish/${roomId}`,{  
      headers: {
        Authorization: `Bearer ${token}`
      },
  }) 
  .then(success)
  .catch(fail);

}

  export {joinRoomSession, finishRoom}   