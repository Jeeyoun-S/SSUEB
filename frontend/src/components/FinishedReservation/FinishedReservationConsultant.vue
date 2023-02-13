<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else>
    <v-sheet class="ma-7">
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
                height="270" width="180" rounded="0"
                v-bind="props"
              >
                <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
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
                height="270" width="390" rounded="0"
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
  </div>
</template>

<script>
import FinishedResultModify from "@/components/FinishedReservation/FinishedResultModify.vue";
import NowLoading from "@/views/NowLoading.vue";

export default {
  name: "FinishedReservationConsultant",
  data() {
    return {
      loaded: true,
      panel: [],
      reservations: [
        {
          userId: "partner@test.com",
          consultantId: "consultant3@test.com",
          reviewGrade: 3,
          reviewComment: "사장님;;",
          rno: 21,
          petName: "ㅎㅎㅎㅎㅎㅎㅎ",
          petBirth: "2022-02",
          petImage: "b72c28b8-5b56-43ee-b6f8-f100fa8cab84.jpg",
          petInfo: null,
          petType: "기니피그",
          petVariety: null,
          reservationDignosisRecord: "아쉽게도",
          reservationDate: "2023-01-19 10:20:00.0",
          reservationConsultContent: "asdfasdfasd",
          pno: 27
        }
      ]
    }
  },
  components: {
    FinishedResultModify,
    NowLoading
  },
  methods: {
    async updateRecord(dignosis) {
      this.loaded = false;
      this.reservations[dignosis.idx].reservationDignosisRecord = await dignosis.reservationDignosisRecord;
      this.loaded = true;
    }
  }
}
</script>

<style>

</style>