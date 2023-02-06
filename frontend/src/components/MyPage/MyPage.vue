<template>
  <MyPagePartner v-if="true"></MyPagePartner>
  <MyPageConsultant v-else></MyPageConsultant>
</template>

<script>
import MyPagePartner from "@/components/MyPage/MyPagePartner/MyPagePartner.vue";
import MyPageConsultant from "@/components/MyPage/MyPageConsultant/MyPageConsultant.vue";

export default {
  name: "MyPage",
  components: {
    MyPagePartner,
    MyPageConsultant,
  },
};
</script>

<style></style>
