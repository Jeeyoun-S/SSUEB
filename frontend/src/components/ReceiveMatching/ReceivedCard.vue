<template>
  <v-card width="300" height="400" elevation="2">
    <img
      class="image"
      width="80"
      :src="require('@/assets/placeholder/placeholder_dog.png')"
    />
    <v-btn variant="outlined" color="primary" height="50">
      상담제안<br />10건
    </v-btn>
    <h3>20XX - XX - XX XX:XX</h3>
    <h4>로이 (8세)</h4>
    <p color="grey">강아지 - 이탈리안 그레이하운드</p>
    <p>
      우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
      귀여워도 돼요? 이거 문제있는거죠?
    </p>

    <v-btn variant="text" color="primary" @click="seefile">
      관련 첨부파일 보기
    </v-btn>
    <v-btn variant="text" color="red"> 삭제 </v-btn>
  </v-card>
</template>

<script>
export default {
  name: "ReceivedCard",
  methods: {
    async seefile() {
      this.$swal.fire({
        imageUrl: "https://unsplash.it/400/200",
        imageWidth: 600,
      });
    },
  },
};
</script>

<style></style>
