<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-view-grid-plus</v-icon>
        <h2>반려인에게 상담 제안 보내기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <CreateCard v-for="(reservation, idx) in reservations" :reservation="reservation" v-bind:key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import CreateCard from "@/components/CreateMatching/CreateCard.vue";

import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";

export default {
  name: "CreateMatching",
  data: () => ({
    loaded: false,
    reservations:[], // [{value,[]},{value,[]}] 꼴
  }),
  computed: {
    ...mapState(userStore, ["userId"]),
    },
  components: {
    CreateCard,
    NowLoading
  },
  
  methods:{
    async getReservation() {
      
      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/consultant/unconfirmed`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].rno;
            reservation["userId"] = data[i].userId;
            reservation["reservationDate"] = data[i].reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationConsultContent;
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
          console.log(this.reservations)
        })
        .catch((err) => {
          console.log(err);
        });
      return await Promise.resolve(true);
    },
  },
  async created() {
    this.loaded = false;
    await this.getReservation().then((res) => {
      this.loaded = res;
    });
  },
};
</script>

<style></style>