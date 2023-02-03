<template>
  <div class="mypage">
    <div class="mypage-title border-sheet-four">
      <h2>회원 정보</h2>
    </div>
    <div class="mypage-info">
      <MyPagePartnerInfo></MyPagePartnerInfo>
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
import MyPagePartnerInfo from '@/components/MyPage/MyPagePartnerInfo.vue'
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
    MyPagePetItem,
    MyPagePartnerInfo
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

<style>
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