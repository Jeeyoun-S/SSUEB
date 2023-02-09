<template>
  <v-form ref="forms">
    <v-sheet class="mx-auto pa-7" width="970">
      <v-row>
        <v-col cols="7">
          <v-row class="mt-3" justify="center">
            <!-- 새로 넣은 이미지 -->
            <v-avatar v-if="consultantModifyInfo.consultantProfile != null && consultantModifyInfo.consultantProfile.length > 0" color="white" size="120">
              <img :src="updateProfileUrl">
            </v-avatar>
            <!-- 기존 이미지 -->
            <v-hover v-else-if="originalProfile != null && !consultantModifyInfo.deleteProfile" v-slot="{ isHovering, props }">
              <v-card v-bind="props" rounded="circle" elevation="0" width="120" height="120" justify="center">
                <img :src="getImageUrl(originalProfile)" />
                <v-overlay :model-value="isHovering" scrim="light-blue-lighten-1" class="align-center justify-center" contained>
                  <v-btn variant="flat" icon="mdi-delete" @click="consultantModifyInfo.deleteProfile = true"></v-btn>
                </v-overlay>
              </v-card>
            </v-hover>
            <!-- 없는 이미지 (기존 이미지 되돌리기 O) -->
            <v-hover v-else-if="originalProfile !=null" v-slot="{ isHovering, props }">
              <v-card v-bind="props" rounded="circle" elevation="0" width="120" height="120" justify="center">
                <img :src="require('@/assets/profile/user.png')"/>
                <v-overlay :model-value="isHovering" scrim="light-blue-lighten-1" class="align-center justify-center" contained>
                  <v-btn variant="flat" icon="mdi-cached" @click="consultantModifyInfo.deleteProfile = false"></v-btn>
                </v-overlay>
              </v-card>
            </v-hover>
            <!-- 없는 이미지 (기존 이미지 되돌리기 X) -->
            <v-avatar v-else color="white" size="120">
              <img :src="require('@/assets/profile/user.png')"/>
            </v-avatar>
          </v-row>
          <v-row class="mr-1 ml-1" justify="center">
            <v-file-input
              v-model="consultantModifyInfo.consultantProfile"
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
              v-model="consultantModifyInfo.consultantIntro"
              :rules="consultantRule.intro"
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
              v-model="consultantModifyInfo.consultantPetType" 
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
            v-model="consultantModifyInfo.id"
            label="이메일"
            variant="outlined"
            density="compact"
            disabled
          ></v-text-field>
          <v-text-field
            class="mb-3"
            v-model="consultantModifyInfo.userName"
            label="이름"
            variant="outlined"
            density="compact"
            :rules="userRule.name"
          ></v-text-field>
          <v-text-field
            v-model="userPhone"
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
          <v-radio-group v-model="consultantModifyInfo.userAlertFlag" color="primary" density="compact" inline>
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
            v-model="consultantModifyInfo.userPassword"
            :rules="userRule.password"
            label="비밀번호"
            variant="outlined"
            density="compact"
          ></v-text-field>
          <v-btn class="mr-3" variant="outlined" color="primary" rounded="0" @click="validate()">저장</v-btn>
          <v-btn class="mr-3" variant="outlined" color="error" rounded="0" @click="back()">취소</v-btn>
        </v-col>
      </v-row>
    </v-sheet>
  </v-form>
</template>

<script>
import { updateConsultantInfo } from "@/api/userInfoConsultant.js";

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
    phoneAuthWarning() {
      return this.$store.getters.getPhoneAuthWarning;
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
      consultantModifyInfo: {
        id: null,
        userName: null,
        userPhone: null,
        userAlertFlag: 0,
        userPassword: null,
        consultantIntro: null,
        consultantProfile: null,
        consultantPetType: [],
        deleteProfile: false
      },
      phoneDisable: true,
      userPhone: null,
      showPassword: false,
      originalProfile: null,
      updateProfileUrl: null
    }
  },
  methods: {
    back() {
      this.$store.dispatch("updateInfoVersion");
    },
    async validate() {
      const { valid } = await this.$refs.forms.validate();

      if (valid && this.phoneAuthStates) {
        this.consultantModifyInfo.userPhone = this.userPhone;
        
        const petType = [0, 0, 0, 0, 0, 0];
        for (var i in this.consultantModifyInfo.consultantPetType) {
          petType[i] = 1;
        }
        this.consultantModifyInfo.consultantPetType = petType.join('');
        updateConsultantInfo(this.consultantModifyInfo);
      }
    },
    phoneAuth() {
      // sendPhoneAuth({
      //   userPhone: this.userPhone,
      // });
      this.$store.dispatch("updatePhoneAuthMessage", this.userPhone);
    },
  },
  created() {
    this.consultantModifyInfo.id = this.getConsultantInfo.id;
    this.consultantModifyInfo.userName = this.getConsultantInfo.userName;
    this.consultantModifyInfo.userAlertFlag = this.getConsultantInfo.userAlertFlag;
    this.consultantModifyInfo.consultantIntro = this.getConsultantInfo.consultantIntro;
    this.originalProfile = this.getConsultantInfo.consultantProfile;
    this.consultantModifyInfo.consultantPetType = this.getConsultantInfo.consultantPetType;
    this.userPhone = this.getConsultantInfo.userPhone;

    this.$store.dispatch("updatePhoneAuthMessage", this.getConsultantInfo.userPhone);
  },
  watch: {
    async userPhone() {
      // 공백 제거
      this.userPhone = this.userPhone.replace(" ", "");

      // 부모 컴포넌트로 전화번호 보내기
      this.$emit("userPhone", this.userPhone);

      // 만약 휴대폰 번호 유효성 검사를 통과하지 못한다면
      if (!this.userPhone || !((/^[0-9]{11}$/).test(this.userPhone))) {
        // 인증 버튼 비활성화
        this.phoneDisable = true;
        // 인증 완료인 경우, 미완으로 변경
        if (this.phoneAuthStates) {
          this.$store.dispatch("resetPhoneAuthMessage");
        }
        // 유효성 검사를 통과한 경우
      } else {
        // 인증 번호 활성화
        this.phoneDisable = false;
        // 기존에 인증한 번호와 동일하면 인증 처리
        if (this.phoneConfirm != null && this.phoneConfirm == this.userPhone) {
          this.$store.dispatch("updatePhoneAuthMessage", this.userPhone);
        }
        // 기존에 인증한 번호와 다르면 미인증 처리
        else {
          this.$store.dispatch("resetPhoneAuthMessage");
        }
      }
    },
    consultantModifyInfo: {
      handler() {
        if (this.consultantModifyInfo.consultantProfile != null && this.consultantModifyInfo.consultantProfile.length == 1) {
          this.updateProfileUrl = URL.createObjectURL(this.consultantModifyInfo.consultantProfile[0]);
        
          // 파일 이름 가져오기
          var filename = this.consultantModifyInfo.consultantProfile[0].name;

          // 확장자
          var extension = filename
            .substring(filename.lastIndexOf("."), filename.length)
            .toLowerCase();

          // 파일 이름에서 확장자 없애기
          filename = filename.substring(0, filename.lastIndexOf("."));

          // 파일 이름 줄이기
          Object.defineProperty(
            this.consultantModifyInfo.consultantProfile[0],
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
}
</script>

<style scoped>
img {
  width: 120px;
  height: 120px;
  object-fit: cover;
}
</style>