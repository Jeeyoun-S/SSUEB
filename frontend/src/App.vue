<template>
  <div class="main">
    <!-- <TheSidebar v-show="isShowSidebar"></TheSidebar> -->
    <TheSidebar v-show="$router.name != 'userJoin'"></TheSidebar>
    <router-view />
  </div>
</template>

<script>
import TheSidebar from "@/views/TheSidebar.vue";

export default {
  name: "app",
  components: {
    TheSidebar,
  },
  data() {
    return {
      isShowSidebar: true, // 사이드바가 보이면 true, 안 보이면 false
    };
  },
  created() {
    console.log("# 라우터 이름: ", this.$router.name);
    // 회원가입 페이지에서는 Sidebar가 안 보이게 설정
    if (document.location.pathname === "/join") {
      this.isShowSidebar = false;
    }
    //SweetAlert2 테스트
    //this.$swal.fire('SweetAlert2 테스트입니다.')
  },
};
</script>

<style>
@import "@/css/font.css";

.main {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: left;

  /* 가로 스크롤 설정 */
  overflow: auto;
  white-space: nowrap;

  font-family: "SUIT-Regular";
  padding-top: 50px;
}
</style>
