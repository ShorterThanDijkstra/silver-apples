import { createApp } from 'vue'
import router from '@/router/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import store from '@/store/store'
const app = createApp(App)

app.use(store)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
