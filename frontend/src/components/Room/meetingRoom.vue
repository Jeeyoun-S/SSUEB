<template>
  <div v-if="roomToken !== null" id="video-bound" class="border-sheet-one">
    <v-sheet id="video-main">
      <!-- <v-col cols="8"> -->
      <v-sheet id="main-video">
        <UserVideo v-if="toggleVideo" id="main-video-stream" :stream-manager="mainStreamManager" />
        <div v-if="!toggleVideo">
          <UserVideo id="main-video-stream" v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
            :stream-manager="sub" />
        </div>
      </v-sheet>
      <!-- </v-col> -->
      <!-- <v-col> -->
        <div id="chatbox" class="border-sheet-one">
          <div id="chatbox-body">
            <div class="up-content">
              <div>
                <div>
                  <div v-for="(item, index) in chat" :key="index">
                    <div v-if="item.from === userInfo.userNickname" class="one-chat black--text">
                      <div class="chat-title">{{ item.from }}({{ item.time }})</div>
                      <div>{{ item.msg }}</div>
                    </div>
                    <div v-if="item.from !== userInfo.userNickname" class="one-chat blue--text">
                      <div class="chat-title">{{ item.from }}({{ item.time }})</div>
                      <div>{{ item.msg }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <v-row class="footer">
            <v-col>
              <div class="chat-input d-flex flex-row align-center">
                <v-text-field v-model="msg" placeholder="채팅을 입력하세요"
                  @keypress.enter="sendMsg" variant="outlined" append-inner-icon="mdi-send"
                  @click:append-inner="sendMsg"
                ></v-text-field>

                <!-- <v-text-field class="" v-model="msg" placeholder="채팅을 입력하세요" @keypress.enter="sendMsg" variant="outlined"></v-text-field> -->
                <!-- <v-btn icon class="chat-input-icon" @click="sendMsg"><v-icon>mdi-send</v-icon></v-btn> -->
              </div>
            </v-col>
          </v-row>
        </div>
      <!-- </v-col> -->
    </v-sheet>
    <v-sheet id="video-option">
      <v-sheet id="options">
        <div class="border-sheet-one sheet">
          <LogoVer2></LogoVer2>
        </div>
        <!-- <div id="room-logo">
          <img class="room-logo-img" :src="logoImageVer2" />
        </div> -->
        <v-dialog v-model="dialog" width="800px" height="800px">
          <template v-slot:activator="{ props }">
            <!-- <div> -->
              <v-btn color="primary" prepend-icon="mdi-book-open-blank-variant"
                variant="elevated" rounded="0" v-bind="props"
              >상담 내역 보기</v-btn>
              <!-- <div class="option-btn" v-bind="props"> -->
                <!-- 상담내역보기: 클릭시 모달창이벤트. -->
                <!-- <div class="option-svg"> <svg-icon type="mdi" :path="pathReservation"></svg-icon></div> -->
                <!-- <div v-if="!toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathOffCamera"></svg-icon></div> -->
                <!-- <div class="option-text">상담내역보기</div> -->
              <!-- </div> -->
            <!-- </div> -->
          </template>
          <v-card class="pa-5">
            <v-card-title>
              <h2>상담 관련 정보</h2>
            </v-card-title>
            <v-card-item class="pb-3">
              <template v-slot:prepend>
                <v-avatar color="#06BEE1" size="100">
                  <span v-if="reservation.petImage == null">{{ reservation.petName }}</span>
                  <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
                </v-avatar>
              </template>
              <v-card-title>{{ reservation.petName }}</v-card-title>
              <v-card-subtitle>
                <p>{{ reservation.petType }} ({{ reservation.petBirth }})</p>
                <p>{{ reservation.petVariety }} </p>
              </v-card-subtitle>
              <v-card-text class="pa-0">
                {{ reservation.petInfo }}
              </v-card-text>
              <!-- <p>{{ reservation.petInfo }}</p> -->

              <!-- <v-avatar color="#06BEE1" size="100">
                <span v-if="reservation.petImage == null">{{reservation.petName}}</span>
                <img v-else :src="getImageUrl(reservation.petImage)" height="100" width="100" />
              </v-avatar> -->
              <!-- <v-avatar class="pt-1" color="white" size="100">
                <img width="100" :src="require('@/assets/placeholder/placeholder_dog.png')" />
              </v-avatar> -->
            </v-card-item>

            <!-- <v-card-title>{{ reservation.reservationDate }}</v-card-title> -->
            <!-- <v-card-title>
              <p>{{ reservation.petName }} ({{ reservation.petBirth }})</p>
              <p>
                {{ reservation.petType }}
                <span v-show="reservation.petVariety != null">-</span>
                {{ reservation.petVariety }}
              </p>
              <p>
                {{ reservation.petInfo }}
              </p>
            </v-card-title> -->
            <v-card-title>
              <h3>상담 신청 내용</h3>
            </v-card-title>
            <v-card-text>{{ reservation.reservationConsultContent }}</v-card-text>
            <v-card-title>
              <h3>상담 제안 내용</h3>
            </v-card-title>
            <v-card-text><span class="bold-font mr-3">가격</span>{{ reservation.reservationCost }}</v-card-text>
            <v-card-text>{{ reservation.reservationReason }}</v-card-text>


            <!-- <v-card-text>
              <h2>상담내용</h2>
              {{ reservation.reservationConsultContent }}
              <h2>견적/측정사유</h2>
              {{ reservation.reservationCost }}/{{ reservation.reservationReason }}
            </v-card-text> -->
            <v-card-item>
            </v-card-item>
            <v-card-actions>
              <SeeAttatchedFiles :reservation="reservation" />
              <v-btn class="ms-auto" color="error" @click="dialog = false">닫기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>

        <v-btn color="primary" :prepend-icon="toggleCamera ? 'mdi-camera' : 'mdi-camera-off'"
          :variant="toggleCamera ? 'elevated' : 'outlined'" @click="muteCamera" rounded="0"
        >카메라 ON/OFF</v-btn>
        <v-btn color="primary" :prepend-icon="toggleAudio ? 'mdi-microphone' : 'mdi-microphone-off'"
          :variant="toggleAudio ? 'elevated' : 'outlined'" @click="muteAudio" rounded="0"
        >마이크 ON/OFF</v-btn>
        <v-btn color="primary" prepend-icon="mdi-arrow-collapse"
          variant="elevated" @click="updateMainVideo" rounded="0"
        >화면 전환</v-btn>
        <v-btn color="primary" prepend-icon="mdi-exit-to-app"
          variant="elevated" @click="leaveSession" rounded="0"
        >상담 종료하기</v-btn>
        <!-- <div>
          <div class="option-btn" >
            <div v-if="toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathCamera"></svg-icon></div>
            <div v-if="!toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathOffCamera"></svg-icon></div>
            <div class="option-text">카메라 ON/OFF</div>
          </div>
        </div> -->
        <!-- <div>
          <div class="option-btn" @click="muteAudio">
            <div v-if="toggleAudio" class="option-svg"> <svg-icon type="mdi" :path="pathMicro"></svg-icon></div>
            <div v-if="!toggleAudio" class="option-svg"> <svg-icon type="mdi" :path="pathOffMicro"></svg-icon></div>
            <div class="option-text">마이크 ON/OFF</div>
          </div>
        </div> -->
        <!-- <div>
          <div class="option-btn" @click="updateMainVideo">
            <div class="option-svg"> <svg-icon type="mdi" :path="pathChange"></svg-icon> </div>
            <div class="option-text">화면전환</div>
          </div>
        </div> -->
        <!-- <div>
          <div class="option-btn " @click="leaveSession">
            <div class="option-svg"><svg-icon type="mdi" :path="pathExit"></svg-icon> </div>
            <div class="option-text">회의실나가기</div>
          </div>
        </div> -->
      </v-sheet>
      <!-- {{ reservation }} -->
      <v-sheet id="sub-video">
        <div v-if="toggleVideo">
          <UserVideo id="subvideo" v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
            :stream-manager="sub" />
        </div>
        <UserVideo v-if="!toggleVideo" id="subvideo" :stream-manager="mainStreamManager" />
      </v-sheet>
    </v-sheet>
  </div>
</template>
<script>
import LogoVer2 from "@/views/LogoVer2.vue";
import { mapMutations, mapState, mapActions } from "vuex"
import UserVideo from "./UserVideo.vue"
// import ChatBox from "./ChatBox.vue"
import { OpenVidu } from "openvidu-browser";
import { joinRoomSession } from "@/api/room";
// import SvgIcon from '@jamescoyle/vue-icon';
import SeeAttatchedFiles from '../SeeAttachedFiles/SeeAttatchedFiles.vue'
import { mdiBookOpenBlankVariant, mdiMicrophoneOff, mdiCameraOff, mdiCamera, mdiSwapHorizontal, mdiMicrophone, mdiExitToApp } from '@mdi/js';
// import LogoVer2 from "@/views/LogoVer2.vue";
const roomStore = "roomStore";
const userStore = "userStore";
export default {
  name: "meeting-room",
  components: {
    UserVideo,
    // SvgIcon,
    SeeAttatchedFiles,
    LogoVer2
  },
  computed: {
    ...mapState(roomStore, ["onAir", "reservation"]),
    ...mapState(userStore, ["userInfo", "userAuth"])
  },
  created() {

    this.initialize()
    // this.session.on('signal:my-chat',(event)=>{
    //   console.log(event.data); // Message
    //   console.log(event.from); // Connection object of the sender
    //   console.log(event.type); // The type of message ("my-chat")
    // })
  },
  methods: {
    ...mapMutations(roomStore, ["UPDATE_ONAIR"]),
    ...mapActions(roomStore, ["setMeetingReservation"]),
    initialize() {
      console.log("meetingRoom 초기화")
      console.log("THISOV: ", this.OV)
      // this.reservation = this.$route.params
      // alert(this.reservation)
      if (this.OV === undefined) {
        console.log("영상 설정 초기화 및 연결")
        //userName가져오기
        joinRoomSession(this.reservation.rno + "",
          (data) => {
            console.log("this.roomToken : ", this.roomToken);
            // this.UPDATE_TOKEN(data.data);
            this.roomToken = data.data
            console.log("this.roomToken: ", this.roomToken);
            this.roomInitialize()
            this.UPDATE_ONAIR(true);

          },
          (error) => {
            // console.log(error);
            //  this.timealert();
            //유효성(권한, 시간)에러

            this.timealert(error.response.data);
            // this.$router.push("/confirmed")
            location.href = `${process.env.VUE_APP_BASE_URL}/confirmed`;
          })

      }
    },
    timealert(msg) {
      this.$swal.fire(
        '화상 상담 입장 대기',
        msg,
        'warning')
    },
    leaveAlert() {
      if (this.userAuth === 'ROLE_USER') {
        this.$swal.fire(
          '상담이 끝나셨나요?',
          `지금 리뷰를 작성해보세요!`
        )
      } else {
        this.$swal.fire(
          '상담이 끝나셨나요?',
          `지금 상담내역을 작성해보세요!`
        )
      }
    },
    roomInitialize() {

      console.log("roomInit: ", this.roomToken)
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // this.sessino.on("SessionDisconnected",()=>{
      //   console.log("세션나가기 로그가 찍히는가?")

      //   this.$router.push("finished-reservation")
      // })

      // Receiver of the message (usually before calling 'session.connect')
      this.session.on('signal:my-chat', (event) => {
        console.log("메시지도착 : ")
        let msg = JSON.parse(event.data)
        // console.log(event.data); // Message
        // console.log(event.from); // Connection object of the sender
        // console.log(event.type); // The type of message ("my-chat")
        this.chat.push(
          msg
        )
      })


      // --- 4) Connect to the session with a valid user token ---
      this.session.connect(this.roomToken, { clientData: this.userInfo.userNickname })
        .then(() => {

          // --- 5) Get your own camera stream with the desired properties ---

          // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
          // element: we will manage it on our own) and with the desired properties
          let publisher = this.OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });

          // Set the main video in the page to display our webcam and store our Publisher
          this.mainStreamManager = publisher;
          this.publisher = publisher;

          // --- 6) Publish your stream ---
          this.session.publish(this.publisher);

        })
        .catch((error) => {
          console.log("There was an error connecting to the session:", error.code, error.message);
        });
      window.addEventListener("beforeunload", this.leaveSession);

    },
    sendMsg() {
      if (this.msg != null) {

        let today = new Date();
        // console.log(today.getHours());
        // console.log(today.getMinutes());
        // console.log(today.getSeconds());
        let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()

        let msgData = {
          //로그인 유저 정보로 수정해야함.
          from: this.userInfo.userNickname,
          time: time,
          msg: this.msg
        }

        //상대에게보내기:
        this.session.signal({
          data: JSON.stringify(msgData),
          to: [],
          type: "my-chat"
        }).then(() => {
          console.log("Message successfully sent")
        })
          .catch(error => {
            console.log(error)
          })

        //내꺼에 추가.
        // msgData.from ="당신"
        // this.chat.push(
        //   //추가구현 필요:
        //   //x축 넘어가지않게 줄 띄우기
        //   // 가장 최신 메시지로 스크롤바 고정.
        //   msgData
        //   )
        this.msg = null;
      }
    },
    async leaveSession() {
      this.$swal.fire({
        title: '상담 종료하기',
        html:'상담을 종료하시면 <b>재입장 불가능</b>합니다.<br>상담을 종료하시겠습니까?',
        icon: 'warning',
        showDenyButton: true,
        confirmButtonText: '종료',
        denyButtonText: '취소',
      }).then((result) => {
        if (result.isConfirmed) {
          this.setMeetingReservation({});
          // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
          console.log("leavesession : session ", this.session)
          if (this.session) this.session.disconnect();
    
          // Empty all properties...
          this.session = undefined;
          this.mainStreamManager = undefined;
          this.publisher = undefined;
          this.subscribers = [];
          this.OV = undefined;
          this.UPDATE_ONAIR(false)
          // Remove beforeunload listener
          window.removeEventListener("beforeunload", this.leaveSession);
          this.leaveAlert();
          location.href = `${process.env.VUE_APP_BASE_URL}/finished-reservation`;
          // this.$router.push("/finished-reservation");
        }
      })
    },

    updateMainVideo() {
      // if (this.mainStreamManager === this.publisher){
      //   this.mainStreamManager = this.subscribers[0]
      // }else{
      //   this.mainStreamManager = this.publisher
      // }
      if (this.toggleVideo) {
        this.toggleVideo = false
      } else {
        this.toggleVideo = true
      }

    },

    muteCamera() {
      if (this.toggleCamera) {
        this.toggleCamera = false
      } else {
        this.toggleCamera = true
      }
      this.publisher.publishVideo(this.toggleCamera);
    },
    muteAudio() {
      if (this.toggleAudio) {
        this.toggleAudio = false
      } else {
        this.toggleAudio = true
      }
      this.publisher.publishAudio(this.toggleAudio);
    },
    getImageUrl(img) {
      return `${process.env.VUE_APP_FILE_PATH_PET}${img}`;
    },
  },
  data() {

    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      roomToken: null,
      subscribers: [],
      myUserName: "Participant" + Math.floor(Math.random() * 100),

      //chatBox:
      chat: [],
      msg: null,

      //optins icons
      pathCamera: mdiCamera,
      pathMicro: mdiMicrophone,
      pathChange: mdiSwapHorizontal,
      pathExit: mdiExitToApp,
      pathOffCamera: mdiCameraOff,
      pathOffMicro: mdiMicrophoneOff,
      pathReservation: mdiBookOpenBlankVariant,
      //logimages
      // logoImageVer2: require('@/assets/logo/logo_ver2.png'),

      //mainVideo
      toggleVideo: true,

      //mutecamera
      toggleCamera: true,

      //muteAudio
      toggleAudio: true,

      //reservation
      // reservation: null,

      //modal dialog
      dialog: false,
    }

  }
}

</script>
<style>
@import "@/css/video.css";
</style>