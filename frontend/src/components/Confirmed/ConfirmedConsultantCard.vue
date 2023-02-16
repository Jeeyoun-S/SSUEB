<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="ma-3 pa-2 d-flex justify-center flex-column"
      width="492"
      height="470"
      variant="outlined"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      rounded="0"
      v-bind="props"
    >
      <v-card-title class="d-flex flex-row justify-space-between">
        <h4>{{ reservation.reservationDate }}</h4>
        <div>
          <v-btn class="mr-2" rounded="pill" color="primary" disabled
            >D-{{ reservation.dDay }}</v-btn
          >
          <v-btn rounded="pill" color="primary" @click="moveRoom">입장</v-btn>
        </div>
      </v-card-title>
      <v-sheet class="d-flex flex-row justify-start align-center" height="90">
        <v-card-item class="d-flex flex-row">
          <template v-slot:prepend>
            <v-avatar color="#06BEE1" size="80">
              <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
              <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
              <v-tooltip
                activator="parent"
                location="end" width="350"
              >{{ reservation.petInfo }}</v-tooltip>
            </v-avatar>
          </template>
          <v-card-title>{{ reservation.petName }}</v-card-title>
          <v-card-subtitle>{{ reservation.petType }} ({{ reservation.petBirth }})</v-card-subtitle>
          <v-card-subtitle v-if="reservation.petVariety != null">{{ reservation.petVariety }}</v-card-subtitle>
        </v-card-item>
      </v-sheet>
      <v-sheet height="110">
        <v-card-text>
          <div>
            <span>
              {{ reservation.reservationConsultContent.substr(0, 60) }}
            </span>
            <span v-if="reservation.reservationConsultContent.length > 60">···</span>
            <v-btn v-if="reservation.reservationConsultContent.length > 60" color="primary" class="mt-2" @click="overlay = !overlay" variant="outlined" rounded="0" block>
              더보기
            </v-btn>
            <v-overlay v-model="overlay" class="d-flex flex-column align-center justify-center" contained>
              <v-sheet class="pa-4 detail-info" height="470">
                {{ reservation.reservationConsultContent }}
                <v-btn class="mt-2" color="primary" @click="overlay = false" rounded="0" block>
                  닫기
                </v-btn>
              </v-sheet>
            </v-overlay>
          </div>
          <!-- {{ reservation.reservationConsultContent }} -->
        </v-card-text>
      </v-sheet>
      <v-divider></v-divider>
      <v-card-text>
        <v-row>
          <v-col class="pb-0 bold-font" cols="2">금액</v-col>
          <v-col class="pb-0">{{ reservation.reservationCost }}</v-col>
        </v-row>
        <v-row>
          <v-col class="pb-0 bold-font" cols="2">설명</v-col>
          <v-col>{{ reservation.reservationReason }}</v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <SeeAttatchedFiles :rno="reservation.rno" />
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import { mapActions } from 'vuex';

const roomStore = "roomStore";
import SeeAttatchedFiles from "../SeeAttachedFiles/SeeAttatchedFiles.vue";
export default {
  name: "ConfirmedConsultantCard",
  data() {
    return {
      overlay: false,
    };
  },
  props: {
    reservation: {},
  },
  components: {
    SeeAttatchedFiles,
  },
  methods: {
    ...mapActions(roomStore, ["setMeetingReservation"]),
    //timealert() {
    //  this.$swal.fire(
    //    "화상 상담 입장 대기",
    //    "화상 상담은 10분 전부터 입장 가능합니다.",
    //    "warning"
    //  );
    //},
    moveRoom() {
      //유효성검사후 없으면 적절한 alert띄우기
      this.setMeetingReservation(this.reservation)
        .then((res) => {
          // console.log("여기")
          if (res) {
            location.href = `${process.env.VUE_APP_BASE_URL}/meeting-room`;
          }
        }); 
      // this.$router.push("meeting-room");
      // this.roomInitialize();
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  created() {

  },
};
</script>

<style></style>
