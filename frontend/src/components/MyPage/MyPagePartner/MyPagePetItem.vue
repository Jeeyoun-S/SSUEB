<template>
  <div v-show="getPetInfo.length < 3">
    <MyPagePartnerTest></MyPagePartnerTest>
  </div>
  <v-hover v-slot="{ isHovering, props }" v-for="pet in getPetInfo" :key="pet.no">
    <v-card class="mr-6 mb-6 pa-2" variant="outlined"
      width="320" height="245" rounded="0"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }" v-bind="props"
    >
      <template v-slot:title>
        {{ pet.petName }}
      </template>
      <template v-slot:prepend>
        <v-avatar color="#06BEE1" size="90">
          <span v-if="pet.petImage == null">{{ pet.petName }}</span>
          <img v-else :src="getImageUrl(pet.petImage)" />
        </v-avatar>
      </template>
      <template v-slot:subtitle>
        {{ pet.petType }}<br />
        {{ pet.petVariety }}<br />
        {{ pet.petBirth }}
      </template>
      <template v-slot:text>
        <div class="pet-info">
          {{ pet.petInfo }}
        </div>
      </template>
      <v-card-actions class="pt-0">
        <MyPagePetModify :pet="pet"></MyPagePetModify>
        <v-btn variant="text" color="error" rounded="0" @click="removePet(pet.no)"
          >삭제</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import { modifyPetInfo, removePetInfo } from "@/api/userInfoPartner.js";
import MyPagePetModify from "@/components/MyPage/MyPagePartner/MyPagePetModify.vue";
import MyPagePartnerTest from "@/components/MyPage/MyPagePartner/MyPagePartnerTest.vue"

export default {
  name: "MyPagePetItem",
  computed: {
    getPetInfo() {
      return this.$store.getters.getPetInfo;
    },
  },
  components: {
    MyPagePetModify,
    MyPagePartnerTest
  },
  methods: {
    modifyPet(pet) {
      modifyPetInfo(pet);
    },
    removePet(petNo) {
      removePetInfo(petNo);
    },
    getImageUrl(img) {
      return require(`${process.env.VUE_APP_IMAGE_FILE_PATH_PET}`+img);
    }
  },
};
</script>

<style scoped>
.pet-info {
  height: 47px;
}
</style>