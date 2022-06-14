import { createApp } from 'vue'
import router from '@/router/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import store from '@/store/store'
import http from '@/http/http'

const app = createApp(App);
app.config.globalProperties.$http = http;
app.config.globalProperties.$backend = "http://localhost:8080/api/v1.0";

app.use(store)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
