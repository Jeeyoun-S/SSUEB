import FreeBoard from '@/components/FreeBoard/FreeBoard.vue';
import NoticeBoard from '@/components/NoticeBoard/NoticeBoard.vue';
import FreeBoardDetail from '@/components/FreeBoard/FreeBoardDetail.vue';
import OpenBoard from '@/components/OpenBoard/OpenBoard.vue';

const community = [
  {
    path: "/free-board",
    name: "freeBoard",
    component: FreeBoard,
  },
  {
    path: "/notice",
    name: "notiveBoard",
    component: NoticeBoard,
  },
  {
    path: "/free-board/detail/:no",
    name: "freeBoardDetail",
    component: FreeBoardDetail,
  },
  {
    path: "/open-consult",
    name: "OpenBoard",
    component: OpenBoard,
  },
]

export default community