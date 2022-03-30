import { createRouter, createWebHistory } from 'vue-router'
const routes = [
    {
        path: '/',
        name: 'Content',
        component: () => import('@/views/Content.vue'),
        alias: '/content'
    },

]
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})
export default router