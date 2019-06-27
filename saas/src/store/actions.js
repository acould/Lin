import { SET_DICT, SET_PERMISSIONS, SET_DEPART, SET_COMPANY, SET_ALL_DEPART } from './mutation_types';
import CommonServices from 'service/common';
import config from 'config';
import { isNotEmpty } from '../utils';

export default {
    getDict({commit}){
        CommonServices
            .getDict()
            .then(res => {
                if (res.code === 0 && isNotEmpty(res.data)) {
                    window.dict = res.data;
                    commit(SET_DICT, res.data);
                } else {
                    console.error('获取数据字典失败，请手动获取');
                }
            });
    },
    getPermissions({commit}){
        CommonServices
            .getPermissions({res_category:config.category})
            .then(res => {
                if (res.code === 0 && isNotEmpty(res.obj)) {

                    //  判断添加测试权限
                    for (let i in res.obj) {
                        if (i.substr(0, 10) === 'saleHouse_') {
                            let key = i.substr(10);
                            res.obj[key] = res.obj[i];
                        }
                    }
                    window.sys_permission = res.obj;
                    commit(SET_PERMISSIONS, res.obj);
                } else {
                    console.error('获取权限失败，请手动获取');
                }
            });
    },
    getDepart({commit}){
        CommonServices
            .getDepartByDepId()
            .then(res => {
                if (res.code === 0 && isNotEmpty(res.obj)) {
                    commit(SET_DEPART, res.obj);
                } else {
                    console.error('获取部门失败，请手动获取');
                }
            });
    },
    getAllDepart({commit}){
        CommonServices
            .getAllDepartByDepId({})
            .then(res => {
                if (res.code === 0 && isNotEmpty(res.obj)) {
                    commit(SET_ALL_DEPART, res.obj);
                } else {
                    console.error('获取部门失败，请手动获取');
                }
            });
    },
    getCompany({commit}){
        CommonServices
            .getCompany()
            .then(res => {
                if (res.code === 0 && isNotEmpty(res.obj)) {
                    commit(SET_COMPANY, res.obj);
                } else {
                    console.error('获取公司失败');
                }
            });
    }
};
