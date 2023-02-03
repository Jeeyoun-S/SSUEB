<template>
  <v-card v-for="pet in getPetInfo" :key="pet.no" width="250" height="350">
    <template v-slot:title>
      <!-- <img v-if="pet.petImage != null" :src="require('C:/Users/SSAFY/Desktop/image/'+pet.petImage)"/> -->
      <!-- <img v-else :src="require('@/assets/profile/pet.png')"/> -->
      <br>
      {{ pet.petName }}
    </template>
    <template v-slot:subtitle>
      {{ pet.petType }} {{ pet.petVariety }}<br />
      {{ pet.petBirth }}
    </template>
    <template v-slot:text>{{ pet.petInfo }}</template>
    <v-card-actions class="pt-0">
      <MyPagePetModify :pet="pet"></MyPagePetModify>
      <v-btn variant="text" color="error" rounded="0" @click="removePet(pet.no)"
        >삭제</v-btn
      >
    </v-card-actions>
  </v-card>
</template>

<script>
import { modifyPetInfo, removePetInfo } from "@/api/userInfoPartner.js";
import MyPagePetModify from "@/components/MyPage/MyPagePartner/MyPagePetModify.vue";

export default {
  name: "MyPagePetItem",
  computed: {
    getPetInfo() {
      return this.$store.getters.getPetInfo;
    },
  },
  components: {
    MyPagePetModify,
  },
  methods: {
    modifyPet(pet) {
      modifyPetInfo(pet);
    },
    removePet(petNo) {
      removePetInfo(petNo);
    },
  },
};
</script>

<style scoped>
img {
  height: 100px;
  width: 100px;

  border-radius: 70%;
}
</style>
