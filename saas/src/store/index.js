import Vue from 'vue';
import Vuex from 'vuex';

import actions from './actions';
import mutations from './mutations';
import * as getters from './getters';

import common from './modules/common';

Vue.use(Vuex);

const listener = store => {
    if (!sessionStorage) {
        return;
    }
    store.subscribe((mutation, state) => {
        sessionStorage.setItem('state', JSON.stringify(state));
    });
};

const state = {
    loading:false,
    dict: {},
    sys_permission: {},
    depart:[],
    all_depart:[],
    company:[],
    account:{
        companyName:'',
        userName:''
    }
};
export default new Vuex.Store({
    state,
    modules: {common},
    mutations,
    actions,
    // plugins: [listener]
});

