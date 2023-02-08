<template>
  <v-item-group>
    <v-container>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <ReceivedCard></ReceivedCard>
          </v-item>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <ReceivedCard></ReceivedCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>
  </v-item-group>
</template>

<script>
import ReceivedCard from "@/components/ReceiveMatching/ReceivedCard.vue";
import axios from "axios";
export default {
  name: "ReceiveMatching",
  data: () => ({
    reservations:[], // [{value,[]},{value,[]}] 꼴
  }),
  components: {
    ReceivedCard,
  },
  methods:{
    getReservation() {
      axios({
        url: process.env.VUE_APP_API_BASE_URL+`/reservation/partner/unconfirm/`+`aa@a`,
        method: "get",
      })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let reservation = {

            };
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

            let matchingConsultants = [];
            for (let j = 0; j < data[i].matchingConsultants.length; j++){
              let matchingConsultant = {};
              matchingConsultant["consultantId"] = data[i].matchingConsultants[j].consultant_id;
              matchingConsultant["consultantIntro"] = data[i].matchingConsultants[j].consultant_intro;
              matchingConsultant["consultantName"] = data[i].matchingConsultants[j].consultant_name;
              matchingConsultant["consultantProfile"] = data[i].matchingConsultants[j].consultant_profile;
              matchingConsultant["consultantRate"] = data[i].matchingConsultants[j].consultant_rate;

              matchingConsultant["matchingComment"] = data[i].matchingConsultants[j].matching_comment;
              matchingConsultant["matchingCost"] = data[i].matchingConsultants[j].matching_comment;
              matchingConsultant["matchingNo"] = data[i].matchingConsultants[j].no;

              matchingConsultants.push(matchingConsultant);
            }
            //각각의 reservation안에 matchingConsultants라는 견적들의 리스트가 있다는 뜻
            reservation["matchingConsultants"] = matchingConsultants;
            this.reservations.push(reservation);
          }
          console.log(this.reservations[0].matchingConsultants)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteReservation(no) {
      //삭제 후 카운트 변경은 추후 생각해보자
      axios
        .delete(process.env.VUE_APP_API_BASE_URL + `/` + no)
        .then(() => {
          console.log("삭제");
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
