<template>
    <div>
        <i-form :model="form">
            <i-form-item label="房间信息">
                <i-checkbox-group v-model="form.roomIds" style="width:100%">
                    <div v-for="(item, itemIndex) in form.roomList" :key="itemIndex">
                        <div style="float:left">
                            <i-checkbox :label="item.roomId">&nbsp;</i-checkbox>
                        </div>
                        <div style="font-size:12px;float:left">
                            <p>{{item.roomTypeName + '-' + item.roomName}}</p>
                            <p>{{'入离时间:' + item.startTime + '至' + item.endTime + ',共' + item.nightCount + '晚,共计' + item.totalPrice + '元'}}</p>
                        </div>
                    </div>
                </i-checkbox-group>
            </i-form-item>
        </i-form>
    </div>
</template>
<script>
import houseService from 'service/house';
import {mapState} from 'vuex';
export default {
    data(){
        return {
            form:{
                orderId:'',
                roomList:[],
                roomIds:[]
            }
        }
    },
    methods:{
        getDetail(orderId, roomId){
            houseService.getOrderCheckinInfo({
                orderId:orderId,
                roomId:roomId
            }).then(res => {
                if (res.code === 0) {
                    res.data['roomIds'] = [];
                    this.$set(this, 'form', res.data);
                }
            });
        },
        save(){
            let params = JSON.parse(JSON.stringify(this.form));
            params['orderInfoId'] = [];
            params.roomIds.forEach(id => {
                params.roomList.forEach(item => {
                    if (item.roomId == id) {
                        params['orderInfoId'].push({
                            roomId:id,
                            orderInfoId:item.orderInfoId
                        })
                    }
                });
            });

            houseService.saveOrderCheckinInfo(params).then(res => {
                if (res.code === 0) {
                    this.$message.success('操作成功!');
                    this.$emit('success', 'orderCheckinStatus');
                }
            });
        }
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>
