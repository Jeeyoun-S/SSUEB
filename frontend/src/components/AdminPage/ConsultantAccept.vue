<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class=" page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-table-account</v-icon>
        <h2>전문가 회원가입 수락하기</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <ConsultantAcceptTable :consultantList="consultantList"></ConsultantAcceptTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import ConsultantAcceptTable from '@/components/AdminPage/ConsultantAcceptTable.vue';

export default {
  name: "ConsultantAccept",
  components: {
    NowLoading,
    ConsultantAcceptTable
  },
  data() {
    return {
      loaded: true,
      consultantList: [
        {
          id: "consultant@consultant",
          userName: "전문가 이름",
          userPhone: "010111111111",
          userJoindate: "2023-02-13 22:41",
          consultantPetType: "000000",
          consultantLicenseNumber: "JJJ",
          consultantLicenseCopyImage: "----.png",
        }
      ]
    }
  },
}
</script>

<style>

</style>