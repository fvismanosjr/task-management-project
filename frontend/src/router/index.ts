import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/pages/auth/LoginPage.vue'
import RegisterPage from '@/pages/auth/RegisterPage.vue'
import BoardPage from '@/pages/board/BoardPage.vue'
import BoardProfile from '@/pages/board/BoardProfilePage.vue'
import { checkIfAuthenticated } from '@/services/auth'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/login",
            name: "login",
            component: LoginPage,
            meta : {
                guestOnly: true,
            }
        },
        {
            path: "/register",
            name: "register",
            component: RegisterPage,
            meta : {
                guestOnly: true,
            }
        },
        {
            path: "/board",
            name: "board",
            component: BoardPage
        },
        {
            path: "/board/:id",
            name: "board-profile",
            component: BoardProfile,
        }
    ],
})

router.beforeEach(async (to) => {
    const authResult = await checkIfAuthenticated();

    if (to.meta.guestOnly) {
        if (to.name === "login" && authResult) {
            return { name: "dashboard" };
        } else return true;
    }

    if (!authResult) {
        return { name: "login" };
    }
})

export default router
