<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class=" page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-book-open-variant</v-icon>
        <h2>공개된 상담</h2>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <OpenBoardTable :openBoardList="openBoardList"></OpenBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import OpenBoardTable from '@/components/OpenBoard/OpenBoardTable.vue';
import { getOpenBoard } from '@/api/communityOpen.js';

export default {
  name: "OpenBoard",
  components: {
    NowLoading,
    OpenBoardTable
  },
  data() {
    return {
      loaded: false,
      openBoardList: []
    }
  },
  async created() {
    this.loaded = false;
    await getOpenBoard().then((res) => {
      this.openBoardList = res;
    });
    this.loaded = true;
  }
}
</script>

<style>

</style>