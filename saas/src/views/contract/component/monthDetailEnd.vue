<template>
    <div class="month-detail-end">
        <i-tabs v-model="activeName">
            <i-tab-pane label="退租结算单" name="first">
                <div class="divider">结算内容</div>
                <div class="info-box mt10">
                    <i-row :gutter="10">
                        <i-col :span="8">
                            <label>退租类型:</label>
                            <span>{{endForm['endTypeCn']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>合同终止日:</label>
                            <span>{{endForm['endDate']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>违约金:</label>
                            <span>{{endForm['breachAmount']}}</span>
                        </i-col>
                        <i-col :span="24">
                            <label>退租原因:</label>
                            <span>{{endForm['endReason']}}</span>
                        </i-col>
                        <i-col :span="24">
                            <label style="float:left;margin-right:10px">证件照片:</label>
                            <i-img-list v-model="endForm['attachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                        </i-col>
                    </i-row>
                </div>
                <div class="divider">结算金额</div>
                <div class="info-box">
                    <label style="float:left">应收款项:</label>
                    <div style="margin-left:70px;">
                        <table class="i-table">
                            <thead>
                                <tr>
                                    <th>费用类型</th>
                                    <th>应收金额</th>
                                    <th>实际应收金额</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item, itemIndex) in endForm['receivableBills']" :key="itemIndex">
                                    <td>{{item.chargeTypeCn}}</td>
                                    <td>{{item.billMoney}}</td>
                                    <td>{{item.paymentMoney}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <label style="float:left">应付款项:</label>
                    <div style="margin-left:70px;">
                        <table class="i-table">
                            <thead>
                                <tr>
                                    <th>费用类型</th>
                                    <th>应付金额</th>
                                    <th>实际应付金额</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item, itemIndex) in endForm['payableBills']" :key="itemIndex">
                                    <td>{{item.chargeTypeCn}}</td>
                                    <td>{{item.billMoney}}</td>
                                    <td>{{item.paymentMoney}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="divider">结算金额</div>
                <div class="info-box mt10">
                    <i-row :gutter="10">
                        <i-col :span="8">
                            <label>收款人类型:</label>
                            <span>{{endForm['payeeTypeCn']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>收款人姓名:</label>
                            <span>{{endForm['payeeName']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>收款账号类型:</label>
                            <span>{{endForm['payeeAccountTypeCn']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>收款账号:</label>
                            <span>{{endForm['account']}}</span>
                        </i-col>
                        <i-col :span="8">
                            <label>开户行:</label>
                            <span>{{endForm['bankName']}}</span>
                        </i-col>
                        <i-col :span="24">
                            <label style="float:left;margin-right:10px">代收款证明:</label>
                            <i-img-list v-model="endForm['proves']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                        </i-col>
                    </i-row>
                </div>
            </i-tab-pane>
            <i-tab-pane label="合同租约详情" name="second">
                <div class="month-detail-header">
            <h6 class="apartment-name">签约房源:{{form['apartmentName']}}</h6>
            <div class="tag-list">
                <i-tag type="primary">{{form['contractTypeCn']}}</i-tag>
                <i-tag type="primary">{{form['signTypeCn']}}</i-tag>
                <i-tag type="primary">下载合同</i-tag>
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
        <i-row class="mt20" v-if="form['lessee'] || form['agent']">
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
        <i-row class="mt20">
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
        <i-row  class="mt20">
            <i-col :span="18">
                <div class="divider">应收账单</div>
            </i-col>
            <i-table :data="form['bills']"  style="width:100%">
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
            </i-tab-pane>
        </i-tabs>
    </div>
    
</template>
<script>
import config from 'config';

export default {
    data(){
        return {
            activeName:'first',
            form:{
                
                attachments:[]
            },
            endForm:{
                attachments:[],
                proves:[],
                receivableBills:[],
                payableBills:[]
            },
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
        }
    },
    methods:{
        getDetail(form, endForm){
            this.$set(this, 'endForm', endForm);
            this.$set(this, 'form', form);
        }
    }
}
</script>
<style lang="scss">
.month-detail-end{
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

        &.mt10{
            .el-col{
                margin-bottom: 10px;
            }
        }
    }

    .divider{
        border-left:4px solid #329dff;
        padding-left:15px;
        margin:15px 0;
        font-weight: bold;
        position: relative;
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
</style>

