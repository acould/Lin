// initial state
const state = {
  spendingInfo: null,
}

// getters
const getters = {}

// mutations
const mutations = {
  setSpendingInfo(state, data) {
    state.spendingInfo = data
  },
  refreshSpendingInfo(state, data) {
    state.spendingInfo = data
  },
}

// actions
const actions = {
  // 设置支出
  setSpendingInfo({ commit}, obj) {
    return new Promise(function(resolve, reject) {
      commit('setSpendingInfo', obj)
      resolve()
    })
  },
  //刷新支出信息
  refreshSpendingInfo({ commit }, obj) {
    return new Promise(function(resolve, reject) {
      commit('refreshSpendingInfo', obj)
      resolve()
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
