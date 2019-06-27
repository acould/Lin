<template>
    <div>
        <i-form :model="receiptForm" ref="receiptForm" label-width="100px">
            <i-row :gutter="10">
                <i-col :span="12">
                    <i-form-item label="费用类型:" prop="chargeType">
                        <i-select v-model="receiptForm.chargeType" :disabled="receiptState == 'update'">
                            <i-option v-for="(option, index) in dict['ChargeType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                        </i-select>
                    </i-form-item>
                </i-col>
                <i-col :span="12">
                    <i-form-item label="应收金额:" prop="billMoney">
                        <i-input v-model="receiptForm.billMoney"></i-input>
                    </i-form-item>
                </i-col>
                <i-col :span="12">
                    <i-form-item label="应收日期:" prop="billDate">
                        <i-date-picker type="date" v-model="receiptForm.billDate" style="width:100%"></i-date-picker>
                    </i-form-item>
                </i-col>
                <i-col :span="24">
                    <i-form-item label="账期:" prop="date" class="date-range">
                        <i-col :span="12">
                            <i-date-picker class="first" type="date" v-model="receiptForm.date[0]" style="width:100%"></i-date-picker>
                        </i-col>
                        <i-col :span="12">
                            <i-date-picker class="second" type="date" v-model="receiptForm.date[1]" style="width:100%"></i-date-picker>
                        </i-col>
                    </i-form-item>
                </i-col>
                <i-col :span="24">
                    <i-form-item label="备注:" prop="remark">
                        <i-input type="textarea" v-model="receiptForm.remark"></i-input>
                    </i-form-item>
                </i-col>
            </i-row>
        </i-form>
    </div>
</template>
<script>
import financeService from 'service/hpms-finance';

import {mapState} from 'vuex';
export default {
    data(){
        return {
            receiptState:'add',
            receiptForm:{
                orderId:'',
                billId:'',
                chargeType:'',
                chargeTypeCn:'',
                date:['',''],
                billMoney:'',
                billStatusCn:'',
                billStatus:'',
                startDate:'',
                endDate:'',
                billDate:'',
                remark:''
            },
            active:''
        }
    },
    methods:{
        addReceipt(orderId){
            this.receiptForm.orderId = orderId;
            this.receiptForm.billId = '';
            this.receiptForm.chargeType = '';
            this.receiptForm.date[0] = '';
            this.receiptForm.date[1] = '';
            this.receiptForm.startDate = '';
            this.receiptForm.endDate = '';
            this.receiptForm.billDate = '';
            this.receiptForm.remark = '';
            this.receiptForm.billMoney = '';
            this.receiptState = 'add';
        },
        getDetail(params, index){            
            this.receiptForm.billId = params.billId;
            this.receiptForm.chargeType = params.chargeType;
            this.receiptForm.date[0] = params.startDate;
            this.receiptForm.date[1] = params.endDate;
            this.receiptForm.startDate = params.startDate;
            this.receiptForm.endDate = params.endDate;
            this.receiptForm.billDate = params.billDate;
            this.receiptForm.remark = params.remark;
            this.receiptForm.billMoney = params.billMoney;
            this.receiptForm.chargeTypeCn = params.chargeTypeCn;
            this.receiptForm.billStatus = params.billStatus;
            this.receiptForm.billStatusCn = params.billStatusCn;
            this.receiptState = 'update';
            this.active = index;
        },
        saveReceipt(){
            let params = JSON.parse(JSON.stringify(this.receiptForm));
            params.startDate = params.date[0];
            params.endDate = params.date[1];

            this.$refs['receiptForm'].validate(valid => {
                if (valid) {
                    params.chargeTypeCn = this._getDictName('ChargeType', params.chargeType);
                    params.billStatusCn = this._getDictName('ReceiveStatus', params.billStatus || 'NOT_RECEIVED');
                    this.$emit('submit', params, this.receiptState, this.active);
                }
            });
        },
        _getDictName(dictName, key){
            let res = '';
            this.dict[dictName].forEach(item => {
                if (key == item.dictKey) {
                    res = item.dictValue;
                }
            });

            return res;
        }
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>
