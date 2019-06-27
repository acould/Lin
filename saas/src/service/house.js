import Http from './http';

export default {
    getRoomList(params){
        return Http.$post('/hpms-house/roomType/list', params);
    },
    getRoomTypeDetail(roomTypeId){
        return Http.$get(`/hpms-house/roomType/get/${roomTypeId}`);
    },
    saveRoomType(params){
        return Http.$post('/hpms-house/roomType/save', params);
    },
    extendRoomType(params){
        return Http.$post('/hpms-house/roomType/extend', params);
    },
    deleteRoomType(id){
        return Http.$delete(`/hpms-house/roomType/delete/${id}`, {});
    },
    getRoomType(apartmentInfoId){
        return Http.$get(`/hpms-house/rooms/floorRoomsList/${apartmentInfoId}`)
    },
    getStrategyList(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/dateList', params);
    },
    getStrategyNormalList(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/normalList', params);
    },
    getStrategyHolidayList(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/holidayList', params);
    },
    saveStrategyPrice(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/updatePrice', params);
    },
    saveNoramlPrice(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/normalSave', params);
    },
    saveHolidayPrice(params){
        return Http.$post('/hpms-house/apartmentPriceStrategySetting/holidaySave', params);
    },
    deleteStrategyPrice(id){
        return Http.$delete(`/hpms-house/apartmentPriceStrategySetting/delete/${id}`, {});
    },
    getRealtimeStatus(params){
        return Http.$post('/hpms-house/roomPriceDay/realTimeStatus', params);
    },
    getRealtimeList(params){
        return Http.$post('/hpms-house/roomPriceDay/realTimeList', params);
    },
    setRoomStatus(params){
        return Http.$post('/hpms-house/roomPriceDay/statusSet', params);
    },
    getRoomMonthList(params){
        return Http.$post('/hpms-house/roomPriceDay/roomMonthly', params);
    },
    getRoomStatusDetail(params){
        return Http.$post('/hpms-house/roomPriceDay/getRoomStatus', params);
    },
    getRoomOrderList(apartmentInfoId){
        return Http.$get(`/hpms-house/roomType/getSimple/${apartmentInfoId}`);
    },
    getRoomAllList(apartmentInfoId){
        return Http.$get(`/hpms-house/roomType/getWithRoom/${apartmentInfoId}`);
    },
    getRoomOrderDay(params){
        return Http.$post(`/hpms-contract/dayRentOrder/getDaySelection`, params);
    },
    getRoomOrderDetail(params){
        return Http.$post('/hpms-contract/dayRentOrder/edit', params);
    },
    getApartmentDropList(){
        return Http.$get('/hpms-house/apartmentinfos/list');
    },
    addOrder(params){
        return Http.$post('/hpms-contract/dayRentOrder/add', params);
    },
    getRoomDayPrice(params){
        return Http.$post('/hpms-house/dayRentRoom/getDayPriceOfRoom', params);
    },
    getOrderReceiveList(params){
        return Http.$post('/hpms-finance/billStream/getReceivedStreamList', params)
    },
    savePrepayInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/savePrepayInfo', params);
    },
    getCheckInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/getCheckinInfo', params);
    },
    saveCheckInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/saveCheckinInfo', params);
    },
    getCheckMember(params){
        return Http.$post('/hpms-contract/dayRentOrder/getCheckinMember', params);
    },
    saveCheckMember(params){
        return Http.$post('/hpms-contract/dayRentOrder/saveCheckinMember', params);
    },
    getOrderCancelInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/getWillCancelInfo', params);
    },
    saveOrderCancel(params){
        return Http.$post('/hpms-contract/dayRentOrder/doCancel', params);
    },
    getOrderLeaveInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/getWillLeaveInfo', params);
    },
    saveOrderLeave(params){
        return Http.$post('/hpms-contract/dayRentOrder/doLeave', params);
    },
    getOrderCheckinInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/getWillCheckinInfo', params);
    },
    saveOrderCheckinInfo(params){
        return Http.$post('/hpms-contract/dayRentOrder/doCheckin', params);
    }
}