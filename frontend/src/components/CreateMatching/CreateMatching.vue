<template>
  <v-item-group>
    <v-container>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <CreateCard></CreateCard>
          </v-item>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="n in 3" :key="n" cols="12" md="4">
          <v-item>
            <CreateCard></CreateCard>
          </v-item>
        </v-col>
      </v-row>
    </v-container>
  </v-item-group>
</template>

<script>
import CreateCard from "@/components/CreateMatching/CreateCard.vue";

export default {
  name: "CreateMatching",
  components: {
    CreateCard,
  },
};
</script>

<style scoped>

</style>
