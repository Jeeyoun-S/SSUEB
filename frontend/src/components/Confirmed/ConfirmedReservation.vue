<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-format-list-checkbox</v-icon>
        <h2>예정된 상담 일정 목록</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <confirmed-card v-for="n in 7" :key="n"></confirmed-card>
      </div>
    </div>
  </div>
</template>

<script>
import ConfirmedCard from "./ConfirmedCard.vue";
import axios from "axios";
export default {
  name: "ConfirmedReservation",
  data: () => ({
    reservations:[],
  }),
  components: {
    ConfirmedCard,
  },
  methods:{
    getReservation() {
      const BASE_URL= `http://localhost:5000/api`;

      //consultant의 경우 -> 위랑 이거는 현재 유저가 유저인지 전문가인지에 따라 취사선택하도록?
      //const API_URL = `http://localhost:5000/api/reservation/consultant/`+`aa@a`;
      
      axios({
        url: BASE_URL+`/reservation/partner/`+`aa@a`,
        method: "get",
      })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            console.log(data[i])
            let reservation = {};
            reservation["rno"] = data[i].reservationPet.rno;
            reservation["userId"] = data[i].reservationPet.userId;
            reservation["reservationDate"] = data[i].reservationPet.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPet.reservationConsultContent;

            reservation["pno"] = data[i].reservationPet.pno;
            reservation["petImage"] = data[i].reservationPet.petImage;
            reservation["petType"] = data[i].reservationPet.petType;
            reservation["petVariety"] = data[i].reservationPet.petVariety;
            reservation["petBirth"] = data[i].reservationPet.petBirth;
            reservation["petInfo"] = data[i].reservationPet.petInfo;

            reservation["consultantName"] = data[i].consultantInfo.consultant_name;
            reservation["consultantIntro"] = data[i].consultantInfo.consultant_intro;
            reservation["consultantProfile"] = data[i].consultantInfo.consultant_profile;
            reservation["consultantRate"] = data[i].consultantInfo.consultant_rate;

            this.reservations.push(reservation);
          }

          console.log(this.reservations[0])
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

<style scoped>



</style>>
