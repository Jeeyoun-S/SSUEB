<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-email-open</v-icon>
        <h2>전문가에게 받은 상담 제안 보기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <ReceivedCard v-for="(reservation, idx) in reservations" :reservation="reservation" :key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import ReceivedCard from "@/components/ReceiveMatching/ReceivedMatchingCard.vue";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const reservationStore = "reservationStore";
const userStore = "userStore";

export default {
  name: "ReceiveMatching",
  data: () => ({
    reservations:[], // [{value,[]},{value,[]}] 꼴
  }),
  computed: {
    ...mapState(userStore, ["userId"]),
    ...mapState(reservationStore),
  },
  components: {
    ReceivedCard,
  },
  methods:{
    async getReservation() {

      const api = apiInstance();
      await api.get({
        url: `${process.env.VUE_APP_API_BASE_URL}/reservation/partner/unconfirm/${this.userId}`, //이메일 바꾸고
      })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].reservationPet.rno;
            reservation["userId"] = data[i].reservationPet.userId;
            reservation["reservationDate"] = data[i].reservationPet.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPet.reservationConsultContent;

            reservation["pno"] = data[i].reservationPet.pno;
            reservation["petImage"] = data[i].reservationPet.petImage;
            reservation["petType"] = data[i].reservationPet.petType;
            reservation["petName"] = data[i].reservationPet.petName;
            reservation["petVariety"] = data[i].reservationPet.petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
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
          //console.log(this.reservations[0].matchingConsultants)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteReservation(no) {
      //삭제 후 카운트 변경은 추후 생각해보자
      const api = apiInstance();
      api
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
