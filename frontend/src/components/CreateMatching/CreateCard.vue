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
                  <v-col cols="10">{{ reservation.reservationDate.substr(0, 16) }}</v-col>
                </v-row>
                <v-row>
                  <v-col class="bold-font mt-2" cols="2">이름</v-col>
                  <v-col class="mt-2" cols="10">{{ reservation.petName }} ({{ reservation.petBirth }})</v-col>
                </v-row>
                <v-row>
                  <v-col class="bold-font mt-2" cols="2">품종</v-col>
                  <v-col class="mt-2" cols="10">{{ reservation.petType }} <span v-show="reservation.petVariety != null">-</span> {{ reservation.petVariety }}</v-col>
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
                      type="number"
                      :rules="rules.matchingCost"
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
                    :rules="rules.matchingComment"
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
        <v-avatar color="#06BEE1" size="100">
          <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
          <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
        </v-avatar>
      </v-card-item>
      <v-card-title class="text-h6">{{ reservation.reservationDate.substr(0, 16) }}</v-card-title>
      <v-card-subtitle>
        <p>{{ reservation.petName }} ({{ reservation.petBirth }})</p>
        <p>{{ reservation.petType }} <span v-show="reservation.petVariety != null">-</span> {{ reservation.petVariety }}</p>
      </v-card-subtitle>
      <v-card-text>
        <div class="reservation-pet-info">
          {{ reservation.reservationConsultContent }}
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
import router from "@/router/index.js";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";


export default {
  name: "CreateCard",
  computed: {
    ...mapState(userStore, ["userId"]),
    },

  data: () => ({
    dialog: false,
    model: null,
    rules: {
        matchingCost: [
          (v) => !!v || "필수 입력 사항입니다.",
          (v) => /^[0-9]*$/.test(v) || '숫자만 입력 가능합니다.',
        ],
        matchingComment: [
          (v) => !!v || "필수 입력 사항입니다.",
          (v) => v.length <= 100 || "최대 100자"
        ],
      },
    matchingComment: "",
    matchingCost: 0,
  }),

  props: {
    reservation:{},
  },

  methods: {
    async createMatching(){
      console.log("he")
      const api = apiInstance();
      await api.post(process.env.VUE_APP_API_BASE_URL+`/reservation/matching`,null,{
        params:{
          //createMathing에서 가져온 예약정보들이랑 현재 로그인한 아이디, 기입한 가격,이유 넣기
          consultantId: this.userId,
          matchingComment: this.matchingComment,
          matchingCost: this.matchingCost,
          reservationDate: this.reservation.reservationDate,
          reservationNo: this.reservation.rno,
        },
      }).then(() => {
        this.dialog=false
        this.$swal.fire({
          title : '상담 제안 완료',
          text : '반려인에게 상담 제안을 보냈습니다.',
          icon : 'success'
        })
        console.log("견적 제안 완료!");
        router.push("/send-matching");
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
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
};
</script>

<style></style>