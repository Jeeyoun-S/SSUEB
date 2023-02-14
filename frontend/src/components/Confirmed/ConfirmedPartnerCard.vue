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
          <v-btn rounded="pill" color="primary" @click="moveRoom">입장</v-btn>
        </div>
      </v-card-title>
      <div class="d-flex flex-row justify-space-around">
        <v-card class="pt-2" width="220" height="320" elevation="0">
          <div class="d-flex align-center flex-column">
            <v-avatar color="#06BEE1" size="80">
              <span v-if="reservation.petImage == null">{{
                reservation.petName
              }}</span>
              <img
                v-else
                :src="getImageUrl(reservation.petImage)"
                height="100"
                width="100"
              />
            </v-avatar>
            <v-card-title class="pb-0">{{ reservation.petName }}</v-card-title>
            <v-card-subtitle
              >{{ reservation.petType }}({{
                reservation.petBirth
              }})</v-card-subtitle
            >
            <v-card-subtitle v-show="reservation.petVariety != null">
              {{ reservation.petVariety }}
            </v-card-subtitle>
          </div>
          <v-card-text>
            {{ reservation.reservationConsultContent }}
          </v-card-text>
          <v-card-actions>
            <SeeAttatchedFiles :reservation="reservation" />
          </v-card-actions>
        </v-card>
        <v-divider vertical></v-divider>
        <v-card class="pt-2" width="220" height="320" elevation="0">
          <div class="d-flex align-center flex-column">
            <v-avatar color="#06BEE1" size="80">
              <img
                v-if="reservation.consultantProfile == null"
                class="image"
                width="80"
                :src="require('@/assets/placeholder/placeholder_person.jpg')"
              />
              <img
                v-else
                :src="getImageUrl(reservation.consultantProfile)"
                height="80"
                width="80"
              />
            </v-avatar>
            <v-card-title class="pb-0">{{
              reservation.consultantName
            }}</v-card-title>
            <v-card-subtitle class="mb-5">반려동물훈련지도사</v-card-subtitle>
          </div>
          <v-card-text>
            <v-row>
              <v-col class="bold-font pr-0" cols="3">금액</v-col>
              <v-col class="pr-0 pl-0" cols="8">{{
                reservation.reservationCost
              }}</v-col>
            </v-row>
            <v-row>
              <v-col class="bold-font pr-0 pt-0" cols="3">이유</v-col>
              <v-col class="pr-0 pl-0 pt-0" cols="9">{{
                reservation.reservationReason
              }}</v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </div>
    </v-card>
  </v-hover>
</template>

<script>
import SeeAttatchedFiles from "../SeeAttachedFiles/SeeAttatchedFiles.vue";

export default {
  name: "ConfirmedPartnerCard",
  props: {
    reservation: {},
  },
  components: {
    SeeAttatchedFiles,
  },
  methods: {
    moveRoom() {
      //유효성검사후 없으면 적절한 alert띄우기
      this.$router.push("meeting-room");
      // this.roomInitialize();
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  created() {
    console.log(this.reservation);
  },
};
</script>

<style></style>
