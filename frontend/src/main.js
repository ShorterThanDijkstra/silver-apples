import { createApp } from 'vue'
import router from '@/router/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import store from '@/store/store'
import http from '@/http/http'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


const app = createApp(App);
app.config.globalProperties.$http = http;
app.config.globalProperties.$backend = "http://8.134.99.20:8080/api/v1.0";

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.use(store)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
