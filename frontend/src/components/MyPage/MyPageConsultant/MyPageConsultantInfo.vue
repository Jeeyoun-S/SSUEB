<template>
  <v-sheet class="mx-auto pa-7" width="970">
    <v-row>
      <v-col cols="7">
        <v-row class="ma-1">
          <v-avatar class="mr-5 mb-2" color="primary" size="170">
            <span>{{ getConsultantInfo.userName }}</span>
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
            column
            disabled
            multiple
          >
            <v-chip
              v-for="(value, index) in petType"
              :key="index"
              filter
              variant="outlined"
            >
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
        <v-radio-group
          v-model="getConsultantInfo.userAlertFlag"
          color="primary"
          density="compact"
          inline
          readonly
        >
          <v-label>알림방법</v-label>&ensp;
          <v-radio label="카카오톡" value="0"></v-radio>&ensp;
          <v-radio label="이메일" value="1"></v-radio>&ensp;
          <v-radio label="문자" value="2"></v-radio>
        </v-radio-group>
        <UserLogout></UserLogout>
        <v-btn
          class="mr-3"
          variant="outlined"
          rounded="0"
          @click="modifyConsultantInfo()"
          >회원 정보 수정</v-btn
        >
        <v-btn
          class="mr-3"
          variant="outlined"
          color="error"
          rounded="0"
          @click="withdrawal"
          >탈퇴</v-btn
        >
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
import UserLogout from "@/components/MyPage/UserLogout.vue";
import { checkPassword } from "@/api/userInfoPartner.js";
import { mapActions } from "vuex";

const userStore = "userStore";
const Swal = require("sweetalert2");

export default {
  name: "MyPageConsultantInfo",
  components: {
    UserLogout,
  },
  computed: {
    getConsultantInfo() {
      return this.$store.getters.getConsultantInfo;
    },
  },
  data() {
    return {
      petType: ["개", "고양이", "토끼", "패럿", "기니피그", "햄스터"],
    };
  },
  methods: {
    ...mapActions(userStore, ["excuteWithdrawal"]),
    modifyConsultantInfo() {
      if (this.socialUserInfo != null) {
        this.$store.dispatch("updateInfoVersion");
      } else {
        checkPassword(this.getConsultantInfo.id);
      }
    },
    // [@Method] 회원 탈퇴
    async withdrawal() {
      Swal.fire({
        title: "탈퇴하시겠습니까?",
        text: "탈퇴 시 해당 계정은 사용하지 못하게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes",
      }).then(async (result) => {
        if (result.isConfirmed) {
          await this.excuteWithdrawal();
        }
      });
    },
  },
};
</script>

<style></style>
