<template>
    <div id="month-detail">
        <div class="month-detail-header">
            <h6 class="apartment-name">签约房源:{{form['apartmentName']}}</h6>
            <div class="tag-list">
                <i-tag type="primary">{{form['contractTypeCn']}}</i-tag>
                <i-tag type="primary">{{form['signTypeCn']}}</i-tag>
                <!-- <i-tag type="primary">下载合同</i-tag> -->
            </div>
            <i-row>
                <i-col :span="24">
                    <label>地址:</label>
                    <span>{{form['apartmentAddress']}}</span>
                </i-col>
                <i-col :span="6">
                    <label>合同状态:</label>
                    <span>{{form['orderStatusCn']}}</span>
                </i-col>
                <i-col :span="6">
                    <label>签约进度:</label>
                    <span>{{form['signProcessCn']}}</span>
                </i-col>
                
            </i-row>
        </div>
        <i-row :gutter="10" class="mt20" v-if="form['lessee'] || form['agent']">
            <i-col :span="24">
                <div class="divider">承租人信息</div>
            </i-col>
            <div class="info-box">
                <div class="lessee" v-if="form['lessee']">
                <i-col :span="8">
                    <label>承租人姓名:</label>
                    <span>{{form['lessee']['customerName']}}</span>
                </i-col>
                <i-col :span="8">
                    <label>手机号码:</label>
                    <span>{{form['lessee']['customerPhone']}}</span>
                </i-col>
                <i-col :span="8">
                    <label>{{form['lessee']['cardTypeCn']}}:</label>
                    <span>{{form['lessee']['idCard']}}</span>
                </i-col>
                <i-col :span="24">
                    <label>邮箱:</label>
                    <span>{{form['lessee']['email']}}</span>
                </i-col>
                <i-col :span="24">
                    <label style="float:left;margin-right:10px">证件照片:</label>
                    <i-img-list v-model="form['lessee']['cardAttachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                </i-col>
            </div>
            <div class="agent" v-if="form['agent']">
                <i-col :span="8">
                    <label>代理人姓名:</label>
                    <span>{{form['agent']['customerName']}}</span>
                </i-col>
                <i-col :span="8">
                    <label>手机号码:</label>
                    <span>{{form['agent']['customerPhone']}}</span>
                </i-col>
                <i-col :span="8">
                    <label>{{form['agent']['cardTypeCn']}}:</label>
                    <span>{{form['agent']['idCard']}}</span>
                </i-col>
                <i-col :span="24">
                    <label>邮箱:</label>
                    <span>{{form['agent']['email']}}</span>
                </i-col>
                <i-col :span="24">
                    <label style="float:left;margin-right:10px">证件照片:</label>
                    <i-img-list v-model="form['agent']['cardAttachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                </i-col>
            </div>
            </div>
            
        </i-row>
        <i-row :gutter="10" class="mt20">
            <i-col :span="24">
                <div class="divider">合同内容</div>
            </i-col>
            <div class="info-box">
                <i-col :span="8">
                <label>合同号:</label>
                <span>{{form['orderNum']}}</span>
            </i-col>
            <i-col :span="8">
                <label>签约日期:</label>
                <span>{{form['signDate']}}</span>
            </i-col>
            <i-col :span="8">
                <label>合同期:</label>
                <span>{{ new Date(form['startDate']).Format('yyyy-MM-dd') + '~' + new  Date(form['endDate']).Format('yyyy-MM-dd')}}</span>
            </i-col>
            <i-col :span="8">
                <label>付款方式:</label>
            </i-col>
            <i-col :span="8">
                <label>月租金:</label>
                <span>{{form['price'] + '元/月'}}</span>
            </i-col>
            <i-col :span="8">
                <label>押金:</label>
                <span>{{form['receiptsDeposit'] + '元'}}</span>
            </i-col>
            <i-col :span="24">
                <label>收款日期:</label>
                <span>{{form['payDateTypeCn'] + '-' +  (form['PayDateType'] == 'ADVANCE' ? form['paymentAdvanceDays'] + '天' : '每月' + form['paymentDays'] + '号')}}</span>
            </i-col>
            <i-col :span="24" v-if="form['otherFees']">
                <label style="float:left">其他费用:</label>
                <div style="margin-left:70px;">
                    <table class="i-table">
                        <thead>
                            <tr>
                                <th>费用类型</th>
                                <th>支付方式</th>
                                <th>金额</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, itemIndex) in form['otherFees']" :key="itemIndex">
                                <td>{{item.feeTypeCn}}</td>
                                <td>{{item.paymentTypeCn}}</td>
                                <td>{{item.money}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                
            </i-col>
            <i-col :span="24">
                <label>备注及补充约定:</label>
                <span>{{form['remark']}}</span>
            </i-col>
            <i-col :span="24">
                <label style="float:left;margin-right:10px">合同附件:</label>
                <i-img-list v-model="form['attachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
            </i-col>
            </div>
            
        </i-row>
        <i-row :gutter="10" class="mt20">
            <i-col :span="18">
                <div class="divider">应收账单</div>
            </i-col>
            <i-col :span="6" style="text-align:right">
                <i-button type="text" style="margin-top:8px" @click.stop="addReceipt">新增账期</i-button>
            </i-col>
            <i-table :data="form['bills']"  style="width:100%" :height="tableHeight" @row-click="handleRowClick">
                <i-table-column prop="chargeTypeCn" label="费用类型"  align="center"></i-table-column>
                <i-table-column label="账期"  align="center" width="200" inline-template>
                    <div>{{new Date(row.startDate).Format('yyyy-MM-dd') + '~' + new Date(row.endDate).Format('yyyy-MM-dd')}}</div>
                </i-table-column>
                <i-table-column prop="billMoney" label="应收金额"  align="center"></i-table-column>
                <i-table-column prop="billDate" label="应收日期"  align="center"></i-table-column>
                <i-table-column prop="remark" label="备注"  align="center"></i-table-column>
                <i-table-column prop="billStatusCn" label="收款状态"  align="center"></i-table-column>
            </i-table>
        </i-row>
        <i-dialog v-model="billDetailDialogStatus" class="pd0" title="应收详情" append-to-body>
            <div>
                <div class="bill-info bb-line">
                    <i-row :gutter="10">
                        <i-col :span="8">
                            <label>应收金额:</label>
                            <span>{{billForm['billMoney']}}</span>
                        </i-col>
                        <i-col :span="12">
                            <label>账期:</label>
                            <span>{{new Date(billForm['startDate']).Format('yyyy-MM-dd') + '~' + new Date(billForm['endDate']).Format('yyyy-MM-dd')}}</span>
                        </i-col>
                        <i-col :span="4">
                            <i-tag type="primary">{{billForm['billStatusCn']}}</i-tag>
                        </i-col>
                        <i-col :span="8">
                            <label>费用类型:</label>
                            <span>{{billForm['chargeTypeCn']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>应收日期:</label>
                            <span>{{new Date(billForm['billDate']).Format('yyyy-MM-dd')}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>剩余应收:</label>
                            <span>{{billForm['surplusPaymentMoney']}}</span>
                        </i-col>
                        <i-col :span="24">
                            <label>备注:</label>
                            <span>{{billForm['remark']}}</span>
                        </i-col>
                    </i-row>
                </div>
                <div class="bill-info" v-if="billForm['billList'].length > 0">
                    <div class="divider">实收详情</div>
                    <ul>
                        <li v-for="(item, itemIndex) in billForm['billList']" :key="itemIndex">
                            <i-row :gutter="10">
                                <i-col :span="8">
                                    <label>实收金额:</label>
                                    <span>{{item.paymentMoney}}</span>
                                </i-col>
                                <i-col :span="8">
                                    <label>实收日期:</label>
                                    <span>{{new Date(item.paymentDate).Format('yyyy-MM-dd')}}</span>
                                </i-col>
                                <i-col :span="8">
                                    <label>收款人:</label>
                                    <span>{{item.paymentUName}}</span>
                                </i-col>
                                <i-col :span="8">
                                    <label>收款渠道:</label>
                                    <span>{{item.paymentChannelCn}}</span>
                                </i-col>
                                <i-col :span="16">
                                    <label>实收备注:</label>
                                    <span>{{item.remark}}</span>
                                </i-col>
                                <i-col :span="24" style="text-align:right">
                                    <i-button type="text" @click.stop="deleteBill(item, itemIndex)" style="color:#ff4949">删除</i-button>
                                    <i-button type="text" @click.stop="updateBill(item, itemIndex)">修改</i-button>
                                </i-col>
                            </i-row>
                        </li>
                    </ul>
                </div>
            </div>
            <span slot="footer">
                <i-button @click.stop="deleteReceipt">撤销账单</i-button>
                <i-button @click.stop="updateReceipt">修改应收</i-button>
                <i-button type="primary" @click.stop="addBill">新增实收</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="billDialogStatus" class="pd0" title="新增实收" append-to-body>
            <div>
                <div class="bill-info bb-line">
                    <i-row :gutter="10" style="padding:10px;background:#eee">
                        <i-col :span="5">
                            <label>应收金额:</label>
                            <span>{{billForm['billMoney']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>账期:</label>
                            <span>{{new Date(billForm['startDate']).Format('yyyy-MM-dd') + '~' + new Date(billForm['endDate']).Format('yyyy-MM-dd')}}</span>
                        </i-col>
                        <i-col :span="5">
                            <label>费用类型:</label>
                            <span>{{billForm['chargeTypeCn']}}</span>
                        </i-col>
                        <i-col :span="6">
                            <label>应收日期:</label>
                            <span>{{new Date(billForm['billDate']).Format('yyyy-MM-dd')}}</span>
                        </i-col>
                    </i-row>
                </div>
                <i-form :model="realityForm" label-width="100px" ref="realityForm" style="padding: 20px">
                    <i-row :gutter="10">
                        <i-col :span="12">
                            <i-form-item label="实收金额:" prop="paymentMoney">
                                <i-input v-model="realityForm.paymentMoney"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="实收日期:" prop="paymentDate">
                                <i-date-picker type="date" v-model="realityForm.paymentDate" style="width:100%"></i-date-picker>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="收款人:">
                                <i-input v-model="account.userName" :disabled="true"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="收款渠道:" prop="paymentChannel">
                                <i-select v-model="realityForm.paymentChannel">
                                    <i-option v-for="(option, index) in dict['ReceivableChannel']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="24">
                            <i-form-item label="备注:" prop="remark">
                                <i-input type="textarea" v-model="realityForm.remark"></i-input>
                            </i-form-item>
                        </i-col>
                    </i-row>
                </i-form>
            </div>
            <span slot="footer">
                <i-button @click.stop="billDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveBill">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="receiptDialogStatus" :title="receiptState == 'update' ? '修改应收' : '新增应收'" append-to-body>
            <receipt ref="receipt" @submit="submitReceipt"></receipt>
            <span slot="footer">
                <i-button @click.stop="receiptDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveReceipt">确定</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import contractService from 'service/contract';
import financeService from 'service/hpms-finance';
import config from 'config';

import { mapState, mapMutations } from 'vuex';

import receipt from './receipt.vue';

export default {
    components:{receipt},
    data(){
        return {
            form:{
                orderId:'',
                attachments:[]
            },
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            tableHeight:'300',
            billDetailDialogStatus:false,
            billForm:{
                billId:'',
                billList:[]
            },
            billDialogStatus:false,
            billState:'add',
            billActive:0,
            realityForm:{
                billId:'',
                paymentMoney:'',
                paymentDate:'',
                paymentChannel:'',
                remark:'',
                paymentUName:''
            },
            receiptDialogStatus:false,
            receiptState:'add',
            receiptForm:{
                orderId:'',
                billId:'',
                chargeType:'',
                date:['',''],
                billMoney:'',
                startDate:'',
                endDate:'',
                billDate:'',
                remark:''
            },
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        getDetail(form){
            this.$set(this, 'form', form);
        },
        discard(){
            this.LOADING(true);
            contractService.discardContract(this.form.orderId).then(res => {
                this.LOADING(false);
                if (res.code  === 0) {
                    this.$message.success('操作成功');
                }
            });
        },
        handleRowClick(row, event, column){
            this.LOADING(true);
            
            financeService.getHousePaymentBillDetail(row.billId).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    let obj = JSON.parse(JSON.stringify(res.data));
                    obj['billList'] = res.data.paymentList;
                    this.$set(this, 'billForm', obj);
                    this.billDetailDialogStatus = true;
                }
            });
        },
        downloadContract(){
            console.log('download');
        },
        addBill(){
            this.realityForm.paymentMoney = '';
            this.realityForm.paymentDate = '';
            this.realityForm.paymentChannel = '';
            this.realityForm.remark = '';
            this.realityForm.billId = this.billForm.billId;
            this.billDialogStatus = true;
            this.billState = 'add'
        },
        updateBill(item, index){
            this.$set(this, 'realityForm', item);
            this.billDialogStatus = true;
            this.billState = 'update';
            this.billActive = index;
        },
        deleteReceipt(){
            let price = 0;
            this.billForm['billList'].forEach(item => {
                price += parseInt(item.paymentMoney);
            });

            if (price > 0) {
                this.$message.error('已有实收,不可以删除应收账单');
                return;
            }  
            
            this.$confirm('确定要撤销该账单吗?撤销后不可恢复', '提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(res => {
                this.LOADING(true);
                financeService.deleteHouseBill(this.billForm.billId).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        contractService.getMonthContractDetail(this.form.orderId).then(result => {
                            if (result.code === 0) {
                                this.getDetail(result.data);
                                this.billDetailDialogStatus = false;
                            }
                        });
                    }
                });
            });
        },
        addReceipt(){
            this.receiptDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['receipt'].addReceipt(this.form.orderId);
            });
            this.receiptState = 'add'
        },
        updateReceipt(){
            let params = JSON.parse(JSON.stringify(this.billForm));
            this.receiptDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['receipt'].getDetail(params);
            });
            this.receiptState = 'update'
        },
        saveReceipt(){
            this.$refs['receipt'].saveReceipt();
            
        },
        submitReceipt(params, state){
            if (this.receiptState == 'add') {
                financeService.addHouseBill(params).then(res => {
                    if (res.code === 0) {
                        this.receiptSuccess();
                    }
                });
            } else {
                financeService.updateHouseBill(params).then(res => {
                    if (res.code == 0) {
                        this.receiptSuccess();
                    }
                });
            }
        },
        receiptSuccess(){
            this.LOADING(true);
            contractService.getMonthContractDetail(this.form.orderId).then(result => {
                this.LOADING(false);
                if (result.code === 0) {
                    this.getDetail(result.data);
                }
            });
        },
        saveBill(){

            this.$refs['realityForm'].validate(valid => {
                if (valid) {
                    this.LOADING(true);
                    let params = JSON.parse(JSON.stringify(this.realityForm));
                    if (this.billState == 'add') {
                        financeService.addHousePaymentBill(params).then(res => {
                            this.LOADING(false);
                            if (res.code === 0) {
                                this.$message.success('保存成功');
                                this.billForm.billList.push(res.data);
                                this.billDialogStatus = false;
                            }
                        });
                    } else {
                        financeService.updateHousePaymentBill(params).then(res => {
                            this.LOADING(false);
                            if (res.code === 0) {
                                this.$message.success('保存成功');
                                this.$set(this.billForm.billList, this.billActive, params);
                            }
                        });
                    }
                    
                }
            });
        },
        deleteBill(item, itemIndex){
            this.$confirm('确定要删除该条实收记录吗？删除后会影响费用的收款状态', '提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                financeService.deleteHousePaymentBill(item.paymentId).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.$message.success('删除成功');
                        this.billForm.billList.splice(itemIndex,1);
                    }
                });
            }).catch(() => {

            });
        }
    },
    computed:{
        ...mapState(['dict', 'account'])
    }
}
</script>
<style lang="scss">
#month-detail{
    .month-detail-header{
        overflow: hidden;
        margin-bottom:10px;
    }

    .apartment-name{
        font-size:16px;
        font-weight: bold;
        float: left;
    }

    .tag-list{
        float: left;
        margin-left:10px;
    }

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

    .mt20{
        margin-bottom: 20px;

        .el-col{
            margin-bottom: 10px;
        }
    }

    .i-table{
        border-collapse: collapse;
        width: 100%;
        background-color: #fff;
        color: #5e6d82;
        font-size: 12px;
        margin-bottom: 10px;

        th{
            border-top: 1px solid #eaeefb;
            background-color: #eff2f7;
        }

        th,td{
            border:1px solid #eaeefb;
            padding: 10px 0;
            text-align: center;
        }

        th:first-child,
        td:first-child{
            padding-left: 10px;

        }
    }
}

.el-dialog-wrapper.pd0{
    .el-dialog-body{
        padding: 30px 0;

        .bill-info{
            padding:0 20px 10px 20px;

            &.bb-line{
                border-bottom: 1px solid #c0ccda;
            }
        }

        .divider{
            border-left:4px solid #329dff;
            padding-left:15px;
            margin:15px 0;
            font-weight: bold
        }
    }
}

.date-range{
    .first{
        .el-input-inner{
            border-right: none;
            border-radius: 2px 0 0 2px;
        }
    }

    .second{
        .el-input-inner{
            border-left: none;
            border-radius: 0 2px 2px 0;
        }
    }
    .el-input-inner:hover,
    .el-input-inner:focus{
        border-color:#c0ccda
    }
}
</style>


