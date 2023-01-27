<template>
  <v-form class="form" ref="form">
    <UserJoinBasicInfo @info="updateBasicInfo"></UserJoinBasicInfo>
    <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
    <v-text-field v-model="info.consultantLicenseNumber" class="mb-2" label="반려동물행동지도사 자격번호" variant="underlined" color="primary" required></v-text-field>
    <v-file-input v-model="info.consultantLicenseCopyImage" accept="image/png, image/jpeg, .pdf" label="반려동물행동지도사 자격증 사본" variant="underlined" color="primary" small-chips show-size></v-file-input>
    <v-combobox v-model="petCheck" :items="petType" label="상담 가능한 동물" variant="underlined" color="primary" multiple chips></v-combobox>
    <v-radio-group v-model="info.userAlertFlag" color="primary" inline>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" value="0"></v-radio>
      <v-radio label="이메일" value="1"></v-radio>
      <v-radio label="문자" value="2"></v-radio>
    </v-radio-group>
    <v-btn variant="outlined" size="large" rounded="0" @click="validate()" block>회원가입 신청</v-btn>
  </v-form>
</template>

<script>
import UserJoinPhone from '@/components/UserJoin/UserJoinPhone.vue'
import UserJoinBasicInfo from '@/components/UserJoin/UserJoinBasicInfo.vue'
import { joinConsultant } from '@/api/userJoin.js'

export default {
  name: 'UserJoinConsultant',
  components: {
    UserJoinPhone,
    UserJoinBasicInfo
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
        consultantLicenseCopyImage: null
      },
      petCheck: [],
      petType: ['개', '고양이', '토끼', '패럿', '기니피그', '햄스터'],
    }
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
        if (this.petCheck.includes("고양이")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("토끼")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("패럿")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("기니피그")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        if (this.petCheck.includes("햄스터")) this.info.consultPetType.push("1");
        else this.info.consultPetType.push("0");
        
        this.info.consultPetType = this.info.consultPetType.join("");
        console.log("#출력 ", this.info);

        // info를 formData 형식으로 바꿔서 보낸다.
        var formData = new FormData();

        for (var key in this.info) {
          formData.append(key, this.info[key]);
        }

        joinConsultant(formData);
      }
    },
    updatePhone(userPhone) {
      this.info.userPhone = userPhone;
    },
    updateBasicInfo(info) {
      this.info.id = info.id
      this.info.userPassword = info.userPassword
      this.info.userName = info.userName
    }
  }
}
</script>

<style scoped>
@import "@/css/form.css";
</style>