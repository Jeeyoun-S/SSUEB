<template>
  <v-sheet width="1000" height="800">
    <v-expansion-panels multiple class="my-4">
      0000    2023-02-06 18:00 ~ 18:30     와싸피 / 로이
      <v-expansion-panel width="800" v-for="i in 50" :key="i">
        <v-expansion-panel-title>
          0000    2023-02-06 18:00 ~ 18:30     와싸피 / 로이                <v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
        </v-expansion-panel-title>
        <v-expansion-panel-text>
          
          <v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-sheet>
</template>

<script>
import axios from "axios";
export default {
  name: "FinishedReservation",
  data: () => ({
    reservations:[],
  }),
  methods:{
    getReservation() {
      const BASE_URL = `http://localhost:5000/api`
      
      axios({
        url: BASE_URL+`/reservation/partner/past/`+`aa@a`,
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
            reservation["petImage"] = data[i].reservationPetFinish.petImage;
            reservation["petType"] = data[i].reservationPetFinish.petType;
            reservation["petVariety"] = data[i].reservationPetFinish.petVariety;
            reservation["petBirth"] = data[i].reservationPetFinish.petBirth;
            reservation["petInfo"] = data[i].reservationPetFinish.petInfo;

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

<style></style>
