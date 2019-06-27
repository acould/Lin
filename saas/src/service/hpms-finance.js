import Http from './http';

export default {
    /**
     * 获取房源详情获取应收账单
     */
    getHouseBillList(params){
        return Http.$post('/hpms-finance/bills/list',params);
    },

    addHouseBill(params){
        return Http.$post('/hpms-finance/bills/bill', params);
    },

    updateHouseBill(params){
        return Http.$put('/hpms-finance/bills/bill', params);
    },

    deleteHouseBill(billId){
        return Http.$delete(`/hpms-finance/bills/bill/${billId}`, {});
    },

    getHousePaymentBillDetail(billId){
        return Http.$get(`/hpms-finance/payments/list/${billId}`);
    },

    addHousePaymentBill(params){
        return Http.$post(`/hpms-finance/payments/payment`, params);
    },

    updateHousePaymentBill(params){
        return Http.$put('/hpms-finance/payments/payment', params);
    },

    deleteHousePaymentBill(paymentId){
        return Http.$delete(`/hpms-finance/payments/payment/${paymentId}`, {});
    },
    //催收短信
    collectionSms(billId){
        return Http.$put(`/hpms-finance/bills/collection/sms/${billId}`, {});
	},
	


	

};
