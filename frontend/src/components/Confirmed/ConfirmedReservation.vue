<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-format-list-checkbox</v-icon>
        <h2>예정된 상담 일정 목록</h2>
      </div>
      <div v-if="userAuth == 'ROLE_USER'" class="page-inner-items border-sheet-four">
        <MoveCreateReservation v-if="reservations == null || reservations.length < 1" message="아직 확정된 상담이 없습니다."></MoveCreateReservation>
        <ConfirmedPartnerCard v-for="(reservation, idx) in reservations" :reservation="reservation" :key="idx" />
      </div>
      <div v-else-if="userAuth == 'ROLE_CONSULTANT'" class="page-inner-items border-sheet-four">
        <MoveCreateMatching v-if="reservations == null || reservations.length < 1" message="아직 확정된 상담이 없습니다."></MoveCreateMatching>
        <ConfirmedConsultantCard v-for="(reservation, idx) in reservations" :reservation="reservation" :key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import ConfirmedPartnerCard from "@/components/Confirmed/ConfirmedPartnerCard.vue";
import ConfirmedConsultantCard from "@/components/Confirmed/ConfirmedConsultantCard.vue";
import MoveCreateReservation from "@/components/CreateReservation/MoveCreateReservation.vue";
import MoveCreateMatching from "@/components/CreateMatching/MoveCreateMatching.vue"
import NowLoading from '@/views/NowLoading.vue';
import { apiInstance } from "@/api/index.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "ConfirmedReservation",
  data: () => ({
    reservations:[],
    loaded: false
  }),
  computed: {
    ...mapState(userStore, ["userId", "userAuth"]),
  },
  components: {
    ConfirmedPartnerCard,
    ConfirmedConsultantCard,
    NowLoading,
    MoveCreateReservation,
    MoveCreateMatching
  },
  methods:{
    async getPartnerReservation() {

      //consultant의 경우 -> 위랑 이거는 현재 유저가 유저인지 전문가인지에 따라 취사선택하도록?
      
      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/partner/${this.userId}`)
        .then(({ data }) => {
          console.log("확정 상담", data)
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].reservationPet.rno;
            reservation["userId"] = data[i].reservationPet.userId;
            reservation["reservationDate"] = data[i].reservationPet.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPet.reservationConsultContent;
            reservation["reservationCost"] = data[i].reservationPet.reservationCost;
            reservation["reservationReason"] = data[i].reservationPet.reservationReason;
            
            reservation["pno"] = data[i].reservationPet.pno;
            reservation["petName"] = data[i].reservationPet.petName;
            reservation["petImage"] = data[i].reservationPet.petImage;
            reservation["petType"] = data[i].reservationPet.petType;
            reservation["petVariety"] = data[i].reservationPet.petVariety;
            if(data[i].reservationPet.petBirth != null){
              reservation["petBirth"] = data[i].reservationPet.petBirth.substr(0,7);
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
    async getConsultantReservation() {

      //consultant의 경우 -> 위랑 이거는 현재 유저가 유저인지 전문가인지에 따라 취사선택하도록?

      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/consultant/${this.userId}`)
        .then(({ data }) => {
          console.log("확정 상담", data)
          for (var i = 0; i < data.length; i++) {
            console.log(data[i])
            let reservation = {};
            reservation["rno"] = data[i].rno;
            reservation["userId"] = data[i].userId;
            reservation["reservationDate"] = data[i].reservationDate.substr(0,16)+":00";
            reservation["reservationConsultContent"] = data[i].reservationConsultContent;
            reservation["reservationCost"] = data[i].reservationCost;
            reservation["reservationReason"] = data[i].reservationReason;

            reservation["pno"] = data[i].pno;
            reservation["petName"] = data[i].petName;
            reservation["petImage"] = data[i].petImage;
            reservation["petType"] = data[i].petType;
            reservation["petVariety"] = data[i].petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].petInfo;

            this.reservations.push(reservation);
          }

        })
        .catch((err) => {
          console.log(err);
        });

        return Promise.resolve(true);
      },
  },
  async created(){
    this.loaded = false;
    if (this.userAuth == 'ROLE_USER') {
      await this.getPartnerReservation()
      .then((res) => {
        this.loaded = res;
      });
    } else if (this.userAuth == 'ROLE_CONSULTANT') {
      await this.getConsultantReservation()
      .then((res) => {
        this.loaded = res;
      });
    }
  },
  async mounted() {
  }
};
</script>

<style></style>
