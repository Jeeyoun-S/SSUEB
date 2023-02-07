import { getTopFiveBoard } from "@/api/mainPage";

const mainPageStore = {
  namespaced: true,
  state: {
    board: [],
  },
  getters: {},
  mutations: {
    SET_BOARD: (state, board) => {
      state.board = board;
      console.log("#SET_BOARD# 게시글 확인: ", state.board);
    },
  },
  actions: {
    async excuteGetBoard({ commit }) {
      console.log("#21# 게시글 가져오기 동작");

      await getTopFiveBoard(
        ({ data }) => {
          console.log("#21# 게시글 가져오기 성공");
          commit("SET_BOARD", data);
        },
        (error) => {
          console.log("#21# 게시글 가져오기 실패");
          console.log(error);
        }
      );
    },
  },
};

export default mainPageStore;
