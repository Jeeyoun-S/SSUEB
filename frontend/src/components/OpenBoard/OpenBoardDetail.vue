<template>
  <v-dialog v-model="dialog" transition="dialog-bottom-transition" width="900" scrollable>
    <v-card>
      <v-toolbar
        class="pa-1 pr-3 pl-3"
        color="primary"
        title="상담 상세 내용"
      >
      </v-toolbar>
      <v-sheet class="pa-5">
        <v-table hover>
          <tbody>
            <tr>
              <td class="bold-font" width="170">상담 일시</td>
              <td width="730">{{ board.reservationDate }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">대분류</td>
              <td width="730">{{ board.petType }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">품종</td>
              <td width="730">{{ board.petVariety }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">생일</td>
              <td width="730">{{ board.petBirth }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">상담 신청 내용</td>
              <td width="730">{{ board.reservationConsultContent }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">상담 결과</td>
              <td width="730">{{ board.reservationDignosisRecord }}</td>
            </tr>
            <tr>
              <td class="bold-font" width="170">별점</td>
              <td width="730">
                <v-rating
                  v-model="board.reviewGrade"
                  density="compact" color="yellow-darken-3" readonly
                ></v-rating>
              </td>
            </tr>
            <tr>
              <td class="bold-font" width="170">리뷰</td>
              <td width="730">{{ board.reviewComment }}</td>
            </tr>
          </tbody>
        </v-table>
        <v-card-actions class="justify-end">
          <v-btn variant="text" @click="dialog= false">닫기</v-btn>
        </v-card-actions>
      </v-sheet>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "OpenBoardDetail",
  data() {
    return {
      dialog: false,
      board: null
    }
  }
}
</script>

<style>

</style>