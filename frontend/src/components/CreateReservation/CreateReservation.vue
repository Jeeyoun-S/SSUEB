<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner">
      <div class="page-inner-title max-page border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-text-box-plus</v-icon>
        <h2>반려동물 상담 신청하기</h2>
      </div>
      <div class="page-inner-items max-page border-sheet-four pa-5">
        <v-form class="max-page" ref="reservationForm">
          <v-row class="d-flex flex-row justify-space-around">
            <v-hover v-slot="{ isHovering, props }">
              <v-card class="ma-3 mb-2 mr-1 pa-2 d-flex justify-space-between flex-column"
                width="700" height="430" variant="outlined"
                :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
                rounded="0" v-bind="props"
              >
                <v-card-title><h4>상담할 반려동물 선택하기</h4></v-card-title>
                <!-- {{ selectedPet }} 선택한 반려동물 보기 -->
                <v-item-group selected-class="bg-primary" v-model="selectedPet" mandatory>
                  <v-container>
                    <v-sheet height="290">
                      <v-row class="dflex flex-row justify-center align-center">
                        <v-col v-for="(pet, index) in historyList" :key="index" height="120" width="305">
                          <v-item v-slot="{ selectedClass, toggle }" :value="pet">
                            <v-card :class="['pa-5 d-flex flex-row', selectedClass]"
                              dark height="130" width="305" @click="toggle" variant="outlined" rounded="0"
                            >
                              <v-card-item class="pa-0">
                                <img width="80" :src="require('@/assets/placeholder/placeholder_dog.png')" />
                              </v-card-item>
                              <v-card-item>
                                <v-card-title>{{ pet.petName }}</v-card-title>
                                <v-card-subtitle>{{ pet.petBirth }}</v-card-subtitle>
                                <v-card-subtitle>{{ pet.petType }}</v-card-subtitle>
                                <v-card-subtitle>{{ pet.petVariety }}</v-card-subtitle>
                              </v-card-item>
                            </v-card>
                          </v-item>
                        </v-col>
                      </v-row>
                    </v-sheet>
                    <v-pagination class="pagination mt-3"
                      v-model="page" :length="pages" @update:modelValue="updatePage"
                      rounded="0" size="32" density="compact"
                    >
                    </v-pagination>
                  </v-container>
                </v-item-group>
              </v-card>
            </v-hover>
            <v-hover v-slot="{ isHovering, props }">
              <v-card class="ma-3 mb-2 pa-2 d-flex justify-space-between flex-column"
                width="305" height="430" variant="outlined"
                :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
                rounded="0" v-bind="props"
              >
                <v-card-title><h4>상담 시간 선택하기</h4></v-card-title>
                <v-card-item>
                  <!-- {{ date.toISOString() }} 선택한 시간 정보 보기 -->
                  <DatePicker v-model="date" mode="dateTime" timezone="Asia/Pyongyang" color="indigo" :minute-increment="10"></DatePicker>
                </v-card-item>
              </v-card>
            </v-hover>
          </v-row>
          <v-row class="d-flex flex-row justify-space-around">
            <v-hover v-slot="{ isHovering, props }">
              <v-card class="ma-3 mr-1 pa-2 d-flex justify-flex-start flex-column"
                width="585" height="360" variant="outlined"
                :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
                rounded="0" v-bind="props"
              >
                <v-card-title class="pa-0 ml-4 mr-4 mt-2 mb-2"><h4>상담 내용 작성하기</h4></v-card-title>
                <v-card-item class="pa-0 ml-4 mr-4 mb-2">
                  <v-textarea class="mt-2 mb-1"
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
                width="410" height="360" variant="outlined"
                :elevation="isHovering ? 8 : 0" :class="{ 'on-hover': isHovering }"
                rounded="0" v-bind="props"
              >
                <v-card-title></v-card-title>
                <v-card-item>
                  <p>
                    상담 예약을 위해, 기입한 반려동물의 정보가
                    모든 전문가 회원에게 상담일정 확정 시점까지
                    공개됩니다.
                  </p>
                  <v-radio-group v-model="radio1"
                    :rules="[(v) => v == 'radio1-yes' || '동의는 필수입니다.']"
                    color="primary" inline
                  >
                    <v-label color="black">이에 동의하십니까?<sup>*</sup></v-label>
                    <v-radio label="동의" value="radio1-yes"></v-radio>
                    <v-radio label="비동의" value="radio1-no"></v-radio>
                  </v-radio-group>
                </v-card-item>
                <v-card-item>
                  <p>
                    상담 등록시, 이와 관련된 알람을 보내드립니다.
                  </p>
                  <v-radio-group v-model="radio2" color="primary" 
                  :rules="[(v) => v == 'radio2-yes' || '동의는 필수입니다.']"
                    inline
                  >
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
          </v-row>
        </v-form>
      </div>
    </div>
  </div>
