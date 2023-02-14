import { apiInstance } from "./index.js";

const api = apiInstance();
async function getFreeBoard() {
  var result = [];
  await api
    .get(`${process.env.VUE_APP_API_BASE_URL}/board/community`)
    .then((res) => {
      result = res.data;
    })
    .catch();
  return await Promise.resolve(result);
}

async function getHeartWhether(no, id) {
  var result = false;
  await api
    .post(`${process.env.VUE_APP_API_BASE_URL}/board/heart/whether`, null, {
      params: {
        boardNo: no,
        userId: id,
      },
    })
    .then((res) => {
      result = res.data;
    })
    .catch();
  return await Promise.resolve(result);
}

async function pushHeart(boardNo, id, isHeart) {
  var result = [];
  await api
    .post(`${process.env.VUE_APP_API_BASE_URL}/board/heart`, null, {
      params: {
        boardNo: boardNo,
        userId: id,
        flag: isHeart,
      },
    })
    .then((res) => {
      result = res.data;
    })
    .catch();
  return await Promise.resolve(result);
}

async function deleteBoard(boardNo) {
  await api
    .delete(`${process.env.VUE_APP_API_BASE_URL}/board/community/${boardNo}`)
    .then(() => {
      console.log("삭제");
    });
}

async function putBoard(modifyBoard, boardFile) {
  var result = null;

  const frm = new FormData();
  frm.append(
    "board",
    new Blob([JSON.stringify(modifyBoard)], { type: "application/json" })
  );
  if (boardFile) {
    frm.append("file", boardFile[0]);
  }

  await api
    .put(`${process.env.VUE_APP_API_BASE_URL}/board/community`, frm, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => {
      result = res.data;
      console.log("업로드");
    })
    .catch();
  return await Promise.resolve(result);
}

export { getFreeBoard, getHeartWhether, pushHeart, deleteBoard, putBoard };
