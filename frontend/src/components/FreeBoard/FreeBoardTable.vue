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
        <th width="100">번호</th>
        <th width="770">제목</th>
        <th width="300">작성자</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="board in getSearchList" :key="board.no"
        @click="moveDetailBoard(board.no)" style="cursor: pointer;"
      >
        <td>{{ String(board.no).padStart(4, '0') }}</td>
        <td>{{ board.boardTitle }}</td>
        <td>{{ board.userNickname }}</td>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
export default {
  name: "FreeBoardTable",
  props:{
    boardSummaryList: Array
  },
  data() {
    return {
      keyword: ""
    }
  },
  computed: {
    getSearchList() {
      return this.boardSummaryList.filter(v => v.boardTitle.includes(this.keyword))
    }
  },
  methods: {
    moveDetailBoard(no) {
      this.$router.push(`/free-board/detail/${no}`);
      // 조회수 높이는 API
      // no의 조회수 1 올리기
    },
  },
  created() {
    // 게시글 전체 조회 API
    // 자유 게시판 전체 목록 불러와서 this.boardSummaryList에 넣기
  }
}
</script>

<style>

</style>