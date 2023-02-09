<template>
  <v-hover v-slot="{ isHovering, props }">
    <v-card class="ma-3 pa-3 d-flex justify-center flex-column"
      width="492" height="400" variant="outlined"
      :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
      rounded="0" v-bind="props"
    >
      <v-card-title class="d-flex flex-row justify-space-between">
        <h4>20XX - XX - XX XX:XX</h4>
        <div>
          <v-btn class="mr-3" rounded="pill" color="primary" disabled>D-2</v-btn>
          <v-btn rounded="pill" color="primary" @click="timealert">입장</v-btn>
        </div>
      </v-card-title>
      <div class="d-flex flex-row justify-space-around">
        <confirmed-pet></confirmed-pet>
        <v-divider vertical></v-divider>
        <confirmed-consultant></confirmed-consultant>
      </div>
    </v-card>
  </v-hover>
</template>

<script>
import ConfirmedPet from "@/components/Confirmed/ConfirmedPet.vue";
import ConfirmedConsultant from "@/components/Confirmed/ConfirmedConsultant.vue";

export default {
  components: { ConfirmedPet, ConfirmedConsultant },
  name: "ConfirmedCard",
  methods: {
    timealert() {
      this.$swal.fire(
        "화상 상담 입장 대기",
        "화상 상담은 10분 전부터 입장 가능합니다.",
        "warning"
      );
    },
  },
};
</script>

<style>
vl {
  border: none;
  border-left: 1px solid hsla(200, 10%, 50%, 100);
  height: 27vh;
  width: 1px;
}
.a-card {
  margin: 15px;
}
.pill-box {
  display: flex;
}
.left-pill {
  display: flex;
  justify-content: flex-end;
  padding: 10 px;
}
.right-pill {
  display: flex;
  padding: 10 px;
}
</style>
