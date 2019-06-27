<template>
    <div class="facility">
        <div class="head">
            <p>房源配套</p>
            <p class="operation-box"></p>
        </div>

        <div class="info-box">
            <div class="room-facility">
                <i-form :v-model="data" ref="data"  >
                    <i-form-item   prop="roomFacility" >
                        <i-checkbox-group  v-model="data.facilityList">
                            <i-checkbox v-for="item in dict['RoomFacility']" :key="item.dictKey" :label="item.dictKey" style="margin-left: 10px;width: 88px;" @change="saveHouseFacility()">{{item.dictValue}}</i-checkbox>
                        </i-checkbox-group>
                    </i-form-item>
                </i-form>
            </div>
        </div>

        <div class="head"  style="border-bottom:none;padding-bottom: 0px;">
            <p>水电煤户号</p>
            <p class="operation-box">
                <a href="javascript:;" @click="editNos()">编辑</a>
            </p>
        </div>

        <div class="info-box" style="border-bottom:none;">
            <div class="info-li">
                <span>水表户号：</span><span>{{data.waterNo}}</span>
            </div>
            <div class="info-li">
                <span>电表户号：</span><span>{{data.electricNo}}</span>
            </div>
            <div class="info-li">
                <span>煤气户号：</span><span>{{data.gasNo}}</span>
            </div>
            
        </div>

        <i-dialog size="tiny" v-model="showEditNos" title="编辑水电煤气户号"  append-to-body >
            <editNos :id="id" ref="editNosFrom" @hideEditNos="hideEditNos" @saveEditNos="saveEditNos"></editNos>
        </i-dialog>
        
    </div>
</template>
<script>

import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import '../index.scss';
import editNos from './editNos.js';

import { mapState,mapMutations } from 'vuex';

export default {
    name: 'hpmsHouseFacility',
    // props:{
    //     id:String
    // },
	computed:{
        ...mapState(['dict'])
    },
    data () {
        return {
			id:'',
            data:{
                facilityList:[]
            },
            roomFacility:[],
            showEditNos:false,
        }
    },
    components:{
        'editNos':editNos,
    },
    mounted(){
        
    },
    watch: {
        // id(val){
        //     this.init()
        // },
        
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        _roomFacility(rule, value, callback){
            // if(this.form1.rentType.length==0){
            //     callback(new Error('请选择出租方式'));
            // }
            callback();
        },
        init(id){
			this.id=id;
            hpmsService.getHouseFacility(id).then(res=>{
                if (res && res.code === 0) {
                    this.data = res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        saveHouseFacility(){
            var params={
                'roomId':this.id,
                'facilityList':this.data.facilityList,
            }
            this.LOADING(true)
            hpmsService.saveHouseFacility(params).then(res=>{
                this.LOADING(false);
                
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }

            })
        },
        //变量定价
        editNos(){
            console.log(321312)
            this.showEditNos=true;
            // this.$nextTick(() => {
            //     this.$refs['editNosFrom'].init(this.data)
            // })
        },
        hideEditNos(){
            this.showEditNos=false;
        },
        saveEditNos(data){
            this.LOADING(true);
            hpmsService.saveEditNos(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.showEditNos=false;
                    this.init();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },

    },
    created(){
    }
}

</script>
<style lang="scss" scoped>
.room-facility{
    margin: 22px;
    .el-checkbox{
        margin-left: 10px;
    }
}
</style>

