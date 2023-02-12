<template>
  <v-dialog v-model="reviewOpen" width="700" persistent>
    <template v-slot:activator="{ props }">
      <v-btn class="ma-2" size="x-large" prepend-icon="mdi-exit-to-app"
        v-bind="props" color="primary" variant="tonal"
      >
        상담 종료
      </v-btn>
    </template>
    <v-card>
      <v-toolbar
        class="pa-2"
        color="primary"
        title="상담이 종료되었습니다."
      ></v-toolbar>
      <v-sheet class="pa-5 d-flex flex-column align-center justify-space-between" height="520">
        <v-hover v-slot="{ isHovering, props }">
          <v-card :class="{ 'on-hover': isHovering }" rounded="0" 
            class="pa-3 d-flex flex-row align-center"
            :elevation="isHovering ? 8 : 0" v-bind="props"
            variant="outlined" width="650" height="200"
          >
            <v-card class="mr-1 d-flex flex-column justify-center align-center" height="175" width="230" elevation="0">
              <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
              <v-card-title class="pa-1">
                전문가 이름
              </v-card-title>
            </v-card>
            <v-card class="d-flex flex-column" height="175" width="550" elevation="0">
              <v-card-title>상담 결과</v-card-title>
              <v-card-text>
                상담 결과는 이 칸에 보여주면 됩니다.
              </v-card-text>
            </v-card>
          </v-card>
        </v-hover>
        <v-hover v-slot="{ isHovering, props }">
          <v-card :class="{ 'on-hover': isHovering }" rounded="0" 
            class="pa-3"
            :elevation="isHovering ? 8 : 0" v-bind="props"
            variant="outlined" width="650" height="260"
          >
            <v-sheet class="d-flex align-center">
              <v-card-title>리뷰 남기기</v-card-title>
              <v-rating class="mr-2"
                v-model="review.reviewGrade" color="yellow-darken-3"
                density="compact"
              ></v-rating>
              <span>{{ review.reviewGrade }}</span>
            </v-sheet>
            <v-card-text>
              <v-textarea v-model="review.reviewComment" label="리뷰를 작성해 주세요."
                variant="outlined" counter="400" maxlength="400"
              >
              </v-textarea>
            </v-card-text>
          </v-card>
        </v-hover>
      </v-sheet>
      <v-card-item class="pt-0">
        <v-checkbox v-model="review.reservationOpen" label="상담 결과를 다른 사람들에게 공개하시겠습니까?" color="primary"
          density="compact" value=1 false-value=0
          messages="상담 신청 내용, 상담 결과, 반려동물의 대분류·품종·생일이 공개됩니다. 여러 반려인에게 많은 도움을 줄 수 있습니다."
        ></v-checkbox>
      </v-card-item>
      <v-card-actions>
        <v-btn class="mb-3" variant="text" color="primary" size="large" block>등록</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "FinishedReview",
  data() {
    return {
      reviewOpen: false,
      review: {
        reviewGrade: 0,
        reviewComment: null,
        reservationOpen: 0
      }
    }
  }
}
</script>

<style>

</style>