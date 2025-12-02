import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/pages/auth/LoginPage.vue'
import RegisterPage from '@/pages/auth/RegisterPage.vue'
import BoardPage from '@/pages/board/BoardPage.vue'
import BoardProfile from '@/pages/board/BoardProfilePage.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/login",
            name: "login",
            component: LoginPage,
        },
        {
            path: "/register",
            name: "register",
            component: RegisterPage,
        },
        {
            path: "/board",
            name: "dashboard",
            component: BoardPage
        },
        {
            path: "/board/:id",
            name: "board-profile",
            component: BoardProfile,
        }
    ],
})

export default router
