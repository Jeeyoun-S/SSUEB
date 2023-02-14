<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="ma-3 pa-2 d-flex justify-center flex-column"
      width="310"
      height="420"
      variant="outlined"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      rounded="0"
      v-bind="props"
      v-for="(consultant, idx) in reservationItem.matchingConsultants"
      :key="idx"
    >
      <v-sheet class="d-flex flex-row justify-end">
        <v-avatar class="mr-2 mt-3 mb-2" color="#06BEE1" size="100">
          <span v-if="consultant.consultantProfile == null">{{ consultant.consultantName }}</span>
          <img v-else :src="getImageUrl(consultant.consultantProfile)" height="100" width="100" />
          <v-tooltip
            activator="parent"
            location="bottom"
            width="300"
          >{{ consultant.consultantIntro }}</v-tooltip>
        </v-avatar>
        <div>
          <v-btn
            class="ma-3 align-self-start"
            variant="outlined"
            color="primary"
            rounded="0"
            @click="
              accept(
                reservationItem.rno,
                consultant.consultantId,
                consultant.matchingCost,
                consultant.matchingComment,
                consultant.consultantName
              )
            "
            >수락</v-btn
          >
        </div>
      </v-sheet>
      <v-card-title class="align-self-center">
        <h4>{{ consultant.consultantName }}</h4>
      </v-card-title>
      <v-card-item class="pa-0 pb-2 align-self-center">
        <v-rating
          v-model="rating"
          color="orange darken-2"
          density="compact"
        ></v-rating>
      </v-card-item>
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
import BootPay from "bootpay-js";
const userStore = "userStore";

export default {
  name: "CarouselCard",
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  props: {
    // dialog: Boolean,
    consultant: Object,
    dialogOff: Function,
    reservationItem: Object,
  },
  emits: ["dialogOff"],
  data() {
    return {
      rating: 3.5,
    };
  },
  methods: {
    async accept(
      rno,
      consultantId,
      matchingCost,
      matchingComment,
      consultantName
    ) {
      console.log(
        rno,
        consultantId,
        matchingComment,
        matchingCost,
        consultantName
      );
      const api = apiInstance();
      await api
        .put(
          process.env.VUE_APP_API_BASE_URL + `/reservation/matching/confirm`,
          null,
          {
            params: {
              reservationNo: rno,
              consultantId: consultantId,
              matchingCost: matchingCost,
              matchingReason: matchingComment,
            },
          }
        )
        .then(() => {
          this.$emit("dialogOff");
          this.$swal
            .fire({
              title: "상담 제안 수락",
              html: `상담 제안을 수락하고, 결제를 진행하시겠습니까? <br> <strong>상담날짜</strong> 
              ${this.reservationItem.reservationDate} <br> <strong>전문가</strong>
              ${consultantName} (반려동물행동지도사) <br> <strong>결제금액</strong> ${matchingCost}`,
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "primary",
              cancelButtonColor: "#d33",
              confirmButtonText: "확인",
              cancelButtonText: "취소",
            })
            .then((result) => {
              if (result.isConfirmed) {
                // Bootpay

                BootPay.request({
                  price: 1000, // 결제할 금액
                  application_id: `${process.env.VUE_APP_BOOTPAY_JS}`,
                  name: `SSEUB 상담`, // 아이템 이름,
                  phone: "(구매자 전화번호 ex) 01000000000)",
                  order_id: `${rno}`,
                })
                  .error(function (data) {
                    // 결제가 실패했을 때 호출되는 함수입니다.
                    var msg = "결제 에러입니다.: " + JSON.stringify(data);
                    alert(msg);
                    console.log(data);
                  })
                  .cancel(function (data) {
                    // 결제창에서 결제 진행을 하다가 취소버튼을 눌렀을때 호출되는 함수입니다.
                    var msg = "결제 취소입니다.: " + JSON.stringify(data);
                    alert(msg);
                    console.log(data);
                  })
                  .done(function (data) {
                    // 결제가 모두 완료되었을 때 호출되는 함수입니다.
                    this.$swal.fire({
                      title: "상담 제안 확정",
                      html: `<strong>상담날짜</strong> ${this.reservationItem.reservationDate} <br>
                        <strong>전문가</strong> ${consultantName}
                        (반려동물행동지도사) <br> <strong>반려동물</strong> ${this.reservationItem.petName} (${this.reservationItem.petType})`,
                      icon: "success",
                      showCancelButton: false,
                      confirmButtonColor: "primary",
                      confirmButtonText: "닫기",
                    });
                    console.log(data);
                  });

                //if result is confirmed
              }
            });
        })
        .catch((error) => {
          alert(error.message);
        });
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PROFILE}${img}`;
    },
  },

  mounted() {
    console.log("carousel");
  },
};
// runtime-core.esm-bundler.js?d2dd:40 [Vue warn]: Maximum recursive updates exceeded in component <VSlideGroup>. This means you have a reactive effect that is mutating its own dependencies and thus recursively triggering itself. Possible sources include component template, render function, updated hook or watcher source function.
</script>

<style></style>
