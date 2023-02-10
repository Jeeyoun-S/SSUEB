<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="310" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-sheet class="d-flex flex-row justify-end">
        <v-avatar class="mr-2 pt-1" color="white" size="100">
          <img width="100" gradient="to top, rgba(0,0,0,.1), rgba(0,0,0,.5)" :src="require('@/assets/placeholder/placeholder_person.jpg')" />
        </v-avatar>
        <v-btn class="ma-3 align-self-start" variant="outlined" color="primary" rounded="0" @click="accept">수락</v-btn>
      </v-sheet>
      <v-card-title class="align-self-center">
        <h4>우싸피</h4>
      </v-card-title>
      <v-card-item class="pa-0 align-self-center">
        <v-rating v-model="rating" color="orange darken-2" density="compact"></v-rating>
      </v-card-item>
      <v-card-text>
        <v-sheet height="100">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam quis eros
          sit amet nisi aliquam tincidunt.
        </v-sheet>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <v-row>
            <v-col class="pb-0 bold-font" cols="3">금액</v-col>
            <v-col class="pb-0">20,000원</v-col>
          </v-row>
          <v-row>
            <v-col class="pb-0 bold-font" cols="3">설명</v-col>
            <v-col class="pb-0">어려운 상담이 될 거 같습니다. 시간은 30분 정도 예상됩니다.</v-col>
          </v-row>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  name: "CarouselCard",
  props: {
    dialog:Boolean
  },
  data() {
    return {
      rating: 3.5
    }
  },
  methods: {
    async accept() {
      this.$emit("dialog-off")
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
                timer: 5000,
                timerProgressBar: true,
                didOpen: () => {
                  this.$swal.showLoading();
                  const b = this.$swal.getHtmlContainer().querySelector("b");
                  timerInterval = setInterval(() => {
                    b.textContent = this.$swal.getTimerLeft();
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
                    html: " <strong>상담날짜</strong> 20XX-XX-XX XX:XX <br> <strong>전문가</strong> 우싸피 (반려동물행동지도사) <br> <strong>반려동물</strong> 로이 (강아지)",
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
    },
  },

  mounted() {
    console.log("carousel");
  },
};
</script>

<style scoped>
hr {
  margin: auto;
  width: 250px;
}
.a-card {
  padding: 5px;
  margin: 15px;
}
.top-row {
  display: flex;
  justify-content: flex-end;
}
</style>
