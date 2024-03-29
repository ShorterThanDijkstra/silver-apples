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
        path: '/make-sentences/:spell',
        name: 'MakeSentences',
        component: () => import('@/views/WordSentences.vue'),
        meta: {
            requireAuth: true,
        },
        props: true
    },
    {
        path: '/custom-words',
        name: 'UserCustomWords',
        component: () => import('@/views/UserCustomWords.vue'),
        meta: {
            requireAuth: true,
        },
        props: true
    },
    {
        path: '/user-statistic/:username',
        name:'UserStatistic',
        component:() => import('@/views/UserStatistic.vue'),
        meta: {
            requireAuth: true,
        },
        props: true
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
    },
    {
        path: '/reset-password-request',
        name: 'ResetPasswordRequest',
        component: () => import('@/views/ResetPasswordRequest.vue')
    },
    {
        path: '/change-password-complete',
        name: 'ResetPasswordComplete',
        component: () => import('@/views/ResetPasswordComplete.vue')
    },
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