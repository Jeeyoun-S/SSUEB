<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="310" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
      v-for="(consultant, idx) in reservationItem.matchingConsultants" :key="idx"
    >
      <v-sheet class="d-flex flex-row justify-end">
        <v-avatar class="mr-2 mt-5" color="#06BEE1" size="100">
          <img v-if="consultant.consultantProfile == null" width="100" gradient="to top, rgba(0,0,0,.1), rgba(0,0,0,.5)" :src="require('@/assets/placeholder/placeholder_person.jpg')" />
          <img v-else :src="getImageUrl(consultant.consultantProfile)" height="100" width="100" />
        </v-avatar>
        <div>
        <v-btn class="ma-3 align-self-start" variant="outlined" color="primary" rounded="0" @click="accept(reservationItem.rno, consultant.consultantId, consultant.matchingCost, consultant.matchingComment)">수락</v-btn>
        </div>
      </v-sheet>
      <v-card-title class="align-self-center">
        <h4>{{ consultant.consultantName }}</h4>
      </v-card-title>
      <v-card-item class="pa-0 align-self-center">
        <v-rating v-model="rating" color="orange darken-2" density="compact"></v-rating>
      </v-card-item>
      <v-card-text>
        <v-sheet height="70">
          {{ consultant.consultantIntro }}
        </v-sheet>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <v-row>
            <v-col class="pb-0 bold-font" cols="3">금액</v-col>
            <v-col class="pb-0">{{ consultant.matchingCost }}</v-col>
          </v-row>
          <v-row>
            <v-col class="pb-0 bold-font" cols="3">설명</v-col>
            <v-col class="pb-0">{{ consultant.matchingComment }}</v-col>
          </v-row>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
// const reservationStore = "reservationStore";
const userStore = "userStore";


export default {
  name: "CarouselCard",
  computed: {
    ...mapState(userStore, ["userId"]),
    // ...mapState(reservationStore),
    },
  props: {
    // dialog: Boolean,
    consultant: Object,
    dialogOff: Function,
    reservationItem: Object
  },
  emits: [
    "dialogOff"
  ],
  data() {
    return {
      rating: 3.5
    }
  },
  methods: {
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
    async accept(rno, consultantId, matchingComment, matchingCost){
      console.log(rno, consultantId, matchingComment, matchingCost)
      const api = apiInstance();
      await api.put(process.env.VUE_APP_API_BASE_URL+`/reservation/matching/confirm`,null,{
        params:{
          reservationNo: rno,
          consultantId: consultantId,
          matchingCost: matchingCost,
          matchingReason: matchingComment,
        },
      }).then(() => {
        this.$emit("dialogOff")
      this.$swal
        .fire({
          title: "상담 제안 수락",
          html: "상담 제안을 수락하고, 결제를 진행하시겠습니까? <br> <strong>상담날짜</strong> 20XX-XX-XX XX:XX <br> <strong>전문가</strong> 우싸피 (반려동물행동지도사) <br> <strong>결제금액</strong> 20000원",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "primary",
          cancelButtonColor: "#d33",
          confirmButtonText: "확인",
          cancelButtonText: "취소",
        })
        .then((result) => {
          if (result.isConfirmed) {
            let timerInterval;
            this.$swal
              .fire({
                title: "결제 진행 화면!",
                html: "이후 구현 예정",
                timer: 2500,
                timerProgressBar: true,
                didOpen: () => {
                  this.$swal.showLoading();
                  //<b> 태그에 남은 시간을 넣어주는 코드로 시간을 나타내지 않는 현재 이 코드를 넣으면 오류 메세지 발생
                  // const b = this.$swal.getHtmlContainer().querySelector("b");
                  timerInterval = setInterval(() => {
                    // b.textContent = this.$swal.getTimerLeft();
                  }, 100);
                },
                willClose: () => {
                  clearInterval(timerInterval);
                },
              })
              .then((result) => {
                if (result.dismiss === this.$swal.DismissReason.timer) {
                  this.$swal.fire({
                    title: "상담 제안 확정",
                    html: `<strong>상담날짜</strong> ${this.reservationItem.reservationDate} <br> <strong>전문가</strong> ${this.consultant.consultantName} (반려동물행동지도사) <br> <strong>반려동물</strong> ${this.reservationItem.petName} (${this.reservationItem.petType})`,
                    icon: "success",
                    showCancelButton: false,
                    confirmButtonColor: "primary",
                    confirmButtonText: "닫기",
                  });
                }
              });

            //if result is confirmed
          }
        });
      }).catch(error => {
        alert(error.message)
      })
    },

  },

  mounted() {
    console.log("carousel");
  },
};
// runtime-core.esm-bundler.js?d2dd:40 [Vue warn]: Maximum recursive updates exceeded in component <VSlideGroup>. This means you have a reactive effect that is mutating its own dependencies and thus recursively triggering itself. Possible sources include component template, render function, updated hook or watcher source function.
</script>

<style></style>
