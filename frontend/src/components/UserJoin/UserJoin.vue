<template>
  <div class="join">
    <div class="join-title border-sheet-one">
      <LogoVer2></LogoVer2>
      <h2>회원가입</h2>
    </div>
    <div class="join-form border-sheet-two">
      <v-tabs class="join-form-tab" v-model="joinTab" color="primary" align-tabs="center" grow>
        <v-tab value="partner">반려인</v-tab>
        <v-tab value="consultant">전문가</v-tab>
      </v-tabs>
      <div class="join-inner-item-form">
        <UserJoinPartner v-if="joinTab == 'partner'"></UserJoinPartner>
        <UserJoinConsult v-else></UserJoinConsult>
      </div>
    </div>
  </div>
</template>

<script>
import LogoVer2 from '@/views/LogoVer2.vue'
import UserJoinConsult from "@/components/UserJoin/UserJoinConsultant.vue";
import UserJoinPartner from "@/components/UserJoin/UserJoinPartner.vue";

export default {
  name: "UserJoin",
  components: {
    UserJoinConsult,
    UserJoinPartner,
    LogoVer2
  },
  data() {
    return {
      joinTab: "partner",
    };
  },
}
</script>

<style>
.join {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin: 0px 48px;
}
.join .join-form {
  height: 850px;
  width: 435px;
  padding: 40px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}
.join .join-form .join-form-tab {
  width: 350px;
}
.join .join-title {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  margin-bottom: 20px;
  
  height: 200px;
  width: 435px;
}
</style>