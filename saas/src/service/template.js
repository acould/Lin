import Http from './http';

export default {
    getTemplateList(params){
        return Http.$post('/hpms-contract/templates/list', params);
    },
    addTemplate(params){
        return Http.$post('/hpms-contract/templates/template', params);
    },
    getDefaultTemplate(){
        return Http.$get('/hpms-contract/templates/defaultTemplate');
    },
    stopTemplate(id){
        return Http.$put(`/hpms-contract/templates/stopUsing/${id}`, {});
    },
    enableTemplate(id){
        return Http.$put(`/hpms-contract/templates/enable/${id}`, {});
    },
    setDefaultTemplate(id){
        return Http.$put(`/hpms-contract/templates/default/${id}`, {});
	},
	getEffectivesTemplateList(){
		return Http.$get('/hpms-contract/templates/effectives');
	},
    getTemplateUrl(id){
        return Http.$get(`/hpms-contract/templates/download/${id}`);
    }
}