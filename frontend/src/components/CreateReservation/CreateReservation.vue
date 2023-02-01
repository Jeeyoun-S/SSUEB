<template>
  <div>
    <v-sheet width="550" height="350" elevation="2" rounded>
      <div>
        <h3>&nbsp; 상담할 반려동물 선택하기</h3>
        <div class="text-center">
          <img
            width="100"
            :src="require('@/assets/placeholder/placeholder_dog.png')"
          />
          <h4>충직한 로이</h4>
        </div>
        <v-pagination v-model="page" :length="6"></v-pagination>
      </div>
    </v-sheet>

    <v-sheet width="550" height="350" elevation="2" rounded>
      <div class="text-center">
        <h1>시간 선택</h1>
      </div>
    </v-sheet>
  </div>

  <div>
    <v-sheet width="500" height="350" elevation="2" rounded>
      <h3>&nbsp; 상담 내용 작성하기</h3>

      <v-container fluid>
        <v-textarea
          label="상담내용 작성하기 (최대 500자)"
          :rules="rules"
          no-resize
          clearable
          clear-icon="mdi-close-circle"
          counter
          variant="outlined"
          rows="5"
          row-height="15"
          maxlength="500"
          oninput="javascript: if (this.value.length > this.maxLength) 
            this.value = this.value.slice(0, this.maxLength);"
        ></v-textarea>
      </v-container>

      <!--파일 인풋 여러개하면 화면 넘어감...
      원래 이렇게 하면 카운터로 표시되어야 하는데... 일단 패스-->
      <v-file-input
        counter
        multiple
        show-size
        small-chips
        truncate-length="15"
      ></v-file-input>
    </v-sheet>
    <v-sheet width="500" height="350" elevation="2" rounded>
      <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 상담 예약을 위해, 상담일정 확정 시점까지
        기입한 반려동물의 정보가 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 모든 반려동물행동지도사 회원에게
        공개됩니다. (필수) <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 이에 동의하십니까?
      </p>
      <v-container fluid>
        <v-radio-group v-model="radio1" inline color="primary">
          <v-radio label="동의" value="radio1-yes"></v-radio>
          <v-radio label="비동의" value="radio1-no"></v-radio>
        </v-radio-group>
        <hr />
        <br />
        <p>
          상담 제의 등록시, 이와 관련된 알람을 보내드립니다. (필수) <br />
          이에 동의하십니까?
        </p>
        <v-radio-group v-model="radio2" inline color="primary">
          <v-radio label="동의" value="radio2-yes"></v-radio>
          <v-radio label="비동의" value="radio2-no"></v-radio>
        </v-radio-group>
        <v-btn variant="flat" color="primary"> 신청하기 </v-btn>
      </v-container>
    </v-sheet>
  </div>
</template>

<script>
import { mapState } from "vuex";

const reservationStore = "reservationStore";

export default {
  name: "CreateReservation",
  computed: {
    ...mapState(reservationStore),
  },
  data: () => ({
    page: 1,
    rules: [(v) => v.length <= 500 || "최대 500자"],
    radio1: "radio1-yes",
    radio2: "radio2-yes",
  }),
};
</script>

<style scoped></style>
