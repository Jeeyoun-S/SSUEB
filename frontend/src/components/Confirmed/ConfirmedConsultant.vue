<template>
  <v-card class="pt-2" width="220" height="320" elevation="0">
    <div class="d-flex align-center flex-column">
      <img
        class="image"
        width="80"
        :src="require('@/assets/placeholder/placeholder_person.jpg')"
      />
      <v-card-title class="pb-0">우싸피</v-card-title>
      <v-card-subtitle class="mb-5">반려동물훈련지도사</v-card-subtitle>
    </div>
    <v-card-text>
      <v-row>
        <v-col class="bold-font pr-0" cols="3">금액</v-col>
        <v-col class="pr-0 pl-0" cols="8">20,000원</v-col>
      </v-row>
      <v-row>
        <v-col class="bold-font pr-0 pt-0" cols="3">이유</v-col>
        <v-col class="pr-0 pl-0 pt-0" cols="9">상담 시간 30분, 난이도 중으로 예상됩니다.</v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "ConfirmedConsultant",
};
</script>

<style scoped>
.top-row {
  display: flex;
  justify-content: space-around;
}
</style>
