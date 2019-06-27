<template>
    <div id="day-list">
        <div class="list-type">
            <a :class="{active:this.searchForm.orderStatus == 'PREPAY'}" @click.stop="setOrderStatus('PREPAY')">已预定</a>
            <a :class="{active:this.searchForm.orderStatus == 'CHECKIN'}" @click.stop="setOrderStatus('CHECKIN')">已入住</a>
            <a :class="{active:this.searchForm.orderStatus == 'LEAVE'}" @click.stop="setOrderStatus('LEAVE')">已退房</a>
            <a :class="{active:this.searchForm.orderStatus == 'CANCEL'}" @click.stop="setOrderStatus('CANCEL')">已取消</a>
            <a :class="{active:this.searchForm.orderStatus == ''}" @click.stop="setOrderStatus('')">全部</a>

        </div>
        <div class="search-wrap">
            <i-form :model="searchForm" ref="searchForm">
                <i-row :gutter="10">
                    <i-col :span="3">
                        <i-select v-model="searchForm.apartmentInfoId" placeholder="项目" size="small" clearable>
                            <i-option v-for="(option, index) in apartmentList" :key="index" :label="option.apartmentName" :value="option.apartmentInfoId"></i-option>
                        </i-select>
                    </i-col>
                    <i-col :span="3">
                        <i-form-item prop="orderNum">
                            <i-input v-model="searchForm.orderNum" size="small" style="width:100%" placeholder="订单编号"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="3">
                        <i-form-item prop="roomNumOrName">
                            <i-input v-model="searchForm.roomNumOrName" size="small" style="width:100%" placeholder="房源编号/房号"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="3">
                        <i-form-item prop="customerNameOrPhone">
                            <i-input v-model="searchForm.customerNameOrPhone" size="small" style="width:100%" placeholder="客户姓名/客户号码"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="2">
                        <i-form-item prop="orderStatus">
                            <i-select v-model="searchForm.orderStatus" placeholder="订单状态" size="small" clearable :multiple-show-all="false" @change="selectOrderStatus">
                                <i-option v-for="(option, index) in dict['DayRentOrderStatus']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="6">
                        <i-form-item prop="remark">
                            <i-input v-model="searchForm.remark" size="small" style="width:100%" placeholder="备注"></i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="6">
                        <i-form-item prop="date">
                            <div class="type-date-wrap">
                                <div class="date-wrap">
                                    <i-date-picker v-model="searchForm.createTimeStart" placeholder="下单开始时间"  type="date" size="small"></i-date-picker>
                                    <i-date-picker v-model="searchForm.createTimeEnd" placeholder="下单截止时间"   type="date" size="small"></i-date-picker>
                                </div>
                            </div>
                        </i-form-item>
                    </i-col>
                    <i-col :span="6">
                        <i-form-item prop="date">
                            <div class="type-date-wrap">
                                <div class="date-wrap">
                                    <i-date-picker v-model="searchForm.checkInDateStart" placeholder="入住开始时间"  type="date" size="small"></i-date-picker>
                                    <i-date-picker v-model="searchForm.checkInDateEnd" placeholder="入住截止时间"   type="date" size="small"></i-date-picker>
                                </div>
                            </div>
                        </i-form-item>
                    </i-col>
                    <i-col :span="5">
                        <i-form-item prop="date">
                            <div class="type-date-wrap">
                                <div class="date-wrap">
                                    <i-date-picker v-model="searchForm.checkOutDateStart" placeholder="离店开始时间"  type="date" size="small"></i-date-picker>
                                    <i-date-picker v-model="searchForm.checkOutDateEnd" placeholder="离店截止时间"   type="date" size="small"></i-date-picker>
                                </div>
                            </div>
                        </i-form-item>
                    </i-col>
                    <i-col :span="4" style="text-align:right">
                        <i-button size="small" style="width:60px"  @click.stop="clearSearch">清空</i-button>
                        <i-button size="small" style="width:60px" type="primary" @click.stop="getGrid">搜索</i-button>
                    </i-col>
                </i-row>
            </i-form>
        </div>
        <div class="grid-wrap">
            <div class="table-wrap">
                <i-table :data="tableData"  
                style="width:100%" 
                :height="tableHeight" 
                :span-method="objectSpanMethod">
                    <i-table-column prop="orderNum" label="订单编号" width="150" align="center"></i-table-column>
                    <i-table-column label="下单时间" width="120" align="center" inline-template>
                        <div>{{new Date(row.createTime).Format('yyyy-MM-dd hh:mm')}}</div>
                    </i-table-column>
                    <i-table-column prop="sourceTypeCn" label="客户来源" align="center"></i-table-column>
                    <i-table-column prop="prepayName" label="客户姓名" align="center"></i-table-column>
                    <i-table-column prop="roomType" label="房型" align="center"></i-table-column>
                    <i-table-column prop="roomName" label="房号" align="center" inline-template>
                        <i-button type="text" class="btn-table-operate" @click.stop="openDetail(row)">{{row.roomName}}</i-button>
                    </i-table-column>
                    <i-table-column label="入住时间" width="100" align="center" inline-template>
                        <div>{{new Date(row.checkInDate).Format('yyyy-MM-dd')}}</div>
                    </i-table-column>
                    <i-table-column label="离店时间" width="100" align="center" inline-template>
                        <div>{{new Date(row.checkOutDate).Format('yyyy-MM-dd')}}</div>
                    </i-table-column>
                    <i-table-column prop="days" label="入住间夜" align="center"></i-table-column>
                    <i-table-column prop="totalPrice" label="订单金额" align="center"></i-table-column>
                    <i-table-column prop="dayRentOrderStatusCn" label="订单状态" align="center"></i-table-column>
                    <i-table-column prop="remark" label="备注" align="center"></i-table-column>
                </i-table>
            </div>
            <div class="pagination-wrap" style="text-align:center;">
                <i-pagination ref="pagination" :page-size="searchForm.size" :current-page="searchForm.current" :total="total" layout="prev,pager,next,total" @current-change="handleCurrentChange"></i-pagination>
            </div>
        </div>

        <i-drawer v-model="detailOrderDrawerStatus" title="订单详情" width="890px">
            <order-detail ref="orderDetail" ></order-detail>
        </i-drawer>
    </div>
