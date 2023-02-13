<template>
  <!--dialog 파일 분리-->
  <v-dialog v-model="dialog" width="1200">
    <template v-slot:activator="{ props }">
      <v-btn variant="outlined" rounded="0" color="primary"
        v-bind="props" block :disabled="reservation.matchingConsultants.length < 1"
      >
        받은 상담 제안 {{ reservation.matchingConsultants.length }}건 보기
      </v-btn>
    </template>
    <v-card class="pa-5">
      <v-card-title><h3>{{ reservation.reservationDate }}</h3></v-card-title>
      <v-card-text class="pa-0 pl-5">
        <p>{{ reservation.petName }} ({{ reservation.petBirth }})</p>
        <p>{{ reservation.petType }} <span v-show="reservation.petVariety != null">-</span> {{ reservation.petVariety }}</p>
      </v-card-text>
      <v-card-item>
        <v-slide-group v-model="model" selected-class="bg-success" show-arrows>
          <!--v-for 자리-->
          <v-slide-group-item >
            <ReceivedMatchingCarousel :reservationItem="reservation" @dialogOff="dialogOff()" />
          </v-slide-group-item>
        </v-slide-group>
      </v-card-item>
      <v-card-actions>
        <v-btn color="primary" block @click="dialog = false">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import ReceivedMatchingCarousel from "@/components/ReceiveMatching/ReceivedMatchingCarousel.vue"
export default {
  name: "ReceivedMatchingCardButton",
  components: {
    ReceivedMatchingCarousel
  },
  props: {
    reservation: {},
  },
  data: () => ({
    dialog: false,
    model: []
  }),
  methods: {
    dialogOff() {
      this.dialog = false;
    }
  },
  created() {
    // console.log("#props 확인", this.reservation);
    // this.matchingConsultants = this.matchingConsultants;
    console.log("#data 반영 확인", this.reservation);
  }
}
</script>

<style></style>