<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="mypage">
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
import NowLoading from "@/views/NowLoading.vue";
import { getUserPartnerInfo } from "@/api/userInfoPartner.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "MyPagePartner",
  data() {
    return {
      infoVer: true,
      loaded: false
    };
  },
  components: {
    MyPagePetRegister,
    MyPagePetItem,
    MyPagePartnerInfo,
    MyPagePartnerInfoModify,
    NowLoading
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
  async mounted() {
    this.loaded = false;
    // if (this.getPartnerInfo.id != this.userId) {
      try {
        await getUserPartnerInfo(this.userId);
      } catch (e) {
        console.error("# 회원정보 조회 오류", e);
      }
    // }
    this.loaded = true;
  },
};
</script>

<style></style>
