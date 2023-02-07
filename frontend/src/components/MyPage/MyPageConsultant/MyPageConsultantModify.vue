<template>
  <v-sheet class="mx-auto pa-7" width="970">
    <v-row>
      <v-col cols="7">
        <v-row justify="center">
          <v-avatar class="mt-3" color="primary" size="120">
            <span>{{ consultantInfo.userName }}</span>
          </v-avatar>
        </v-row>
        <v-row class="mr-1 ml-1" justify="center">
          <v-file-input
            :rules="consultantRule.profile"
            accept="image/png, image/jpeg"
            placeholder="Pick an avatar"
            prepend-icon="mdi-camera"
            label="프로필 사진"
            variant="underlined"
          ></v-file-input>
        </v-row>
        <v-row class="mr-1 ml-1">
          <v-textarea
            v-model="consultantInfo.intro"
            :rules="consultantRule.profile"
            label="소개글 및 경력"
            variant="outlined"
            counter="150"
          ></v-textarea>
        </v-row>
        <v-row class="ml-2" rows="5" sm="2" md="4">
          <span class="subheading">상담가능동물</span>
        </v-row>
        <v-row class="ml-2">
          <v-chip-group
            selected-class="text-primary"
            v-model="consultantInfo.consultantPetType" 
            :rules="consultantRule.type"
            column multiple>
            <v-chip v-for="value, index in petType"
              :key="index" filter variant="outlined">
              {{ value }}
            </v-chip>
          </v-chip-group>
        </v-row>
      </v-col>
      <v-col cols="5">
        <v-text-field
          class="mb-3"
          v-model="consultantInfo.id"
          label="이메일"
          variant="outlined"
          density="compact"
          disabled
        ></v-text-field>
        <v-text-field
          class="mb-3"
          v-model="consultantInfo.userName"
          label="이름"
          variant="outlined"
          density="compact"
          :rules="userRule.name"
        ></v-text-field>
        <v-text-field
          v-model="consultantInfo.userPhone"
          label="휴대폰 번호"
          variant="outlined"
          density="compact"
          :rules="userRule.phone"
        ></v-text-field>
        <v-btn
          variant="tonal"
          rounded="0"
          @click="phoneAuth()"
          :disabled="phoneDisable || phoneAuthStates"
          block
          >{{ phoneAuthMessage }}</v-btn
        >
        <div class="warning" v-if="!phoneDisable">{{ phoneAuthWarning }}</div>
        <div class="warning" v-else></div>
        <v-radio-group v-model="consultantInfo.userAlertFlag" color="primary" density="compact" inline>
          <v-label>알림방법</v-label>&ensp;
          <v-radio label="카카오톡" value="0"></v-radio>&ensp;
          <v-radio label="이메일" value="1"></v-radio>&ensp;
          <v-radio label="문자" value="2"></v-radio>
        </v-radio-group>
        <v-text-field
          class="mb-2"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword ? 'text' : 'password'"
          @click:append="showPassword = !showPassword"
          v-model="showPassword"
          :rules="userRule.password"
          label="비밀번호"
          variant="outlined"
          density="compact"
        ></v-text-field>
        <UserLogout></UserLogout>
        <v-btn class="mr-3" variant="outlined" color="primary" rounded="0">저장</v-btn>
        <v-btn class="mr-3" variant="outlined" color="error" rounded="0" @click="back()">취소</v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  name: "MyPageConsultantModify",
  computed: {
    phoneAuthMessage() {
      return this.$store.getters.getPhoneAuthMessage;
    },
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
    phoneConfirm() {
      return this.$store.getters.getPhoneConfirm;
    },
    getConsultantInfo() {
      return this.$store.getters.getConsultantInfo;
    },
    userRule() {
      return this.$store.getters.getUserRule;
    },
    consultantRule() {
      return this.$store.getters.getConsultantRule;
    },
  },
  data() {
    return {
      petType: ["개", "고양이", "토끼", "패럿", "기니피그", "햄스터"],
      consultantInfo: {
        id: null,
        userName: null,
        userPhone: null,
        userAlertFlag: 0,
        consultantIntro: null,
        consultantProfile: null,
        consultantPetType: [],
      },
      phoneDisable: true,
      userPhone: null,
      showPassword: false,
    }
  },
  methods: {
    back() {
      this.$store.dispatch("updateInfoVersion");
    },
  },
  created() {
    this.consultantInfo = this. getConsultantInfo;
  }
}
</script>

<style>

</style>