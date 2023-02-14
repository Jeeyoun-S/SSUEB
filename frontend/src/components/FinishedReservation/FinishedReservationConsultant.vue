<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-calendar-search</v-icon>
        <h2>종료된 상담 목록 조회하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <MoveCreateMatching v-if="reservations == null || reservations.length < 1" message="아직 완료한 상담이 없습니다."></MoveCreateMatching>
        <v-sheet v-else  width="100%">
          <v-sheet class="ma-7 mt-5" width="95%">
            <v-row>
              <v-col class="pa-0 pl-2" cols="1"><h3>번호</h3></v-col>
              <v-col class="pa-0" cols="3"><h3>상담일시</h3></v-col>
              <v-col class="pa-0" cols="1"><h3>대분류</h3></v-col>
              <v-col class="pa-0" cols="3"><h3>반려동물 이름</h3></v-col>
              <v-col class="pa-0"><h3>별점</h3></v-col>
            </v-row>
          </v-sheet>
          <v-expansion-panels v-model="panel" multiple>
            <v-expansion-panel class="reservation" v-for="(reservation, idx) in reservations" :key="{idx}">
        
              <!-- 타이틀 -->
              <v-expansion-panel-title>
                <v-col class="pa-0 pl-2 bold-font" cols="1">{{ idx + 1 }}</v-col> <!--번호, 연결하지 않음-->
                <v-col class="pa-0" cols="3">{{ reservation.reservationDate }}</v-col> <!--상담일시-->
                <v-col class="pa-0" cols="1">{{ reservation.petType }}</v-col> <!--대분류-->
                <v-col class="pa-0" cols="3">{{ reservation.petName }}</v-col> <!--애기이름-->
                <v-col class="pa-0"> <!--별점-->
                  <v-rating v-model="reservation.reviewGrade"  color="orange darken-2" density="compact" half-increments readonly></v-rating>
                </v-col>
              </v-expansion-panel-title>
        
              <!-- 내용 -->
              <v-expansion-panel-text class="pa-3">
                <v-sheet class="d-flex flex-row justify-space-between align-center">
                  <!-- 반려동물 정보 -->
                  <v-hover
                    v-slot="{ isHovering, props }"
                    open-delay="200"
                  >
                    <v-card
                      :elevation="isHovering ? 3 : 0"
                      :class="{ 'on-hover': isHovering }"
                      class="pa-2 mb-5 d-flex flex-column justify-center align-center" variant="outlined"
                      height="270" width="178" rounded="0"
                      v-bind="props"
                    >
                      <v-avatar color="#06BEE1" size="100">
                        <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
                        <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
                      </v-avatar>
                      <!-- <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" /> -->
                      <v-card-title class="pa-1">{{ reservation.petName }}</v-card-title> <!--애기 이름--><!--(출생연월)-->
                      <v-card-subtitle>{{ reservation.petBirth }}</v-card-subtitle><!--생일-->
                      <v-card-subtitle>{{ reservation.petType }}</v-card-subtitle><!--품종 대분류-->
                      <v-card-subtitle>{{ reservation.petVariety }}</v-card-subtitle><!--품종 소분류-->
                    </v-card>
                  </v-hover>
                  <!-- 상담 신청 내용 -->
                  <v-hover
                    v-slot="{ isHovering, props }"
                    open-delay="200"
                  >
                    <v-card
                      :elevation="isHovering ? 3 : 0"
                      :class="{ 'on-hover': isHovering }"
                      class="pa-3 mb-5 d-flex flex-column justify-center" variant="outlined"
                      height="270" width="385" rounded="0"
                      v-bind="props"
                    >
                      <v-card-title><h4>상담 신청 내용</h4></v-card-title>
                      <v-card-text>
                        {{ reservation.reservationConsultContent }}
                      </v-card-text>
                    </v-card>
                  </v-hover>
                  <!-- 상담 결과 -->
                  <v-hover
                    v-slot="{ isHovering, props }"
                    open-delay="200"
                  >
                    <v-card
                      :elevation="isHovering ? 3 : 0"
                      :class="{ 'on-hover': isHovering }"
                      class="pa-3 mb-5 d-flex flex-column justify-center" variant="outlined"
                      height="270" width="390" rounded="0"
                      v-bind="props"
                    >
                      <v-card-title><h4>상담 결과</h4></v-card-title>
                      <v-card-text>
                        {{ reservation.reservationDignosisRecord }}
                      </v-card-text>
                      <v-card-actions class="justify-end">
                          <FinishedResultModify
                            :record="reservation.reservationDignosisRecord" :no="reservation.rno"
                            :idx="idx" @updateRecord="updateRecord"
                          ></FinishedResultModify>
                        </v-card-actions>
                    </v-card>
                  </v-hover>
                </v-sheet>
              </v-expansion-panel-text>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-sheet>
      </div>
    </div>
  </div>


</template>

<script>
import FinishedResultModify from "@/components/FinishedReservation/FinishedResultModify.vue";
import NowLoading from "@/views/NowLoading.vue";
import MoveCreateMatching from "@/components/CreateMatching/MoveCreateMatching.vue";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";

export default {
  name: "FinishedReservationConsultant",

  data() {
    return {
      loaded: true,
      panel: [],
      reservations: [],
    }
  },
  components: {
    FinishedResultModify,
    NowLoading,
    MoveCreateMatching
  },
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  methods:{
    async updateRecord(dignosis) {
      this.loaded = false;
      this.reservations[dignosis.idx].reservationDignosisRecord = await dignosis.reservationDignosisRecord;
      this.loaded = true;
    },
    async getReservation() {

      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/consultant/past/${this.userId}`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            console.log(data[i])
            let reservation = {};
            reservation["rno"] = data[i].rno;
            reservation["consultantId"] = data[i].consultantId;
            reservation["reservationDate"] = data[i].reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationConsultContent;

            // reservation["consultantId"] = data[i].reservationPetFinish.consultant_id;

            reservation["reservationDignosisRecord"] = data[i].reservationDignosisRecord;
            reservation["reviewGrade"] = data[i].reviewGrade;
            reservation["reviewComment"] = data[i].reviewComment;

            reservation["pno"] = data[i].pno;
            reservation["petName"] = data[i].petName;
            reservation["petImage"] = data[i].petImage;
            reservation["petType"] = data[i].petType;
            reservation["petVariety"] = data[i].petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].petInfo;
            this.reservations.push(reservation);
          }
          console.log(this.reservations)
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created(){
    this.getReservation();
  }
}
</script>

<style></style>
