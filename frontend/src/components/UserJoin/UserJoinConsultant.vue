<template>
  <v-form class="form" ref="form">
    <UserJoinBasicInfo @info="updateBasicInfo"></UserJoinBasicInfo>
    <UserJoinPhone @userPhone="updatePhone"></UserJoinPhone>
    <v-text-field v-model="info.consultantLicenseNumber" class="mb-2" label="반려동물행동지도사 자격번호" variant="underlined" color="primary" required></v-text-field>
    <v-file-input v-model="info.consultantLicenseCopyImage" accept="image/png, image/jpeg, .pdf" label="반려동물행동지도사 자격증 사본" variant="underlined" color="primary" small-chips></v-file-input>
    <v-combobox v-model="info.consultPetType" :items="petType" label="상담 가능한 동물" variant="underlined" color="primary" multiple chips></v-combobox>
    <v-radio-group v-model="info.userAlertFlag" color="primary" inline>
      <v-label>알림방법</v-label>
      <v-radio label="카카오톡" size="small" value="0"></v-radio>
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
      petType: ['개', '고양이', '토끼', '기니피그', '패럿', '햄스터'],
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
        joinConsultant(this.info);
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