import MainPage from "@/components/MainPage/MainPage.vue";
import MyPage from "@/components/MyPage/MyPage.vue";
import userJoin from "@/components/UserJoin/UserJoin.vue";

const user = [
  {
    path: "/",
    name: "mainPage",
    component: MainPage,
  },
  {
    path: "/mypage",
    name: "myPage",
    component: MyPage,
  },
  {
    path: "/join",
    name: "userJoin",
    component: userJoin,
  },
];

export default user;
