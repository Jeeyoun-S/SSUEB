<template>
  <v-sheet class="mr-2" width="150">
    <v-select
      :items="['전체', '내용', '품종']"
      variant="outlined" density="compact" v-model="range"
      hide-details
    ></v-select>
  </v-sheet>
  <v-text-field
    class="mb-1"
    v-model="keyword"
    density="compact"
    variant="outlined"
    label="검색"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
  ></v-text-field>
  <v-table fixed-header hover>
    <thead>
      <tr>
        <th width="96">번호</th>
        <th width="100">대분류</th>
        <th width="920">상담 신청 내용</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(board, index) in getSearchList" :key="index"
        @click="moveDetailBoard(board)" style="cursor: pointer;"
      >
        <td>{{ String(index).padStart(4, '0') }}</td>
        <td>{{ board.petType }}</td>
        <td>
          {{ board.reservationConsultContent.substr(0, 25) }}
          <span v-show="board.reservationConsultContent.length > 25">···</span>
        </td>
      </tr>
      <OpenBoardDetail ref="openBoardDetail"></OpenBoardDetail>
    </tbody>
  </v-table>
</template>

<script>
import OpenBoardDetail from "@/components/OpenBoard/OpenBoardDetail.vue";

export default {
  name: "OpenBoardTable",
  components: {
    OpenBoardDetail
  },
  props: {
    openBoardList: Array
  },
  computed: {
    getSearchList() {
      if (this.range == '전체') {
        return this.openBoardList.filter(v => 
          v.reservationConsultContent.includes(this.keyword) ||
          v.reservationDignosisRecord.includes(this.keyword) ||
          v.reviewComment.includes(this.keyword) ||
          v.petType.includes(this.keyword) ||
          v.petVariety.includes(this.keyword)
        );
      } else if (this.range == '내용') {
        return this.openBoardList.filter(v => 
          v.reservationConsultContent.includes(this.keyword) ||
          v.reservationDignosisRecord.includes(this.keyword) ||
          v.reviewComment.includes(this.keyword)
        );
      } else {
        return this.openBoardList.filter(v => 
          v.petType.includes(this.keyword) ||
          v.petVariety.includes(this.keyword)
        );
      }
    },
  },
  data() {
    return {
      keyword: "",
      range: '전체'
    }
  },
  methods: {
    async moveDetailBoard(board) {
      this.$refs.openBoardDetail.board = board;
      this.$refs.openBoardDetail.dialog = true;
    }
  }
}
</script>

<style>

</style>