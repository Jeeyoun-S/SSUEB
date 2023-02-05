<template>
  <v-card class="a-card" width="300" height="400" elevation="2">
    <div class="top-row">
      <img
        width="90"
        :src="require('@/assets/placeholder/placeholder_dog.png')"
      />
      <span>&nbsp;</span>

      <v-row justify="center">
    <v-dialog
      v-model="dialog"
    >
      <template v-slot:activator="{ props }">
        <v-btn variant="outlined" color="primary" height="50" v-bind="props">
            상담제안<br />보내기
          </v-btn>
      </template>

      <v-card>
        <v-card-title>
          <span class="text-h5">상담 제안하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <div>
                <strong>날짜</strong>&nbsp;&nbsp; 20XX - XX - XX XX : XX <br />
              <strong>이름</strong>&nbsp;&nbsp; 로이 (8세)<br />
              <strong>품종</strong>&nbsp;&nbsp; 강아지 - 이탈리안 그레이하운드
              <br />
            </div>
            <strong>금액</strong>&nbsp;&nbsp;
                <v-text-field
                  label="Price *"
                  required
                ></v-text-field>
                <strong>금액 책정 이유</strong>&nbsp;&nbsp;<br />
              <v-textarea
                label="Reason"
                :rules="rules"
                no-resize
                clearable
                clear-icon="mdi-close-circle"
                counter
                variant="outlined"
                rows="4"
                row-height="15"
                maxlength="100"
                oninput="javascript: if (this.value.length > this.maxLength) 
            this.value = this.value.slice(0, this.maxLength);"
              ></v-textarea>

          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue-darken-1"
            variant="text"
            @click="dialog = false"
          >
            취소
          </v-btn>
          <v-btn
            color="blue-darken-1"
            variant="text"
            @click="dialog = false"
          >
            등록
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>

    </div>
    <div><br /></div>

    <div class="text-center">
      <h3>20XX - XX - XX XX:XX</h3>
      <h4>로이 (8세)</h4>
      <p color="grey">강아지 - 이탈리안 그레이하운드</p>
    </div>
    <br />

    <p>
      우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
      귀여워도 돼요? 이거 문제있는거죠? 말이 됩니까??????? 우리 로이 좀 보세요
      세상에
    </p>
    <div><br /></div>

    <div>
      <v-btn variant="text" color="primary" @click="seefile">
        관련 첨부파일 보기
      </v-btn>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "CreateCard",

  data: () => ({
    dialog: false,
    model: null,
    rules: [(v) => v.length <= 100 || "최대 100자"],
  }),

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

<style scoped>
.a-card {
  white-space: normal;
  padding: 10px;
  align-content: space-between;
}
.top-row {
  display: flex;
  justify-content: flex-end;
}
</style>
