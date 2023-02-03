<template>
    <v-item-group>
    <v-container>
      <v-row>
        <v-col v-for="n in 2" :key="n" cols="12" md="4">
          <v-item>
            <confirmed-card></confirmed-card>
          </v-item>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="n in 2" :key="n" cols="12" md="4">
          <v-item>
            <confirmed-card></confirmed-card>
          </v-item>
        </v-col>
      </v-row>
    </v-container>
  </v-item-group>
</template>

<script>
import ConfirmedCard from "./ConfirmedCard.vue";

export default {
  name: "ConfirmedReservation",
  components: {
    ConfirmedCard,
  },
};
</script>

<style></style>
