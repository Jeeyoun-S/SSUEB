<template>
  <v-sheet width="1000" height="800">
    <v-expansion-panels multiple class="my-4">
      <v-expansion-panel width="800" v-for="i in 50" :key="i">
        <v-expansion-panel-title>Panel 1</v-expansion-panel-title>
        <v-expansion-panel-text>
          Some content
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
    <v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
  </v-sheet>
</template>

<script>
export default {
  name: "FinishedReservation",
};
</script>

<style></style>
