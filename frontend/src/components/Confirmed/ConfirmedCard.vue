<template>
  <v-card width="550" height="400" elevation="2">
    <br />
        <h3 align="center">20XX - XX - XX XX:XX</h3>
    <v-row> 
      <v-col align="right">
        <v-btn
      rounded="pill"
      color="black"
      disabled
    >
      D-2
    </v-btn>
      </v-col>
      <v-col>
        <v-btn
      rounded="pill"
      color="primary"
      @click="timealert"
    >
      입장하기
    </v-btn>
      </v-col>
    </v-row>
    <br /><br />
    <v-row>
      <v-col align = "center">
        <img
          class="image"
          width="80"
          :src="require('@/assets/placeholder/placeholder_dog.png')"
        />
        <h3>로이 (8세)</h3>
        <h4>강아지 - 이탈리안 그레이하운드</h4>
        <br />
        <p>로렌 입숨 어쩌구</p>
      </v-col>
      <hr />
      <v-col align = "center">
        <img
          class="image"
          width="80"
          :src="require('@/assets/placeholder/placeholder_person.jpg')"

        />
        <h3>우싸피</h3>
        <h4>반려동물훈련지도사</h4>
        <br />
        <p>로렌 입숨 절씨구</p>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  name: "ConfirmedCard",
  methods :
  {
    timealert() {
      this.$swal.fire(
        '화상 상담 입장 대기',
  '화상 상담은 10분 전부터 입장 가능합니다.',
  'warning'
      )
    }
  },
};
</script>

<style>
hr{ 
  
  border:         none;
  border-left:    1px solid hsla(200, 10%, 50%,100);
  height:         27vh;
  width:          1px;       
}
</style>
