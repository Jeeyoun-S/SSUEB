<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class=" page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-bell-ring</v-icon>
        <h2>회원에게 알림 보내기</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <UserAlaramTable :userList="userList"></UserAlaramTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import UserAlaramTable from '@/components/AdminPage/UserAlarmTable.vue';
import { getAllUserList } from "@/api/admin.js";

export default {
  name: "UserAlarm",
  components: {
    NowLoading,
    UserAlaramTable
  },
  data() {
    return {
      loaded: false,
      userList: []
    }
  },
  async created() {
    this.loaded = false;
    await getAllUserList()
    .then((res) => this.userList = res); 
    this.loaded = true;
  }
}
</script>

<style>

</style>