<template>
  <v-sheet class="mr-2" width="150">
    <v-combobox
      :items="['이메일', '이름']"
      variant="outlined" density="compact" v-model="range"
      hide-details
    ></v-combobox>
  </v-sheet>
  <v-text-field
    class="mb-1"
    v-model="keyword"
    density="compact"
    variant="outlined"
    label="검색"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
  ></v-text-field>
  <v-table fixed-header hover>
    <thead>
      <tr>
        <th width="100">번호</th>
        <th width="200">가입 날짜</th>
        <th width="300">이름</th>
        <th width="450">이메일</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(consultant, index) in consultantList" :key="index"
        @click="moveDetailPage(consultant, index)" style="cursor: pointer;"
      >
        <td>{{ String(index).padStart(4, '0') }}</td>
        <td>{{ consultant.userJoindate }}</td>
        <td>{{ consultant.userName }}</td>
        <td>{{ consultant.id }}</td>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
export default {
  name: "ConsultantAcceptTable",
  props: {
    consultantList: Array
  },
  data() {
    return {
      range: "이메일",
      keyword: "",
    }
  },
  methods: {
    moveDetailPage(consultantDetail, index) {
      consultantDetail.index = index;
      this.$store.dispatch("updateConsultantDetail", consultantDetail);
      this.$router.push("/consultant-accept/detail");
    }
  }
}
</script>

<style>

</style>