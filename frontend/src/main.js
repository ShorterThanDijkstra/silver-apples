import { createApp } from 'vue'
import router from '@/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

const backend = "http://localhost:8080/api/v1.0"

const app = createApp(App)
app.config.globalProperties.$backend = backend;
app.use(VueAxios, axios)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
