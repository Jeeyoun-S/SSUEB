<template>
  <div width="260" height="250">
    <v-row class="top-row">
      <img
        class="image"
        width="80"
        :src="require('@/assets/placeholder/placeholder_person.jpg')"
      />
    </v-row>
    <br />
    <div class="text-center">
      <h3>우싸피</h3>
      <h4>반려동물훈련지도사</h4>
      <br />
      <span>
        <strong> 금액 &nbsp;&nbsp;&nbsp;</strong>
        20,000원
      </span>
      <br />
      <span>
        <strong> 이유 &nbsp;&nbsp;&nbsp;</strong>
        상담 시간 30분, <br />난이도 중으로 예상됩니다.
      </span>
    </div>
  </div>
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
