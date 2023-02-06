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
                  <img :src="require('@/assets/profile/pet.png')"/>
                </v-row>
                <v-row>
                  <v-file-input v-model="petModifyInfo.petImage" accept="image/png, image/jpeg"
                    prepend-icon="mdi-camera" :rules="petRules.petImage" label="사진" variant="underlined">
                  </v-file-input>
                  <v-btn icon color="info" variant="text" size="small" @click="deleteImage()">
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
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
              <v-textarea v-model="petModifyInfo.petInfo" label="특이사항" variant="underlined" counter="50"></v-textarea>
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
      },
      petOriginalImage: null
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
    deleteImage() {

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