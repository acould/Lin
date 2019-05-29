import httpUrl from '../../api/api.js'
import axios from '../../Axios/axios.js'
// initial state
const state = {
  isAllArranged: null, //订单是否全部已排班
  carTimeoutSum: null,
  comlaintNum:null,
  backlogRemind:null
}

// getters
const getters = {}

// mutations
const mutations = {
  getMessageCondition (state, data) {
    state.isAllArranged = data.receive_order_num
    state.carTimeoutSum = data.car_time_out_num
    state.comlaintNum =data.complaint_not_read_num
    // 待办事项（到期条数）
    state.backlogRemind=data.backlog_remind_not_plan
  }
}

// actions
const actions = {
  getMessageCondition ({commit}) {
    return new Promise(function(resolve) {
      axios.get(httpUrl.homeCount).then(res => {
        if (res) {
          commit("getMessageCondition", res)
          resolve()
        }
      })
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}