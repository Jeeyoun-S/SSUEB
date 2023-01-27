<template>
  <div class="join">
    <div class="join-inner">
      <div class="join-inner-item left">
        <LogoVer1 class="border-sheet-one"></LogoVer1>
        <div class="border-sheet-one sheet">
        </div>
      </div>
      <div class="border-sheet-two join-inner-item right sheet">
        <h2>회원가입</h2>
        <v-tabs class="join-inner-item-tab" v-model="joinTab" color="primary" align-tabs="center" grow>
          <v-tab value="partner">반려인</v-tab>
          <v-tab value="consultant">전문가</v-tab>
        </v-tabs>
        <div class="join-inner-item-form">
          <UserJoinPartner v-if="joinTab == 'partner'"></UserJoinPartner>
          <UserJoinConsult v-else></UserJoinConsult>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LogoVer1 from "@/views/LogoVer1.vue";
import UserJoinConsult from "@/components/UserJoin/UserJoinConsultant.vue";
import UserJoinPartner from "@/components/UserJoin/UserJoinPartner.vue";

export default {
  name: "UserJoinCopy",
  components: {
    LogoVer1,
    UserJoinConsult,
    UserJoinPartner,
  },
  data() {
    return {
      joinTab: "partner",
    };
  },
};
</script>

<style scoped>
@import "@/css/sheet.css";
.join {
  display: flex;
  align-items: center;
  justify-content: center;

  margin: 30px auto;
}
.join .join-inner {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

  padding: 30px;
}
.join .join-inner .join-inner-item {
  height: 850px;
  width: 435px;

  display: flex;
  flex-direction: column;
  align-items: center;
}
.join .join-inner .join-inner-item.right {
  justify-content: flex-start;
}
.join .join-inner .join-inner-item.left .sheet {
  height: 400px;
  width: 400px;
}
.join .join-inner .join-inner-item .join-inner-item-tab {
  width: 350px;
}
.join .join-inner .join-inner-item.left {
  justify-content: space-between;
}
.join .join-inner .join-inner-item.sheet {
  padding: 30px;
}
</style>
