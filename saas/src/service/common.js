import Http from './http';

export default {
    /**
     * 获取数字字典
     */
    getDict(){
        return Http.$get('/gaodu-dict/dicts/all');
    },

    /**
     * 获取权限
     */
    getPermissions(params){
        return Http.$get('/isz_base/CommController/power.action', params);
    },

    /**
     * 获取菜单
     */
    getMenus(params){
        return Http.$get('/isz_base/CommController/treeMenu.action', params);
    },

    /**
     * 获取城市与部门
     */
    getDeparts(){
        return Http.$get('/isz_base/CommController/selectCityDepTree.action');
    },

    getUserInfo(params){
        return Http.$get('/isz_base/CommController/user.action', params);
    },

    getSession(){
        return Http.$get('/isz_base/CommController/getSession.action', {});
    },

    /**
     * 切换分支
     */
    setBranch(params){
        return Http.$post('/isz_base/CommController/changeCity.action', params);
    },
    /**
     * 获取部门
     */
    getDepart(){
        return Http.$get('/isz_base/DepartController/searchAllDepart.action');
    },
    /**
     * 根据权限获取部门
     */
    getDepartByDepId(){
        return Http.$get('/isz_base/DepartController/searchAllDepartInCurrCityCode.action');
    },
    getAllDepartByDepId(params){
        return Http.$post('/isz_base/DepartController/searchAllDepartByDepId.action', params);
    },
    /**
     * 基于部门获取部门下人员
     */
    getUserByDepart(params){
        return Http.$post('/isz_base/CommController/selectAllUserByDepart.action', params);
    },

    getCompany(){
        return Http.$get('/isz_base/CommController/selectCompanyByCode.action');
    },

    getQrcodeTicket(count = 1){
        return Http.$get(`/isz_wechat/WechatQrCodeController/tempQrCode/${count}`);
    },
    /**
     * 数据字典
     */
    getDictsAll(){
        return Http.$get('/gaodu-dict/dicts/all')
    },
    /**
     * 城市城区商圈
     */
    getCityAreaAll(){
        return Http.$get('/homs-user/districts/tree/company')
    }
};
