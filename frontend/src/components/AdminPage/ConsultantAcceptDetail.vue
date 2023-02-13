<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-btn class="mr-1" variant="text" icon="mdi-arrow-left" @click="back()"></v-btn>
        <v-chip class="mr-3" color="primary">
          {{ String(getConsultantDetail.index).padStart(4, '0') }}
        </v-chip>
        <h2>{{ getConsultantDetail.userName }}</h2>
      </div>
    </div>
    <div class="pa-8 d-flex flex-column page-inner-items border-sheet-four">
      <v-table>
        <tbody>
          <tr>
            <td class="bold-font" width="150">이름</td>
            <td width="920">{{ getConsultantDetail.userName }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">가입 날짜</td>
            <td width="920">{{ getConsultantDetail.userJoindate }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">이메일</td>
            <td width="920">{{ getConsultantDetail.id }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">휴대폰 번호</td>
            <td width="920">{{ getConsultantDetail.userPhone }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">상담 가능한 동물</td>
            <td width="920">{{ getConsultantDetail.consultantPetType }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">자격 번호</td>
            <td width="920">{{ getConsultantDetail.consultantLicenseNumber }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">자격증 사본</td>
            <td>
              <v-btn rounded="pill" variant="tonal" prepend-icon="mdi-paperclip">
                {{ getConsultantDetail.consultantLicenseCopyImage }}
              </v-btn>
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">가입 수락</td>
            <td>
              <v-btn rounded="pill" variant="tonal" @click="accept">
                수락
              </v-btn>
            </td>
          </tr>
        </tbody>
      </v-table>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import { acceptConsultant } from "@/api/admin.js";

export default {
  name: "ConsultantAcceptDetail",
  components: {
    NowLoading,
  },
  computed: {
    getConsultantDetail() {
      return this.$store.getters.getConsultantDetail;
    }
  },
  data() {
    return {
      loaded: true,
    }
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    accept() {
      acceptConsultant(this.getConsultantDetail.id);
    }
  }
}
</script>

<style>

</style>