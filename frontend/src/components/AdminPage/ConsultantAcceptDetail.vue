<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-btn
          class="mr-1"
          variant="text"
          icon="mdi-arrow-left"
          @click="back()"
        ></v-btn>
        <v-chip class="mr-3" color="primary">
          {{ String(this.consultantDetail.index).padStart(4, "0") }}
        </v-chip>
        <h2>{{ this.consultantDetail.user_name }}</h2>
      </div>
    </div>
    <div class="pa-8 d-flex flex-column page-inner-items border-sheet-four">
      <v-table>
        <tbody>
          <tr>
            <td class="bold-font" width="150">이름</td>
            <td width="900">{{ this.consultantDetail.user_name }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">가입 날짜</td>
            <td width="900">
              {{ this.consultantDetail.user_joindate.substring(0, 10) }}
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">이메일</td>
            <td width="900">{{ this.consultantDetail.id }}</td>
          </tr>
          <tr>
            <td class="bold-font" width="150">휴대폰 번호</td>
            <td width="900">
              {{ this.consultantDetail.user_phone.substring(0, 3) }}-{{
                this.consultantDetail.user_phone.substring(3, 7)
              }}-{{ this.consultantDetail.user_phone.substring(7, 11) }}
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">상담 가능한 동물</td>
            <td>
              <v-chip-group
                selected-class="text-primary"
                v-model="this.consultantDetail.consultant_pet_type"
                column
                disabled
                multiple
              >
                <v-chip
                  v-for="(value, index) in petType"
                  :key="index"
                  filter
                  variant="outlined"
                >
                  {{ value }}
                </v-chip>
              </v-chip-group>
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">자격 번호</td>
            <td width="900">
              {{ this.consultantDetail.consultant_license_number }}
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">자격증 사본</td>
            <td>
              <v-btn :href="getLicenseUrl(consultantDetail.consultant_license_copy_image)" 
                rounded="pill" variant="tonal" prepend-icon="mdi-paperclip"
                download
              >
                {{ this.consultantDetail.consultant_license_copy_image }}
              </v-btn>
            </td>
          </tr>
          <tr>
            <td class="bold-font" width="150">가입 수락</td>
            <td>
              <v-btn rounded="pill" variant="tonal" @click="accept"
                color="primary"
              >
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
import NowLoading from "@/views/NowLoading.vue";
import { acceptConsultant } from "@/api/admin.js";
import { mapState } from "vuex";

const adminStore = "adminStore";

export default {
  name: "ConsultantAcceptDetail",
  components: {
    NowLoading,
  },
  computed: {
    ...mapState(adminStore, ["consultantDetail"]),
  },
  data() {
    return {
      loaded: true,
      petType: ["개", "고양이", "토끼", "패럿", "기니피그", "햄스터"],
    };
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    // [@Method] 전문가 가입 수락 + 알림 발송 (SMS, EMAIL)
    accept() {
      acceptConsultant(this.getConsultantDetail.id);
    },
    getLicenseUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_LICENSE}${img}`;
    }
  },
};
</script>

<style></style>
