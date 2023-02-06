<template>
  <v-card class="a-card" width="270" height="450" elevation="2">
    
    <div class="top-row">
      <img
        width="120"
        :src="require('@/assets/placeholder/placeholder_person.jpg')"
      />
      <v-btn variant="outlined" color="primary"> 수락 </v-btn>
    </div>

    <div class="text-center">
      <h3>우싸피</h3>
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
