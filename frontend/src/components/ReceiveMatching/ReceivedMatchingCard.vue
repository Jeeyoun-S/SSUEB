<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item>
        <ReceivedMatchingCardButton></ReceivedMatchingCardButton>
      </v-card-item>
      <v-card-item class="align-self-center">
        <v-avatar class="pt-1" color="white" size="100">
          <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
        </v-avatar>
      </v-card-item>
      <v-card-title>20XX - XX - XX XX : XX</v-card-title>
      <v-card-subtitle>
        <p>로이 (8세)</p>
        <p>강아지 - 이탈리안 그레이하운드</p>
      </v-card-subtitle>
      <v-card-text>
        <div class="reservation-pet-info">
          우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
          귀여워도 돼요? 이거 문제있는거죠? 말이 됩니까??????? 우리 로이 좀 보세요
          세상에
        </div>
      </v-card-text>
      <v-card-actions class="d-flex flex-row justify-space-between">
        <v-btn variant="text" color="primary" @click="seefile">
          관련 첨부파일 보기
        </v-btn>
        <v-btn variant="text" color="error">
          삭제
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import ReceivedMatchingCardButton from "./ReceivedMatchingCardButton.vue";
import { mapState } from "vuex";
//import { apiInstance } from "@/api/index.js";
const userStore = "userStore";

export default {
  name: "ReceivedMatchingCard",
  components: { 
    ReceivedMatchingCardButton
   },
   computed: {
    ...mapState(userStore, ["userId"]),
  },

  data: () => ({
    dialog: false,
    model: null,
  }),
  props: {
    reservation:{},
  },
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
    
  }
};
</script>

<style scoped>
</style>
