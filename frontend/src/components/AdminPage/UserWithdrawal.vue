<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class=" page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-account-remove</v-icon>
        <h2>회원 탈퇴 처리하기</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <UserWithdrawalTable :userList="userList"
          @deleteRemoveUser="deleteRemoveUser"
        ></UserWithdrawalTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import UserWithdrawalTable from '@/components/AdminPage/UserWithdrawalTable.vue'
import { getAllUserList } from "@/api/admin.js";

export default {
  name: "UserWithdrawal",
  components: {
    NowLoading,
    UserWithdrawalTable
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
  },
  methods: {
    deleteRemoveUser(index) {
      this.userList.splice(index, 1);
    }
  }
}
</script>

<style>

</style>