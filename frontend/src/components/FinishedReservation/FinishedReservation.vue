<template>
  <div class="history">
    <FinishedReservationPartner v-if="userAuth == 'ROLE_USER'"></FinishedReservationPartner>
    <FinishedReservationConsultant v-else-if="userAuth == 'ROLE_CONSULTANT'"></FinishedReservationConsultant>
  </div>
</template>

<script>
import FinishedReservationPartner from "./FinishedReservationPartner.vue";
import FinishedReservationConsultant from "./FinishedReservationConsultant.vue";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "FinishedReservation",
  computed: {
    ...mapState(userStore, ["userId", "userAuth"]),
  },
  components: {
    FinishedReservationPartner,
    FinishedReservationConsultant
  }
};
</script>

<style></style>