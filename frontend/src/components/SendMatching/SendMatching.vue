<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-send</v-icon>
        <h2>내가 보낸 상담 제안 확인하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <MoveCreateMatching v-if="matchings == null || matchings.length < 1" message="보낸 상담 제안이 없습니다."></MoveCreateMatching>
        <SendMatchingCard v-for="(matching, idx) in matchings" 
          :matching="matching" :idx="idx" :key="idx"
          @deleteMatching="deleteMatching"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import SendMatchingCard from "@/components/SendMatching/SendMatchingCard.vue";
import MoveCreateMatching from "@/components/CreateMatching/MoveCreateMatching.vue";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";


export default {
  name: "SendMatching",
  data: () => ({
    loaded: false,
    matchings:[], 
  }),
  components: {
    SendMatchingCard,
    MoveCreateMatching,
    NowLoading
  },
  computed: {
    ...mapState(userStore, ["userId"]),
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
      return await Promise.resolve(true);
    },
    async deleteMatching(index) {
      this.matchings.splice(index, 1);
    }
    // async deleteMatching(no) {
    //   const api = apiInstance();
    //   await api
    //     .delete(process.env.VUE_APP_API_BASE_URL + `/reservation/matching/` + no)
    //     .then(() => {
    //       console.log("삭제");
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
  created(){
    this.loaded = false;
    this.getMatchings().then((res) => {
      this.loaded = res;
    });
  }
};
</script>

<style></style>