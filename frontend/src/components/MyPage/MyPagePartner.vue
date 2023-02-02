<template>
  <div class="mypage">
    <div class="mypage-title border-sheet-four">
      <h2>회원 정보</h2>
    </div>
    <div class="mypage-info">
      <v-sheet class="mypage-info-item" height="400" width="500" elevation="2" rounded>
        <v-text-field
          v-model="getPartnerInfo.id"
          label="이메일"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="getPartnerInfo.userName"
          label="이름"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="getPartnerInfo.userPhone"
          label="휴대폰 번호"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="getPartnerInfo.userNickname"
          label="닉네임"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-radio-group v-model="getPartnerInfo.userAlertFlag" color="primary" density="compact" inline readonly>
          <v-label>알림방법</v-label>
          <v-radio label="카카오톡" value="0"></v-radio>
          <v-radio label="이메일" value="1"></v-radio>
          <v-radio label="문자" value="2"></v-radio>
        </v-radio-group>
        <!-- <v-btn variant="outlined" color="primary" rounded="0" block>로그아웃</v-btn>
        <v-btn variant="outlined" color="error" rounded="0" block>회원탈퇴</v-btn>
        <v-btn variant="outlined" rounded="0" block>회원 정보 수정</v-btn> -->
      </v-sheet>
      <v-sheet class="mypage-info-item" height="300" width="500" elevation="2" rounded>
        
      </v-sheet>
    </div>
    <div class="mypage-title border-sheet-four">
      <h2>반려동물 정보 <MyPagePetRegister></MyPagePetRegister></h2>
    </div>
    <div class="mypage-pet-item">
      <MyPagePetItem></MyPagePetItem>
    </div>
  </div>
</template>

<script>
import MyPagePetRegister from '@/components/MyPage/MyPagePetRegister.vue'
import MyPagePetItem from '@/components/MyPage/MyPagePetItem.vue'
import { getUserPartnerInfo } from '@/api/userInfoPartner.js'
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: 'MyPagePartner',
  data() {
    return {
      userInfo: {
        id: "ssafy@ssafy.com",
        userName: "김싸피",
        userPhone: "010-8888-8888",
        userNickname: "인싸피",
        userAlertFlag: "0"
      }
    }
  },
  components: {
    MyPagePetRegister,
    MyPagePetItem
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
  },
  created() {
    if (this.getPartnerInfo.id != this.userId) {
      getUserPartnerInfo(this.userId);
    }
  }
}
</script>

<style scoped>
.mypage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;

  width: 1070px;
}
.mypage .mypage-title {
  width: 1070px;
  height: 50px;
}
.mypage .mypage-info {
  width: 100%;
  height: 400px;

  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
}
.mypage .mypage-info .mypage-info-item {
  padding: 20px;
}

.mypage .mypage-pet-item {
  width: 1070px;

  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>