<template>
    <div>
    </div>
</template> 
<script>
import {mapState} from "vuex";
// import RoomRtc from "./RooRtc.vue";
const roomStore = "roomStore";
export default{
    name : "room-window",
    components:{
        // RoomRtc,
    },
    computed:{
        ...mapState(roomStore,["roomToken"])
    },
    methods:{
        openPortal(){
        //null에서 바뀐경우만 띄우기.
        console.log("openPortal")
        console.log(this.what);
        window.open("", "", "width=600,height=400,left=200,top=200");
        // this.windowRef.document.body.appendChild(this.$el);
            
            
        },
    },
    created(){
    //   console.log("creatd::::::")  
    },
    mounted(){
        this.openPortal();
    },
    data(){
        return{
            what:"???"
        }
    }

}

</script>
<style scoped>

</style>
