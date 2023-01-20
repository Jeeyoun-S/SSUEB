import { createRouter, createWebHistory } from 'vue-router'
import user from '@/router/user'
import community from '@/router/community'
import meetingRoom from '@/router/meetingRoom'
import reservation from '@/router/reservation'

const routes = [
  ...user,
  ...community,
  ...meetingRoom,
  ...reservation
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
