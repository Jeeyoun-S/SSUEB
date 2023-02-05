<template>
  <div class="main">
    <TheSidebar v-show="isShowSidebar"></TheSidebar>
    <router-view class="main-inner" />
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
    // console.log("# 라우터 이름: ", this.$router.name);
    // console.log("# 라우터 이름: ", document.location.pathname);
    // 회원가입 페이지에서는 Sidebar가 안 보이게 설정
    if (document.location.pathname == "/join") {
      this.isShowSidebar = false;
    }
    //SweetAlert2 테스트
    //this.$swal.fire('SweetAlert2 테스트입니다.')
  },
};
</script>

<style>
@import "@/css/font.css";
* {
  font-family: "SUIT-Regular";
}
#app {
  /* 가로 스크롤 설정 */
  overflow-x: auto; 
}
.main {
  min-height: 100vh;
  min-width: 1440px;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: center;
  
  padding: 48px;
}
.main .main-inner {
  max-width: 1070px;
}
</style>