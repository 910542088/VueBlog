import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//完整引入elementUI
import Element from 'element-ui';
//elementUI样式文件
import 'element-ui/lib/theme-chalk/index.css'
//引入axios
import axios from 'axios'
import mavonEditor from 'mavon-editor'
import "./axios";
import 'mavon-editor/dist/css/index.css'
import './permission'

Vue.use(Element);
Vue.use(mavonEditor);
Vue.config.productionTip = false;
Vue.prototype.$axios = axios;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

