<template>
    <div class="month-bill-box">
       <div class="bill-list">
            <i-table :data="pageData" border stripe style="width:100%" @row-dblclick = "openInfo">
                <i-table-column prop="orderNum" label="合同号" align="center" width="150"></i-table-column>
                <i-table-column prop="chargeTypeCn" label="类型" align="center" width="150"></i-table-column>
                <i-table-column  label="账期" align="center" width="150" inline-template>
                    <span v-if="row">{{row.startDate}}-{{row.endDate}}</span>
                </i-table-column>
                <i-table-column prop="billMoney" label="应收金额" align="center" width="150"></i-table-column>
                <i-table-column prop="billDate" label="应收日期" align="center" width="150"></i-table-column>
                <i-table-column prop="billStatusCn" label="收款状态" align="center" width="150"></i-table-column>
                <i-table-column  label="操作" align="center" width="150" inline-template>
					<i-button type="text"  @click="openInfo(row)">查看详情</i-button>
                </i-table-column>
            </i-table>

            <i-dialog v-model="isShowInfo" title="详情" size="small"  append-to-body >
                <div class="list-info">
                    <i-row :gutter="10">
                        <i-col :span="12">
                            <span>是否逾期：</span>{{this.showInfoData.isOverdueCn}}
                            <a href="javascript:;" style="margin-left:20px" v-show="this.showInfoData.isOverdue=='Y'" @click="collectionSms()">催款</a>
                        </i-col>
                        <i-col :span="12">
                            <span>实收金额：</span>{{this.showInfoData.paymentMoney}}
                        </i-col>
                     </i-row>
                    <i-row :gutter="10">
                        <i-col :span="12">
                            <span>实收日期：</span>{{this.showInfoData.paymnetDate}}
                        </i-col>
                        <i-col :span="12">
                            <span>收款渠道：</span>{{this.showInfoData.paymentChannel}}
                        </i-col>
                    </i-row>
                    <i-row :gutter="10">
                        <i-col :span="12">
                            <span>收款人：</span>{{this.showInfoData.paymentUid}}
                        </i-col>
                        <i-col :span="12">
                            <span>收费审核：</span>{{this.showInfoData.billStatusCn}}
                        </i-col>
                    </i-row>
                </div>
            </i-dialog>
            
       </div>
        <div class="pagination-wrap" style="float: right;">
            <i-pagination ref="pagination"
                @current-change="handleCurrentChange"
                :current-page="pageNumber"
                :page-size="pageSize"
                layout="prev,pager,next,jumper"
                :total="total" v-show="total>0">
            </i-pagination>
        </div>
    </div>
    
</template>
<style lang="scss" scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

   .warning-row {
    background: oldlace;
  }

   .success-row {
    background: #f0f9eb;
  }
   a,a:hover{
          color:#329DFF;
      }
  .list-info{
     font-size: 12px;
  }
</style>
<script>
import dictsService from 'service/common';
import hpmsService from 'service/hpms-house';
import hpmsFinanceService from 'service/hpms-finance';

import { mapState,mapMutations } from 'vuex';

export default {
    // props:{
    //     params:Object,
    // },
    data(){
        return {
            pageNumber:1,
            pageSize:10,
            total:3,
            rentType:'MONTH_RENT',
            isShowInfo:false,
			showInfoData:{},
			params:{},
            // pageData:[],
            pageData:[
                // {
                //     orderNum:'CN23120',
                //     chargeType:'123',
                //     startDate:'2019-3-3',
                //     endDate:'2019-4-3',
                //     billMoney:3200,
                //     billDate:'2019-3-3',
                //     paymentMoney:3000,
                //     isOverdueCn:'是',
                //     isOverdue:'Y',
                //     billId:123,
                //     paymentMoney:123,
                //     paymnetDate:'2019-3-4',
                //     paymentChannel:'微信',
                // },
                // {
                //     orderNum:'CN23133',
                //     chargeType:'123',
                //     startDate:'2019-3-3',
                //     endDate:'2019-4-3',
                //     billMoney:3200,
                //     billDate:'2019-3-3',
                //     paymentMoney:300,
                // }
            ],
           
        }
    },
    mounted(){
        
    },
    methods: {
        ...mapMutations(['LOADING']),
        init(params){
			this.params=params;
			console.log(params);
            // console.log('month init')
            this.pageNumber=1;
            this.getList();
        },
        getList(){
            let params={
                roomId:this.params.roomId,
                rentType:this.rentType,
                onlyEffective:this.params.onlyEffective,
                current:this.pageNumber,
                size:this.pageSize,

            };
            // console.log(params);
            this.LOADING(true);
            hpmsFinanceService.getHouseBillList(params).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                 /*    this.total=30
                    this.pageData=[
                        {
                   orderNum:'CN23120',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                    isOverdueCn:'是',
                    isOverdue:'Y',
                    billId:123,
                    paymentMoney:123,
                    paymnetDate:'2019-3-4',
                    paymentChannel:'微信',
                    paymentUid:'12321',
                    billStatusCn:'审核',
                },{
                    orderNum:'CN23120',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                    isOverdueCn:'是',
                    billId:123,
                    paymentMoney:123,
                    paymnetDate:'2019-3-4',
                    paymentChannel:'微信',
                    paymentUid:'12321',
                    billStatusCn:'审核',
                },{
                    orderNum:'CN23123',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                },{
                    orderNum:'CN23123',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                },{
                    orderNum:'CN23123',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                },{
                    orderNum:'CN23123',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                },
                    ] */
                    this.total=res.total;
                    this.pageData=res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        collectionSms(billId){
            var billId=this.showInfoData.billId;
            this.LOADING(true);
            hpmsFinanceService.collectionSms(billId).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('操作催款成功');
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        openInfo(row){
            console.log('row',row);
            this.isShowInfo=true;
            this.showInfoData=row;
        },
       
        handleCurrentChange(val){
            this.pageNumber = val;
            this.getList();
        },
        
    }
}
</script>

