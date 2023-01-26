<template>
  <div class="sidebar">
    <v-sheet elevation="2" class="mx-auto sheet" height="100" width="230" rounded></v-sheet>
    <v-card class="mx-auto" max-width="230">
      <v-list active-color="primary" :items="items" mandatory link></v-list>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'TheSidebar',
  data() {
    return {
      items: [
        { title: '메인페이지', value: 1, props: {prependIcon: 'mdi-home', to: '/'} },
        { title: '마이페이지', value: 2, props: {prependIcon: 'mdi-account', to: '/mypage'} },
        { type: 'divider' },
        { type: 'subheader', title: '상담' },
        { title: '신규 상담 등록', value: 3, props: {prependIcon: 'mdi-calendar-plus', to: '/create-reservation'} }, // 반려인
        // { title: '신규 상담 제안', value: 4, props: {prependIcon: 'mdi-calendar-plus', to: '/create-matching'} }, // 전문가
        { title: '받은 상담 제안', value: 5, props: {prependIcon: 'mdi-clipboard-text-outline', to: '/receive-matching'} }, // 반려인
        // { title: '보낸 상담 제안', value: 6, props: {prependIcon: 'mdi-clipboard-text-outline', to: '/send-matching'} }, // 전문가
        { title: '확정 상담 목록', value: 7, props: {prependIcon: 'mdi-format-list-text', to: '/confirmed'} },
        { title: '화상 상담 입장', value: 8, props: {prependIcon: 'mdi-video-account', to: '/meeting-room'} },
        { title: '이전 상담 이력', value: 9, props: {prependIcon: 'mdi-format-list-bulleted', to: '/finished-reservation'} },
        { type: 'divider' },
        { type: 'subheader', title: '커뮤니티' },
        { title: '공지사항', value: 10, props: {prependIcon: 'mdi-bell', to: '/notice'} },
        { title: '자유 게시판', value: 11, props: {prependIcon: 'mdi-clipboard-edit-outline', to:'/free-board'} },
      ]
    }
  }
}
</script>

<style>
.sidebar {
  padding: 0px 50px;
}
.sidebar .sheet {
  margin-bottom: 10px;
}
</style>