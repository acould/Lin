import Http from 'tofu-http';
import Vue from 'vue';
import Tofu from 'i-tofu';
Vue.use(Tofu);

const http = new Http(false, res => {
    if (res.status == 401) {
        window.App.$message.error(`未登录，请重新登录`);
        window.App.$router.push('/login');    
        //throw Error('登录异常');
        return false;
    }
    if (res.status >= 400) {
        window.App.$message.error(`服务出错，状态码：${res.status}`);
        throw Error('服务出错');
    }

    if(res.code !== 0){
        new Vue().$message.error(res.msg);
    }

    return res;
}, {}, err => {
    console.error(err);
});

export default http;