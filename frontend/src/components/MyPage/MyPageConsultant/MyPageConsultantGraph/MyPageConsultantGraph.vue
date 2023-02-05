<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="mr-5 mb-5 pa-3" variant="outlined"
      width="325" height="380" rounded="0"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }" v-bind="props"
    >
      <v-sheet height="250">
        <MyPageConsultantGraphOne></MyPageConsultantGraphOne>
      </v-sheet>
      <v-card-title>상담 제안 및 확정 횟수</v-card-title>
      <v-card-text>현재까지 상담 제안 및 확정 수 총합을 전체 전문가 평균과 비교</v-card-text>
    </v-card>
  </v-hover>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="mr-5 mb-5 pa-2" variant="outlined"
      width="325" height="380" rounded="0"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }" v-bind="props"
    > 
      <v-sheet height="250">
        <MyPageConsultantGraphTwo></MyPageConsultantGraphTwo>
      </v-sheet>
      <v-card-title>최근 6개월 예약 확정 횟수</v-card-title>
      <v-card-text>취소한 예약을 제외한 지난 6개월 동안 확정된 예약 횟수</v-card-text>
    </v-card>
  </v-hover>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="mr-5 mb-5 pa-2" variant="outlined"
      width="325" height="380" rounded="0"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }" v-bind="props"
    >
      <v-sheet height="250">
        <MyPageConsultantGraphThree></MyPageConsultantGraphThree>
      </v-sheet>
      <v-card-title>최근 6개월 별점 평균</v-card-title>
      <v-card-text>상담 종료 이후 반려인이 평가한 별점 평균의 최근 6개월 데이터</v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
import MyPageConsultantGraphOne from '@/components/MyPage/MyPageConsultant/MyPageConsultantGraph/MyPageConsultantGraphOne.vue';
import MyPageConsultantGraphTwo from '@/components/MyPage/MyPageConsultant/MyPageConsultantGraph/MyPageConsultantGraphTwo.vue';
import MyPageConsultantGraphThree from '@/components/MyPage/MyPageConsultant/MyPageConsultantGraph/MyPageConsultantGraphThree.vue';

export default {
  name: "MyPageConsultantGraph",
  components: {
    MyPageConsultantGraphOne,
    MyPageConsultantGraphTwo,
    MyPageConsultantGraphThree
  },
}
</script>

<style>

</style>