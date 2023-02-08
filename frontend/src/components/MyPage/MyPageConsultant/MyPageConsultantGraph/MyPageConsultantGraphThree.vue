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
  data() {
    return {
      data: {
        labels: this.$store.getters.getGraphLabels,
        datasets: [
          {
            label: '별점 평균',
            backgroundColor: '#00BCD4',
            data: this.$store.getters.getGraphThree,
            max: 5
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
