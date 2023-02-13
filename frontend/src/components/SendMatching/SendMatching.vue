<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-send</v-icon>
        <h2>내가 보낸 상담 제안 확인하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <SendMatchingCard v-for="(matching, idx) in matchings" :matching="matching" v-bind:key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import SendMatchingCard from "@/components/SendMatching/SendMatchingCard.vue";

import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const reservationStore = "reservationStore";
const userStore = "userStore";


export default {
  name: "SendMatching",
  data: () => ({
    matchings:[], 
  }),
  components: {
    SendMatchingCard
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    ...mapState(reservationStore),
    },
  methods:{
    async getMatchings(){
      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/matching/consultant/${this.userId}`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            console.log(data[i]);
            let matching = {};
            matching["userId"] = data[i].user_id;
            matching["reservationDate"] = data[i].reservation_iate;
            matching["reservationConsultContent"] = data[i].reservation_consult_content;
            
            matching["no"] = data[i].matching_no;
            matching["matchingCost"] = data[i].matching_cost;
            matching["matchingComment"] = data[i].matching_comment;

            matching["petName"] = data[i].pet_name;
            matching["petImage"] = data[i].pet_image;
            matching["petType"] = data[i].pet_type;
            matching["petVariety"] = data[i].pet_variety;
            if(data[i].petBirth != null){
              matching["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              matching["petBirth"] = "생년월일 미상";
            }
            matching["petInfo"] = data[i].pet_info;

            this.matchings.push(matching);
          }
          console.log(this.matchings);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async deleteMatching(no) {
      const api = apiInstance();
      await api
        .delete(process.env.VUE_APP_API_BASE_URL + `/reservation/matching/` + no)
        .then(() => {
          console.log("삭제");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created(){
    this.getMatchings();
  }
};
</script>

<style></style>