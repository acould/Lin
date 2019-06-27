<template>
    <div class="history-box">
        <div class="head" style="border-bottom:none">
            <p>历史数据</p>
            <p class="operation-box">
            </p>
        </div>
        <div class="history-info">
            <div class="table-head">时间</div>
            <div class="history-total-data">
                <div class="data-li">
                    <p><label>创建日期：</label>{{data.createTime}}</p>
					<p><label>累计空闲时长：</label>{{data.vacantTotal}}</p>
                </div>
                <!-- <div class="data-li">
                    <p><span>委托周期：</span>{{data.startDate}}~{{data.endDate}}</p>
                    <p><span>装修日期：</span>{{data.decorationCn}}</p>
                </div> -->
            </div>

            <div class="table-head">业主合同</div>
            <div>
                <i-table :data="ownerData" style="width:100%" :showHeader="false">
                    <i-table-column prop="date" label="合同号" ></i-table-column>
                    <i-table-column prop="name" label="承租周期"></i-table-column>
                    <i-table-column prop="name" label="月租金"></i-table-column>
                    <i-table-column prop="address" label="收款状态"></i-table-column>
                    <i-table-column prop="address" label="操作" inline-template>
                        <a href="javascript:;" >操作</a>
                    </i-table-column>
                </i-table> 
            </div>

           
        </div>

        <i-tabs  v-model="activeName" ref="tab">
        <i-tab-pane label="月租租约" name="monthList" >
            <monthList ref="monthList" ></monthList>
        </i-tab-pane>
        <i-tab-pane label="日租订单" name="dayList">
            <dayList ref="dayList"  ></dayList>
        </i-tab-pane>
        
    </i-tabs>
        
    </div>
</template>
<script>

import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import '../index.scss';
import monthList from './monthList';
import dayList from './dayList';

import { mapState,mapMutations } from 'vuex';

export default {
    name: 'hpmsHouseFacility',
    props:{
        // id:String,
        data:Object,
    },
    watch: {
        activeName(val){
			if(val=='dayList'){
				this.$refs['dayList'].init(this.id);
			}else{
				this.$refs['monthList'].init(this.id);
			}
        }
    },
   
    data () {
        return {
			id:'',
            roomFacility:[],
            activeName:'monthList',
            showEditNos:false,
            ownerData:[],
        }
    },
    components:{
        'monthList':monthList,
        'dayList':dayList,
    },
    mounted(){
    },
    methods: {
		...mapMutations(['LOADING']),
		init(id){
			this.id=id;
			this.$refs['monthList'].init(id);
			this.activeName='monthList';
		}
       
    },
    created(){
       
    }
}

</script>


