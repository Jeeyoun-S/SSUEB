<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="ma-3 pa-3 d-flex justify-center flex-column"
      width="492"
      height="450"
      variant="outlined"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      rounded="0"
      v-bind="props"
    >
      <v-card-title class="d-flex flex-row justify-space-between">
        <h4>{{ reservation.reservationDate }}</h4>
        <div>
          <!--날짜계산-->
          <v-btn class="mr-3" rounded="pill" color="primary" disabled
            >D-{{ reservation.dDay }}</v-btn
          >
          <v-btn rounded="pill" color="primary" @click="moveRoom">입장</v-btn>
        </div>
      </v-card-title>
      <div class="d-flex flex-row justify-space-around">
        <v-card class="pt-2" width="220" height="360" elevation="0">
          <div class="d-flex flex-column">
            <v-avatar color="#06BEE1" size="80" class="align-self-center">
              <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
              <img v-else :src="getImageUrl(reservation.petImage)" height="80" width="80" />
              <v-tooltip v-if="reservation.petInfo != null"
                activator="parent"
                location="bottom" width="220"
              >{{ reservation.petInfo }}</v-tooltip>
            </v-avatar>
            <v-card-title class="pb-0">{{ reservation.petName }}</v-card-title>
            <v-card-subtitle>{{ reservation.petType }} ({{ reservation.petBirth }})</v-card-subtitle>
            <v-card-subtitle>{{ reservation.petVariety }}&nbsp;</v-card-subtitle>
          </div>
          <v-card-text class="pb-0">
            <div class="reservation-pet-info">
              <v-sheet height="130">
                <span>
                  {{ reservation.reservationConsultContent.substr(0, 55) }}
                </span>
                <span v-if="reservation.reservationConsultContent.length > 55">···</span>
                <v-btn v-if="reservation.reservationConsultContent.length > 55"
                  color="primary" class="mt-2" v-model="overlayDetail"
                  variant="outlined" rounded="0" block 
                >
                  더보기
                  <v-dialog v-model="overlayDetail" activator="parent" width="600">
                    <v-card class="pa-8">
                      <h2 class="mb-3">상담 신청 내용</h2>
                      {{ reservation.reservationConsultContent }}
                      <v-btn class="mt-4" color="primary" @click="overlayDetail = false" rounded="0" block>
                        닫기
                      </v-btn>
                    </v-card>
                  </v-dialog>
                </v-btn>
              </v-sheet>
            </div>
          </v-card-text>
          <v-card-actions>
            <SeeAttatchedFiles :rno="reservation.rno" />
          </v-card-actions>
        </v-card>
        <v-divider vertical></v-divider>
        <v-card class="pt-2 mb-5" width="220" height="350" elevation="0">
          <div class="d-flex flex-column">
            <v-avatar color="#06BEE1" size="80" class="align-self-center">
              <img v-if="reservation.consultantProfile == null" class="image" width="80" :src="require('@/assets/placeholder/placeholder_person.jpg')" />
              <img v-else :src="getProfileUrl(reservation.consultantProfile)" height="80" width="80" />
              <v-tooltip v-if="reservation.consultantIntro != null"
                activator="parent"
                location="bottom" width="220"
              >{{ reservation.consultantIntro }}</v-tooltip>
            </v-avatar>
            <v-card-title class="pb-0">{{ reservation.consultantName }}</v-card-title>
            <v-card-subtitle class="mb-5">반려동물훈련지도사</v-card-subtitle>
          </div>
          <v-sheet height="162">
            <v-card-text class="pa-4">
              <p>
                <span class="bold-font mr-2">금액</span>
                <span>{{ reservation.reservationCost }}</span>
              </p>
              <p class="mt-2">
                <span class="bold-font mr-2">설명</span>
                <span>{{ reservation.reservationReason }}</span>
              </p>
            </v-card-text>
          </v-sheet>
        </v-card>
      </div>
    </v-card>
  </v-hover>
</template>

<script>
import { mapActions } from 'vuex';

const roomStore = "roomStore";
import SeeAttatchedFiles from "../SeeAttachedFiles/SeeAttatchedFiles.vue";

export default {
  name: "ConfirmedPartnerCard",
  props: {
    reservation: {},
  },
  data() {
    return {
      overlayDetail: false
    }
  },
  components: {
    SeeAttatchedFiles,
  },
  methods: {
    ...mapActions(roomStore, ["setMeetingReservation"]),
    moveRoom(){
      //유효성검사후 없으면 적절한 alert띄우기 
        // console.log("reservation 정보 : ",this.reservation.rno)
        // alert(this.reservation.rno)
        // this.$store.dispatch("setMeetingReservation", this.reservation)
        this.setMeetingReservation(this.reservation)
        .then((res) => {
          // console.log("여기")
          if (res) {
            location.href = `${process.env.VUE_APP_BASE_URL}/meeting-room`;
          }
        }); 
        // location.href = `${process.env.VUE_APP_BASE_URL}/meeting-room`;
        // this.$router.push(
        //   {
        //     name:"meetingRoom",
        //     params: this.reservation
        //   }
        // );
        // this.roomInitialize();
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
    getProfileUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PROFILE}${img}`;
    },
  },
  created() {
    //console.log(this.reservation);
  },
};
</script>

<style></style>
