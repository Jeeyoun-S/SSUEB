<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-table-account</v-icon>
        <h2>전문가 회원가입 수락하기</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <ConsultantAcceptTable
          :consultantList="this.consultantList"
        ></ConsultantAcceptTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from "@/views/NowLoading.vue";
import ConsultantAcceptTable from "@/components/AdminPage/ConsultantAcceptTable.vue";
import { mapActions, mapState } from "vuex";

const adminStore = "adminStore";

export default {
  name: "ConsultantAccept",
  components: {
    NowLoading,
    ConsultantAcceptTable,
  },
  data() {
    return {
      loaded: true,
    };
  },
  computed: {
    ...mapState(adminStore, ["consultantList"]),
  },
  created() {
    // [@Method] 미검증 자격증 전문가 list 조회
    this.excuteGetUnCertifiedConsultantList();
  },
  methods: {
    ...mapActions(adminStore, ["excuteGetUnCertifiedConsultantList"]),
  },
};
</script>

<style></style>
