<template>
  <v-icon color="orange darken-2">mdi-paperclip</v-icon>
  <v-sheet>
    <div class="text-center">
    <v-pagination
      v-model="page"
      :length="6"
    ></v-pagination>
  </div>
  </v-sheet>
  <v-sheet>
    <h3>상담 내용 작성하기</h3>

    <v-container fluid>
      <v-textarea
        clearable
        clear-icon="mdi-close-circle"
        counter
        label="Text"
        :rules="rules"
        :value="value"
      ></v-textarea>
  </v-container>

    <v-file-input
      counter
      small-chips
      multiple
      label="관련 영상 또는 이미지 첨부        최대 100MB"
    ></v-file-input>
    
  </v-sheet>
  <DatepickerSheet></DatepickerSheet>
  <v-sheet>
    <p>
      상담 예약을 위해, 상담일정 확정 시점까지 기입한 반려동물의 정보가 모든 반려동물행동지도사 회원에게 공개됩니다. (필수)
      이에 동의하십니까?
    </p>
    <v-container fluid>
    <v-radio-group
      v-model="radio1"
      row
    >
      <v-radio
        label="동의"
        value="radio1-yes"
      ></v-radio>
      <v-radio
        label="비동의"
        value="radio1-no"
      ></v-radio>
    </v-radio-group>
    <hr>
    <p>
      상담 제의 등록시, 이와 관련된 알람을 보내드립니다. (필수)
      이에 동의하십니까?
    </p>
    <v-radio-group
      v-model="radio2"
      row
    >
      <v-radio
        label="동의"
        value="radio2-yes"
      ></v-radio>
      <v-radio
        label="비동의"
        value="radio2-no"
      ></v-radio>
    </v-radio-group>
  </v-container>
  </v-sheet>
</template>

<script>
import { mapState } from "vuex";
import DatepickerSheet from "./DatepickerSheet.vue";

const reservationStore = "reservationStore";

export default {
    name: "CreateReservation",
    computed: {
        ...mapState(reservationStore),
    },
    data: () => ({
        rules: [v => v.length <= 500 || "최대 500자"],
        value: "상담내용 작성하기 (최대 500자)",
        radio1: 'radio1-yes',
        radio2: 'radio2-yes',
    }),
    components: { DatepickerSheet }
};
</script>

<style>

</style>