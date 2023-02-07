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
      const API_URL = `http://localhost:5000/api/reservation/partner/`+`aa@a`;

      //consultant의 경우 -> 위랑 이거는 현재 유저가 유저인지 전문가인지에 따라 취사선택하도록?
      //const API_URL = `http://localhost:5000/api/reservation/consultant/`+`aa@a`;
      
      axios({
        url: API_URL,
        method: "get",
      })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].rno;
            reservation["userId"] = data[i].userId;
            reservation["reservationDate"] = data[i].reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationConsultContent;

            reservation["pno"] = data[i].pno;
            reservation["petImage"] = data[i].petImage;
            reservation["petType"] = data[i].petType;
            reservation["petVariety"] = data[i].petVariety;
            reservation["petBirth"] = data[i].petBirth;
            reservation["petInfo"] = data[i].petInfo;

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

<style scoped>



</style>>
