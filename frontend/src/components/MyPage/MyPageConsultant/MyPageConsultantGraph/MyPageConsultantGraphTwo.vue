<template>
  <Line :data="data" :options="options" />
</template>

<script>
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js'
import { Line } from 'vue-chartjs'
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend)

export default {
  name: 'MyPageConsultantGraphTwo',
  components: {
    Line
  },
  computed: {
    getGraphTwo() {
      return this.$store.getters.getGraphTwo;
    },
  },
  data() {
    return {
      data: {
        labels: this.$store.getters.getGraphLabels,
        datasets: [
          {
            label: '예약 횟수',
            backgroundColor: '#3F51B5',
            data: this.$store.getters.getGraphTwo,
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        elements: {
          point: {
            radius: 5
          }
        }
      }
    }
  }
}
</script>