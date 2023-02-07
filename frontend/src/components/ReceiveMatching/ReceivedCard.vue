<template>
  <v-card class="a-card" width="300" height="400" elevation="2">
    <div class="top-row">
      <img 
        width="90"
        :src="require('@/assets/placeholder/placeholder_dog.png')"
      />
      <span>&nbsp;</span>
      <v-btn variant="outlined" color="primary" height="50">
        상담제안<br />10건
        <v-dialog width="1450" height="750" v-model="dialog" activator="parent">
          <v-card>
            <v-card-text>
              <span>
                <span class="big-letters">20XX - XX - XX XX : XX</span>
                &nbsp;&nbsp; <span class="big-letters">로이 (8세)</span>&nbsp;
                <span>강아지 - 이탈리안 그레이하운드</span>
              </span>
            </v-card-text>
            <br />
            <v-slide-group
              v-model="model"
              class="pa-4"
              selected-class="bg-success"
              show-arrows
            >
              <v-slide-group-item v-for="n in 10" :key="n">
                <carousel-card @dialog-off="dialog=false"> </carousel-card>
              </v-slide-group-item>
            </v-slide-group>
            <v-card-actions>
              <v-btn color="primary" block @click="dialog = false"
                >Close Dialog</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-btn>
    </div>
    <div><br /></div>

    <div class="text-center">
      <h3>20XX - XX - XX XX:XX</h3>
      <h4>로이 (8세)</h4>
      <p color="grey">강아지 - 이탈리안 그레이하운드</p>
    </div>
    <br />

    <p>
      우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
      귀여워도 돼요? 이거 문제있는거죠? 말이 됩니까??????? 우리 로이 좀 보세요
      세상에
    </p>
    <div><br /></div>

    <div class="bottom-row">
      <v-btn variant="text" color="primary" @click="seefile">
        관련 첨부파일 보기
      </v-btn>
      <v-btn variant="text" color="red"> 삭제 </v-btn>
    </div>
  </v-card>
</template>

<script>
import CarouselCard from "@/components/ReceiveMatching/CarouselCard.vue";

export default {
  components: { 
    "carousel-card" : CarouselCard,
   },
  name: "ReceivedCard",

  data: () => ({
    dialog: false,
    model: null,
  }),

  methods: {

    dialogOff() {
      
    },


    async seefile() {
      this.$swal.fire({
        imageUrl: "https://unsplash.it/400/200",
        imageWidth: 600,
      });
    },
  },

  created(){
    console.log("he")
  }
};
</script>

<style scoped>
.a-card {
  white-space: normal;
  padding: 10px;
  align-content: space-between;
}
.top-row {
    display: flex;
    justify-content: flex-end;
}
.bottom-row {
  display: flex;
  justify-content: space-between;
}
.big-letters {
  font-size: 22px;
  font-weight: lighter;
}
</style>
