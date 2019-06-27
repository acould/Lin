<template>
    <div id="month-list">
        <div class="list-type">
            <a :class="{active:this.searchForm.type == 1}" @click.stop="setType(1)">全部</a>
            <a :class="{active:this.searchForm.type == 2}" @click.stop="setType(2)">将到期<span v-if="willExpireNum">({{willExpireNum}})</span></a>
            <a :class="{active:this.searchForm.type == 3}" @click.stop="setType(3)">退租未结算<span v-if="expireNoClearNum">({{expireNoClearNum}})</span></a>
        </div>
        <div class="search-wrap">
            <i-form :model="searchForm" ref="searchForm">
                <i-row :gutter="10">
                    <i-col :span="6">
                        <i-form-item prop="fieldContent">
                            <i-input v-model="searchForm.fieldContent" size="small" style="width:100%" placeholder="房源编号/房号/承租人/承租人手机">
                                <i-select v-model="searchForm.fieldType" slot="prepend" size="small">
                                    <i-option label="全部" value=""></i-option>
                                    <i-option v-for="(option, index) in dict['FieldType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-input>
                        </i-form-item>
                    </i-col>
                    <i-col :span="4">
                        <i-form-item prop="orderStatus">
                            <i-select v-model="searchForm.orderStatus" placeholder="合同状态" size="small" multiple :multiple-show-all="false">
                                <i-option v-for="(option, index) in dict['OrderStatus']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="4">
                        <i-form-item prop="depositPeriods">
                            <i-select v-model="searchForm.depositPeriods" placeholder="付款方式" size="small" multiple :multiple-show-all="false">
                                <i-option v-for="(option, index) in dict['DepositPeriod']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="3">
                        <i-form-item prop="signType">
                            <i-select v-model="searchForm.signType" placeholder="签约方式" size="small">
                                <i-option v-for="(option, index) in dict['SignType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="3">
                        <i-form-item prop="contractType">
                            <i-select v-model="searchForm.contractType" placeholder="性质" size="small">
                                <i-option v-for="(option, index) in dict['ContractType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="4">
                        <i-form-item prop="signProcess">
                            <i-select v-model="searchForm.signProcess" placeholder="签约进度" size="small" multiple :multiple-show-all="false">
                                <i-option v-for="(option, index) in dict['SignProcess']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="6">
                        <i-form-item prop="date">
                            <div class="type-date-wrap">
                                <i-select v-model="searchForm.dateType" size="small">
                                    <i-option v-for="(option, index) in dict['DateType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                                <div class="date-wrap">
                                    <i-date-picker v-model="searchForm.startDate" type="date" size="small"></i-date-picker>
                                    <i-date-picker v-model="searchForm.endDate" type="date" size="small"></i-date-picker>
                                </div>
                            </div>
                        </i-form-item>
                    </i-col>
                    <i-col :span="4">
                        <i-form-item prop="apartmentIds">
                            <i-select v-model="searchForm.apartmentIds" size="small" multiple :multiple-show-all="false">
                                <i-option v-for="(option, index) in apartmentList" :key="index" :label="option.apartmentName" :value="option.apartmentInfoId"></i-option>
                            </i-select>
                        </i-form-item>
                    </i-col>
                    <i-col :span="14" style="text-align:right">
                        <i-button size="small" style="width:60px"  @click.stop="clearSearch">清空</i-button>
                        <i-button size="small" style="width:60px" type="primary" @click.stop="getGrid">搜索</i-button>
                    </i-col>
                </i-row>
            </i-form>
        </div>
        <div class="grid-wrap">
            <div class="table-wrap">
                <i-table :data="tableData"  style="width:100%" :height="tableHeight" @row-click="handleRowClick">
                    <i-table-column prop="orderStatusCn" label="合同状态" width="100" align="center"></i-table-column>
                    <i-table-column prop="orderNum" label="合同号"  align="center"></i-table-column>
                    <i-table-column prop="apartmentName" label="房源" align="center" inline-template>
                        <div>
                            <div>{{row.orderNum}}</div>
                            <div>{{row.apartmentName}}</div>
                        </div>
                    </i-table-column>
                    <i-table-column label="租赁周期" width="120" align="center" inline-template>
                        <div>
                            <div>{{new Date(row.startDate).Format('yyyy-MM-dd')}}</div>
                            <div>{{new Date(row.endDate).Format('yyyy-MM-dd')}}</div>
                        </div>
                    </i-table-column>
                    <i-table-column label="承租人" width="120" align="center" inline-template>
                        <div>
                            <div>{{row.lesseeName}}</div>
                            <div>{{row.lesseePhone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')}}</div>
                        </div>
                    </i-table-column>
                    <i-table-column prop="price" label="月租金" width="100" align="center"></i-table-column>
                    <i-table-column prop="payPeriodCn" label="付款周期" width="100" align="center"></i-table-column>
                    <i-table-column prop="signTypeCn" label="签约方式" width="100" align="center"></i-table-column>
                    <i-table-column prop="signProcessCn" label="签约进度" width="100" align="center"></i-table-column>
                    <i-table-column prop="contractTypeCn" label="性质" width="100" align="center"></i-table-column>
                    <i-table-column label="创建日期" width="100" align="center" inline-template>
                        <div>{{new Date(row.createTime).Format('yyyy-MM-dd')}}</div>
                    </i-table-column>
                    <i-table-column prop="signPersonName" label="签约人" width="100" align="center"></i-table-column>
                </i-table>
            </div>
            <div class="pagination-wrap" style="text-align:center;">
                <i-pagination ref="pagination" :page-size="searchForm.size" :current-page="searchForm.current" :total="total" layout="prev,pager,next,total" @current-change="handleCurrentChange"></i-pagination>
            </div>
        </div>
        <i-dialog v-model="detailDialogStatus" title="查看租约" :close-on-click-modal="false">
            <month-detail ref="monthDetail"></month-detail>
            <span slot="footer">
				<i-button @click="handleDownloadPDF(downPDFUrl)" v-if="downPDFUrl!=''"  style="float: left;">下载合同</i-button>
                <i-button @click="contractEnd">退租</i-button>
                <i-button @click="renew">续租</i-button>
                <i-button @click="discard">作废</i-button>
                <i-button type="primary" @click.stop="updateContract">修改租约</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="formDialogStatus" :title="state == 'renew' ? '续租' : '修改租约'" size="large" :close-on-click-modal="false">
            <month-contract ref="monthContract"></month-contract>
            <span slot="footer">
                <i-button @click.stop="formDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveForm">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="contractEndDialogStatus" title="退租结算" size="large" :close-on-click-modal="false">
            <surrender ref="surrender"></surrender>  
            <span slot="footer">
                <i-button @click.stop="contractEndDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveEnd">提交</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="endDetailDialogStatus" title="查看租约" :close-on-click-modal="false">
            <month-detail-end ref="monthDetailEnd"></month-detail-end>
        </i-dialog>
    </div>
</template>
<script>
import Promise from 'es6-promise';
import houseService from 'service/hpms-house';
import contractService from 'service/contract';

import monthDetail from './component/monthDetail.vue';
import monthContract from './component/monthContract.vue';
import monthDetailEnd from './component/monthDetailEnd.vue';
import surrender from './component/surrender.vue';

import {mapState, mapMutations} from 'vuex';
export default {
    data(){
        return {
            searchForm:{
                type:1,
                fieldType:'',
                fieldContent:'',
                orderStatus:[],
                depositPeriods:[],
                payPeriods:[],
                signType:'',
                contractType:'',
                signProcess:[],
                dateType:'CREATE_TIME',
                startDate:'',
                endDate:'',
                apartmentIds:[],
                current:1,
                size:30
            },
            apartmentList:[],
            tableHeight:'',
            tableData:[],
            total:0,
            willExpireNum:0,
            expireNoClearNum:0,
            detailDialogStatus:false,
            formDialogStatus:false,
            endDetailDialogStatus:false,
            contractEndDialogStatus:false,
            orderId:'',
			state:'renew',
			downPDFUrl:'',
        }
    },
    components:{monthDetail,monthDetailEnd,monthContract,surrender},
    methods:{
        ...mapMutations(['LOADING']),
        setType(val){
            if (this.searchForm.type == val) {
                return;
            }

            this.searchForm.type = val;
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

            contractService.getMonthContractList(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'tableData', res.data.orders);
                    this.willExpireNum = res.data.willExpireNum;
                    this.expireNoClearNum = res.data.expireNoClearNum;
                    this.total = res.total;
                }
            });
        },
        handleCurrentChange(val){
            this.searchForm.current = val;
            this.getGrid();
        },
        handleRowClick(row){
			this.LOADING(true);
            if (row.isEnd == 'Y') {
                Promise.all([contractService.getMonthContractDetail(row.orderId), 
                             contractService.getMonthContractEndDetail(row.orderId)])
                             .then(res => {
                    this.LOADING(false);                                 
                    if (res[0].code === 0 && res[1].code === 0) {
                        this.endDetailDialogStatus = true;
                        this.$nextTick(() => {
							this.$refs['monthDetailEnd'].getDetail(res[0].data, res[1].data);
							if(res[0].data.pdfSrc!=''){
								this.downPDFUrl=res[0].data.pdfSrc;
							}else{
								this.downPDFUrl='';
							}
                        });
                    }
                });
            } else {
                contractService.getMonthContractDetail(row.orderId).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.detailDialogStatus = true;
						this.orderId = row.orderId;
						
						if(res.data.pdfSrc!=''){
							this.downPDFUrl=res.data.pdfSrc;
						}else{
							this.downPDFUrl='';
						}
						
                        this.$nextTick(() => {
                            this.$refs['monthDetail'].getDetail(res.data);
                        });
                    }
                });
            }
            
        },
        getHouseAllList(){
            houseService.getHouseAllList().then(res => {
                if (res.code === 0) {
                    this.$set(this, 'apartmentList', res.data);
                }
            });
        },
        discard(){
            this.$refs['monthDetail'].discard();
		},
		//修改租约
        updateContract(){
            this.state = 'update';
			this.formDialogStatus = true;
			this.$nextTick(() => {
				this.$refs['monthContract'].getDetail(this.orderId);
			})
        },
        bindTableHeight(){
            this.tableHeight = document.documentElement.clientHeight - 100 - 16 - 48  - 40;
            window.addEventListener('resize', () => {
                this.tableHeight = document.documentElement.clientHeight - 100 - 16 - 48 - 40;
            });
        },
        saveForm(){
			// console.log('saveFormsaveForm')
            this.$refs['monthContract'].saveContract();
        },
        contractEnd(){
            this.contractEndDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['surrender'].getInfo(this.orderId);
            });
        },
        saveEnd(){
            this.$refs['surrender'].save();
		},
		//续租
        renew(){
            this.state = 'renew';
			this.formDialogStatus = true;
			this.$nextTick(() => {
				this.$refs['monthContract'].getDetail(this.orderId);
			})
		},
		handleDownloadPDF(src){
            this.downloadPdf(src);
        },
        downloadPdf(url){
            let a = document.createElement('a');

            a.href = url;

            a.target = '_blank';

            a.download = '电子合同.pdf';

            a.click();
        },
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

#month-list{
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
            .el-select{
                width: 90px;
                float: left;

                .el-input-icon{
                    display: none;
                }

                .el-input-icon + .el-input-inner{
                    padding-right: 10px;
                }

                .el-input-inner{
                    background: #f9fafc;
                    border-radius: 2px 0 0 2px;
                }
            }

            .date-wrap{
                margin-left: 90px;
                display: flex;
                height: 28px;
                border:1px solid #c0ccda;
                border-left: none;
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


