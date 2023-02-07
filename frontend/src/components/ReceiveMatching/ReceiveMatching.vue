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
    reservations:[],
  }),
  components: {
    ReceivedCard,
  },
  methods:{
    getReservation() {
      const API_URL = `http://localhost:5000/api/reservation/partner/unconfirm/`+`aa@a`;
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

<style></style>
