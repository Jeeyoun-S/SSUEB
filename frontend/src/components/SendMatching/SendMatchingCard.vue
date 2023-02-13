<template>
<div>
    <v-hover v-slot="{ isHovering, props }">
      <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
        width="320" height="480" variant="outlined"
        :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
        rounded="0" v-bind="props"
      >
        <v-btn class="ms-auto align-self-end" color="error" variant="text" @click="deleteSendMatching()">
          삭제
        </v-btn>
        <v-avatar class="align-self-center" color="#06BEE1" size="100">
          <span v-if="matching.petImage == null">{{ matching.petName }}</span>
          <img v-else :src="getImageUrl(matching.petImage)" height="100" width="100" />
        </v-avatar>
        <v-card-title>
          <h5>{{ matching.reservationDate }}</h5>
        </v-card-title>
        <v-card-subtitle>
          <p>{{ matching.petName }} ({{ matching.petBirth }})</p>
          <p>{{ matching.petType }} <span v-show="matching.petVariety != null">-</span> {{ matching.petVariety }}</p>
        </v-card-subtitle>
        <v-card-text>
          {{ matching.reservationConsultContent }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text class="pa-2">
          <v-row class="ma-0">
            <v-col class="bold-font" cols="3">금액</v-col>
            <v-col>{{ matching.matchingCost }}</v-col>
          </v-row>
          <v-row class="ma-0">
            <v-col class="bold-font" cols="3">설명</v-col>
            <v-col>{{ matching.matchingComment }}</v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
export default {
  name: "SendMatchingCard",
  props: {
    matching: {},
  },
  methods: {
    deleteSendMatching() {
      alert("삭제 버튼 활성화")
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  }
}
</script>

<style></style>