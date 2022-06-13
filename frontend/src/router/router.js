import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/store'

const routes = [
    {
        path: '/unit/:unit',
        name: 'Unit',
        component: () => import('@/views/Unit.vue'),
        meta: {
            requireAuth: true,
        },
        props: true,
    },
    {
        path: '/root/:name',
        name: 'Root',
        component: () => import('@/views/Root.vue'),
        meta: {
            requireAuth: true,
        },
        props: true
    },
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: {
            requireAuth: true,
        },
    },
    {
        path: '/quizzes/:unit',
        props: true,
        name: 'Exercise',
        component: () => import('@/views/Exercise.vue'),
        meta: {
            requireAuth: true,
        },
    },
    {
        path: '/special/:unit',
        name: 'SpecialSection',
        component: () => import('@/views/SpecialSection.vue'),
        meta: {
            requireAuth: true,
        },
        props: true,

    },
    {
        path: '/intro',
        name: 'Introduction',
        component: () => import('@/views/TheIntroduction.vue'),
        meta: {
            requireAuth: true,
        },
    },
    {
        path: '/register-complete',
        name: 'RegisterComplete',
        component: () => import('@/views/SignUpComplete.vue'),
    },
    {
        path: '/register-request',
        name: 'RegisterRequest',
        component: () => import('@/views/SignUpRequest.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/SignIn.vue')
    }

]
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})
router.beforeEach(
    (to, from, next) => {
        if (to.meta.requireAuth) {
            if (store.getters.userToken) {
                next();
            } else {
                next({
                    name: "Login",
                    query: { redirect: to.fullPath }
                })
            }
        } else {
            next()
        }
    }
)
export default router