</template>

<script>
import NowLoading from '@/views/NowLoading.vue';
import { mapState } from "vuex";
import { DatePicker } from 'v-calendar';
import moment from 'moment';
import { apiInstance } from "@/api/index.js";
const reservationStore = "reservationStore";
const userStore = "userStore";
import router from "@/router/index.js";

export default {
  name: "CreateReservation",
  computed: {
    ...mapState(userStore, ["userId"]),
    ...mapState(reservationStore),
    pages() {
      if (this.pageSize == null || this.listCount == null) return 0;
      return Math.ceil(this.listCount / this.pageSize);
    }
  },
  components: {
    DatePicker,
    NowLoading
  },
  data: () => ({
    loaded: false,
    files:null,
    // 반려동물 목록에 필요한 데이터
    page: 1,
    pageSize: 4,
    petList: [],
    listCount: 0,
    historyList: [],
    rules: [
      (v) => v != null || "필수 입력 사항입니다.",
      (v) => v.length <= 500 || "최대 500자"
    ],
    radio1: "radio1-no",
    radio2: "radio2-no",
    reservation:{
      userId: null,
      reservationPetNo: 0,
      reservationDate: null, //YYYY-MM-DD HH-mm-ss ex)2023-02-22 22:22:22
      reservationConsultContent:null,
    },
    selectedPet: null,
    date: new Date(),
    timeList: [],
  }),
  methods: {
    async registed() {
      const { valid } = await this.$refs.reservationForm.validate();
      if (valid) {
        this.createReservation();
      }
    },
    async createReservation() {
      // process.env.VUE_APP_API_BASE_URL -> baseurl env파일에서 호출
      //날짜 timestamp형식으로
      this.reservation.reservationDate = this.date.getFullYear()+"-"+(this.date.getMonth()+1)+"-"+this.date.getDate()+" "+
          this.date.getHours()+":"+this.date.getMinutes()+":"+this.date.getSeconds();
      this.reservation.reservationPetNo = this.selectedPet.no;
      this.reservation.userId = this.userId;

      for(let i=0; i<this.timeList.length; i++){
        const diff = 
              moment.duration(moment(this.reservation.reservationDate, "YYYY-MM-DD HH:mm:ss").diff(moment(this.timeList[i], "YYYY-MM-DD HH:mm:ss"))).asMinutes();
        if(-30 < diff && diff < 30){
          //console.log("근처 시간대 존재");
          this.$swal.fire(
          '상담 등록 실패',
          '신규 상담 등록이 실패하였습니다. <br /> 등록한 시간대 30분 이내에 상담이 있습니다. <br /> 시간을 다시 선택해 주세요.',
          'error'
          )
          return;
        }
      }

      const frm = new FormData();
      frm.append("reservation",  new Blob([ JSON.stringify(this.reservation) ], {type : "application/json"}));

      if(this.files){
        console.log("파일있음")
        for(let i=0; i<this.files.length; i++){
          if(this.files[i].type.startsWith('image')){
            if(this.files[i].size <= 3*1024*1024){//사진은 3메가
              frm.append("files", this.files[i]);
            }
            else{
              this.$swal.fire(
              '상담 등록 실패',
              '신규 상담 등록이 실패하였습니다. <br /> 이미지 파일의 크기가 3MB이상입니다.',
              'error'
              )
              return;
            }
          }
          else if(this.files[i].type.startsWith('video')){
            if(this.files[i].size <= 50*1024*1024){//비디오는 50메가
              frm.append("files", this.files[i]);
            }
            else{
              this.$swal.fire(
              '상담 등록 실패',
              '신규 상담 등록이 실패하였습니다. <br /> 영상 파일의 크기가 50MB이상입니다.',
              'error'
              )
              return;
            }
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

      const api = apiInstance();
      await api.post(process.env.VUE_APP_API_BASE_URL+`/reservation`, frm, {
        headers: {'Content-Type': 'multipart/form-data'}
      }).then(() => {
        this.$swal.fire(
          '상담 등록 완료',
          '신규 상담 등록이 완료되었습니다. <br /> 전문가가 상담에 대한 제안을 보내면 받은 상담 제안 메뉴에서 확인 후 수락해 상담 예약을 확정할 수 있습니다.<br /> 상담 제안이 오면 메인페이지의 알림창에서 확인이 가능합니다.',
          'success'
        )
        router.push("/receive-matching");
      }).catch(error => {
        console.log(error.message)
        this.$swal.fire(
          '상담 등록 실패',
          '신규 상담 등록이 실패하였습니다. <br /> 첨부한 파일, 반려동물 선택에 문제가 있는지 확인해주십시오.',
          'error'
        )
        return;
      })
    },

    async petInfo(){
      var result = true;
      const api = apiInstance();
      await api.get(`${process.env.VUE_APP_API_BASE_URL}/reservation/pet-list/${this.userId}`)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            let petinfo={};
            petinfo["no"] = data[i].no;
            petinfo["petName"] = data[i].petName;
            petinfo["petImage"] = data[i].petImage;
            petinfo["petType"] = data[i].petType;
            petinfo["petVariety"] = data[i].petVariety;
            if(data[i].petBirth != null){
              petinfo["petBirth"] = data[i].petBirth.substr(0,7);
            }
            else{
              petinfo["petBirth"] = "생년월일 미상";
            }
            this.petList.push(petinfo);
          }
          console.log(this.petList);
        })
        .catch((err) => {
          console.log(err);
        })
        return await Promise.resolve(result);
      },
    async getTimeList(){
      const api = apiInstance();
      await api.get({
        url: process.env.VUE_APP_API_BASE_URL+`/reservation/date-validation/`+`aa@a`,
      })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            this.timeList.push(data[i]);
          }
          //console.log(this.timeList);
        })
        .catch((err) => {
          console.log(err);
        })
    },
    initPage: function() {
			this.listCount = this.petList.length;
			if (this.listCount < this.pageSize) {
				this.historyList = this.petList;
			} else {
				this.historyList = this.petList.slice(0, this.pageSize);
			}
		},
		updatePage: function(pageIndex) {
			let _start = (pageIndex - 1) * this.pageSize;
			let _end = pageIndex * this.pageSize;
			this.historyList = this.petList.slice(_start, _end);
			this.page = pageIndex;
		}
  },

  watch: {
    files() {
      // 등록한 파일 이름 짧게 수정하기
      if (this.files != null) {
        var maxLength = 30; // 파일명 길이는 default 30
        if (this.files.length > 4) maxLength = 6; // 첨부한 파일이 4개 이상인 경우 길이 6
        else if (this.files.length > 2) maxLength = 11; // 첨부한 파일이 2개 이상인 경우 길이 11

        // 첨부한 전체 파일 반복
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
  },

  async created() {
    this.loaded = false;
    try {
      await this.petInfo().then((res) => {
        this.loaded = res;
      });
      this.getTimeList();
      this.initPage();
      this.updatePage(this.page);
    } catch (e) {
      console.error("# 회원정보 조회 오류", e);
    }
  }
};
</script>

<style></style>
