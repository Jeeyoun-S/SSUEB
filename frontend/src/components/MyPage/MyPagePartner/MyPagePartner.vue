<template>
  <div class="mypage">
    <div class="mypage-title border-sheet-four">
      <h2>회원 정보</h2>
    </div>
    <div class="mypage-info">
      <MyPagePartnerInfo v-if="getInfoVersion"></MyPagePartnerInfo>
      <MyPagePartnerInfoModify v-else></MyPagePartnerInfoModify>
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
import MyPagePetRegister from "@/components/MyPage/MyPagePartner/MyPagePetRegister.vue";
import MyPagePetItem from "@/components/MyPage/MyPagePartner/MyPagePetItem.vue";
import MyPagePartnerInfo from "@/components/MyPage/MyPagePartner/MyPagePartnerInfo.vue";
import MyPagePartnerInfoModify from "@/components/MyPage/MyPagePartner/MyPagePartnerInfoModify.vue";
import { getUserPartnerInfo } from "@/api/userInfoPartner.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "MyPagePartner",
  data() {
    return {
      infoVer: true,
    };
  },
  components: {
    MyPagePetRegister,
    MyPagePetItem,
    MyPagePartnerInfo,
    MyPagePartnerInfoModify,
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    getPartnerInfo() {
      return this.$store.getters.getPartnerInfo;
    },
    getInfoVersion() {
      return this.$store.getters.getInfoVersion;
    },
  },
  created() {
    if (this.getPartnerInfo.id != this.userId) {
      getUserPartnerInfo(this.userId);
    }
  },
};
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
