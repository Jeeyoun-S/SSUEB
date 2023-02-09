<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-alert</v-icon>
        <h2>공지사항</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <NoticeBoardTable></NoticeBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import NoticeBoardTable from "@/components/NoticeBoard/NoticeBoardTable.vue"

export default {
  name: "NoticeBoard",
  components: {
    NoticeBoardTable
  }
}
</script>

<style>

</style>