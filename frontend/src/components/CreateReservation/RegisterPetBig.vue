<template>
  <v-dialog v-model="registOpen" width="700">
    <template v-slot:activator="{ props }">
      <!-- <v-sheet v-bind="props" height="370"> -->
      <v-sheet v-bind="props" class="mt-2 mb-4 mr-5">
        <v-hover v-slot="{ isHovering, props }">
          <v-card variant="outlined"
            width="300" height="120" rounded="0"
            :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
            v-bind="props" style="cursor: pointer;"
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
          </v-card>
        </v-hover>
      </v-sheet>
    </template>
    <v-card class="pa-5">
      <v-card-title>
        <h3>반려동물 등록</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form">
            <v-row>
              <v-col class="pr-10" cols="5" align-self="center">
                <v-row justify="center">
                  <v-avatar color="#06BEE1" size="200">
                    <img :src="imageUrl"/>
                  </v-avatar>
                </v-row>
                <v-row>
                  <v-file-input v-model="petRegistInfo.petImage" accept="image/png, image/jpeg"
                    prepend-icon="mdi-camera" :rules="petRules.petImage" label="사진" variant="underlined">
                  </v-file-input>
                </v-row>
              </v-col>
              <v-col>
                <v-row>
                  <v-text-field :rules="petRules.petName" v-model="petRegistInfo.petName"
                    label="이름" variant="underlined" required>
                  </v-text-field>
                </v-row>
                <v-row>
                  <v-select :rules="petRules.petType" v-model="petRegistInfo.petType"
                    :items="['개', '고양이', '토끼', '패럿', '기니피그', '햄스터']"
                    label="종류" variant="underlined" required>
                  </v-select>
                </v-row>
                <v-row>
                  <v-text-field :rules="petRules.petVariety" v-model="petRegistInfo.petVariety"
                    label="품종" variant="underlined">
                  </v-text-field>
                </v-row>
                <v-row v-show="knowBirth == '0'">
                  <v-text-field :rules="petRules.petBirth" v-model="petRegistInfo.petBirth" label="생일"
                    variant="underlined">
                  </v-text-field>
                </v-row>
              </v-col>
            </v-row>
            <v-row>
              <v-textarea :rules="petRules.petInfo" v-model="petRegistInfo.petInfo" label="특이사항" variant="underlined"
                counter="70" maxlength="70">
              </v-textarea>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" variant="text" @click="registOpen = false">취소</v-btn>
        <v-btn color="primary" variant="text" @click="validate()">등록</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
const userStore = "userStore";
import { mapState } from "vuex";
import { registerPetInfo } from '@/api/userInfoPartner.js'

export default {
  name: 'RegisterPetBig',
  computed: {
    ...mapState(userStore, ["userId"]),
    petRules() {
      return this.$store.getters.getPetRule;
    },
    getPetInfo() {
      return this.$store.getters.getPetInfo;
    },
  },
  data() {
    return {
      registOpen: false,
      knowBirth: "0",
      petRegistInfo: {
        petBirth: null,
        petImage: null,
        petInfo: null,
        petName: null,
        petType: null,
        petVariety: null,
      },
      imageUrl: require('@/assets/profile/pet.png')
    }
  },
  watch: {
    petRegistInfo: {
      handler() {
        if (this.petRegistInfo.petImage != null && this.petRegistInfo.petImage.length == 1) {
          this.imageUrl = URL.createObjectURL(this.petRegistInfo.petImage[0]);

          // 파일 이름 가져오기
          var filename = this.petRegistInfo.petImage[0].name;

          // 확장자
          var extension = filename
            .substring(filename.lastIndexOf("."), filename.length)
            .toLowerCase();

          // 파일 이름에서 확장자 없애기
          filename = filename.substring(0, filename.lastIndexOf("."));

          // 파일 이름 줄이기
          Object.defineProperty(
            this.petRegistInfo.petImage[0],
            "name",
            {
              writable: true,
              value: filename.substr(0, 10) + extension,
            }
          );
        } else {
          this.imageUrl = require('@/assets/profile/pet.png');
        }
      },
      deep: true
    }
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.form.validate();
      
      if (valid) {
        var petFormData = new FormData();

        for (var key in this.petRegistInfo) {

          if (this.petRegistInfo[key] != null) {
            if (key == "petImage" && this.petRegistInfo[key].length >= 1) {
              petFormData.append(key, this.petRegistInfo[key][0]);
            }
            else petFormData.append(key, this.petRegistInfo[key]);
          }
        }

        await registerPetInfo(petFormData, this.userId)
        .then((res) => {
          const result = res.result;
          if (!result) {
            this.$emit("addPetList", res.petInfo);
            this.petRegistInfo = {
              petBirth: null,
              petImage: null,
              petInfo: null,
              petName: null,
              petType: null,
              petVariety: null
            }
            this.registOpen = result;
          }
        });
      }
    }
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