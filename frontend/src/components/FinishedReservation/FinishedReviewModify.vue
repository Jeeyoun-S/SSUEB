<template>
  <v-dialog v-model="reviewOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn class="pr-5 pl-5" rounded="pill" v-bind="props"
        prepend-icon="mdi-pencil" color="primary" variant="tonal"
      >수정</v-btn>
    </template>
    <v-card>
      <v-toolbar
        class="pr-2 pl-2"
        color="primary"
        title="리뷰 수정"
      ></v-toolbar>
      <v-card-item>
        <v-sheet class="mt-2 mb-2 d-flex align-center">
          <v-card-title>별점</v-card-title>
          <v-rating class="mr-2 ml-2"
            v-model="review.reviewGrade" color="yellow-darken-3"
            density="compact"
          ></v-rating>
          <span>{{ review.reviewGrade }}</span>
        </v-sheet>
      </v-card-item>
      <v-card-text>
        <v-textarea v-model="review.reviewComment" label="리뷰를 작성해 주세요."
          variant="outlined" counter="400" maxlength="400" hide-details
        >
        </v-textarea>
      </v-card-text>
      <v-card-item class="pt-0">
        <v-checkbox v-model="review.reservationOpen" label="상담 결과를 다른 사람들에게 공개하시겠습니까?" color="primary"
          density="compact" value=1 false-value=0
          messages="상담 신청 내용, 상담 결과, 반려동물의 대분류·품종·생일이 공개됩니다. 여러 반려인에게 많은 도움을 줄 수 있습니다."
        ></v-checkbox>
      </v-card-item>
      <v-card-actions class="justify-end">
        <v-btn class="mb-3" variant="text" color="error" size="large" @click="reviewOpen = false">취소</v-btn>
        <v-btn class="mb-3" variant="text" color="primary" size="large" @click="modifyReview">등록</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { registReview } from "@/api/meetingFinish.js";

export default {
  name: "FinishedReviewModify",
  data() {
    return {
      reviewOpen: false,
      review: {
        no: 0,
        reviewGrade: 0,
        reviewComment: null,
        reservationOpen: 0
      }
    }
  },
  props: {
    rno: Number,
    reviewGrade: Number,
    reviewComment: String,
    idx: Number
  },
  methods: {
    async modifyReview() {
      await registReview(this.review)
      .then((res) => {
        if (res) {
          this.reviewOpen = false;
          this.$swal.fire(
            '리뷰 수정 성공',
            '리뷰가 수정되었습니다.',
            'success'
          )
          const review = this.review;
          review["idx"] = this.idx;
          this.$emit("updateReview", review);
        } else {
          this.reviewOpen = false;
          this.$swal.fire(
            '리뷰 수정 실패',
            '리뷰 수정에 실패했습니다. 다시 시도해 주시기 바랍니다.',
            'error'
          );
          this.reviewOpen = true;
        }
      });
    }
  },
  created() {
    this.review.reviewGrade = this.reviewGrade;
    this.review.reviewComment = this.reviewComment;
    this.review.no = this.rno;
  }
}
</script>

<style>

</style>