// import router from '../../router'
import httpUrl from '../../api/api.js'
import axios from '../../Axios/axios.js'
let router
if (!process.env.IS_WEB) {
  import('../../router').then(module => {
    router = module.default
  })
} else {
  import('../../webRouter').then(module => {
    router = module.default
  })
}
// initial state
const state = {
  token: null,
  user: null,
  user_profile: null
}

// getters
const getters = {}

// mutations
const mutations = {
  // 用户登录成功，存储 token 并设置 header 头
  logined(state, token) {
    localStorage.setItem('token',token)
    state.token = token
  },
  // 用户刷新 token 成功，使用新的 token 替换掉本地的token
  refreshToken(state, token) {
    state.token = token   
    localStorage.setItem('token', token)
  },
  // 登录成功后拉取用户的信息存储到本地
  profile(state, data) {
    state.user_profile = data.user_profile
    state.user = data.user
    localStorage.setItem('user', JSON.stringify(data.user))
    localStorage.setItem('user_profile', JSON.stringify(data.user_profile))
  },
  // 用户登出，清除本地数据
  logout(state) {    
    localStorage.removeItem('token') 
    state.user = null
    state.user_profile = null
    state.token = null
    localStorage.removeItem('user')
    localStorage.removeItem('user_profile')
  }
}

// actions
const actions = {
  // 登录成功后保存用户信息
  logined({ dispatch, commit }, obj) {
    return new Promise(function(resolve, reject) {
      commit('logined', obj.token)
      commit('profile', obj)
      resolve()
    })
  },
  // 用户登出，清除本地数据并重定向至登录页面  logout
  logout({ commit }) {
    return new Promise(function(resolve, reject) {
      axios.get(httpUrl.logout).then(res => {   
        if (res) {         
          commit('logout')
          router.push({ name: 'login' })          
          resolve()
        }   
      })
    })
  },
  getUserInfo({ commit }) {
    return new Promise(function(resolve, reject) {
      axios.get(httpUrl.userInfo).then(res => {
        if (res) {
          commit('profile', res)
          resolve(res.user.role_id)
        }
      })
    })
  },
  // 将更新缓存和state中的用户信息保存至本地
  refreshUserInfo({ commit }, params) {
    return new Promise(function(resolve, reject) {
      let user = JSON.parse(localStorage.getItem('user'))
      let user_profile = JSON.parse(localStorage.getItem('user_profile'))
      for (let m in params) {
        if (user[m]) {
          user[m] = params[m]
        } else if (user_profile[m]) {
          user_profile[m] = params[m]
        }
      }
      let data = { user_profile: user_profile, user: user }
      commit('profile', data)
    })
  },
  // 将刷新的 token 保存至本地
  refreshToken({ commit }, token) {
    return new Promise(function(resolve, reject) {    
      commit('refreshToken', token)
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
