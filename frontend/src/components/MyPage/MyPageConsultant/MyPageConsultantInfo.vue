<template>
  <v-sheet class="mx-auto pa-7" width="970">
    <v-row>
      <v-col cols="7">
        <v-row class="ma-1">

          <v-avatar class="mr-5 mb-2" color="primary" size="170">
            <span v-if="getConsultantInfo.consultantProfile == null">{{ getConsultantInfo.userName }}</span>
            <img v-else :src="getImageUrl(getConsultantInfo.consultantProfile)" height="170" width="170" />
          </v-avatar>
          <v-textarea
            v-model="getConsultantInfo.consultantIntro"
            label="소개글 및 경력"
            variant="outlined"
            readonly
            hide-details
          ></v-textarea>
        </v-row>
        <v-row class="ml-2" rows="5" sm="2" md="4">
          <span class="subheading">상담가능동물</span>
        </v-row>
        <v-row class="ml-2">
          <v-chip-group
            selected-class="text-primary"
            v-model="getConsultantInfo.consultantPetType" 
            column disabled multiple>
            <v-chip v-for="value, index in petType"
              :key="index" filter variant="outlined">
              {{ value }}
            </v-chip>
          </v-chip-group>
        </v-row>
      </v-col>
      <v-col cols="5">
        <v-text-field
          v-model="getConsultantInfo.id"
          label="이메일"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="getConsultantInfo.userName"
          label="이름"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="getConsultantInfo.userPhone"
          label="휴대폰 번호"
          variant="outlined"
          density="compact"
          readonly
        ></v-text-field>
        <v-radio-group v-model="getConsultantInfo.userAlertFlag" color="primary" density="compact" inline readonly>
          <v-label>알림방법</v-label>&ensp;
          <v-radio label="카카오톡" value="0"></v-radio>&ensp;
          <v-radio label="이메일" value="1"></v-radio>&ensp;
          <v-radio label="문자" value="2"></v-radio>
        </v-radio-group>
        <UserLogout></UserLogout>
        <v-btn class="mr-3" variant="outlined" rounded="0" @click="modifyConsultantInfo()">회원 정보 수정</v-btn>
        <v-btn class="mr-3" variant="outlined" color="error" rounded="0">탈퇴</v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
import UserLogout from "@/components/MyPage/UserLogout.vue";
import { checkPassword } from "@/api/userInfoPartner.js";

export default {
  name: "MyPageConsultantInfo",
  components: {
    UserLogout
  },
  computed: {
    getConsultantInfo() {
      return this.$store.getters.getConsultantInfo;
    },
  },
  data() {
    return {
      petType: ["개", "고양이", "토끼", "패럿", "기니피그", "햄스터"],
    }
  },
  methods: {
    modifyConsultantInfo() {
      
      if (this.socialUserInfo != null) {
        this.$store.dispatch("updateInfoVersion");
      } else {
        checkPassword(this.getConsultantInfo.id);
      }
    },
    getImageUrl(img) {
      return require(`${process.env.VUE_APP_IMAGE_FILE_PATH_PROFILE}` + img);
    }
  }
}
</script>

<style>

</style>