import Http from './http';

export default {
    getBusinessRule(key){
        return Http.$get(`/hpms-merchant/bussinessRule/${key}`)
    },
    saveBusinessRule(params){
        return Http.$put('/hpms-merchant/bussinessRule', params);
    }
}