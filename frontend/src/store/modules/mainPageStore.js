import { getTopFiveBoard, getReservationCount } from "@/api/mainPage";
import VueJwtDecode from "vue-jwt-decode";

const mainPageStore = {
  namespaced: true,
  state: {
    board: [],
    reservationCount: null,
  },
  getters: {},
  mutations: {
    SET_BOARD: (state, board) => {
      state.board = board;
      console.log("#SET_BOARD# 게시글 확인: ", state.board);
    },
    SET_RESERVATION_COUNT: (state, reservationCount) => {
      state.reservationCount = reservationCount;
      console.log(
        "#SET_RESERVATION_COUNT# 금일 예약 상담 건 수 확인: ",
        state.reservationCount
      );
    },
  },
  actions: {
    // [@Method] Top5 게시글 조회
    async excuteGetBoard({ commit }) {
      await getTopFiveBoard(
        ({ data }) => {
          console.log("#mainPageStore# 게시글 가져오기 성공");
          commit("SET_BOARD", data);
        },
        (error) => {
          console.log("#mainPageStore# 게시글 가져오기 실패");
          console.log(error);
        }
      );
    },
    // [@Method] 로그인한 사용자의 금일 예약 건 수 조회
    async excuteGetReservationCount({ commit }) {
      const id = VueJwtDecode.decode(localStorage.getItem("token")).sub;
      console.log("#21# 예약 건 수 조회 호출 - id: ", id);

      await getReservationCount(
        id,
        ({ data }) => {
          if (data.response == "success") {
            console.log(`#mainPageStore# ${data.message}`);
            commit("SET_RESERVATION_COUNT", data.reservationCount);
          } else {
            console.log(`#mainPageStore# ${data.message}`);
          }
        },
        (error) => {
          commit("SET_RESERVATION_COUNT", 0);
          console.log(`#mainPageStore# 예약 건 수 조회 실패: ${error}`);
        }
      );
    },
    // [@Method] store 내 저장된 정보 초기화
    initMainPageStore({ commit }) {
      commit("SET_BOARD", []);
      commit("SET_RESERVATION_COUNT", null);
    },
  },
};

export default mainPageStore;
