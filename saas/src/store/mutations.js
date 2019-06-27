import * as Mutations from './mutation_types';

export default {
    [Mutations.SET_DICT](state, dict){
        state.dict = dict;
    },
    [Mutations.SET_PERMISSIONS](state, sys_permissions){
        state.sys_permission = sys_permissions;
    },
    [Mutations.LOADING](state, loading){
        state.loading = loading;
    },
    [Mutations.SET_DEPART](state, val){
        state.depart = val;
    },
    [Mutations.SET_ALL_DEPART](state, val){
        state.all_depart = val;
    },
    [Mutations.SET_COMPANY](state, val){
        state.company = val;
    },
    [Mutations.SET_ACCOUNT](state, val){
        state.account = val;
    }
};
