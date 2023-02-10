<template>
  <v-dialog v-model="modifyOpen" width="700">
    <template v-slot:activator="{ props }">
      <v-btn variant="text" color="primary" rounded="0" v-bind="props">수정</v-btn>
    </template>
    <v-card class="pa-5">
      <v-card-title>
        <h3>반려동물 정보 수정</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form">
            <v-row>
              <v-col class="pr-10" cols="5" align-self="center">
                <v-row justify="center">
                  <!-- 새로 넣은 이미지 -->
                  <v-avatar v-if="petModifyInfo.petImage != null && petModifyInfo.petImage.length > 0" color="white" size="200">
                    <img :src="updateImageUrl">
                  </v-avatar>
                  <!-- 기존 이미지 -->
                  <v-hover v-else-if="petOriginalImage != null && !petModifyInfo.petDeleteImage" v-slot="{ isHovering, props }">
                    <v-card v-bind="props" rounded="circle" elevation="0" width="200" height="200" justify="center">
                      <img :src="getImageUrl(petOriginalImage)"/>
                      <v-overlay :model-value="isHovering" scrim="light-blue-lighten-1" class="align-center justify-center" contained>
                        <v-btn variant="flat" icon="mdi-delete" @click="petModifyInfo.petDeleteImage = true"></v-btn>
                      </v-overlay>
                    </v-card>
                  </v-hover>
                  <!-- 없는 이미지 (기존 이미지 되돌리기 O) -->
                  <v-hover v-else-if="petOriginalImage !=null" v-slot="{ isHovering, props }">
                    <v-card v-bind="props" rounded="circle" elevation="0" width="200" height="200" justify="center">
                      <img :src="require('@/assets/profile/pet.png')"/>
                      <v-overlay :model-value="isHovering" scrim="light-blue-lighten-1" class="align-center justify-center" contained>
                        <v-btn variant="flat" icon="mdi-cached" @click="petModifyInfo.petDeleteImage = false"></v-btn>
                      </v-overlay>
                    </v-card>
                  </v-hover>
                  <!-- 없는 이미지 (기존 이미지 되돌리기 X) -->
                  <v-avatar v-else color="white" size="200">
                    <img :src="require('@/assets/profile/pet.png')"/>
                  </v-avatar>
                </v-row>
                <v-row>
                  <v-file-input v-model="petModifyInfo.petImage" accept="image/png, image/jpeg"
                    prepend-icon="mdi-camera" :rules="petRules.petImage" label="사진" variant="underlined">
                  </v-file-input>
                </v-row>
              </v-col>
              <v-col>
                <v-row>
                  <v-text-field :rules="petRules.petName" v-model="petModifyInfo.petName" label="이름" variant="underlined" required></v-text-field>
                </v-row>
                <v-row>
                  <v-select :rules="petRules.petType" v-model="petModifyInfo.petType" :items="['개', '고양이', '토끼', '패럿', '기니피그', '햄스터']" label="종류" variant="underlined" required></v-select>
                </v-row>
                <v-row>
                  <v-text-field :rules="petRules.petVariety" v-model="petModifyInfo.petVariety" label="품종" variant="underlined"></v-text-field>
                </v-row>
                <v-row>
                  <v-text-field :rules="petRules.petBirth" v-model="petModifyInfo.petBirth" label="생일" variant="underlined"></v-text-field>
                </v-row>
              </v-col>
            </v-row>
            <v-row>
              <v-textarea :rules="petRules.petInfo" v-model="petModifyInfo.petInfo" label="특이사항" variant="underlined" counter="40"></v-textarea>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" variant="text" @click="modifyOpen = false">취소</v-btn>
        <v-btn color="primary" variant="text" @click="validate(pet)">수정</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { modifyPetInfo } from '@/api/userInfoPartner.js'

export default {
  name: 'MyPagePetModify',
  data() {
    return {
      modifyOpen: false,
      petModifyInfo: {
        petBirth: null,
        petImage: null,
        petInfo: null,
        petName: null,
        petType: null,
        petVariety: null,
        petDeleteImage: false
      },
      petOriginalImage: null,
      overlay: false,
      updateImageUrl: null
    }
  },
  computed: {
    petRules() {
      return this.$store.getters.getPetRule;
    }
  },
  props: {
    pet: Object
  },
  methods: {
    modifyPet(pet) {
      modifyPetInfo(pet);
    },
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid) {
        await modifyPetInfo(this.petModifyInfo, this.pet.no)
        .then((res) => {
          this.modifyOpen = res;
        });
      }
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  watch: {
    petModifyInfo: {
      handler() {
        if (this.petModifyInfo.petImage != null && this.petModifyInfo.petImage.length == 1) {
          this.updateImageUrl = URL.createObjectURL(this.petModifyInfo.petImage[0]);
        
          // 파일 이름 가져오기
          var filename = this.petModifyInfo.petImage[0].name;

          // 확장자
          var extension = filename
            .substring(filename.lastIndexOf("."), filename.length)
            .toLowerCase();

          // 파일 이름에서 확장자 없애기
          filename = filename.substring(0, filename.lastIndexOf("."));

          // 파일 이름 줄이기
          Object.defineProperty(
            this.petModifyInfo.petImage[0],
            "name",
            {
              writable: true,
              value: filename.substr(0, 10) + extension,
            }
          );
        }
      },
      deep: true
    }
  },
  created() {
    this.petModifyInfo.petBirth = this.pet.petBirth;
    this.petModifyInfo.petInfo = this.pet.petInfo;
    this.petModifyInfo.petName = this.pet.petName;
    this.petModifyInfo.petType = this.pet.petType;
    this.petModifyInfo.petVariety = this.pet.petVariety;
    this.petOriginalImage = this.pet.petImage;
  }
}
</script>

<style scoped>
img {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
</style>