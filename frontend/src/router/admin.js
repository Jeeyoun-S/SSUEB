import ConsultantAccept from "@/components/AdminPage/ConsultantAccept.vue";
import ConsultantAcceptDetail from "@/components/AdminPage/ConsultantAcceptDetail.vue";

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
];

export default admin;
