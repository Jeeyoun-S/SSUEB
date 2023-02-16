<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-calendar-search</v-icon>
        <h2>종료된 상담 목록 조회하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <MoveCreateReservation v-if="reservations == null || reservations.length < 1" message="아직 완료한 상담이 없습니다."></MoveCreateReservation>
        <!-- <CreateCard v-for="(reservation, idx) in reservations" :reservation="reservation" v-bind:key="idx" /> -->
        <v-sheet v-else  width="100%">
          <v-sheet class="ma-7 mt-5" width="95%">
            <v-row>
              <v-col class="pa-0 pl-2" cols="1"><h3>번호</h3></v-col>
              <v-col class="pa-0" cols="3"><h3>상담일시</h3></v-col>
              <v-col class="pa-0" cols="5"><h3>전문가 / 반려동물</h3></v-col>
              <v-col class="pa-0" cols="3"><h3>별점</h3></v-col>
            </v-row>
          </v-sheet>
          <v-expansion-panels v-model="panel">
            <v-expansion-panel v-for="(reservation, idx) in reservations" :key="{idx}">
        
              <!-- 타이틀 -->
              <v-expansion-panel-title>
                <v-col class="pa-0 pl-2 bold-font" cols="1">{{ String(idx + 1).padStart(4, '0') }}</v-col> <!--번호, 연결하지 않음-->
                <v-col class="pa-0" cols="3">{{ reservation.reservationDate.substr(0,16) }}</v-col> <!--상담일시-->
                <v-col class="pa-0" cols="5">{{ reservation.consultantName }} / {{ reservation.petName }}</v-col> <!--전문가/애기이름-->
                <v-col class="pa-0"> <!--별점-->
                  <v-rating v-model="reservation.reviewGrade"  color="orange darken-2" density="compact" half-increments readonly></v-rating>
                </v-col>
              </v-expansion-panel-title>
        
              <!-- 내용 -->
              <v-expansion-panel-text class="pa-3">
                <v-sheet class="mb-5 d-flex flex-row justify-space-between align-start" height="490" width="965">  
                  <v-hover
                    v-slot="{ isHovering, props }"
                    open-delay="200"
                  >
                    <v-card
                      :elevation="isHovering ? 3 : 0"
                      :class="{ 'on-hover': isHovering }"
                      class=" pa-3 d-flex flex-column justify-start align-center" variant="outlined"
                      height="490" width="580" rounded="0"
                      v-bind="props"
                    >
                      <!-- 좌측 반려동물 정보 -->
                      <v-card class="pl-3" height="140" width="560" elevation="0">
                        <v-card-item>
                          <template v-slot:prepend>
                            <v-avatar class="align-self-center" color="#06BEE1" size="100">
                              <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
                              <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
                            </v-avatar>
                          </template>
                          <!-- <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" /> -->
                          <v-card-title>{{ reservation.petName }}</v-card-title> <!--애기 이름--><!--(출생연월)-->
                          <v-card-subtitle>{{ reservation.petBirth }}</v-card-subtitle><!--품종 대분류-->
                          <v-card-subtitle>{{ reservation.petType }}</v-card-subtitle><!--품종 대분류-->
                          <v-card-subtitle>{{ reservation.petVariety }}</v-card-subtitle><!--품종 소분류-->
                        </v-card-item>
                      </v-card>
                      <!-- 우측 상담 신청 내용 -->
                      <v-card class="pa-3 d-flex flex-column" height="440" width="580" elevation="0">
                        <v-card-title>상담 신청 내용</v-card-title>
                        <v-card-text>
                          {{ reservation.reservationConsultContent }}
                        </v-card-text>
                      </v-card>
                    </v-card>
                  </v-hover>
        
                  <v-hover
                    v-slot="{ isHovering, props }"
                    open-delay="200"
                  >
                    <v-card
                      :elevation="isHovering ? 3 : 0"
                      :class="{ 'on-hover': isHovering }"
                      class="mb-5 pa-2 d-flex flex-column justify-start" variant="outlined"
                      height="490" width="360" rounded="0"
                      v-bind="props"
                    >
                      <v-sheet class="d-flex align-center">
                        <v-card-title>리뷰</v-card-title>
                        <v-rating class="mr-2"
                          v-model="reservation.reviewGrade" color="yellow-darken-3"
                          density="compact" readonly
                        ></v-rating>
                      </v-sheet>
                      <v-card-text class="pt-0 pb-0 pr-4 pl-4">
                        {{ reservation.reviewComment }}
                      </v-card-text>
                      <v-card-actions class="justify-end">
                        <FinishedReviewModify :reviewComment="reservation.reviewComment"
                          :reviewGrade="reservation.reviewGrade" :rno="reservation.rno" :idx="idx"
                          @updateReview="updateReview"
                        ></FinishedReviewModify>
                      </v-card-actions>
                    </v-card>
                  </v-hover>
                </v-sheet>
                <v-divider></v-divider>
                <!-- 전문가 상담 내용 -->
                <v-hover
                  v-slot="{ isHovering, props }"
                  open-delay="200"
                >
                  <v-card
                    :elevation="isHovering ? 3 : 0"
                    :class="{ 'on-hover': isHovering }"
                    class="mt-5 d-flex flex-row justify-start align-center" variant="outlined"
                    height="240" rounded="0"
                    v-bind="props"
                  >
                    <!-- 좌측 전문가 정보 -->
                    <v-card class="d-flex flex-column justify-center align-center" height="210" width="190" elevation="0">
                      <v-avatar color="#06BEE1" size="100">
                          <span v-if="reservation.consultantProfile == null">{{ reservation.consultantName }}</span>
                          <img v-else :src="getProfileUrl(reservation.consultantProfile)" height="100" width="100" />
                        </v-avatar>
                      <!-- <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" /> -->
                      <v-card-title>{{ reservation.consultantName }}</v-card-title>
                      <v-card-item class="pa-0">
                        <v-rating v-model="reservation.consultantRate" icon="mdi-star"
                          color="yellow-darken-3" density="compact"
                          empty-icon="mdi-star-outline" full-icon="mdi-star" half-icon="mdi-star-half"
                          half-increments readonly
                        ></v-rating>
                      </v-card-item>
                    </v-card>
                    <!-- 우측 상담 결과 -->
                    <v-card class="d-flex flex-column" height="210" width="763" elevation="0">
                      <v-card-title>상담 결과</v-card-title>
                      <v-card-text>
                        {{ reservation.reservationDignosisRecord }}
                      </v-card-text>
                    </v-card>
                  </v-card>
                </v-hover>
              </v-expansion-panel-text>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-sheet>
      </div>
    </div>
  </div>
