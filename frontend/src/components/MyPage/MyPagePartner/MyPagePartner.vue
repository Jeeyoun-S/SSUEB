<template>
  <div class="mypage">
    <div class="mypage-inner border-sheet-four">
      <div class="mypage-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-account</v-icon>
        <h2>회원 정보</h2>
      </div>
      <div class="mypage-content border-sheet-four">
        <MyPagePartnerInfo v-if="getInfoVersion"></MyPagePartnerInfo>
        <MyPagePartnerInfoModify v-else></MyPagePartnerInfoModify>
      </div>
    </div>
    <div class="mypage-inner border-sheet-four">
      <div class="mypage-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-paw</v-icon>
        <h2>반려동물 정보</h2>
        <MyPagePetRegister></MyPagePetRegister>
      </div>
      <div class="mypage-pet-item border-sheet-four">
        <MyPagePetItem></MyPagePetItem>
      </div>
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

<style></style>
