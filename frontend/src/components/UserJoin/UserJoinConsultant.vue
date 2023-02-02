<template>
  <v-form class="forms" ref="form">
    <UserJoinBasicInfo @info="updateBasicInfo"></UserJoinBasicInfo>
    <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
    <v-text-field
      v-model="info.consultantLicenseNumber"
      :rules="consultantRule.license"
      class="mb-2"
      label="반려동물행동지도사 자격번호"
      variant="underlined"
      color="primary"
      required
    ></v-text-field>
    <v-file-input
      v-model="info.consultantLicenseCopyImage"
      :rules="consultantRule.image"
      accept="image/png, image/jpeg, .pdf"
      label="반려동물행동지도사 자격증 사본"
      variant="underlined"
      color="primary"
      show-size
    ></v-file-input>
    <v-combobox
      v-model="petCheck"
      :items="petType"
      :rules="consultantRule.type"
      label="상담 가능한 동물"
      variant="underlined"
      color="primary"
      multiple
      chips
    ></v-combobox>
    <v-radio-group v-model="info.userAlertFlag" color="primary" inline>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" size="large" rounded="0" @click="validate()" block
      >회원가입 신청</v-btn
    >
  </v-form>
</template>

<script>
import UserJoinPhone from "@/components/UserJoin/UserJoinPhone.vue";
import UserJoinBasicInfo from "@/components/UserJoin/UserJoinBasicInfo.vue";
import { joinConsultant } from "@/api/userJoin.js";

export default {
  name: "UserJoinConsultant",
  components: {
    UserJoinPhone,
    UserJoinBasicInfo,
  },
  data() {
    return {
      info: {
        id: null,
        userPassword: null,
        userName: null,
        userAlertFlag: "0",
        userPhone: null,
        consultPetType: [],
        consultantLicenseNumber: null,
        consultantLicenseCopyImage: [],
      },
      petCheck: [],
      petType: ["개", "고양이", "토끼", "패럿", "기니피그", "햄스터"],
      consultantRule: {
        license: [(v) => !!v || "필수 입력 사항입니다."],
        image: [
          (v) => v.length == 1 || "자격증 사본을 하나의 파일로 첨부해 주세요.",
          (v) =>
            v[0].size <= 5000000 ||
            "첨부 파일 크기는 최대 5MB까지만 가능합니다.",
          (v) =>
            ["image/png", "image/jpeg", "application/pdf"].includes(
              v[0].type
            ) || "png, jpg, pdf 파일만 첨부 가능합니다.",
        ],
        type: [
          (v) => v.length >= 1 || "상담 가능한 동물을 하나 이상 선택해 주세요.",
        ],
      },
      socialAccess: true,
      formData: new FormData(),
    };
  },
  computed: {
    phoneAuthStates() {
      return this.$store.getters.getPhoneAuthStates;
    },
  },
  methods: {
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid && this.phoneAuthStates) {
        // 펫 타입 확인
        if (this.petCheck.includes("개")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("고양이"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("토끼")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("패럿")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("기니피그"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("햄스터"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");

        this.info.consultPetType = this.info.consultPetType.join("");

        // info를 formData 형식으로 바꿔서 보낸다.
        for (var key in this.info) {
          if (key == "consultantLicenseCopyImage")
            this.formData.append(key, this.info[key][0]);
          else this.formData.append(key, this.info[key]);
        }

        joinConsultant(this.formData, this.socialAccess);
      }
      // #21# 소셜 로그인 접근 회원가입
      else if (
        !valid &&
        this.phoneAuthStates &&
        this.info.userPassword == null
      ) {
        // 펫 타입 확인
        if (this.petCheck.includes("개")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("고양이"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("토끼")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("패럿")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("기니피그"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("햄스터"))
          this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");

        this.info.consultPetType = this.info.consultPetType.join("");

        // info를 formData 형식으로 바꿔서 보낸다.
        for (var keySocial in this.info) {
          if (keySocial == "consultantLicenseCopyImage")
            this.formData.append(keySocial, this.info[keySocial][0]);
          else this.formData.append(keySocial, this.info[keySocial]);
        }

        joinConsultant(this.formData, this.socialAccess);
      }
    },
    updatePhone(userPhone) {
      this.info.userPhone = userPhone;
    },
    updateBasicInfo(info) {
      this.info.id = info.id;
      this.info.userPassword = info.userPassword;
      this.info.userName = info.userName;
      this.socialAccess = info.socialAccess; // #21# false면 소셜 로그인 접근
    },
  },
  watch: {
    info: {
      handler() {
        if (
          this.info.consultantLicenseCopyImage.length >= 1 &&
          this.info.consultantLicenseCopyImage[0].name.length > 15
        ) {
          // 파일 이름 가져오기
          var filename = this.info.consultantLicenseCopyImage[0].name;

          // 확장자
          var extension = filename
            .substring(filename.lastIndexOf("."), filename.length)
            .toLowerCase();

          // 파일 이름에서 확장자 없애기
          filename = filename.substring(0, filename.lastIndexOf("."));

          // 파일 이름 줄이기
          Object.defineProperty(
            this.info.consultantLicenseCopyImage[0],
            "name",
            {
              writable: true,
              value: filename.substr(0, 15) + extension,
            }
          );
        }
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
@import "@/css/form.css";
</style>
