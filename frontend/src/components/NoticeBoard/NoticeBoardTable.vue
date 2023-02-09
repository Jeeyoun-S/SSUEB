<template>
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
        <th width="920">제목</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="board in getSearchList" :key="board.no"
        @click="moveDetailBoard(board.no)" style="cursor: pointer;"
      >
        <td>{{ String(board.no).padStart(4, '0') }}</td>
        <td>{{ board.boardTitle }}</td>
      </tr>
      <NoticeBoardDetail ref="noticeBoardDetail"></NoticeBoardDetail>
    </tbody>
  </v-table>
</template>

<script>
import NoticeBoardDetail from "@/components/NoticeBoard/NoticeBoardDetail.vue"

export default {
  name: "NoticeBoardTable",
  data() {
    return {
      boardSummaryList: [
        { no: 3, boardTitle: "잘 모르겠는데 알려주실 분" },
        { no: 2, boardTitle: "와 이건 뭐냐 신기하네~~" },
        { no: 1, boardTitle: "광고광고광고광고광고광고광고광고" },
      ],
      keyword: ""
    }
  },
  components: {
    NoticeBoardDetail
  },
  computed: {
    getSearchList() {
      return this.boardSummaryList.filter(v => v.boardTitle.includes(this.keyword))
    },
  },
  methods: {
    moveDetailBoard(no) {
      this.$refs.noticeBoardDetail.boardNo = no;
      this.$refs.noticeBoardDetail.dialog = true;
    }
  }
}
</script>

<style>

</style>