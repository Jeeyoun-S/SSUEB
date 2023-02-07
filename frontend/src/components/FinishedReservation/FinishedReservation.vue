<template>
  <v-sheet width="1000" height="800">
    <v-expansion-panels multiple class="my-4">
      0000    2023-02-06 18:00 ~ 18:30     와싸피 / 로이
      <v-expansion-panel width="800" v-for="i in 50" :key="i">
        <v-expansion-panel-title>
          0000    2023-02-06 18:00 ~ 18:30     와싸피 / 로이                <v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
        </v-expansion-panel-title>
        <v-expansion-panel-text>
          
          <v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon><v-icon size="small" color="orange darken-2"> mdi-star </v-icon>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-sheet>
</template>

<script>
export default {
  name: "FinishedReservation",
};
</script>

<style></style>
