import axios from 'axios'
import {
  Message
} from 'element-ui'
import store from '../store'
const qs = require('qs')
let router, windowUtil
if (!process.env.IS_WEB) {
  import('../router').then(module => {
    router = module.default
  })
  import('../../main/ipcRenderer').then(module => {
    windowUtil = module.default
  })
} else {
  import('../webRouter').then(module => {
    router = module.default
  })
}
const Axios = axios.create({
  baseURL: '/',
  timeout: 20000,
  responseType: 'json',
  withCredentials: true, // 是否允许带cookie这些
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
  }
})

let client = process.env.IS_WEB ? 'saas_pc' : 'saas_windows'
const initParams = {
  client: client,
  version: '1.3.0'
}
// POST传参序列化(添加请求拦截器)
Axios.interceptors.request.use(
  config => {
    // 鉴权token
    let token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = 'Bearer' + ' ' + token
    }
    if (
      config.method === 'post' ||
      config.method === 'put' ||
      config.method === 'delete'
    ) {
      // 序列化
      config.data = config.data || {}
      Object.assign(config.data,initParams)
      
      if (config.headers['Content-Type'] != 'multipart/form-data') {
        config.data = qs.stringify(config.data)
      }
    }
    if (config.method === 'get') {
      config.params = config.params || {}
      Object.assign(config.params,initParams)
    }
    return config
  },
  error => {
    Message({
      showClose: true,
      message: error,
      type: 'error'
    })
    return Promise.reject(error)
  }
)

// 返回状态判断(添加响应拦截器)
Axios.interceptors.response.use(
  res => {
    // 重置Content-Type
    if (Axios.defaults.headers['Content-Type'] == 'multipart/form-data') {
      Axios.defaults.headers['Content-Type'] =
        'application/x-www-form-urlencoded;charset=utf-8'
    }
    const o = res.data
    let token = res.headers.authorization
    if (token) {
      store.dispatch('refreshToken', token.replace('Bearer ', ''))
    }
    if (o.status_code == 401) {
      if (!process.env.IS_WEB) {
        store.commit('logout')
        windowUtil.logout()
      } else {
        router.push({
          name: 'login'
        })
      }
      return
    }
    // 200时 返回true 或者 接口数据
    if (o.status_code == 200) {
      if (!o.data) {
        o.data = {}
      }
      if(!o.data.message) {
        o.data.message = o.message
      }
      return o.data || true
    } else {
      // 除200以外默认全局提示错误
      if (o.message) {
        
        if(o.message=='缺少参数driver cars'){
          Message({
            showClose: true,
            message:'请选择车辆',
            type: 'error'
          })
        }else{
          Message({
            showClose: true,
            message: o.message,
            type: 'error'
          })
        }
      }
      if (o.errors) {
        let errorArr = o.errors
        for (var errorIndex in errorArr) {
          Message({
            showClose: true,
            message: errorArr[errorIndex],
            type: 'error'
          })
          break
        }
      }
    }
    return false
  },
  error => {
    console.log(error)
  }
)

export default Axios