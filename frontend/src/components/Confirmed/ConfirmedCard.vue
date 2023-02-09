<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="ma-3 pa-3 d-flex justify-center flex-column"
      width="492"
      height="400"
      variant="outlined"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      rounded="0"
      v-bind="props"
    >
      <v-card-title class="d-flex flex-row justify-space-between">
        <h4>{{ reservation.reservationDate }}</h4>
        <div>


          <!--날짜계산-->
          <v-btn class="mr-3" rounded="pill" color="primary" disabled
            >D-2</v-btn
          >


          <v-btn rounded="pill" color="primary" @click="timealert">입장</v-btn>
        </div>
      </v-card-title>
      <div class="d-flex flex-row justify-space-around">
        <v-card class="pt-2" width="220" height="320" elevation="0">
          <div class="d-flex align-center flex-column">
            <img
              class="image"
              width="80"
              :src="require('@/assets/placeholder/placeholder_dog.png')"
            />
            <v-card-title class="pb-0">{{ reservation.petName }} ({{ reservation.petBirth }})</v-card-title>
            <v-card-subtitle>{{ reservation.petType }}</v-card-subtitle>
            <v-card-subtitle>{{ reservation.petVariety }}</v-card-subtitle>
          </div>
          <v-card-text> {{ reservation.reservationConsultContent }} </v-card-text>
        </v-card>
        <v-divider vertical></v-divider>
        <v-card class="pt-2" width="220" height="320" elevation="0">
          <div class="d-flex align-center flex-column">
            <img
              class="image"
              width="80"
              :src="require('@/assets/placeholder/placeholder_person.jpg')"
            />
            <v-card-title class="pb-0">{{ reservation.consultantName }}</v-card-title>
            <v-card-subtitle class="mb-5">반려동물훈련지도사</v-card-subtitle>
          </div>
          <v-card-text>
            <v-row>
              <v-col class="bold-font pr-0" cols="3">금액</v-col>
              <v-col class="pr-0 pl-0" cols="8">20,000원</v-col>
            </v-row>
            <v-row>
              <v-col class="bold-font pr-0 pt-0" cols="3">이유</v-col>
              <v-col class="pr-0 pl-0 pt-0" cols="9"
                >상담 시간 30분, 난이도 중으로 예상됩니다.</v-col
              >
            </v-row>
          </v-card-text>
        </v-card>
      </div>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  name: "ConfirmedCard",
  props: {
    reservation: {},
  },

  methods: {
    timealert() {
      this.$swal.fire(
        "화상 상담 입장 대기",
        "화상 상담은 10분 전부터 입장 가능합니다.",
        "warning"
      );
    },
  },
  created(){
    console.log(this.reservation);
  }
};
</script>

<style>
vl {
  border: none;
  border-left: 1px solid hsla(200, 10%, 50%, 100);
  height: 27vh;
  width: 1px;
}
.a-card {
  margin: 15px;
}
.pill-box {
  display: flex;
}
.left-pill {
  display: flex;
  justify-content: flex-end;
  padding: 10 px;
}
.right-pill {
  display: flex;
  padding: 10 px;
}
</style>
