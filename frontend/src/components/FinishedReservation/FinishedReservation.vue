<template>
  <v-row>
    <v-expansion-panels variant="popout" class="my-4">
      <v-expansion-panel
        v-for="i in 3"
        :key="i"
        title="Item"
        text="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
      ></v-expansion-panel>
    </v-expansion-panels>
  </v-row>

  <v-card width="300" height="400" elevation="2">
    <v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
  </v-card>
</template>

<script>
export default {
  name: "FinishedReservation",
};
</script>

<style></style>
