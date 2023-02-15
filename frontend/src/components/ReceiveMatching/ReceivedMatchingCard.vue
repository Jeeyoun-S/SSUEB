<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="430" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item>
        <ReceivedMatchingCardButton :reservation="reservation" />
      </v-card-item>
      <v-card-title><h4>{{ reservation.reservationDate.substr(0, 16) }}</h4></v-card-title>
      <v-card-item class="d-flex flex-row">
        <template v-slot:prepend>
          <v-avatar color="#06BEE1" size="90">
            <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
            <img v-else :src="getImageUrl(reservation.petImage)" height="90" width="90" />
            <v-tooltip v-if="reservation.petInfo != null" activator="parent" location="end" width="180">{{ reservation.petInfo }}</v-tooltip>
          </v-avatar>
        </template>
        <v-card-title>{{ reservation.petName }}</v-card-title>
        <v-card-subtitle>
          <p>{{ reservation.petType }} ({{ reservation.petBirth }})</p>
          <p>{{ reservation.petVariety }} </p>
        </v-card-subtitle>
      </v-card-item>
      <v-card-text>
        <div class="reservation-pet-info">
          <v-sheet height="90">
            {{ reservation.reservationConsultContent.substr(0, 80) }}
            <span v-if="reservation.reservationConsultContent.length > 80">···</span>
          </v-sheet>
          <v-btn v-if="reservation.reservationConsultContent.length > 80" 
            color="primary" v-model="overlay" class="mt-2"
            variant="outlined" rounded="0" block
          >
            더보기
            <v-dialog v-model="overlay" activator="parent" width="600">
              <v-card class="pa-8">
                <h2 class="mb-3">상담 신청 내용</h2>
                {{ reservation.reservationConsultContent }}
                <v-btn class="mt-4" color="primary" @click="overlay = false" rounded="0" block>
                  닫기
                </v-btn>
              </v-card>
            </v-dialog>
          </v-btn>
        </div>
      </v-card-text>
      <v-card-actions class="d-flex flex-row justify-space-between">
        <SeeAttatchedFiles :reservation="reservation"/>
        <v-btn variant="text" color="error" @click="deleteRese"> 삭제 </v-btn>
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import ReceivedMatchingCardButton from "./ReceivedMatchingCardButton.vue";
import SeeAttatchedFiles from "../SeeAttachedFiles/SeeAttatchedFiles.vue";
import { apiInstance } from "@/api/index.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "ReceivedMatchingCard",
  components: {
    ReceivedMatchingCardButton,
    SeeAttatchedFiles,
  },
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  emits: ["deleteReservation"],
  data: () => ({
    dialog: false,
    model: null,
    overlay: false
  }),
  props: {
    reservation: Object,
    idx: Number,
  },
  methods: {

    dialogOff() {},

    async deleteRese() {
      const api = apiInstance();
      await api
        .delete(
          `${process.env.VUE_APP_API_BASE_URL}/reservation/${this.reservation.rno}`
        )
        .then(() => {
          this.$emit("deleteReservation", this.idx);
          this.$swal.fire(
            "상담 삭제 완료",
            "상담이 삭제되었습니다.",
            "success"
          );
        })
        .catch((err) => {
          alert(err);
        });
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },

  created() {
    //console.log("reservation", this.reservation);
  },
};
</script>

<style>
.detail-info {
  background-color : rgb(0,0,0,0.7);
  color: white;
}
</style>
