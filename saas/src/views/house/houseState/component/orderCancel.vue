<template>
    <div>
        <i-form :model="form" ref="form" label-width="100px">
            <i-form-item label="取消原因:" prop="reason">
                <i-input type="textarea" v-model="form.reason"></i-input>
            </i-form-item>
            <i-form-item label="款项类型:" prop="receivePayType">
                <i-select v-model="form.receivePayType">
                    <i-option v-for="(option, index) in dict['DayRentMoneyType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                </i-select>
            </i-form-item>
            <i-form-item label="支付渠道:" prop="channel">
                <i-select v-model="form.channel">
                    <i-option v-for="(option, index) in dict['ReceivableChannel']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                </i-select>
            </i-form-item>
            <i-form-item label="金额:" prop="money">
                <i-input v-model="form.money"></i-input>
            </i-form-item>
            <i-form-item label="订单总额:">
                <span>{{info.totalMoney}}</span>
            </i-form-item>
            <i-form-item label="预授权金额:">
                <span>{{info.depositMoney}}</span>
            </i-form-item>
            <i-form-item label="商户实收押金">
                <span>{{info.receivedMoney}}</span>
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
                reason:'',
                receivePayType:'',
                channel:'',
                money:''
            },
            info:{
                totalMoney:'',
                receivedMoney:'',
                depositMoney:''
            }
        }
    },
    methods:{
        getInfo(orderId){
            let params = {
                orderId: orderId
            }
            this.form.orderId = orderId;
            houseService.getOrderCancelInfo(params).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'info', res.data);
                }
            });
        },
        save(){
            let params = JSON.parse( JSON.stringify(this.form));
            houseService.saveOrderCancel(params).then(res => {
                if (res.code === 0) {
                    this.$message.success('操作成功');
                    this.$emit('success', 'orderCancelStatus');
                }
            });
        }
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>

