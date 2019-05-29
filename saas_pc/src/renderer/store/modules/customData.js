// initial state
const state = {
    customData: null
}

// getters
const getters = {
    getCustomData (state) {
        return state.customData
    },
}

// mutations
const mutations = {
    setCustomData(state, data) {
        state.customData = data
    },
    clearCustomData(state, data) {
        state.customData = null
    }
}

// actions
const actions = {
    // 存储页面数据
    setCustomData({
        commit
    }, data) {
        return new Promise(function (resolve, reject) {
            commit('setCustomData', data)
            resolve()
        })
    },
    // 清空存储的页面数据
    clearCustomData({
        commit
    }) {
        return new Promise(function (resolve, reject) {
            commit('clearCustomData')
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