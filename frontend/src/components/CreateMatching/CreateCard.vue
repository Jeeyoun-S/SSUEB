<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="pt-3 pl-3 mr-5 mb-5 d-flex justify-center flex-column"
      width="322" height="450" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-item>
        <v-dialog v-model="dialog" width="800">
          <template v-slot:activator="{ props }">
            <v-btn variant="outlined" color="primary" v-bind="props" block>
              상담 제안 보내기
            </v-btn>
          </template>
          <v-card class="pa-5">
            <v-card-title class="text-h5">
              20XX - XX - XX XX : XX
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>

                </v-row>
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
              <v-btn color="error" variant="text" @click="dialog = false">
                취소
              </v-btn>
              <v-btn color="primary" variant="text" @click="dialog = false">
                등록
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-item>
      <v-card-item align-self="center">
        <v-avatar class="pt-1" color="white" size="100">
          <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
        </v-avatar>
      </v-card-item>
      <v-card-title class="text-h6">20XX - XX - XX XX:XX</v-card-title>
      <v-card-subtitle>
        <p>로이 (8세)</p>
        <p>강아지 - 이탈리안 그레이하운드</p>
      </v-card-subtitle>
      <v-card-text>
        <div class="reservation-pet-info">
          우리 갱얼쥐... 이제 노견인데 아직도 너무 긔 여 어 여 노견이 이렇게
          귀여워도 돼요? 이거 문제있는거죠? 말이 됩니까??????? 우리 로이 좀 보세요
          세상에
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn variant="text" color="primary" @click="seefile">
          관련 첨부파일 보기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-hover>
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
.reservation-pet-info {
  height: 90px;
}
.a-card {
  white-space: normal;
  padding: 10px;
  align-content: space-between;
}
.top-row {
  display: flex;
  justify-content: flex-end;
}
.dialog-class {
  display: flex;
  justify-self: center;
  align-self: center;
}
.jus-cen {
  display: flex;
  justify-self: center;
  justify-content: center;
}
</style>
