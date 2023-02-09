import { createRouter, createWebHistory } from "vue-router";

import user from "@/router/user";
import community from "@/router/community";
import meetingRoom from "@/router/meetingRoom";
import reservation from "@/router/reservation";
import error from "@/router/error";

const routes = [
  ...user,
  ...community,
  ...meetingRoom,
  ...reservation,
  ...error,
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// # router 전역 가드
const Swal = require("sweetalert2");

router.beforeEach((to, from, next) => {
  // console.log("#21# router 가드 동작 to: ", to);
  // console.log("#21# router 가드 동작 from: ", from);

  // 메인페이지, 회원가입 페이지 제외
  if (to.path == "/" || to.path == "/join") {
    next();
  }
  // - 로그인한 상태가 아니라면, 메인 페이지(로그인)로 이동
  else if (localStorage.getItem("token") == null) {
    Swal.fire("SSEUB", `로그인이 필요합니다.`, "error");
    next("/");
  }
  // - 로그인 상태라면, 이동하려는 페이지로 이동
  else {
    next();
  }
});

export default router;
