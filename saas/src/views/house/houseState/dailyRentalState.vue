<template>
    <div class="daily-rental-state">
        <div class="search-wrap">
            <i-row>
                <i-col :span="5" class="tab-wrap" >
                    <a :class="{active: activeIndex == 0}" @click.stop="activeTab(0)">月度房态</a>
                    <a :class="{active: activeIndex == 1}" @click.stop="activeTab(1)">实时房态</a>
                    <a :class="{active: activeIndex == 2}" @click.stop="activeTab(2)">价格管理</a>
                </i-col>
                <i-col :span="1" class="split" style="">|</i-col>
                <i-col :span="3">
                    <i-select v-model="searchForm.apartmentId" size="small">
                        <i-option v-for="(option, index) in apartmentList" :key="index" :label="option.apartmentName" :value="option.apartmentInfoId"></i-option>
                    </i-select>
                </i-col>
                <i-col :span="15" style="text-align:right" v-if="activeIndex != 2">
                    <div style="width:413px;display:inline-block">
                        <i-input v-model="searchForm.content" placeholder="房号/联系客户姓名/客户号码/订单号/备注" size="small">
                            <i-select v-model="searchForm.type" slot="prepend" placeholder="请选择" style="width:112px">
                                <i-option label="房号" value="roomName" ></i-option>
                                <i-option label="联系客户姓名" value="custName" ></i-option>
                                <i-option label="客户号码" value="custPhone" ></i-option>
                                <i-option label="订单号" value="orderNo" ></i-option>
                                <i-option label="备注" value="remark" ></i-option>
                            </i-select>
                        </i-input>
                    </div>
                    <div style="width:76px;display:inline-block;vertical-align:top;margin-top:4px;margin-left:12px">
                        <i-button type="primary" style="padding:8px 15px;width:100%" @click.stop="search">搜索</i-button>
                    </div>
                </i-col>
            </i-row>
        </div>
        <div class="container">
            <div class="month-state" style="height:100%" v-if="activeIndex == 0">
                <hi-calender ref="calender" :apartmentid="searchForm.apartmentId" :contenttype="searchForm.type" :content="searchForm.content"></hi-calender>
            </div>
            <div class="actual-state" style="height:100%" v-if="activeIndex == 1">
                <actual-state ref="actual" :apartmentid="searchForm.apartmentId" :contenttype="searchForm.type" :content="searchForm.content"></actual-state>
            </div>
            <div class="price-list" style="height:100%" v-if="activeIndex == 2">
                <price-list ref="priceList" :apartmentid="searchForm.apartmentId"></price-list>
            </div>
        </div>
    </div>
</template>
<script>
import houseService from 'service/house';

import hiCalender from './component/calender';
import priceList from './component/priceList';
import actualState from './component/actualState';

import data from './mock/calender';
export default {
    components:{hiCalender, priceList, actualState},
    data(){
        return {
            searchForm:{
                apartmentId:'',
                content:'',
                type:'roomName'
            },
            dates:['05-01', '05-31'],
            activeIndex:0,
            apartmentList:[]
        }
    },
    watch:{
        activeIndex:{
            handler(){
                this.search();
            },
        }
    },
    methods:{
        activeTab(index){
            this.activeIndex = index;
        },
        getApartmentList(){
            houseService.getApartmentDropList().then(res => {
                if (res.code === 0 && res.data.length > 0) {
                    this.$set(this, 'apartmentList', res.data);
                    this.searchForm.apartmentId = res.data[0].apartmentInfoId;
                }
            });
        },
        search(){
            switch(this.activeIndex) {
                case 0:
                    this.$nextTick(() => {
                        this.$refs['calender'].search();
                    });
                    break;
                case 1:
                    this.$nextTick(() => {
                        this.$refs['actual'].search();
                    });
                    break;
                case 2:
                    this.$nextTick(() => {
                        this.$refs['priceList'].getGrid();
                    });
                    break;        
            }
        }
    },
    computed:{
        headers(){
            return data.rooms();
        },
        rooms(){
            return data.rooms();
        },
        data(){
            let ary = ['朝南','朝北', '朝东', '朝西', '阁楼'];
            let res = [];
            let list = data.headers();
            for (let i = 0; i < 5; i++) {
                let obj = {};
                let child = {}
                obj['name'] = ary[i];
                obj['type'] = '大床房'
                obj['children'] = []
                child['room'] = parseInt(Math.random() * 100);
                list.forEach(item => {
                    child[item.date] = '￥200'
                });
                obj['children'].push(child);
                child = JSON.parse(JSON.stringify(child));
                child['room'] = parseInt(Math.random() * 100);
                obj['children'].push(child);
                res.push(obj);
            }
            return res;
        }
    },
    mounted(){
        this.getApartmentList();
    }
}
</script>
<style lang="scss">
.daily-rental-state{
    padding:8px;
    background: #f5f5f5;

    .search-wrap{
        height: 56px;
        width: 100%;
        padding:8px;
        border:1px solid #e9e9e9;
        background: #fff;

        .el-input,
        .el-select{
            margin-top:4px;
        }

        .el-input-small .el-input-inner,
        .el-select.is-small input{
            height:32px;
            line-height: 32px;
        }

        .el-input{
            .el-select{
                margin: -10px;
            }

            .el-select .el-input .el-input-inner:focus{
                border-color: transparent;
            }
        }

        .split{
            line-height:40px;
            text-align:center;
            color:#d8d8d8;
            font-size:24px;
        } 

        
        .tab-wrap{
            line-height: 40px;
            padding-left:10px;
            
            a{
                position: relative;
            }

            a + a{
                margin-left: 18px;
            }

            a.active{
                color:#329DFF;

                &::before{
                    content:' ';
                    position: absolute;
                    height: 2px;
                    width: 100%;
                    background: #329DFF;
                    bottom:-16px;
                }
            }
        }
    }

    .container{
        margin-top:8px;
        height:calc(100% - 70px);
        overflow: hidden;
    }
}
</style>

