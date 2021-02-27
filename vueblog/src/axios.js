import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import store from './store'
axios.defaults.baseURL = "http://localhost:8081";

//使用axios的前置拦截处理错误信息

axios.interceptors.request.use(req=>{
    return req;
})

axios.interceptors.response.use(resp=>{
    const response = resp.data;
   if(response.code === 200){
     return resp;
   } else{
     return Promise.reject(response.data);
   }
},error => {
    if(error.response.status ===401){
        store.commit("REMOVE_INFO");
        router.push("/login");
    }
    if(error.response.data){
        error.message = error.response.data.msg;
    }
    Element.Message.error(error.message);
    return Promise.reject(error);
}

)