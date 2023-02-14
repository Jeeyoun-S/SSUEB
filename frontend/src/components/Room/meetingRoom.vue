<template>
    <div v-if="roomToken!==null" id ="video-bound" class="border-sheet-one">
      <v-row id="video-main">
        <v-col  cols="9">
        <UserVideo v-if="toggleVideo" id="main-video-stream" :stream-manager="mainStreamManager" />
        <div v-if="!toggleVideo">
          <UserVideo id="main-video-stream" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        </div>
        </v-col>
          <div id="chatbox" class="border-sheet-one">
            <div id="chatbox-body">
              <div class="up-content">
                    <div>
                        <div>
                            <div v-for="(item, index) in chat" :key="index">
                                <div v-if="item.from == '당신'" class="one-chat black--text">
                                  <div class="chat-title">{{ item.from}}({{item.time}})</div>
                                  <div>{{ item.msg }}</div>
                                  </div>
                                <div v-if="item.from != '당신'" class="one-chat blue--text">``
                                  <div class="chat-title">{{ item.from}}({{item.time}})</div>
                                  <div>{{ item.msg }}</div>
                                  </div>
                            </div>
                        </div>
                    </div>
                </div>
              <div>

            </div>
          </div>
            <v-row class="footer">
                    <!-- <v-row> -->
                        <v-col>
                          <div class="chat-input d-flex flex-row align-center">
                            <v-text-field class="" v-model="msg" placeholder="글을 입력하세요" @keypress.enter="sendMsg"></v-text-field>
                            <v-btn icon class="" @click="sendMsg"><v-icon>mdi-send</v-icon></v-btn>
                          </div>
                        </v-col>                   
                    <!-- </v-row> -->
            </v-row>
          </div>

      </v-row>
      <v-row id="video-option">
        <v-col id="options" cols="9">
          <div id="room-logo">
            <img class="room-logo-img" :src="logoImageVer2" />
          </div>
          <div>
            <div class="option-btn">
              <!-- 상담내역보기: 클릭시 모달창이벤트. -->
              <div class="option-svg"> <svg-icon type="mdi" :path="pathReservation"></svg-icon></div>
              <!-- <div v-if="!toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathOffCamera"></svg-icon></div> -->
              <div class="option-text">상담내역보기</div> 
            </div>

          </div>
          <div>
            <div class="option-btn" @click="muteCamera">
              <div v-if="toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathCamera"></svg-icon></div>
              <div v-if="!toggleCamera" class="option-svg"> <svg-icon type="mdi" :path="pathOffCamera"></svg-icon></div>
              <div class="option-text">카메라 ON/OFF</div> 
            </div>
          </div>
          <div>
            <div class="option-btn" @click="muteAudio">
             <div v-if="toggleAudio" class="option-svg">  <svg-icon type="mdi" :path="pathMicro"></svg-icon></div>
             <div v-if="!toggleAudio" class="option-svg">  <svg-icon type="mdi" :path="pathOffMicro"></svg-icon></div>
              <div class="option-text">마이크 ON/OFF</div> 
            </div>
          </div>
          <div>
            <div class="option-btn" @click="updateMainVideo">
              <div class="option-svg"> <svg-icon type="mdi" :path="pathChange"></svg-icon> </div> 
              <div class="option-text">화면전환</div> 
            </div>
          </div>
          <div>
            <div class="option-btn " @click="leaveSession"> 
              <div class="option-svg"><svg-icon type="mdi" :path="pathExit"></svg-icon> </div>              
              <div class="option-text">회의실나가기</div>
              </div>
          </div>
        </v-col>
        <v-col id="sub-video" cols="2">
          <div v-if="toggleVideo">
           <UserVideo id="subvideo" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
          </div>
          <UserVideo v-if="!toggleVideo" id="subvideo" :stream-manager="mainStreamManager" />
        </v-col>
      </v-row>
    </div>
</template>
<script>
import {mapMutations, mapState } from "vuex"
import UserVideo from "./UserVideo.vue" 
// import ChatBox from "./ChatBox.vue"
const roomStore = "roomStore";
import { OpenVidu } from "openvidu-browser";
import {joinRoomSession} from "@/api/room";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiBookOpenBlankVariant,mdiMicrophoneOff,mdiCameraOff ,mdiCamera, mdiSwapHorizontal, mdiMicrophone,mdiExitToApp} from '@mdi/js';
// import LogoVer2 from "@/views/LogoVer2.vue";

