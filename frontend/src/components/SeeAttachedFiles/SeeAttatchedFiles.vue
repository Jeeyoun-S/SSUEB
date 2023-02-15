<template>
  <v-dialog v-model="dialog" width="800" scrollable>
    <template v-slot:activator="{ props }">
      <v-btn variant="text" color="primary" v-bind="props" @click="seefile">
        <!--block :disabled="reservation.matchingConsultants.length < 1" -->
        관련 첨부파일 보기
      </v-btn>
    </template>

    <v-card>
      <v-card-title><h3>관련 첨부파일 보기</h3></v-card-title>
      <v-divider></v-divider>
      <v-card-text class="pa-0 pl-5"> </v-card-text>
      <v-card-item>
        <div class="pa-5">
          <v-row v-for="(url, idx) in attach" :key="idx" class="ma-2 pa-2" justify="center">
            <img :src="url" v-if="url.endsWith('jpg')" />
            <video
              autoplay
              controls
              muted
              :src="url"
              v-if="url.endsWith('mp4')"
              type="video/mp4"
            />
          </v-row>
          <br />
        </div>
      </v-card-item>
      <v-card-actions>
        <v-btn color="primary" block @click="dialog = false">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState } from "vuex";
import { getAttachUrl } from "@/api/attachFile.js";
const userStore = "userStore";

export default {
  name: "SeeAttachedFiles",
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  data: () => ({
    dialog: false,
    loaded: false,
    attach: null,
  }),
  props: {
    rno: Number,
    idx: Number,
  },
  methods: {
    async seefile() {
      if (this.attach == null)
        this.attach = await getAttachUrl(this.rno);
      this.dialog = true;
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  created() {

  },
};
</script>

<style></style>
