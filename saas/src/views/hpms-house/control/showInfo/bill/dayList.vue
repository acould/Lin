<template>
    <div class="month-bill-box">
       <div>
           <i-table :data="pageData" border stripe style="width:100%" >
                <i-table-column prop="orderNum" label="订单编号" align="center" width="150"></i-table-column>
                <i-table-column prop="chargeTypeCn" label="类型" align="center" width="150"></i-table-column>
                <i-table-column  label="账期" align="center" width="150" inline-template>
                    <span v-if="row">{{row.startDate}}-{{row.endDate}}</span>
                </i-table-column>
                <i-table-column prop="billMoney" label="应收金额" align="center" width="150"></i-table-column>
                <i-table-column prop="billDate" label="应收日期" align="center"></i-table-column>
				<i-table-column prop="billStatusCn" label="收款状态" align="center" width="150"></i-table-column>
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
            total:0,
			rentType:'DAY_RENT',
			params:{},
            // pageData:[],
            pageData:[
                // {
                //     orderNum:'CN23123',
                //     chargeType:'123',
                //     startDate:'2019-3-3',
                //     endDate:'2019-4-3',
                //     billMoney:3200,
                //     billDate:'2019-3-3',
                //     paymentMoney:3000,
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
            ]
        }
    },
    mounted(){
        
    },
    methods: {
        ...mapMutations(['LOADING']),
        init(params){
			this.params=params;
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

