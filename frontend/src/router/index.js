import { createRouter, createWebHistory } from 'vue-router'
const routes = [
    {
        path: '/unit/:id',
        name: 'Unit',
        component: () => import('@/views/Unit.vue'),
        props: true,
    },
    {
        path: '/root/:id/:name',
        name: 'Root',
        component: () => import('@/views/Root.vue'),
        props: true
    },
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
    },

]
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})
export default router