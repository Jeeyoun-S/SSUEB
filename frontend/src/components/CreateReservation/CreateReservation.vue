<template>
  <div>
    <v-sheet class="a-sheet" width="550" height="350" elevation="2" rounded>
      <div>
        <h3>&nbsp; 상담할 반려동물 선택하기</h3>
        <div class="text-center">
          <img
            width="100"
            :src="require('@/assets/placeholder/placeholder_dog.png')"
          />
          <h4>깜찍한 로이</h4>
        </div>
        <v-pagination v-model="page" :length="6"></v-pagination>
      </div>
    </v-sheet>

    <v-sheet class="a-sheet" width="550" height="350" elevation="2" rounded>
      <div class="text-center">
        <h1>시간 선택</h1>
      </div>
    </v-sheet>
  </div>

  <div>
    <v-sheet class="a-sheet" width="500" height="350" elevation="2" rounded>
      <h3>&nbsp; 상담 내용 작성하기</h3>

      <v-container fluid>
        <v-textarea
          label="상담내용 작성하기 (최대 500자)"
          :rules="rules"
          no-resize
          clearable
          clear-icon="mdi-close-circle"
          counter
          variant="outlined"
          rows="5"
          row-height="15"
          maxlength="500"
          oninput="javascript: if (this.value.length > this.maxLength) 
            this.value = this.value.slice(0, this.maxLength);"
          v-model="reservation.reservationConsultContent"
        ></v-textarea>
      </v-container>

      <!--파일 인풋 많이 하면 화면 넘어감...-->
      <v-file-input
        v-model="files"
        counter
        multiple
        show-size
        chips
        truncate-length="15"
        accept="video/*, image/*"
      ></v-file-input>
      <v-btn variant="flat" color="primary" @click="select"> 파일 보내기 </v-btn>
    </v-sheet>
    <v-sheet class="a-sheet" width="500" height="350" elevation="2" rounded>
      <br />
      <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 상담 예약을 위해, 상담일정 확정 시점까지
        기입한 반려동물의 정보가 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 모든 반려동물행동지도사 회원에게
        공개됩니다. (필수) <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 이에 동의하십니까?
      </p>
      <v-container fluid>
        <v-radio-group v-model="radio1" inline color="primary">
          <v-radio label="동의" value="radio1-yes"></v-radio>
          <v-radio label="비동의" value="radio1-no"></v-radio>
        </v-radio-group>
        <br />
        <p>
          &nbsp;상담 제의 등록시, 이와 관련된 알람을 보내드립니다. (필수) <br />
          &nbsp;이에 동의하십니까?
        </p>
        <v-radio-group v-model="radio2" inline color="primary">
          <v-radio label="동의" value="radio2-yes"></v-radio>
          <v-radio label="비동의" value="radio2-no"></v-radio>
        </v-radio-group>
        <div class="register-btn">
          <v-btn variant="flat" color="primary" @click="registed"> 신청하기 </v-btn>
        </div>

      </v-container>
    </v-sheet>
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
      userId: "aa@a",
      reservationPetNo: 2,
      reservationDate: "2023-02-22 22:22:22", //YYYY-MM-DD HH-mm-ss ex)2023-02-22 22:22:22
      reservationConsultContent:"까비가 아파요",
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
        const BASE_URL = `http://localhost:5000/api`;
        // process.env.VUE_APP_API_BASE_URL
        
        const frm = new FormData();
        frm.append("reservation",  new Blob([ JSON.stringify(this.reservation) ], {type : "application/json"}));

        if(this.files){
          console.log("파일있음")
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
        }

        console.log(frm);
        
        axios.post(BASE_URL+`/reservation`, frm, {
          headers: {'Content-Type': 'multipart/form-data'}
        }).then(() => {
          console.log("업로드 완료!")
        }).catch(error => {
          alert(error.message)
        })
      }
  }
};
</script>

<style scoped>
.a-sheet {
  margin: 15px;
}
.register-btn {
  display: flex;
  justify-content: flex-end;
}
</style>
