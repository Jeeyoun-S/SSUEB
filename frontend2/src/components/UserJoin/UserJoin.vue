<template>
  <div class="join">
    <div class="join-inner">
      <div class="join-inner-item">    
          <LogoVer1></LogoVer1>
          <v-sheet elevation="2" class="mx-auto sheet" height="290" width="370" rounded>

          </v-sheet>
      </div>
      <v-sheet elevation="2" class="join-inner-item mx-auto sheet" rounded>
        <h2>회원가입</h2>
        <v-tabs v-model="joinTab" color="primary" align-tabs="center">
          <v-tab value="partner">반려인</v-tab>
          <v-tab value="consultant">전문가</v-tab>
        </v-tabs>
        <UserJoinPartner v-if="joinTab == 'partner'"></UserJoinPartner>
        <UserJoinConsult v-else></UserJoinConsult>
      </v-sheet>
    </div>
  </div>
</template>

<script>
import LogoVer1 from '@/views/LogoVer1.vue';
import UserJoinConsult from '@/components/UserJoin/UserJoinConsultant.vue';
import UserJoinPartner from '@/components/UserJoin/UserJoinPartner.vue';

export default {
  name: 'UserJoin',
  components: {
    LogoVer1,
    UserJoinConsult,
    UserJoinPartner
  },
  data() {
    return {
      joinTab: 'partner'
    }
  }
}
</script>

<style scoped>
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
  height: 900px;
  width: 400px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}
.join .join-inner .join-inner-item.sheet {
  padding: 20px;
}
</style>