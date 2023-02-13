<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-btn class="mr-1" variant="text" icon="mdi-arrow-left" @click="back()"></v-btn>
        <v-chip class="mr-3" color="primary">
          {{ String(boardDetail.no).padStart(4, '0') }}
        </v-chip>
        <h2>{{ boardDetail.boardTitle }}</h2>
      </div>
      <div class="pa-8 d-flex flex-column page-inner-items border-sheet-four">
        <v-sheet>
          <v-chip class="ma-2" color="light-blue-darken-2" size="large" label>
            <v-icon start icon="mdi-account-circle-outline"></v-icon>
            {{ boardDetail.userNickname }}
          </v-chip>
          <v-chip class="ma-2" color="indigo-accent-4" size="large" label>
            <v-icon start icon="mdi-star-box"></v-icon>
            <span v-if="boardDetail.boardFlag == 0">관리자</span>
            <span v-else-if="boardDetail.boardFlag == 1">전문가</span>
            <span v-else>반려인</span>
          </v-chip>
          <v-chip class="ma-2" color="light-blue-darken-4" size="large" label>
            <v-icon start icon="mdi-calendar-blank-outline"></v-icon>
            {{ boardDetail.boardWritetime }}
          </v-chip>
          <v-chip class="ma-2" color="blue-accent-4" size="large" label>
            <v-icon start icon="mdi-eye"></v-icon>
            {{ boardDetail.boardViews }}
          </v-chip>
          <v-chip class="ma-2 pr-6" color="blue-darken-3" size="large" label>
            <v-checkbox-btn false-icon="mdi-thumb-up-outline"
              true-icon="mdi-thumb-up" v-model="isHeart"
              @click="recommend"
            ></v-checkbox-btn>
            <v-label>{{ boardDetail.boardHeartnum }}</v-label>
          </v-chip>
        </v-sheet>
        <v-sheet class="ma-3">
          {{ boardDetail.boardContent }}
        </v-sheet>
        <v-sheet class="d-flex align-center ma-3" width="100%">
          <v-btn class="me-auto" rounded="pill" variant="tonal" prepend-icon="mdi-paperclip">
            {{ boardDetail.boardFile }}
          </v-btn>
          <v-sheet v-show="this.userId == this.boardDetail.userId">
            <FreeBoardModify :boardDetail="boardDetail"></FreeBoardModify>
            <!-- <v-btn class="ma-1" :rounded="0" color="primary" size="large" @click="moveModify()">수정</v-btn> -->
            <v-btn class="ma-1 mr-10" :rounded="0" color="error" size="large" @click="remove">삭제</v-btn>
          </v-sheet>
        </v-sheet>
        <v-sheet class="ma-3">
          <FreeBoardReply></FreeBoardReply>
        </v-sheet>
      </div>
    </div>
  </div>
</template>

<script>
import FreeBoardReply from "@/components/FreeBoard/FreeBoardReply.vue";
import FreeBoardModify from "@/components/FreeBoard/FreeBoardModify.vue"
import { getDetailBoard } from "@/api/communityNotice.js"
import { getHeartWhether, pushHeart, deleteBoard } from "@/api/communityFree.js"
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "FreeBoardDetail",
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  components: {
    FreeBoardReply,
    FreeBoardModify
  },
  data() {
    return {
      loaded: true,
      boardDetail: {
        // no: 5, //this.$route.params.no으로 호출하긴함
        // userId: "",
        // userNickname: "와싸피",
        // boardTitle: "제목",
        // boardContent: "컨텐츠",
        // boardFile: "파일명",
        // boardFlag: 0,
        // boardHeartnum: 1,
        // boardViews: 2,
        // boardWritetime: "2023-01-25 16:43:28",
      },
      isHeart: false
    }
  },
  methods: {
    back() {
      this.$router.go(-1)
    },
    async recommend(){
      if(this.isHeart){
        this.boardDetail.boardHeartnum -= 1;
      }
      else{
        this.boardDetail.boardHeartnum += 1;
      }
      this.isHeart = this.isHeart?false:true;
      await pushHeart(this.$route.params.no, this.userId, this.isHeart?0:1)//isHeart를 reverse한거라 isHeart 넣어주는 값도 반대
    },
    async remove(){
      await deleteBoard(this.$route.params.no)
      .then(()=>{
        this.$swal.fire(
          '글 삭제 완료',
          '게시글의 삭제가 완료되었습니다.',
          'success'
        )
        this.back()
      });
    }
  },
  async created() {
    // 게시글 상세 조회 API
    // 게시글 번호 this.$route.params.no로 조회해 boardDetail에 넣기
    this.boardDetail = await getDetailBoard(this.$route.params.no);
    this.isHeart = await getHeartWhether(this.$route.params.no, this.userId);
  }
}
</script>

<style></style>