import ConsultantAccept from "@/components/AdminPage/ConsultantAccept.vue";
import ConsultantAcceptDetail from "@/components/AdminPage/ConsultantAcceptDetail.vue";
import UserWithdrawal from "@/components/AdminPage/UserWithdrawal.vue";
import UserAlarm from "@/components/AdminPage/UserAlarm.vue";

const admin = [
  {
    path: "/consultant-accept",
    name: "consultantAccept",
    component: ConsultantAccept
  },
  {
    path: "/consultant-accept/detail",
    name: "consultantAcceptDetail",
    component: ConsultantAcceptDetail
  },
  {
    path: "/user-withdrawal",
    name: "userWithdrawal",
    component: UserWithdrawal
  },
  {
    path: "/user-alarm",
    name: "userAlarm",
    component: UserAlarm
  },
];

export default admin;
