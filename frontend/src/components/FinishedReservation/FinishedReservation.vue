<template>
  <div class="history">
    <v-expansion-panels multiple>
      <v-expansion-panel v-for="i in 40" :key="i">

        <!-- 타이틀 -->
        <v-expansion-panel-title>
          <v-col class="pa-0 pl-2 bold-font" cols="1">{{ String(i).padStart(4, '0') }}</v-col>
          <v-col class="pa-0" cols="3">2023-02-06 18:00 ~ 18:30</v-col>
          <v-col class="pa-0" cols="5">와싸피 / 로이</v-col>
          <v-col class="pa-0">
            <v-rating v-model="rating" color="orange darken-2" density="compact" half-increments readonly></v-rating>
          </v-col>
        </v-expansion-panel-title>

        <!-- 내부 텍스트 -->
        <v-expansion-panel-text class="pa-3">
          <v-hover
            v-slot="{ isHovering, props }"
            open-delay="200"
          >
            <v-card
              :elevation="isHovering ? 3 : 0"
              :class="{ 'on-hover': isHovering }"
              class="mb-5 mx-auto" variant="outlined"
              height="170" rounded="0"
              v-bind="props" color="grey-lighten-1"
            >
              <div>
                <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
                <v-card-title></v-card-title>
              </div>
              <v-card-text class="font-weight-medium mt-12 text-center text-subtitle-1">
                Open Delay (Mouse enter)
              </v-card-text>
            </v-card>
          </v-hover>
          <v-divider></v-divider>
          <v-hover
            v-slot="{ isHovering, props }"
            open-delay="200"
          >
            <v-card
              :elevation="isHovering ? 3 : 0"
              :class="{ 'on-hover': isHovering }"
              class="mt-5 mx-auto d-flex flex-row justify-space-evenly align-center" variant="outlined"
              height="200" rounded="0"
              v-bind="props"
            >
              <v-card class="d-flex flex-column align-center" width="200">
                <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
                <v-card-title class="pa-0">로이 (8세)</v-card-title>
                <p>강아지</p>
                <p>이탈리안 그레이하운드</p>
              </v-card>
              <v-card class="d-flex flex-column align-center" width="700">
                <v-card-text>
                  Open Delay (Mouse enter)
                </v-card-text>
              </v-card>
            </v-card>
          </v-hover>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
export default {
  name: "FinishedReservation",
  data() {
    return {
      rating: 3.5
    }
  }
};
</script>

<style></style>
