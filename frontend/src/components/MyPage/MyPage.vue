<template>
  <MyPagePartner v-if="userAuth == 'ROLE_USER'"></MyPagePartner>
  <MyPageConsultant v-else></MyPageConsultant>
</template>

<script>
import MyPagePartner from "@/components/MyPage/MyPagePartner/MyPagePartner.vue";
import MyPageConsultant from "@/components/MyPage/MyPageConsultant/MyPageConsultant.vue";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "MyPage",
  components: {
    MyPagePartner,
    MyPageConsultant,
  },
  computed: {
    ...mapState(userStore, ["userAuth"]),
  },
};
</script>

<style>
@import "@/css/mypage.css";
</style>
