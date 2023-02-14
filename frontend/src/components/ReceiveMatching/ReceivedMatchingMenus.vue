<template>
  <div class="text-center">
    <v-menu
      open-on-hover
    >
      <template v-slot:activator="{ props }">
        <v-btn
        variant="text" 
        color="primary" 
        @click="seefile"
          v-bind="props"
        >
        관련 첨부파일 보기
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          v-for="(item, index) in items"
          :key="index"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
import { getAttachUrl } from "@/api/attachFile.js";

export default {
  name: "ReceivedMatchingMenus",
  methods: {
    data: () => ({
      attach: null,
      items: [
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me 2' },
      ],
    }),
    props: {
      reservation: Object,
      idx: Number,
    },
    async seefile() {
      if (this.attach == null)
        this.attach = await getAttachUrl(this.reservation.rno);
      console.log(this.attach);
      this.$swal.fire({
        imageUrl: "https://unsplash.it/400/200",
        imageWidth: 600,
      });
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
};
</script>

<style></style>
