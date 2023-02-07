<template>
  <v-card width="550" height="400" elevation="2">
    <br />
    <div class="text-center">
      <h3>20XX - XX - XX XX:XX</h3>
    </div>

    <v-row class="pill-box">
      <v-col class="left-pill">
        <v-btn rounded="pill" color="black" disabled> D-2 </v-btn>
      </v-col>
      <v-col class="right-pill">
        <v-btn rounded="pill" color="primary" @click="timealert"> 입장 </v-btn>
      </v-col>
    </v-row>
    <br />

    <v-row>
      <v-col>
        <confirmed-pet></confirmed-pet>
      </v-col>
      <vl />
      <v-col>
        <confirmed-consultant></confirmed-consultant>
      </v-col>
    </v-row>
  </v-card>
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
