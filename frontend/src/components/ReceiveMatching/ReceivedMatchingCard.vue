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
import ReceivedMatchingCardButton from "./ReceivedMatchingCardButton.vue";

export default {
  name: "ReceivedMatchingCard",
  components: { 
    ReceivedMatchingCardButton
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
