import * as types from '../mutation_types';

const state = {
    search_loading:false,
    submit_loading:false,
    save_loading:false,
    export_loading:false,
};
const getters = {};
const actions = {};
const mutations = {
    [types.COM_SEARCH_LOADING](state, val){
        state.search_loading = val;
    },
    [types.COM_SUBMIT_LOADING](state, val){
        state.submit_loading = val;
    },
    [types.COM_SAVE_LOADING](state, val){
        state.save_loading = val;
    },
    [types.COM_EXPORT_LOADING](state, val){
        state.export_loading = val;
    },
};
export default {
    state,
    getters,
    actions,
    mutations
};
