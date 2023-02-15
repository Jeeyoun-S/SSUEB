<template>
  <v-dialog
    transition="dialog-bottom-transition"
    width="700"
  >
    <template v-slot:activator="{ props }">
      <v-btn color="primary" v-bind="props">
        반려동물 정보 및 상담 신청 내용 보기
      </v-btn>
    </template>
    <template v-slot:default="{ isActive }">
      <v-card>
        <v-toolbar class="pr-2 pl-2"
          color="primary"
          title="반려동물 정보 및 상담 신청 내용"
        ></v-toolbar>
        <v-sheet class="pa-5">
          <v-card-item>
            <v-sheet class="d-flex flex-row align-center">
              <img width="120" height="120" :src="require('@/assets/placeholder/placeholder_dog.png')" />
              <v-sheet class="mr-5 ml-5" width="520">
                <v-row>
                  <v-col cols="5">
                    <v-row class="ma-0">
                      <v-col class="pa-0 bold-font" cols="4">이름</v-col>
                      <v-col class="pa-0" cols="7">까비</v-col>
                    </v-row>
                    <v-row class="ma-0">
                      <v-col class="pa-0 bold-font" cols="4">대분류</v-col>
                      <v-col class="pa-0" cols="7">고양이</v-col>
                    </v-row>
                    <v-row class="ma-0">
                      <v-col class="pa-0 bold-font" cols="4">품종</v-col>
                      <v-col class="pa-0" cols="7">코리안 숏헤어</v-col>
                    </v-row>
                    <v-row class="ma-0">
                      <v-col class="pa-0 bold-font" cols="4">생일</v-col>
                      <v-col class="pa-0" cols="7">2022-02</v-col>
                    </v-row>
                  </v-col>
                  <v-col>
                    <v-row class="ma-0 bold-font">특이사항</v-row>
                    <v-row class="ma-0">특이사항을 입력하는 곳</v-row>
                  </v-col>
                </v-row>
              </v-sheet>
              <!-- <v-sheet>
                <p><b>특이사항</b></p>
                <p>특이사항을 입력하는 곳</p>
              </v-sheet> -->
            </v-sheet>
          </v-card-item>
          <v-divider class="ma-3"></v-divider>
          <v-card-title>상담 신청 내용</v-card-title>
          <v-card-text>
            상담 신청 내용을 보여주는 곳
          </v-card-text>
          <v-divider class="ma-3"></v-divider>
          <v-card-title>관련 첨부 파일</v-card-title>
          <v-card-text>
            관련 첨부 파일을 보여주는 곳
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn
              variant="text"
              @click="isActive.value = false"
            >닫기</v-btn>
          </v-card-actions>
        </v-sheet>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "PetInfoButton"
}
</script>

<style>

</style>