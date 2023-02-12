<template>
          <div id="chatbox-body">
              <div class="up-content">
                    <div>
                        <div>
                            <div v-for="(item, index) in chat" :key="index">
                                <div v-if="item.from == '당신'" class="one-chat black--text">
                                  <div class="chat-title">{{ item.from}}({{item.time}})</div>
                                  <div>{{ item.msg }}</div>
                                  </div>
                                <div v-if="item.from != '당신'" class="one-chat blue--text">
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

</template>
<script>
export default{
    data(){

        return {
            chat: [],
            msg : null
        }

    },
    props:{
      session: Object,
    },

    created(){
      // this.session.on('signal:my-chat',(event)=>{
      //   console.log(event.data); // Message
      //   console.log(event.from); // Connection object of the sender
      //   console.log(event.type); // The type of message ("my-chat")
      // })
      },
    methods:{
    sendMsg(){
      if(this.msg != null){

        let today = new Date();
        console.log(today.getHours());
        console.log(today.getMinutes());
        console.log(today.getSeconds());
        let time = today.getHours() +":"+ today.getMinutes()+":"+ today.getSeconds()
        
        let msgData = {
          //로그인 유저 정보로 수정해야함.
          from : "나임",
          time : time,
          msg : this. msg
        }

        //상대에게보내기:
        this.session.signal({
          data: "이건 잘 보내짐?",
          to: [],
          type: "my-chat"
        }).then(()=>{
          console.log("Message successfully sent")
        })
        .catch(error=>{
          console.log(error)
        })

        //내꺼에 추가.
        msgData.from ="당신"
        this.chat.push(
          //추가구현 필요:
          //x축 넘어가지않게 줄 띄우기
          // 가장 최신 메시지로 스크롤바 고정.
          msgData
          )
          this.msg = null;
      }
    },

    }
    
}
</script>
<style>
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
</style>