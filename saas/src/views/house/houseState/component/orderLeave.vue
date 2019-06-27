<template>
    <div>
        <i-form :model="form">
            <i-form-item label="房间信息">
                <i-checkbox-group v-model="form.roomIds">
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
            <i-form-item label="退房备注">
                <i-input v-model="form.remark" type="textarea"></i-input>
            </i-form-item>
        </i-form>
        <div>
            <label>商户实收金额:</label>
            <span>{{form.receivedMoney}}</span>
        </div>
        <div>
            <label>订单总额:</label>
            <span>{{form.totalMoney}}</span>
        </div>
        <div>
            <label>还需收取:</label>
            <span>{{form.leftMoney}}</span>
        </div>
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
                receivedMoney:'',
                totalMoney:'',
                leftMoney:'',
                remark:'',
                roomList:[],
                roomIds:[]
            }
        }
    },
    methods:{
        getDetail(orderId, roomId){
            houseService.getOrderLeaveInfo({
                orderId:orderId,
                roomId:roomId
            }).then(res => {
                if (res.code === 0) {
                    res.data['remark'] = '';
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

            houseService.saveOrderLeave(params).then(res => {
                if (res.code === 0) {
                    this.$message.success('操作成功!');
                    this.$emit('success','orderLeaveStatus');
                }
            });
        }
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>