</template>
<script>
import Promise from 'es6-promise';
import houseService from 'service/hpms-house';
import contractService from 'service/contract';
import {mapState, mapMutations} from 'vuex';
import orderDetail from '../house/houseState/component/orderDetail.vue';
const orderRowspanMap = new Map();
export default {
    data(){
        return {
            searchForm:{
                orderNum:'',
                roomNumOrName:'',
                customerNameOrPhone:'',
                orderStatus:'PREPAY',//PREPAY
                remark:'',
                createTimeStart:'',
                createTimeEnd:'',
                checkInDateStart:'',
                checkInDateEnd:'',
                checkOutDateStart:'',
                checkOutDateEnd:'',
                apartmentInfoId:'',
                current:1,
                size:30
            },
            apartmentList:[],
            tableHeight:'',
            tableData:[],
            total:0,
            detailOrderDrawerStatus:false
        }
    },
    components:{orderDetail},
    methods:{
        ...mapMutations(['LOADING']),
        selectOrderStatus(val){
            this.getGrid();            
        },
        getHouseAllList(){
            houseService.getHouseAllList().then(res => {
                if (res.code === 0) {
                    this.$set(this, 'apartmentList', res.data);
                }
            });
        },
        setOrderStatus(val){
            if (this.searchForm.orderStatus == val) {
                return;
            }
            this.searchForm.orderStatus = val;
            this.getGrid();
        },
        clearSearch(){
            this.$nextTick(() => {
                this.$refs['searchForm'].resetFields();
                this.getGrid();
            });
        },
        getGrid(){
            this.LOADING(true);
            let params = JSON.parse(JSON.stringify(this.searchForm));
            orderRowspanMap.clear();
            contractService.getDayOrderyList(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'tableData', res.data);
                    this.total = res.total;
                }
            });
        },
        handleCurrentChange(val){
            this.searchForm.current = val;
            this.getGrid();
        },
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            if(columnIndex<=3 || columnIndex==11){
                let _rowspan = 0;
                let firstOrderRowIndexKey = row.orderId+"first";
                if(orderRowspanMap.get(firstOrderRowIndexKey)==undefined){
                    orderRowspanMap.set(firstOrderRowIndexKey,rowIndex)
                }
                if(orderRowspanMap.get(row.orderId)==undefined){
                    this.tableData.forEach(data => {
                        if(data.orderId === row.orderId){
                            _rowspan++;
                        }
                    });
                    orderRowspanMap.set(row.orderId,_rowspan);
                }                    
                if(orderRowspanMap.get(firstOrderRowIndexKey)==rowIndex){
                    return {rowspan: orderRowspanMap.get(row.orderId),colspan: 1};
                }else{
                    return {rowspan: 0,colspan: 0};
                }
            }else{
                return {rowspan: 1,colspan: 1};
            }
        },
        openDetail(row){
            this.detailOrderDrawerStatus = true;
            this.$nextTick(() => {
                this.$refs['orderDetail'].getDetail(row.apartmentInfoId, row.orderId, row.orderInfoId, row.roomId);
            });
        },
        bindTableHeight(){
            this.tableHeight = document.documentElement.clientHeight - 100 - 16 - 48  - 40;
            window.addEventListener('resize', () => {
                this.tableHeight = document.documentElement.clientHeight - 100 - 16 - 48 - 40;
            });
        }
    },
    computed:{
        ...mapState(['dict'])
    },
    created(){
        this.bindTableHeight();
        this.getHouseAllList();
        this.getGrid();
    }
}
</script>
<style lang="scss">

#day-list{
    .list-type{
        padding-left:20px;
        a {
            margin-right: 10px;

            &.active{
                color: #1d90e6;
            }
        }
    }

    .search-wrap{
        margin-top:10px;

        .el-form-item{
            margin-bottom: 10px;
        }

        .el-input-group-append, .el-input-group-prepend{
            width: 90px;

            .el-input-icon{
                display: none;
            }

            .el-input-icon + .el-input-inner{
                padding-right: 10px;
            }
        }

        .type-date-wrap{

            .date-wrap{
                display: flex;
                height: 28px;
                border:1px solid #c0ccda;
                border-radius: 0 2px 2px 0;

                &:hover,
                &:focus{
                    border-color: #20a0ff;
                }

                .el-input-inner{
                    border:none;
                }

                .el-input-small .el-input-inner,
                .el-date-editor.el-input-small{
                    height: 26px;
                }
            }
        }
    }
}
    
</style>


