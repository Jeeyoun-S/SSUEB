<template>
  <v-row no-gutters justify="center">
    <v-col cols="3">
      <ReceivedCard></ReceivedCard>
    </v-col>
  </v-row>
</template>
  
<script>
import ReceivedCard from '@/components/ReceivedMatching/ReceivedCard.vue'

export default {
  name: 'ReceivedMatching',
  components: {
    ReceivedCard
  }
}
</script>
  
<style>
  
</style>