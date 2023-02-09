<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="320" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item class="align-self-center">
        <v-avatar class="pt-1" color="white" size="100">
          <img width="100" :src="require('@/assets/placeholder/placeholder_person.jpg')" />
        </v-avatar>
      </v-card-item>
      <v-card-title><h4>우싸피</h4></v-card-title>
      <v-divider></v-divider>
    </v-card>
  </v-hover>

  <v-card class="a-card" width="270" height="450" elevation="2">
    <div class="top-row">
      <img
        width="120"
        :src="require('@/assets/placeholder/placeholder_person.jpg')"
      />
      <v-btn variant="outlined" color="primary" @click="accept"> 수락 </v-btn>
    </div>

    <div class="text-center">
      <h3>우싸피 4.8</h3>
      <p color="grey">반려동물행동지도사</p>
    </div>
    <br />
    <div>
      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam quis eros
      sit amet nisi aliquam tincidunt.
    </div>
    <br />
    <hr />
    <br />
    <div>
      <span>
        <strong> 금액 &nbsp;&nbsp;&nbsp;</strong>
        20,000원 </span
      ><br />
      <span>
        <strong> 이유 &nbsp;&nbsp;&nbsp;</strong>
        상담 시간 30분, 난이도 중으로 예상됩니다.
      </span>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "CarouselCard",

  props: {
    dialog:Boolean
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
