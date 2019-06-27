<template>
    <div class="month-bill-box">
       <div class="bill-list">
           <i-table :data="pageData" border stripe style="width:100%">
                <i-table-column prop="orderNum" label="订单号" align="center" width="150"></i-table-column>
                <i-table-column  label="订单周期" align="center" width="150" inline-template>
                    <span v-if="row">{{row.startDate}}-{{row.endDate}}</span>
                </i-table-column>
                <i-table-column prop="price" label="房费" align="center" width="150"></i-table-column>
                <i-table-column prop="lesseeName" label="租客姓名" align="center" width="150"></i-table-column>
                <i-table-column prop="dayRentOrderStatusCn:" label="订单状态" align="center"></i-table-column>
                <i-table-column label="操作" align="center">
					<i-button type="text">查看</i-button>
                </i-table-column>
            </i-table>

            
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

<script>
import dictsService from 'service/common';
import hpmsService from 'service/hpms-house';
import hpmsFinanceService from 'service/hpms-finance';

import { mapState,mapMutations } from 'vuex';
export default {
    props:{
        // roomId:String,
    },
    data(){
        return {
			roomId:'',
            pageNumber:1,
            pageSize:10,
            total:0,
            orderType:'DAY_RENT',
            onlyEffective:'Y',
            pageData:[],
            /* pageData:[
                {
                    orderNum:'CN23123',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:3000,
                },
                {
                    orderNum:'CN23133',
                    chargeType:'123',
                    startDate:'2019-3-3',
                    endDate:'2019-4-3',
                    billMoney:3200,
                    billDate:'2019-3-3',
                    paymentMoney:300,
                }
            ] */
        }
    },
    mounted(){
        
    },
    methods: {
        ...mapMutations(['LOADING']),
        init(id){
			this.roomId=id;
            // console.log('month init')
            this.pageNumber=1;
            this.getList();
        },
        getList(){
            let params={
                roomId:this.roomId,
                orderType:this.orderType,
                current:this.pageNumber,
                size:this.pageSize,
            };
            // console.log(params);
            this.LOADING(true);
            hpmsService.getApartmentOrders(params).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.total=res.total;
                    this.pageData=res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        
       
        handleCurrentChange(val){
            this.pageNumber = val;
            this.getList();
        },
        
    }
}
</script>

