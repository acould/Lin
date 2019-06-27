import Http from './http';

export default {
    /**
     * 获取当前公司所有公寓列表
     */
    getComHouseList(name){
		let searchText=''
		if(name){
			searchText=`/${name}`;
		}
        return Http.$get(`/hpms-house/apartmentinfos/list${searchText}`);
    },

    getHouseAllList(){
        return Http.$get('/hpms-house/apartmentinfos/list');
    },
    /**
     * 指定公寓房源列表
     */
    getHouseList(params){
        return Http.$post('/hpms-house/rooms/list', params);
    },
    
    /**
     * 获取公寓房间列表
     */
    getRoomsList(id){
        return Http.$get('/hpms-house/rooms/room/${id}');
    },
    /**
     * 获取房间详情
     */
    getHouseInfo(id){
        return Http.$get(`/hpms-house/rooms/room/${id}`);
    },
    /**
     * 编辑房间详情
     */
    editHouseInfo(params){
        return Http.$put('/hpms-house/rooms/room',params);
    },

    /**
     * 删除房间详情
     */
    deleteHouseInfo(id){
        return Http.$delete(`/hpms-house/rooms/room/${id}`,{});
    },
    /**
     * 删除房源详情图片
     */
    imgDel(id){
        return Http.$delete(`/hpms-house/roomAttachments/attachment/${id}`,{});
    },
    imgAdd(params){
        return Http.$post('/hpms-house/roomAttachments/attachment', params);
    },

    /**
     * 修改房源基础信息
     */
    saveEditHouse(params){
        return Http.$put('/hpms-house/rooms/roomBase', params);
    },


    /**
     * 修改出租类型
     */
    saveChangeRentType(params){
        return Http.$put('/hpms-house/rooms/rentType', params);
    },
    /**
     * 重新定价
     */
    saveChangePrice(params){
        return Http.$put('/hpms-house/rooms/price', params);
    },
    /**
     * 编辑备注
     */
    saveChangeRemark(params){
        return Http.$put('/hpms-house/rooms/remark', params);
    },
    /**
     * 编辑问题房源
     */
    saveChangeIllRoom(params){
        return Http.$put('/hpms-house/rooms/illRoom', params);
    },
    /**
     * 获取房间配置
     */
    getHouseFacility(id){
        return Http.$get(`/hpms-house/rooms/facility/${id}`);
    },

    /**
     * 更新房间配置
     */
    saveHouseFacility(params){
        return Http.$put(`/hpms-house/rooms/facility`,params);
    },
    /**
     * 更新水电煤气户号
     */
    saveEditNos(params){
        return Http.$put(`/hpms-house/rooms/chargeNo`,params);
    },
    /**
     * 获取订单信息
     */
    getOrderDetail(params){
        return Http.$post('/hpms-contract/dayRentOrder/edit', params);
    },
    /**
     * 获取公寓订单
     */
    getApartmentOrders(params){
        return Http.$post(`/hpms-contract/apartmentorders/list/room`,params);
    },
    /**
     * 获取当前公司公寓列表-用于创建SaaS公寓项目
     */
    getListByCompany(){
        return Http.$get('/hpms-house/apartments/listByCompany')
    },
    /**
     * 缓存公寓信息
     */
    saveApartmentInfos(params){
        return Http.$post('/hpms-house/apartmentinfos/cache',params)
    },
    /**
     * 获取公寓缓存信息
     */
    getApartmentInfos(){
        return Http.$get('/hpms-house/apartmentinfos/cache')
    },

     /**
     * 缓存房间模板
     */
    saveRoomTemp(params){
        return Http.$post('/hpms-house/apartmentinfos/cache/roomTemp',params)
    },
    /**
     * 缓存添加房间
     */
    saveRoomData(params){
        return Http.$post('/hpms-house/apartmentinfos/cache/room',params)
    },
    /**
     * 删除缓存房间
     */
    deleteRoomData(roomId){
        return Http.$delete(`/hpms-house/apartmentinfos/cache/room/${roomId}`,{})
    },
    /**
     * 更新房间模板
     */
    updateRoomTemp(params){
        return Http.$put('/hpms-house/apartmentinfos/cache/roomTemp',params)
    },

    /**
     * 缓存修改楼层信息
     */
    updateTempFloor(params){
        return Http.$put('/hpms-house/apartmentinfos/cache/floor',params)
    },

    /**
     * 缓存删除楼层信息
     */
    deleteTempFloor(floorId){
        return Http.$delete(`/hpms-house/apartmentinfos/cache/floor/${floorId}`,{});
    },

    /**
     * 创建项目和房源
     */
    createApartmentInfo(params){
        return Http.$post('/hpms-house/apartmentinfos/apartmentinfo',params)
    },

    /**
     * 获取项目和房源基础信息
     */
    getApartmentInfo(apartmentInfoId){
        return Http.$get(`/hpms-house/apartmentinfos/apartmentinfo/${apartmentInfoId}`)
    },
    /**
     * 保存基础信息编辑
     */
    updateApartmentInfos(params){
        return Http.$put('/hpms-house/apartmentinfos/apartmentinfo',params)
    },

    /**
     * 获取模板
     */
    getRoomTempsList(apartmentInfoId){
        return Http.$get(`/hpms-house/roomTemps/list/${apartmentInfoId}`)
    },
    /**
     * 保存模板
     */
    editRoomTemp(params){
        return Http.$put('/hpms-house/roomTemps/roomTemp',params)
    },
    /**
     * 删除模板
     */
    delRoomTemp(tempId){
        return Http.$delete(`/hpms-house/roomTemps/roomTemp/${tempId}`,{})
    },
    /**
     * 编辑项目-新增楼层
     */
    saveFloorName(params){
        return Http.$post(`/hpms-house/apartmentinfos/floor`,params)
    },
    /**
     * 编辑项目-修改楼层
     */
    editFloorName(params){
        return Http.$put(`/hpms-house/apartmentinfos/floor`,params)
    },
    /**
     * 编辑项目-删除楼层
     */
    deleteFloorName(floorId){
        return Http.$delete(`/hpms-house/apartmentinfos/floor/${floorId}`,{})
    },
    /**
     * 编辑项目-设计房间模板
     */
    saveBatchRoom(params){
        return Http.$put(`/hpms-house/rooms/tempRoom`,params)
    },
    /**
     * 编辑项目-新增房间
     */
    addRoom(params){
        return Http.$post(`/hpms-house/rooms/room`,params)
    },
	getRoomsPhone(roomId){
		return Http.$get(`/hpms-house/rooms/phone/${roomId}`);
	},
	//详情页更改图片
	saveRoomInfoImgs(params){
		return Http.$put(`/hpms-house/roomAttachments/batch`,params);
	}

};