export default{
    name : "meeting-room",
    components:{
        UserVideo,
        SvgIcon,
        // LogoVer2
    },
    computed:{
        ...mapState(roomStore,["onAir"])
    },
    created(){
      this.initialize()
      // this.session.on('signal:my-chat',(event)=>{
      //   console.log(event.data); // Message
      //   console.log(event.from); // Connection object of the sender
      //   console.log(event.type); // The type of message ("my-chat")
      // })
    },
    methods:{
        ...mapMutations(roomStore,["UPDATE_ONAIR"]),

        initialize(){
            console.log("meetingRoom 초기화")
            console.log("THISOV: ",this.OV)
            if(this.OV===undefined){
                console.log("영상 설정 초기화 및 연결")
                //userName가져오기
                joinRoomSession(this.userName,
                (data)=>{
                    console.log("this.roomToken : ",this.roomToken);
                    // this.UPDATE_TOKEN(data.data);
                    this.roomToken = data.data
                    console.log("this.roomToken: ",this.roomToken);
                    this.roomInitialize()
                    this.UPDATE_ONAIR(true);
        
                },
                (error)=>{
                  // console.log(error);
                //  this.timealert();
                  //유효성(권한, 시간)에러
                
                this.timealert(error.response.data);
                this.$router.push("/confirmed")
              })

            }
        },
        timealert(msg) {
          this.$swal.fire(
              '화상 상담 입장 대기',
              msg,
            'warning') 
        },
        roomInitialize(){

            console.log("roomInit: ",this.roomToken)
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

            // Receiver of the message (usually before calling 'session.connect')
            this.session.on('signal:my-chat',(event)=>{
              console.log("메시지도착 : ")
              let msg = JSON.parse(event.data)
              console.log(event.data); // Message
              console.log(event.from); // Connection object of the sender
              console.log(event.type); // The type of message ("my-chat")
              if(msg.from !== "당신"){
                        this.chat.push(
                        msg
                       )
              }
              
            })


            // --- 4) Connect to the session with a valid user token ---
            this.session.connect(this.roomToken, { clientData: this.myUserName })
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
    sendMsg(){
      if(this.msg != null){

        let today = new Date();
        // console.log(today.getHours());
        // console.log(today.getMinutes());
        // console.log(today.getSeconds());
        let time = today.getHours() +":"+ today.getMinutes()+":"+ today.getSeconds()
        
        let msgData = {
          //로그인 유저 정보로 수정해야함.
          from : this.myUserName,
          time : time,
          msg : this. msg
        }

        //상대에게보내기:
        this.session.signal({
          data: JSON.stringify(msgData),
          to: [],
          type: "my-chat"
        }).then(()=>{
          console.log("Message successfully sent")
        })
        .catch(error=>{
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
        leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      console.log("leavesession : session ",this.session)
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
      this.$router.push("/")
    },

    updateMainVideo() {
      // if (this.mainStreamManager === this.publisher){
      //   this.mainStreamManager = this.subscribers[0]
      // }else{
      //   this.mainStreamManager = this.publisher
      // }
      if(this.toggleVideo){
        this.toggleVideo = false
      }else{
        this.toggleVideo = true
      }

    },

    muteCamera(){
      if(this.toggleCamera){
        this.toggleCamera = false
      }else{
        this.toggleCamera = true
      }
      this.publisher.publishVideo(this.toggleCamera); 
    },
    muteAudio(){
      if(this.toggleAudio){
        this.toggleAudio = false
      }else{
        this.toggleAudio = true
      }
      this.publisher.publishAudio(this.toggleAudio); 
    }
    },
    data(){
        
        return{
            OV: undefined,
            session: undefined,
            mainStreamManager: undefined,
            publisher: undefined,
            roomToken : null,
            subscribers: [],
            myUserName : "Participant" + Math.floor(Math.random()*100),

            //chatBox:
            chat: [],
            msg : null,

            //optins icons
             pathCamera: mdiCamera,
             pathMicro :mdiMicrophone,
             pathChange : mdiSwapHorizontal,
             pathExit : mdiExitToApp,
             pathOffCamera:  mdiCameraOff,
             pathOffMicro : mdiMicrophoneOff,
             pathReservation : mdiBookOpenBlankVariant,
             //logimages
              logoImageVer2: require('@/assets/logo/logo_ver2.png'),

            //mainVideo
             toggleVideo: true,

             //mutecamera
             toggleCamera : true,

             //muteAudio
             toggleAudio : true,

        }
        
    }
}

</script>
<style>

#room-logo{
  height: 100%;
  margin-right: 100px;
}

.room-logo-img{
  width: 350px;
  height: 150px;
}

#chatbox{
  margin-top: 40px;
  width: 350px;
  height:650px;
  /* margin-top: 10p; */
}

#subvideo video{
	/* position: relative;
	float: left;
	width: 50%;
	cursor: pointer;
	object-fit: cover; */
  /* margin-left: ; */
  width: 300px;
	height: 150px;
}

#main-video-stream{
  height: 100%;
}

#video-main video{
   width: 1000px;
	height: 650px; 
  margin-top: 30px;
  /* margin: 0 auto; */
}

#video-main{
  height: 80%;
  /* width: 80%; */
}

#video-option{
  height: 20%;
  /* align-items: center; */
  /* flex-direction: column; */

}

/* #video-option *{
  height: 100%;
  
} */

#options{
  display: flex;
  flex-direction: row;
  /* justify-content: space-around; */
}

#video-bound{
  border-style: solid;
  height: 900px;
  width: 1500px;
}

#sub-video{
  margin-left: 10px;
}

#chatbox-body{
  padding:20px;
  display: flex;
  flex-direction: column;
  min-height: 90%;
  overflow-y: auto;
  max-height: 90%;
  overflow-x: hidden;
  /* overflow-y: hidden; */

}

.up-content{
  font-size: 20px;
}
.chat-title{

  font-weight: bold;
  
}

.one-chat{
  margin-bottom: 10px;
}

.chat-input{
  margin-left: 20px;
  margin-right: 20px;
}

svg{
  width: 70px;
  height: 70px;
}

.option-btn{
  width: 109px;
  height: 100%;
  background-color: rgb(221, 214, 214);
  margin-left: 10px;
}

.option-btn:hover{
  background-color: gray;
}

.option-text{
  height: 40px;
}

</style>