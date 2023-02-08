<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item>
        <v-dialog v-model="dialog" width="600">
          <template v-slot:activator="{ props }">
            <v-btn variant="outlined" rounded="0" color="primary" v-bind="props" block>
              상담 제안 보내기
            </v-btn>
          </template>
          <v-card class="pa-5">
            <v-card-title>
              <h3>상담 제안 보내기</h3>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col class="bold-font" cols="2">날짜</v-col>
                  <v-col cols="10">20XX - XX - XX XX : XX</v-col>
                </v-row>
                <v-row>
                  <v-col class="bold-font mt-2" cols="2">이름</v-col>
                  <v-col class="mt-2" cols="10">로이 (8살)</v-col>
                </v-row>
                <v-row>
                  <v-col class="bold-font mt-2" cols="2">품종</v-col>
                  <v-col class="mt-2" cols="10">강아지 - 이탈리안 그레이하운드</v-col>
                </v-row>
                <v-row>
                  <v-col class="bold-font mt-2" cols="2">금액</v-col>
                  <v-col cols="10" align-self="center">
                    <v-text-field
                      v-model="this.matchingCost"
                      label="Price"
                      variant="outlined"
                      density="compact"
                      required
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row class="bold-font pl-3 pb-3">
                  설명
                </v-row>
                <v-row class="pl-3 pr-3">
                  <v-textarea
                    v-model="this.matchingComment"
                    label="가격 책정 이유, 상담 난이도, 예정 소요 시간 등을 작성해 주세요."
                    :rules="rules"
                    no-resize
                    clearable
                    clear-icon="mdi-close-circle"
                    counter
                    variant="outlined"
                    rows="4"
                    row-height="15"
                    maxlength="100"
                    oninput="javascript: if (this.value.length > this.maxLength) 
                    this.value = this.value.slice(0, this.maxLength);"
                  ></v-textarea>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="error" variant="text" @click="dialog = false">
                취소
              </v-btn>
              <v-btn color="primary" variant="text" @click="createMatching()">
                등록
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-item>
      <v-card-item class="align-self-center">
        <v-avatar class="pt-1" color="white" size="100">
          <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
        </v-avatar>
      </v-card-item>
      <v-card-title class="text-h6">20XX - XX - XX XX:XX</v-card-title>
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
      <v-card-actions>
        <v-btn variant="text" color="primary" @click="seefile">
          관련 첨부파일 보기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import axios from "axios";
export default {
  name: "CreateCard",

  data: () => ({
    dialog: false,
    model: null,
    rules: [(v) => v.length <= 100 || "최대 100자"],
    matchingComment: "",
    matchingCost: 0,
  }),

  methods: {
    createMatching(){
      console.log("he")
      axios.post(process.env.VUE_APP_API_BASE_URL+`/reservation/matching`,null,{
        params:{
          //createMathing에서 가져온 예약정보들이랑 현재 로그인한 아이디, 기입한 가격,이유 넣기
          consultantId: "ssafy@ssafy",
          matchingComment: this.matchingComment,
          matchingCost: this.matchingCost,
          reservationDate: "2023-02-22 22:22:22",
          reservationNo: 2,
        },
      }).then(() => {
        console.log("견적 제안 완료!")
      }).catch(error => {
        alert(error.message)
      })
    },

    async seefile() {
      this.$swal.fire({
        imageUrl: "https://unsplash.it/400/200",
        imageWidth: 600,
      });
    },
  },
};
</script>

<style scoped>
</style>
