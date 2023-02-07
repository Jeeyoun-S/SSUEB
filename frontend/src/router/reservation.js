import CreateReservation from "@/components/CreateReservation/CreateReservation.vue";
import ReceiveMatching from "@/components/ReceiveMatching/ReceiveMatching.vue";
import ConfirmedReservation from "@/components/Confirmed/ConfirmedReservation.vue";
import FinishedReservation from "@/components/FinishedReservation/FinishedReservation.vue";
import CreateMatching from "@/components/CreateMatching/CreateMatching.vue"
import SendMatching from "@/components/SendMatching/SendMatching.vue"

const reservation = [
  {
    path: "/create-reservation",
    name: "createReservation",
    component: CreateReservation,
  },
  {
    path: "/receive-matching",
    name: "receiveMatching",
    component: ReceiveMatching,
  },
  {
    path: "/confirmed",
    name: "confirmedReservation",
    component: ConfirmedReservation,
  },
  {
    path: "/finished-reservation",
    name: "finishedReservation",
    component: FinishedReservation,
  },
  {
    path: "/create-matching",
    name: "createMatching",
    component: CreateMatching,
  },
  {
    path: "/send-matching",
    name: "sendMatching",
    component: SendMatching,
  },

]

export default reservation