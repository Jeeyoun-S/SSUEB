<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-format-list-checkbox</v-icon>
        <h2>예정된 상담 일정 목록</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <confirmed-card v-for="(reservation, idx) in reservations" :reservation="reservation" v-bind:key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import ConfirmedCard from "./ConfirmedCard.vue";
import NowLoading from '@/views/NowLoading.vue';
import axios from "axios";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "ConfirmedReservation",
  data: () => ({
    reservations:[],
    loaded: false
  }),
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  components: {
    ConfirmedCard,
    NowLoading
  },
  methods:{
    async getReservation() {

      //consultant의 경우 -> 위랑 이거는 현재 유저가 유저인지 전문가인지에 따라 취사선택하도록?
      //const API_URL = `http://localhost:5000/api/reservation/consultant/`+`aa@a`;
      
      await axios({
        url: process.env.VUE_APP_API_BASE_URL+`/reservation/partner/${this.userId}`,
        method: "get",
      })
        .then(({ data }) => {
          console.log("확정 상담", data)
          for (var i = 0; i < data.length; i++) {
            console.log(data[i])
            let reservation = {};
            reservation["rno"] = data[i].reservationPet.rno;
            reservation["userId"] = data[i].reservationPet.userId;
            reservation["reservationDate"] = data[i].reservationPet.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPet.reservationConsultContent;

            reservation["pno"] = data[i].reservationPet.pno;
            reservation["petName"] = data[i].reservationPet.petName;
            reservation["petImage"] = data[i].reservationPet.petImage;
            reservation["petType"] = data[i].reservationPet.petType;
            reservation["petVariety"] = data[i].reservationPet.petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].reservationPet.petInfo;

            reservation["consultantName"] = data[i].consultantInfo.consultant_name;
            reservation["consultantIntro"] = data[i].consultantInfo.consultant_intro;
            reservation["consultantProfile"] = data[i].consultantInfo.consultant_profile;
            reservation["consultantRate"] = data[i].consultantInfo.consultant_rate;

            this.reservations.push(reservation);
          }

          //console.log(this.reservations[0])
        })
        .catch((err) => {
          console.log(err);
        });

        return Promise.resolve(true);
    },
  },
  async created(){
    this.loaded = false;
    await this.getReservation()
    .then((res) => {
      this.loaded = res;
    });
  },
  async mounted() {
  }
};
</script>

<style scoped>



</style>>
