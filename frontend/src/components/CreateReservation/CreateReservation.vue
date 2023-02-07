<template>
  <div class="page max-page border-sheet-four">
    <div class="page-inner">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-text-box-plus</v-icon>
        <h2>반려동물 상담 신청하기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <v-hover v-slot="{ isHovering, props }">
          <v-card class="ma-3 pa-2 d-flex justify-space-between flex-column"
            width="450" height="273" variant="outlined"
            :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
            rounded="0" v-bind="props"
          >
            <v-card-title><h4>상담할 반려동물 선택하기</h4></v-card-title>
            <v-card-item>
              <v-pagination 
                v-model="page" :length="6" density="compact"
                rounded="0" size="32"
              >
              </v-pagination>
            </v-card-item>

          </v-card>
        </v-hover>
        <v-hover v-slot="{ isHovering, props }">
          <v-card class="ma-3 pa-2 d-flex justify-space-between flex-column"
            width="534" height="273" variant="outlined"
            :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
            rounded="0" v-bind="props"
          >
            <v-card-title><h4>상담 시간 선택하기</h4></v-card-title>
          </v-card>
        </v-hover>
        <v-hover v-slot="{ isHovering, props }">
          <v-card class="ma-3 pa-2 d-flex justify-flex-start flex-column"
            width="564" height="360" variant="outlined"
            :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
            rounded="0" v-bind="props"
          >
            <v-card-title class="pa-0 ml-4 mr-4 mt-2 mb-2"><h4>상담 내용 작성하기</h4></v-card-title>
            <v-card-item class="pa-0 ml-4 mr-4 mb-2">
              <v-textarea class="mt-2"
                label="상담내용 작성하기 (최대 500자)"
                :rules="rules" no-resize clearable
                clear-icon="mdi-close-circle"
                counter variant="outlined"
                rows="5" row-height="15" maxlength="500"
                oninput="javascript: if (this.value.length > this.maxLength) 
                  this.value = this.value.slice(0, this.maxLength);"
                v-model="reservation.reservationConsultContent"
              ></v-textarea>
              <v-file-input
                v-model="files"
                counter
                multiple
                show-size
                chips
                accept="video/*, image/*"
                variant="outlined"
              ></v-file-input>
            </v-card-item>
          </v-card>
        </v-hover>
        <v-hover v-slot="{ isHovering, props }">
          <v-card class="ma-3 pa-2 d-flex justify-center flex-column"
            width="420" height="360" variant="outlined"
            :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
            rounded="0" v-bind="props"
          >
            <v-card-title></v-card-title>
            <v-card-item>
              <p>
                상담 예약을 위해, 상담일정 확정 시점까지
                기입한 반려동물의 정보가 모든 반려동물행동지도사 회원에게
                공개됩니다.
              </p>
              <v-radio-group v-model="radio1" color="primary" inline>
                <v-label color="black">이에 동의하십니까?<sup>*</sup></v-label>
                <v-radio label="동의" value="radio1-yes"></v-radio>
                <v-radio label="비동의" value="radio1-no"></v-radio>
              </v-radio-group>
            </v-card-item>
            <v-card-item>
              <p>
                상담 등록시, 이와 관련된 알람을 보내드립니다.
              </p>
              <v-radio-group v-model="radio2" color="primary" inline>
                <v-label color="black">이에 동의하십니까?<sup>*</sup></v-label>
                <v-radio label="동의" value="radio2-yes"></v-radio>
                <v-radio label="비동의" value="radio2-no"></v-radio>
              </v-radio-group>
            </v-card-item>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn size="x-large" rounded="0" 
                color="primary" @click="registed"
                variant="tonal" class="pr-10 pl-10"
              >
                신청하기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-hover>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";

const reservationStore = "reservationStore";

export default {
  name: "CreateReservation",
  computed: {
    ...mapState(reservationStore),
  },
  data: () => ({
    files:null,
    page: 1,
    rules: [(v) => v.length <= 500 || "최대 500자"],
    radio1: "radio1-no",
    radio2: "radio2-no",
    reservation:{
      userId: null,
      reservationPetNo: 0,
      reservationPetType: null,
      reservationDate: null, //YYYY-MM-DD HH-mm-ss ex)2023-02-22 22:22:22
      reservationConsultContent:null,
    }
  }),
  methods: {

    registed() {
      this.$swal.fire(
        '상담 등록 완료',
        '신규 상담 등록이 완료되었습니다. <br /> 전문가가 상담에 대한 제안을 보내면 받은 상담 제안 메뉴에서 확인 후 수락해 상담 예약을 확정할 수 있습니다.<br /> 상담 제안이 오면 메인페이지의 알림창에서 확인이 가능합니다.',
        'success'
      )
    },

    select() {
      console.log(this.reservation.reservationConsultContent)
      if(!this.files){
        console.log("file이 없다")
        return;
      }
      const frm = new FormData();

      for(let i=0; i<this.files.length; i++){
        if(this.files[i].type.startsWith('image') || this.files[i].type.startsWith('video')){
          frm.append("files", this.files[i]);
        }
        else{
          console.log("잘못된 확장자")
        }
      }

      if(frm.getAll("files").length == 0){
        alert("유효하지 않은 파일들입니다.")
        return;
      }

      axios.post(`http://localhost:5000/api/reservation/save`, frm, {
        headers: {'Content-Type': 'multipart/form-data'}
      }).then(() => {
        console.log("업로드 완료!")
      }).catch(error => {
        alert(error.message)
      })
    }
  },
  watch: {
    files() {
      
      if (this.files != null) {
        var maxLength = 30;
        if (this.files.length > 4) maxLength = 4;
        else if (this.files.length > 2) maxLength = 11;

        for (var i=0; i<this.files.length; i++) {

          // 파일 이름 가져오기
          var filename = this.files[i].name;
    
          // 확장자
          var extension = filename
            .substring(filename.lastIndexOf("."), filename.length)
            .toLowerCase();
    
          // 파일 이름에서 확장자 없애기
          filename = filename.substring(0, filename.lastIndexOf("."));
    
          // 파일 이름 줄이기
          Object.defineProperty(
            this.files[i],
            "name",
            {
              writable: true,
              value: filename.substr(0, maxLength) + String(i) + extension,
            }
          );
        }
      }
    }
  }
};
</script>

<style scoped>
</style>
