<template>
  <div class="sidebar">
    <div class="border-sheet-one sheet">
      <LogoVer2></LogoVer2>
    </div>
    <v-card class="mx-auto border-sheet-one" max-width="230">
      <v-list active-color="primary" :items="items" mandatory link></v-list>
    </v-card>
  </div>
</template>

<script>
import LogoVer2 from "@/views/LogoVer2.vue";

export default {
  name: "TheSidebar",
  components: {
    LogoVer2,
  },
  data() {
    return {
      items: [
        {
          title: "메인페이지",
          value: 1,
          props: { prependIcon: "mdi-home", to: "/" },
        },
        {
          title: "마이페이지",
          value: 2,
          props: { prependIcon: "mdi-account", to: "/mypage" },
        },
        { type: "divider" },
        { type: "subheader", title: "상담" },
        {
          title: "신규 상담 등록",
          value: 3,
          props: {
            prependIcon: "mdi-calendar-plus",
            to: "/create-reservation",
          },
        }, // 반려인
        // { title: '신규 상담 제안', value: 4, props: {prependIcon: 'mdi-calendar-plus', to: '/create-matching', active=this.role} }, // 전문가
        {
          title: "받은 상담 제안",
          value: 5,
          props: {
            prependIcon: "mdi-clipboard-text-outline",
            to: "/receive-matching",
          },
        }, // 반려인
        // { title: '보낸 상담 제안', value: 6, props: {prependIcon: 'mdi-clipboard-text-outline', to: '/send-matching'} }, // 전문가
        {
          title: "확정 상담 목록",
          value: 7,
          props: { prependIcon: "mdi-format-list-text", to: "/confirmed" },
        },
        {
          title: "화상 상담 입장",
          value: 8,
          props: { prependIcon: "mdi-video-account", to: "/meeting-room" },
        },
        {
          title: "이전 상담 이력",
          value: 9,
          props: {
            prependIcon: "mdi-format-list-bulleted",
            to: "/finished-reservation",
          },
        },
        { type: "divider" },
        { type: "subheader", title: "커뮤니티" },
        {
          title: "공지사항",
          value: 10,
          props: { prependIcon: "mdi-bell", to: "/notice" },
        },
        {
          title: "자유 게시판",
          value: 11,
          props: {
            prependIcon: "mdi-clipboard-edit-outline",
            to: "/free-board",
          },
        },
      ],
    };
  },
};
</script>

<style>
@import "@/css/sheet.css";
.sidebar {
  padding-right: 48px;
}
.sidebar .sheet {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  margin-bottom: 20px;

  height: 100px;
  width: 230px;
}
.sidebar .v-list-item-title {
  font-family: 'NanumSquareNeo-Variable';
}
</style>
