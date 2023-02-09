<template>
  <div v-for="(match, index) in sendMatchingList" :key="index">
    <v-hover v-slot="{ isHovering, props }">
      <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
        width="320" height="480" variant="outlined"
        :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
        rounded="0" v-bind="props"
      >
        <v-btn class="ms-auto align-self-end" color="error" variant="text" @click="deleteSendMatching()">
          삭제
        </v-btn>
        <v-avatar class="align-self-center" color="white" size="100">
          <img width="320" height="100" :src="require('@/assets/placeholder/placeholder_dog.png')"/>
        </v-avatar>
        <v-card-title>
          <h5>20XX - XX - XX XX : XX</h5>
        </v-card-title>
        <v-card-subtitle>
          <p>로이 (8살)</p>
          <p>강아지 - 이탈리안 그레이하운드</p>
        </v-card-subtitle>
        <v-card-text>
          우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
          귀여워도 돼요? 이거 문제있는거죠? 말이 됩니까??????? 우리 로이 좀 보세요
          세상에
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <v-row>
            <v-col class="bold-font" cols="3">금액</v-col>
            <v-col>20,000원</v-col>
          </v-row>
          <v-row>
            <v-col class="bold-font" cols="3">설명</v-col>
            <v-col>어려운 상담이 될 거 같습니다. 시간은 30분 정도 예상됩니다.</v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
export default {
  name: "SendMatchingCard",
  data() {
    return {
      sendMatchingList: [
        {},
        {},
        {},
        {},
        {},
        {},
        {},
      ]
    }
  },
  methods: {
    deleteSendMatching() {
      alert("삭제 버튼 활성화")
    }
  }
}
</script>

<style>

</style>