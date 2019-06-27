<template>
    <div class="surrender">
        <i-form :model="form" ref="form" label-width="100px">
            <div class="divider">基础信息</div>
            <div class="info-box">
                <i-row :gutter="10">
                    <i-col :span="8">
                        <i-form-item label="承租人姓名:">
                            <span>{{info.customerName}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="合同号:">
                            <span>{{info.orderNum}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="签约房源:">
                            <span>{{info.apartmentName}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="合同期:">
                            <span>{{info.orderStartDate + '~' + info.orderEndDate}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="实付押金:">
                            <span>{{info.paymentDeposit}}</span>
                        </i-form-item>
                    </i-col>
                </i-row>
            </div>
            <div class="divider">结算内容</div>
            <div class="info-box">
                <i-row :gutter="10">
                    <i-col :span="8">
                        <i-form-item label="退租类型:" prop="endType">
                            <i-select v-model="form.endType">
                                <i-option v-for="(option, index) in dict['OrderEndType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="合同终止日:" prop="endDate">
                            <i-date-picker type="date" v-model="form.endDate" style="width:100%"></i-date-picker>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="违约金:" prop="breachAmount">
                            <i-input v-model="form.breachAmount"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="违约金承担方:" prop="breachBearType">
                            <i-select v-model="form.breachBearType">
                                <i-option v-for="(option, index) in dict['BreachBearType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="24">
                        <i-form-item label="退租原因说明:" prop="endReason">
                            <i-input type="textarea" v-model="form.endReason"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="24">
                        <i-form-item label="附件:" prop="attachments">
                            <i-img-list v-model="form['attachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                            <i-uploader class="upload-pc"
                                action="/gaodu-files/upload/image"
                                :show-file-list="false"
                                :on-success="handleSuccess"
                                :multiple="true"
                                :before-upload="handleValidate">
                                <i class="el-icon-plus"></i>
                            </i-uploader>
                        </i-form-item>
                    </i-col>
                </i-row>
            </div>
            <div class="divider">结算金额</div>
            <div class="info-box">
                    <i-form-item label="应收款项: " >
                        <template v-for="(item, itemIndex) in form.receivableBills" >
                            <i-row :gutter="10" style="margin:0 !important" :key="'receivable' + itemIndex">
                                <i-col :span="7">
                                    <i-form-item label-width="0px"  :prop="'receivableBills[' + itemIndex + '].chargeType'">
                                        <i-select v-model="item.chargeType" placeholder="费用类型" clearable>
                                            <i-option v-for="(option, index) in dict['ChargeType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                        </i-select>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="7">
                                    <i-form-item label-width="0px" :prop="'receivableBills[' + itemIndex + '].billMoney'">
                                        <i-input v-model="item.billMoney" placeholder="应收金额"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="7">
                                    <i-form-item label-width="0px" :prop="'receivableBills[' + itemIndex + '].paymentMoney'">
                                        <i-input v-model="item.paymentMoney" placeholder="实际应收金额"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="3">
                                    <a class="btn add" v-if="itemIndex == 0" @click.stop="addReceivable">
                                        <i class="el-icon-plus"></i>
                                    </a>
                                    <a class="btn delete" v-if="itemIndex != 0" @click.stop="deleteReceivable(itemIndex)">
                                        <i class="el-icon-minus"></i>
                                    </a>
                                </i-col>
                            </i-row>
                        </template>

                    </i-form-item>
                    <i-form-item label="应付款项:" >
                        <template  v-for="(item, itemIndex) in form.payableBills">

                            <i-row :gutter="10" style="margin:0 !important" :key="'payable' +itemIndex">
                                <i-col :span="7">
                                    <i-form-item label-width="0px" :prop="'payableBills[' + itemIndex + '].chargeType'">
                                        <i-select v-model="item.chargeType" placeholder="费用类型" clearable>
                                            <i-option v-for="(option, index) in dict['ChargeType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                        </i-select>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="7">
                                    <i-form-item label-width="0px" :prop="'payableBills[' + itemIndex + '].billMoney'">
                                        <i-input v-model="item.billMoney" placeholder="应收金额"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="7">
                                    <i-form-item label-width="0px" :prop="'payableBills[' + itemIndex + '].paymentMoney'">
                                        <i-input v-model="item.paymentMoney" placeholder="实际应收金额"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="3">
                                    <a class="btn add" v-if="itemIndex == 0" @click.stop="addPayable">
                                        <i class="el-icon-plus"></i>
                                    </a>
                                    <a class="btn delete" v-if="itemIndex != 0" @click.stop="deletePayable(itemIndex)">
                                        <i class="el-icon-minus"></i>
                                    </a>
                                </i-col>
                            </i-row>
                        </template> 

                    </i-form-item>
                <i-row :gutter="10">
                    <i-col :span="5">
                        <i-form-item label="总应收:">
                            <span>{{form.totalReceivable}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="7">
                        <i-form-item label="总应付:">
                            <span>{{form.totalPayable}}</span>
                        </i-form-item>
                    </i-col>
                    <i-col :span="7">
                        <i-form-item label="合计:">
                            <span>{{(form.finalReceivable > 0 ? '应收' : '应付') + Math.abs(form.finalReceivable) + '元'}}</span>
                        </i-form-item>
                    </i-col>
                </i-row>
            </div>
            <div class="divider">租客收款信息</div>
            <div class="info-box">
                <i-row :gutter="10">
                    <i-col :span="8">
                        <i-form-item label="收款人类型:" prop="payeeType">
                            <i-select v-model="form.payeeType">
                                <i-option v-for="(option, index) in dict['PayeeType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="收款人姓名:" prop="payeeName">
                            <i-input v-model="form.payeeName"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="收款账号类型:" prop="payeeAccountType">
                            <i-select v-model="form.payeeAccountType">
                                <i-option v-for="(option, index) in dict['PayeeAccountType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="收款账号:" prop="account">
                            <i-input v-model="form.account"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="8">
                        <i-form-item label="开户行:" prop="bankName">
                            <i-input v-model="form.bankName"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="24">
                        <i-form-item label="代收款证明:" prop="proves">
                            <i-img-list v-model="form['proves']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                            <i-uploader class="upload-pc"
                                action="/gaodu-files/upload/image"
                                :show-file-list="false"
                                :on-success="handleProvesSuccess"
                                :multiple="true"
                                :before-upload="handleProvesValidate">
                                <i class="el-icon-plus"></i>
                            </i-uploader>
                        </i-form-item>
                    </i-col>
                </i-row>
            </div>
        </i-form>
        
    </div>
</template>
<script>
import contractService from 'service/contract';
import {mapState} from 'vuex';
import config from 'config';

export default {
    data(){
        return {
            form:{
                orderId:'',
                endType:'',
                endDate:'',
                breachBearType:'',
                breachAmount:'',
                endReason:'',
                payeeType:'',
                payeeName:'',
                payeeAccountType:'',
                account:'',
                bankName:'',
                totalReceivable:0,
                totalPayable:0,
                finalReceivable:0,
                receivableBills:[{
                    chargeType:'',
                    paymentMoney:'',
                    billMoney:''
                }],
                payableBills:[{
                    chargeType:'',
                    paymentMoney:'',
                    billMoney:''
                }],
                attachments:[],
                proves:[]
            },
            info:{
                orderNum:'',
                orderStartDate:'',
                orderEndDate:'',
                customerName:'',
                apartmentName:'',
                paymentDeposit:''
            },
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
        }
    },
    watch:{
        totalReceivable(val){
            this.form.totalReceivable = val;
            this.setFinalReceivable();
        },
        totalPayable(val){
            this.form.totalPayable = val;
            this.setFinalReceivable();
        }
    },
    methods:{
        setFinalReceivable(){
            this.form.finalReceivable = this.form.totalReceivable - this.form.totalPayable;
        },
        getInfo(orderId){
            this.form.orderId = orderId;
            contractService.getMonthContractEndBaseDetail(orderId).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'info', res.data);
                }
            });
        },
        handleSuccess(res){
            if (res.code === 0) {
                let item = {
                    src: res.data.src,
                    imageId:res.data.imageId
                }
                this.form.attachments.push(item);
            }
        },
        handleValidate(file){
             var reg = new RegExp(/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i);

            if ( !reg.test(file.name) ) {
                this.$message.error('请上传图片格式!');
                return false;
            }
            if ( file.size > (20 * 1024 * 1024) ) {
                this.$message.error('图片最大不能超过20M!');
                return false;
            }
            return true;
        },
        handleProvesSuccess(res){
            if (res.code === 0) {
                let item = {
                    src: res.data.src,
                    imageId:res.data.imageId
                }
                this.form.proves.push(item);
            }
        },
        handleProvesValidate(file){
             var reg = new RegExp(/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i);

            if ( !reg.test(file.name) ) {
                this.$message.error('请上传图片格式!');
                return false;
            }
            if ( file.size > (20 * 1024 * 1024) ) {
                this.$message.error('图片最大不能超过20M!');
                return false;
            }
            return true;
        },
        addPayable(){
            this.form.payableBills.push({
                    chargeType:'',
                    paymentMoney:'',
                    billMoney:''
                });
        },
        deletePayable(index){
            this.form.payableBills.splice(index, 1);
        },
        addReceivable(){
            this.form.receivableBills.push({
                    chargeType:'',
                    paymentMoney:'',
                    billMoney:''
                });
        },
        deleteReceivable(index){
            this.form.receivableBills.splice(index, 1);
        },
        save(){
            this.$refs['form'].validate(valid => {
                if (valid) {
                    let params = JSON.parse(JSON.stringify(this.form));

                    contractService.addMonthContractEnd(params).then(res => {
                        if (res.code === 0) {
                            this.$message.success('提交成功!');
                        }
                    });
                }
            });
        }
    },
    computed:{
        ...mapState(['dict']),
        totalReceivable(){
            let res = 0;
            this.form.receivableBills.forEach(item => {
                res += parseInt(item.paymentMoney || 0);
            });
            return res;
        },
        totalPayable(){
            let res = 0;
            this.form.payableBills.forEach(item => {
                res += parseInt(item.paymentMoney || 0);
            });

            return res;
        }
    }
}
</script>
<style lang="scss">
.surrender{
    .info-box{
        position: relative;
        border:1px solid #ddd;
        padding:10px;
        overflow: hidden;
    }

    .divider{
        border-left:4px solid #329dff;
        padding-left:15px;
        margin:15px 0;
        font-weight: bold
    }

    .btn{
        display: inline-block;
        width: 16px;
        height: 16px;
        line-height: 16px;
        border-radius: 50%;
        text-align: center;
        color: #fff;

        &.add{
            background: #20a0ff;
        }

        &.delete{
            background: #ff4949;
        }
    }

    .el-form-item .el-form-item{
        margin-bottom: 22px;
    }
}
</style>

