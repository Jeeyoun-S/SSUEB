<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="550" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item>
        <ReceivedMatchingCardButton :reservation="reservation" />
      </v-card-item>
      <v-card-item class="align-self-center">
        <v-avatar color="#06BEE1" size="100">
          <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
          <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
        </v-avatar>
      </v-card-item>
      <v-card-title><h4>{{ reservation.reservationDate.substr(0, 16) }}</h4></v-card-title>
      <v-card-subtitle>
        <p>{{ reservation.petName }}</p>
        <p>{{ reservation.petBirth }} - {{ reservation.petType }}</p>
        <p>{{ reservation.petVariety }}</p>
      </v-card-subtitle>
      <v-card-text>
        <div class="reservation-pet-info">
          <span>
            {{ reservation.reservationConsultContent.substr(0, 120) }}
          </span>
          <span v-if="reservation.reservationConsultContent.length > 120">···</span>
          <v-btn v-if="reservation.reservationConsultContent.length > 120" color="primary" class="mt-2" @click="overlay = !overlay" variant="outlined" rounded="0" block>
            더보기
          </v-btn>
          <v-overlay v-model="overlay" class="d-flex flex-column align-center justify-center" contained>
            <v-sheet class="pa-4 detail-info">
              {{ reservation.reservationConsultContent }}
              <v-btn class="mt-2" color="primary" @click="overlay = false" rounded="0" block>
                닫기
              </v-btn>
            </v-sheet>
          </v-overlay>
        </div>
      </v-card-text>
      <v-card-actions class="d-flex flex-row justify-space-between">
        <v-btn variant="text" color="primary" @click="seefile">
          관련 첨부파일 보기
        </v-btn>
        <v-btn variant="text" color="error" @click="deleteRese">
          삭제
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import ReceivedMatchingCardButton from "./ReceivedMatchingCardButton.vue";
import { apiInstance } from "@/api/index.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "ReceivedMatchingCard",
  components: { 
    ReceivedMatchingCardButton
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
    idx: Number
  },
  methods: {

    dialogOff() {
    },

    async seefile() {
      this.$swal.fire({
        imageUrl: "https://unsplash.it/400/200",
        imageWidth: 600,
      });
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
    async deleteRese() {
      //삭제 후 카운트 변경은 추후 생각해보자
      const api = apiInstance();
      await api
        .delete(`${process.env.VUE_APP_API_BASE_URL}/reservation/${this.reservation.rno}`)
        .then(() => {
          this.$emit("deleteReservation", this.idx);
          this.$swal.fire(
            '상담 삭제 완료',
            '상담이 삭제되었습니다.',
            'success'
          )
        })
        .catch((err) => {
          alert(err);
        });
    },
  },

  created(){
    console.log("reservation", this.reservation);
  }
};
</script>

<style>
.detail-info {
  background-color : rgb(0,0,0,0.6);
  color: white;
}
</style>
