import { apiInstance } from "./index.js";
// import { BASE_APP_URL } from "@/uilts.js";

const token = localStorage.getItem("token");
const api = apiInstance();
async function joinRoomSession(loginInfo, success, fail) {
    //나중에 수정. login 정보와 기타등등을 넘겨야함.
    // console.log(loginInfo);d
    await api
      .post(`api/room/session`, {customSessionId: '2'},{  
          headers: {
            Authorization: `Bearer ${token}`
          },
      }) 
      .then(success)
      .catch(fail);
  } 

  export {joinRoomSession}   