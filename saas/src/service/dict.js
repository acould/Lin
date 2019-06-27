import Http from './http';

export default {
    getDictList(name){
        return Http.$get(`/gaodu-dict/companydicts/list${name ? '/' + name : ''}`);
    },
    getDictItemList(dictId){
        return Http.$get(`/gaodu-dict/companydicts/items/${dictId}`);
    },
    addDictItem(params){
        return Http.$post('/gaodu-dict/companydicts/items/item', params);
    },
    updateDictItem(params){
        return Http.$put('/gaodu-dict/companydicts/items/item', params);
    },
    disableDictItem(id){
        return Http.$put(`/gaodu-dict/companydicts/items/item/disable/${id}`, {});
    },
    enableDictItem(id){
        return Http.$put(`/gaodu-dict/companydicts/items/item/enable/${id}`, {});
    }
}