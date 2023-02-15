<template>
  <v-divider class="mt-3 mb-3"></v-divider>
  <v-text-field label="댓글 작성하기" variant="underlined"
    :rules="replyRule" append-inner-icon="mdi-plus"
    @click:append-inner="registReply()"
    v-on:keyup.enter="registReply()"
    v-model="replyContent"
  ></v-text-field>
  <v-table fixed-header hover>
    <thead>
      <tr>
        <th width="100">번호</th>
        <th width="900">내용</th>
        <th width="200">작성자</th>
        <th width="280">작성일시</th>
        <th width="90"></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(reply, index) in replyList" :key="reply.no">
        <td>{{ String(index+1).padStart(2, '0') }}</td>
        <td>{{ reply.replyContent }}</td>
        <td>{{ reply.userNickname }}</td>
        <td>{{ reply.replyWritetime }}</td>
        <td>
          <v-btn v-show="this.userId == reply.userId || userAuth == 'ROLE_ADMIN'" @click="remove(reply.no, index)"
          variant="text" icon="mdi-delete" color="red-lighten-2"></v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
import { mapState } from "vuex";
const userStore = "userStore";
import { getReply, createReply, removeReply } from "@/api/communityReply.js"

export default {
  name: "FreeBoardReply",
  computed: {
    ...mapState(userStore, ["userId", "userInfo", "userAuth"]),
  },
  data() {
    return {
      replyList: [
        // { no: 1, userNickname: "id@di.com", replyContent: "댓글 달아요~", replyWritetime: "2023-01-25 16:43:28" },
        // { no: 2, userNickname: "id@di.com", replyContent: "댓글 달아요~", replyWritetime: "2023-01-25 16:43:28" },
        // { no: 3, userNickname: "id@di.com", replyContent: "댓글 달아요~", replyWritetime: "2023-01-25 16:43:28" },
        // { no: 4, userNickname: "id@di.com", replyContent: "댓글 달아요~", replyWritetime: "2023-01-25 16:43:28" }
      ],
      replyRule: [
        (v) => v != null || v.length > 1 || "내용을 입력해 주세요.",
        (v) => v.length < 200 || "최대 200자까지 입력 가능합니다."
      ],
      replyContent: null,
    }
  },
  methods: {
  async registReply() {
      var userNickname = "";
      if (this.userAuth == 'ROLE_CONSULTANT') userNickname = this.userInfo.userName;
      else userNickname = this.userInfo.userNickname;
      
      this.replyList.push(await createReply(this.$route.params.no, this.replyContent, this.userId, userNickname));
      
      const date = new Date();
      this.replyList[this.replyList.length-1].replyWritetime = 
          date.getFullYear()+"-" + (date.getMonth()+1+'').padStart(2,'0') + "-"+date.getDate()+" "+
          date.getHours()+":"+date.getMinutes()+":"+(date.getSeconds()+'').padStart(2,'0');
      this.replyContent = null;
    },
    async remove(no, index){
      await removeReply(no)
      .then(()=>{
        this.replyList.splice(index,1)
      });
    }
  },
  async created() {
    this.replyList = await getReply(this.$route.params.no);
  },
  
}
</script>

<style></style>