import Http from './http';

export default {
    login(params){
        return Http.$post('/hpms-merchant/login/login', params);
    },
    getVerCodeImg(){
        return Http.$get('/hpms-merchant/login/vercode');
    },
    getForgetCode(phone){
        return Http.$get(`/hpms-merchant/login/forgetvercode/${phone}`)
    },
    checkPhoneAndCode(params){
        return Http.$post('/hpms-merchant/login/checkPhoneAndVerificationCode', params);
    },
    password(params){
        return Http.$put('/hpms-merchant/login/password', params);
    },
    logout(){
        return Http.$post('/hpms-merchant/login/logout', {});
    },
    getAccount(){
        return Http.$get('/hpms-merchant/companies/account');
    },
    updateAccount(params){
        return Http.$put('/hpms-merchant/company/users/userPhone', params);
    },
    updatePassword(params){
        return Http.$put('/hpms-merchant/company/users/password', params);
    },
    getAuthCodeByPhone(phone){
        return Http.$get(`/hpms-merchant/company/users/vercode/${phone}`);
    },
    addElectron(params){
        return Http.$post('/hpms-merchant/companies/electronicSeal', params);
    },
    deleteElectron(attachmentId){
        return Http.$delete(`/hpms-merchant/companies/electronicSeal/${attachmentId}`, {});
    }
}