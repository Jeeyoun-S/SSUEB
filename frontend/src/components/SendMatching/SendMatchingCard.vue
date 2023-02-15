<template>
  <div>
    <v-hover v-slot="{ isHovering, props }">
      <v-card
        class="ma-3 pa-2 d-flex justify-center flex-column"
        width="320"
        height="620"
        variant="outlined"
        :elevation="isHovering ? 8 : 0"
        :class="{ 'on-hover': isHovering }"
        rounded="0"
        v-bind="props"
      >
        <v-btn
          class="ms-auto align-self-end"
          color="error"
          variant="text"
          @click="deleteSendMatching()"
        >
          삭제
        </v-btn>
        <v-avatar class="align-self-center" color="#06BEE1" size="100">
          <span v-if="matching.petImage == null">{{ matching.petName }}</span>
          <img v-else :src="getImageUrl(matching.petImage)" height="100" width="100" />
          <v-tooltip v-if="matching.petInfo != null"
            activator="parent"
            location="bottom" width="280"
          >{{ matching.petInfo }}</v-tooltip>
        </v-avatar>
        <v-card-title>
          <h4>{{ matching.reservationDate.substr(0, 16) }}</h4>
        </v-card-title>
        <v-card-subtitle>
          <p>{{ matching.petName }}</p>
          <p>{{ matching.petType }}({{ matching.petBirth }})</p>
          <p>{{ matching.petVariety }}&nbsp;</p>
        </v-card-subtitle>
        <v-card-text>
          <div>
            <v-sheet height="110">
              {{ matching.reservationConsultContent.substr(0, 60) }}
              <span v-if="matching.reservationConsultContent.length > 60">···</span>
              <v-btn v-if="matching.reservationConsultContent.length > 60" color="primary" class="mt-2" @click="overlay = !overlay" variant="outlined" rounded="0" block>
                더보기
              </v-btn>
              <v-overlay v-model="overlay" contained>
                <v-sheet class="d-flex flex-column justify-center align-center pa-4 detail-info" width="320" height="620">
                  <v-sheet color="transparent" width="280" height="620">
                    {{ matching.reservationConsultContent }}
                    <v-btn class="mt-2" color="primary" @click="overlay = false" rounded="0" block>
                      닫기
                    </v-btn>
                  </v-sheet>
                </v-sheet>
              </v-overlay>
            </v-sheet>
          </div>
        </v-card-text>
        <v-divider></v-divider>
        <v-sheet height="162">
          <v-card-text class="pa-4">
            <p>
              <span class="bold-font mr-2">금액</span>
              <span>{{ matching.matchingCost }}</span>
            </p>
            <p class="mt-2">
              <span class="bold-font mr-2">설명</span>
              <span>{{ matching.matchingComment }}</span>
            </p>
          </v-card-text>
        </v-sheet>
        <v-card-actions>
          <SeeAttatchedFiles :rno="matching.reservationNo" />
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
import { deleteMatching } from "@/api/reservationMatching.js";
import SeeAttatchedFiles from "../SeeAttachedFiles/SeeAttatchedFiles.vue";

export default {
  name: "SendMatchingCard",
  data() {
    return {
      overlay: false,
    };
  },
  props: {
    matching: Object,
    idx: Number,
  },
  components: {
    SeeAttatchedFiles,
  },
  methods: {
    async deleteSendMatching() {
      await deleteMatching(this.matching.no).then((res) => {
        if (res) {
          this.$emit("deleteMatching", this.idx);
          this.$swal.fire(
            "제안한 상담 삭제 완료",
            "제안하신 상담이 삭제되었습니다.",
            "success"
          );
        }
      });
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  created(){
    console.log(this.reservation)
  }
};
</script>

<style></style>
