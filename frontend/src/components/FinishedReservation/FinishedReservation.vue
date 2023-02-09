<template>
  <div class="history">
    <v-sheet class="ma-7">
      <v-row>
        <v-col class="pa-0 pl-2" cols="1"><h3>번호</h3></v-col>
        <v-col class="pa-0" cols="3"><h3>상담일시</h3></v-col>
        <v-col class="pa-0" cols="5"><h3>전문가 / 반려동물</h3></v-col>
        <v-col class="pa-0"><h3>별점</h3></v-col>
      </v-row>
    </v-sheet>
    <v-expansion-panels multiple>
      <v-expansion-panel class="reservation" v-for="(reservation, idx) in reservations" :key="{idx}">

        <!-- 타이틀 -->
        <v-expansion-panel-title>
          <v-col class="pa-0 pl-2 bold-font" cols="1">{{ idx + 1 }}</v-col> <!--번호, 연결하지 않음-->
          <v-col class="pa-0" cols="3">{{ reservation.reservationDate }}</v-col> <!--상담일시-->
          <v-col class="pa-0" cols="5">{{ reservation.consultantName }} / {{ reservation.petName }}</v-col> <!--전문가/애기이름-->
          <v-col class="pa-0"> <!--별점-->
            <v-rating v-model="reservation.reviewGrade"  color="orange darken-2" density="compact" half-increments readonly></v-rating>
          </v-col>
        </v-expansion-panel-title>

        <!-- 내용 -->
        <v-expansion-panel-text class="pa-3">
          <v-hover
            v-slot="{ isHovering, props }"
            open-delay="200"
          >
            <v-card
              :elevation="isHovering ? 3 : 0"
              :class="{ 'on-hover': isHovering }"
              class="mb-5 mx-auto d-flex flex-row justify-center align-center" variant="outlined"
              height="200" rounded="0"
              v-bind="props"
            >
              <!-- 좌측 반려동물 정보 -->
              <v-card class="mr-5 d-flex flex-column justify-center align-center" height="175" width="230" elevation="0">
                <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
                <v-card-title class="pa-0">{{ reservation.petName }} ({{ reservation.petBirth }})</v-card-title> <!--애기 이름--><!--(출생연월)-->
                <p>{{ reservation.petType }}</p><!--품종 대분류-->
                <p>{{ reservation.petVariety }}</p><!--품종 소분류-->
              </v-card>
              <!-- 우측 상담 신청 내용 -->
              <v-card class="d-flex flex-column" height="175" width="700" elevation="0">
                <v-card-title>상담 신청 내용</v-card-title>
                <v-card-text>
                  {{ reservation.reservationConsultContent }}
                </v-card-text>
              </v-card>
            </v-card>
          </v-hover>
          <v-divider></v-divider>
          <!-- 전문가 상담 내용 -->
          <v-hover
            v-slot="{ isHovering, props }"
            open-delay="200"
          >
            <v-card
              :elevation="isHovering ? 3 : 0"
              :class="{ 'on-hover': isHovering }"
              class="mt-5 mx-auto d-flex flex-row justify-center align-center" variant="outlined"
              height="200" rounded="0"
              v-bind="props"
            >
              <!-- 좌측 반려동물 정보 -->
              <v-card class="mr-5 d-flex flex-column justify-center align-center" height="175" width="230" elevation="0">
                <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
                <v-card-title class="pa-0">
                  {{ reservation.consultantName }}
                  &nbsp;<v-icon icon="mdi-star" color="orange darken-2" size="x-small"></v-icon>
                  {{ reservation.consultantRate }}
                </v-card-title>
                <p>반려동물행동지도사</p>
              </v-card>
              <!-- 우측 상담 신청 내용 -->
              <v-card class="d-flex flex-column" height="175" width="700" elevation="0">
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
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "FinishedReservation",
  data: () => ({
    reservations:[],
    rating: 3.5,
  }),
  methods:{
    getReservation() {
      axios({
        url: process.env.VUE_APP_API_BASE_URL+`/reservation/partner/past/`+`aa@a`,
        method: "get",
      })
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

            reservation["consultantName"] = data[i].consultantInfo.consultant_name;
            reservation["consultantIntro"] = data[i].consultantInfo.consultant_intro;
            reservation["consultantProfile"] = data[i].consultantInfo.consultant_profile;
            reservation["consultantRate"] = data[i].consultantInfo.consultant_rate;

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
};
</script>

<style></style>