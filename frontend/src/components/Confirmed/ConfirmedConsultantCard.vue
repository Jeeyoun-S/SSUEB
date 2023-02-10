<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
      width="492" height="380" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    > 
      <v-card-title class="d-flex flex-row justify-space-between">
        <h5>{{ reservation.reservationDate }}</h5>
        <div>
          <v-btn class="mr-2" rounded="pill" color="primary" disabled>D-2</v-btn>
          <v-btn rounded="pill" color="primary" @click="timealert">입장</v-btn>
        </div>
      </v-card-title>
      <v-sheet class="d-flex flex-row justify-start align-center" height="90">
        <v-card-item>
          <v-avatar color="white" size="80">
            <img height="80" :src="require('@/assets/placeholder/placeholder_dog.png')"/>
          </v-avatar>
        </v-card-item>
        <div>
          <v-card-title class="pa-0 pl-4">{{ reservation.petName }} ({{ reservation.petBirth }})</v-card-title>
          <v-card-subtitle>{{ reservation.petType }}</v-card-subtitle>
          <v-card-subtitle v-show="reservation.petVariety != null">{{ reservation.petVariety }}</v-card-subtitle>
        </div>
      </v-sheet>
      <v-sheet height="110">
        <v-card-text>
          {{ reservation.reservationConsultContent }}
        </v-card-text>
      </v-sheet>
      <v-divider></v-divider>
      <v-card-text>
        <v-row>
          <v-col class="pb-0 bold-font" cols="2">금액</v-col>
          <v-col class="pb-0">{{ reservation.reservationCost }}</v-col>
        </v-row>
        <v-row>
          <v-col class="pb-0 bold-font" cols="2">설명</v-col>
          <v-col>{{ reservation.reservationReason }}</v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  name: "ConfirmedConsultantCard",
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
}
</script>

<style>

</style>