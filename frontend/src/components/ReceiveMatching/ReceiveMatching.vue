<template>
  <v-row no-gutters justify="center">
    <v-col cols="3">
      <ReceivedCard></ReceivedCard>
    </v-col>
  </v-row>
</template>

<script>
import ReceivedCard from "@/components/ReceiveMatching/ReceivedCard.vue";

export default {
  name: "ReceiveMatching",
  components: {
    ReceivedCard,
  },
};
</script>

<style>

</style>
