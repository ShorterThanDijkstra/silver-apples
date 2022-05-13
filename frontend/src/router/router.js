import { createRouter, createWebHistory } from 'vue-router'
const routes = [
    {
        path: '/roots',
        name: 'Unit',
        component: () => import('@/views/Unit.vue'),
        props: true,
    },
    {
        path: '/words',
        name: 'Root',
        component: () => import('@/views/Root.vue'),
        props: true
    },
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
    },
    {
        path: '/quizzes',
        name:'Exercise',
        component:() => import('@/views/Exercise.vue')
    },
    {
        path: '/special',
        name:'SpecialSection',
        component:() => import('@/views/SpecialSection.vue')
    }

]
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})
export default router