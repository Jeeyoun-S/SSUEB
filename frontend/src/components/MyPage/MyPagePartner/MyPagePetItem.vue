<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card
      class="mr-5 mb-5 pa-2"
      variant="outlined"
      v-show="getPetInfo.length < 3"
      width="320"
      height="245"
      rounded="0"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      v-bind="props"
      @click="register()"
    >
      <template v-slot:title>새 반려동물 등록</template>
      <template v-slot:prepend>
        <v-avatar color="grey-lighten-1" size="90">
          <h1>+</h1>
        </v-avatar>
      </template>
      <template v-slot:subtitle>
        대분류<br />
        품종<br />
        생일
      </template>
      <template v-slot:text>
        <div class="pet-info">
          해당 카드를 클릭하시면 새로운 반려동물을 등록하실 수 있습니다.
        </div>
      </template>
    </v-card>
  </v-hover>
  <v-hover
    v-slot="{ isHovering, props }"
    v-for="pet in getPetInfo"
    :key="pet.no"
  >
    <v-card
      class="mr-6 mb-6 pa-2"
      variant="outlined"
      width="320"
      height="245"
      rounded="0"
      :elevation="isHovering ? 8 : 0"
      :class="{ 'on-hover': isHovering }"
      v-bind="props"
    >
      <template v-slot:title>
        {{ pet.petName }}
      </template>
      <template v-slot:prepend>
        <v-avatar color="#06BEE1" size="90">
          <!-- {{ petImagePath+pet.petImage }} -->
          <span v-if="pet.petImage == null">{{ pet.petName }}</span>
          <img v-else :src="petImagePath + pet.petImage" />
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
        <v-btn
          variant="text"
          color="error"
          rounded="0"
          @click="removePet(pet.no)"
          >삭제</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-hover>
</template>

<script>
import { modifyPetInfo, removePetInfo } from "@/api/userInfoPartner.js";
import MyPagePetModify from "@/components/MyPage/MyPagePartner/MyPagePetModify.vue";

export default {
  name: "MyPagePetItem",
  data() {
    return {
      petImagePath: process.env.VUE_APP_IMAGE_FILE_PATH_PET,
    };
  },
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
    register() {
      console.log("반려동물 등록");
    },
  },
};
</script>

<style scoped>
.pet-info {
  height: 47px;
}
</style>