</template>

<script>
//import axios from "axios";
import FinishedReviewModify from "@/components/FinishedReservation/FinishedReviewModify.vue"
import NowLoading from "@/views/NowLoading.vue";
import MoveCreateReservation from "@/components/CreateReservation/MoveCreateReservation.vue";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";


export default {
  name: "FinishedReservationPartner",
  data: () => ({
    panel: null,
    reservations: [],
    rating: 3.5,
    loaded: false
  }),
  components: {
    FinishedReviewModify,
    NowLoading,
    MoveCreateReservation
  },
  computed: {
    ...mapState(userStore, ["userId"]),

  },
  methods:{
    async getReservation() {

      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/partner/past/${this.userId}`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            console.log(data[i])
            let reservation = {};
            reservation["rno"] = data[i].reservationPetFinish.rno;
            reservation["userId"] = data[i].reservationPetFinish.userId;
            reservation["reservationDate"] = data[i].reservationPetFinish.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPetFinish.reservationConsultContent;

            reservation["reservationDignosisRecord"] = data[i].reservationPetFinish.reservationDignosisRecord;
            reservation["reviewGrade"] = data[i].reservationPetFinish.reviewGrade;
            reservation["reviewComment"] = data[i].reservationPetFinish.reviewComment;

            reservation["pno"] = data[i].reservationPetFinish.pno;
            reservation["petName"] = data[i].reservationPetFinish.petName;
            reservation["petImage"] = data[i].reservationPetFinish.petImage;
            reservation["petType"] = data[i].reservationPetFinish.petType;
            reservation["petVariety"] = data[i].reservationPetFinish.petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].reservationPetFinish.petInfo;

            reservation["consultantId"] = data[i].reservationPetFinish.consultant_id;
            reservation["consultantName"] = data[i].consultantInfo.consultant_name;
            reservation["consultantIntro"] = data[i].consultantInfo.consultant_intro;
            reservation["consultantProfile"] = data[i].consultantInfo.consultant_profile;
            reservation["consultantRate"] = data[i].consultantInfo.consultant_rate;

            this.reservations.push(reservation);
          }

          this.reservations.sort((a, b) => {
            if (a.reservationDate > b.reservationDate) return -1;
            else if (a.reservationDate < b.reservationDate) return 1;
            else return 0;
          });
          // console.log(this.reservations)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async updateReview(review) {
      this.loaded = false;
      this.reservations[review.idx].reviewGrade = await review.reviewGrade;
      this.reservations[review.idx].reviewComment = await review.reviewComment;
      this.loaded = true;
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
    getProfileUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PROFILE}${img}`;
    },
  },
  async created(){
    this.loaded = false;
    try {
      await this.getReservation();
      // console.log(this.$route.query.no)
      if (this.$route.query.no != undefined) {
        // console.log("실행");
        this.panel = await Number(this.$route.query.no);
      }
    } catch (e) {
      console.log(e);
    }
    await this.getReservation();
    this.loaded = true;
  }
}
</script>

<style></style>