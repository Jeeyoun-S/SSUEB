<template>
  <v-dialog v-model="modifyOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn class="pr-5 pl-5" rounded="pill" v-bind="props"
        prepend-icon="mdi-pencil" color="primary" variant="tonal"
      >수정</v-btn>
    </template>
    <v-card>
      <v-toolbar
        class="pr-2 pl-2"
        color="primary"
        title="상담 결과 수정"
      ></v-toolbar>
      <v-sheet class="pa-5">
        <v-card-text>
          <v-textarea v-model="recordModify.reservationDignosisRecord" label="상담 결과를 작성해 주세요."
            variant="outlined" counter="400" maxlength="400" hide-details
          >
          </v-textarea>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn class="mb-3" variant="text" color="error" @click="modifyOpen = false">취소</v-btn>
          <v-btn class="mb-3" variant="text" color="primary" @click="modifyResult">등록</v-btn>
        </v-card-actions>
      </v-sheet>
    </v-card>
  </v-dialog>
</template>

<script>
import { registDignosis } from "@/api/meetingFinish.js";

export default {
  name: "FinishedResultModify",
  props: {
    record: String,
    no: Number,
    idx: Number
  },
  data() {
    return {
      recordModify: {
        no: null,
        reservationDignosisRecord: null
      },
      modifyOpen: false,
    }
  },
  created() {
    this.recordModify.reservationDignosisRecord = this.record;
    this.recordModify.no = this.no;
  },
  methods: {
    async modifyResult() {
      await registDignosis(this.recordModify)
      .then((res) => {
        if (res) {
          this.modifyOpen = false;
          this.$swal.fire(
            '상담 결과 수정 성공',
            '상담 결과가 수정되었습니다.',
            'success'
          )
          const dignosis = this.recordModify;
          dignosis["idx"] = this.idx;
          this.$emit("updateRecord", dignosis);
        } else {
          this.modifyOpen = false;
          this.$swal.fire(
            '상담 결과 수정 실패',
            '상담 결과 수정에 실패했습니다. 다시 시도해 주시기 바랍니다.',
            'error'
          );
          this.modifyOpen = true;
        }
      });
    }
  }
}
</script>

<style>

</style>