<template>
    전문가 보낸 상담 확인 페이지
</template>

<script>
import axios from "axios";
export default {
  name: "SendMatching",
  data: () => ({
    matchings:[], 
  }),
  methods:{
    getMatchings(){
      axios({
        url: process.env.VUE_APP_API_BASE_URL+`/reservation/matching/consultant/`+`asd@fgh3`,
        method: "get",
      })
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
    deleteMatching(no) {
      axios
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

<style scoped>

</style>