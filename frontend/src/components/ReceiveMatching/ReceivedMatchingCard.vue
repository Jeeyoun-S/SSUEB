<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="450" variant="outlined"
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
        <!-- <v-avatar class="pt-1" color="white" size="100">
          <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
        </v-avatar> -->
      </v-card-item>
      <v-card-title>{{ reservation.reservationDate }}</v-card-title>
      <v-card-subtitle>
        <p>{{ reservation.petName }} ({{ reservation.petBirth }})</p>
        <p>{{ reservation.petType }} <span v-show="reservation.petVariety != null">-</span> {{ reservation.petVariety }}</p>
      </v-card-subtitle>
      <v-card-text>
        <div class="reservation-pet-info">
          {{ reservation.reservationConsultContent }}
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

<style></style>
