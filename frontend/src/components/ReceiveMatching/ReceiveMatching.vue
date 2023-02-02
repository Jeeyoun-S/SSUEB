<template>
  <v-item-group>
    <v-container>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <ReceivedCard></ReceivedCard>
          </v-item>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <ReceivedCard></ReceivedCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>
  </v-item-group>
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

<style></style>
