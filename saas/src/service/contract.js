import Http from './http';

export default {
    getDayOrderyList(params){
        return Http.$post('/hpms-contract/apartmentorders/dayList', params);
    },
    getMonthContractList(params){
        return Http.$post('/hpms-contract/apartmentorders/list', params);
    },
    getMonthContractDetail(orderId){
        return Http.$get(`/hpms-contract/apartmentorders/${orderId}`);
    },
    discardContract(orderId){
        return Http.$get(`/hpms-contract/apartmentorders/cancel/${orderId}`);
    },
    addMonthContract(params){
        return Http.$post('/hpms-contract/apartmentorders/add', params);
    },
    updateMonthContract(params){
        return Http.$post('/hpms-contract/apartmentorders/update', params);
    },
    getMonthContractEndDetail(orderId){
        return Http.$get(`/hpms-contract/apartmentorderend/${orderId}`);
    },
    getMonthContractEndBaseDetail(orderId){
        return Http.$get(`/hpms-contract/apartmentorderend/base/${orderId}`);
    },
    addMonthContractEnd(params){
        return Http.$post(`/hpms-contract/apartmentorderend/add`, params);
	},
	//创建合同账单
	createBill(params){
		return Http.$post('/hpms-finance/bills/init',params)
	},
	//预览合同
	previeContract(params){
		return Http.$post('/hpms-contract/apartmentorders/preview',params)
	}
	
}