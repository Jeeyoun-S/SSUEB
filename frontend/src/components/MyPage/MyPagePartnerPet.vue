<template>
  <v-sheet class="mypage-info-item" height="400" width="500" elevation="2" rounded>
    <v-dialog v-model="registOpen" persistent>
      <template v-slot:activator="{ props }">
        <v-btn variant="outlined" rounded="0" v-bind="props" block>반려동물 등록</v-btn>
      </template>
      <v-card>
        <v-card-title>
          <h3>반려동물 등록</h3>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="petRegistInfo.petName" label="이름" variant="underlined" required></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-select v-model="petRegistInfo.petType" :items="['개', '고양이', '토끼', '패럿', '기니피그', '햄스터']" label="종류" variant="underlined" required></v-select>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="petRegistInfo.petVariety" label="품종" variant="underlined"></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-radio-group v-model="knowBirth" color="primary" density="compact" inline>
                  <v-label>생일</v-label>&ensp;
                  <v-radio label="알아요" value="0"></v-radio>&ensp;
                  <v-radio label="몰라요" value="1"></v-radio>
                </v-radio-group>
              </v-col>
              <v-col cols="12" sm="6" md="4" v-show="knowBirth == '0'">
                <v-text-field v-model="petRegistInfo.petBirth" label="년" variant="underlined"></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4" v-show="knowBirth == '0'">
                <v-select v-model="petRegistInfo.petType" :items="['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']" label="월" variant="underlined" required></v-select>
              </v-col>
              <v-col cols="12">
                <v-textarea v-model="petRegistInfo.petInfo" label="특이사항" variant="underlined"></v-textarea>
              </v-col>
              <v-col cols="12">
                <v-file-input v-model="petRegistInfo.petImage" accept="image/png, image/jpeg, image/bmp" prepend-icon="mdi-camera" label="사진" variant="underlined"></v-file-input>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="registOpen = false">Close</v-btn>
          <v-btn color="blue-darken-1" variant="text" @click="registOpen = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-sheet>

</template>

<script>
export default {
  name: 'MyPagePartnerPet',
  data() {
    return {
      registOpen: false,
      knowBirth: "0",
      petRegistInfo: {
        petBirth: null,
        petImage: null,
        petInfo: null,
        petName: null,
        petType: null,
        petVariety: null,
      },
      petRules: [

      ]
    }
  },
  methods: {
    registPetOpen() {

    }
  }
}
</script>

<style>

</style>