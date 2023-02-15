<template>
  <v-btn
    class="mr-3"
    variant="outlined"
    color="primary"
    rounded="0"
    @click="logout"
    >로그아웃</v-btn
  >
</template>

<script>
import { mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "UserLogout",
  methods: {
    ...mapActions(userStore, ["excuteLogout"]),
    // [@Method] 로그아웃
    async logout() {
      await this.excuteLogout();
    },
  },
};
</script>

<style></style>
