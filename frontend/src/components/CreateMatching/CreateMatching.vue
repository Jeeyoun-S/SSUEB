<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-view-grid-plus</v-icon>
        <h2>반려인에게 상담 제안 보내기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <v-sheet class="pr-4 pl-4 d-flex flex-row align-center">
          <h3 class="mr-3">품종</h3>
          <v-checkbox v-model="petType" label="개" value="개" color="primary" inline hide-details></v-checkbox>
          <v-checkbox v-model="petType" label="고양이" value="고양이" color="primary" inline hide-details></v-checkbox>
          <v-checkbox v-model="petType" label="토끼" value="토끼" color="primary" inline hide-details></v-checkbox>
          <v-checkbox v-model="petType" label="패럿" value="패럿" color="primary" inline hide-details></v-checkbox>
          <v-checkbox v-model="petType" label="기니피그" value="기니피그" color="primary" inline hide-details></v-checkbox>
          <v-checkbox v-model="petType" label="햄스터" value="햄스터" color="primary" inline hide-details></v-checkbox>
        </v-sheet>
        <v-sheet class="pa-2 pr-4 pl-4" width="1037">
          <DatePicker v-model="date" mode="dateTime"
            timezone="Asia/Pyongyang" color="indigo" :minute-increment="10"
            :available-dates='{ start: new Date(new Date().setDate(new Date().getDate() + 1)), end: new Date(new Date().setMonth(new Date().getMonth() + 2)) }'
            is-range
            >
            <template v-slot="{ inputValue, inputEvents }">
              <div class="d-flex align-center">
                <h3 class="mr-3 mb">일시</h3>
                <input
                  :value="inputValue.start"
                  v-on="inputEvents.start"
                  class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                />
                <svg
                  class="w-3 h-3 mx-2"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M14 5l7 7m0 0l-7 7m7-7H3"
                  />
                </svg>
                <input
                  :value="inputValue.end"
                  v-on="inputEvents.end"
                  class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                />
              </div>
            </template>
          </DatePicker>
        </v-sheet>
        <CreateCard v-for="(reservation, idx) in getFilterList" :reservation="reservation" v-bind:key="idx" />
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import CreateCard from "@/components/CreateMatching/CreateCard.vue";
import { DatePicker } from 'v-calendar';
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";

export default {
  name: "CreateMatching",
  data: () => ({
    loaded: false,
    reservations:[], // [{value,[]},{value,[]}] 꼴
    date: null,
    petType: []
  }),
  computed: {
    ...mapState(userStore, ["userId"]),
    getFilterList() {
      if (this.date == null) {
        if (this.petType.length > 0) {
          return this.reservations.filter(
            v => this.petType.includes(v.petType)
          )
        }
        return this.reservations;
      } else {
        if (this.petType.length > 0) {
          return this.reservations.filter(
            v => this.petType.includes(v.petType) && (new Date(this.date.start) <= new Date(v.reservationDate)) && (new Date(v.reservationDate) <= new Date(this.date.end)) 
          )
        }
        return this.reservations.filter(
          v => (new Date(this.date.start) <= new Date(v.reservationDate)) && (new Date(v.reservationDate) <= new Date(this.date.end)) 
        )
      }
    }
  },
  components: {
    CreateCard,
    NowLoading,
    DatePicker
  },
  
  methods:{
    async getReservation() {
      
      const api = apiInstance();
      await api.get(process.env.VUE_APP_API_BASE_URL+`/reservation/consultant/unconfirmed`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].rno;
            reservation["userId"] = data[i].userId;
            reservation["reservationDate"] = data[i].reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationConsultContent;
            reservation["pno"] = data[i].pno;
            reservation["petName"] = data[i].petName;
            reservation["petImage"] = data[i].petImage;
            reservation["petType"] = data[i].petType;
            reservation["petVariety"] = data[i].petVariety;
            if(data[i].petBirth != null){
              reservation["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].petInfo;

            this.reservations.push(reservation);
          }
          console.log(this.reservations)
        })
        .catch((err) => {
          console.log(err);
        });
      return await Promise.resolve(true);
    },
  },
  async created() {
    this.loaded = false;
    await this.getReservation().then((res) => {
      this.loaded = res;
    });
  },
};
</script>

<style></style>