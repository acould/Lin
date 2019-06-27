<template>
    <i-dialog v-model="show" size="small" title="选择房间模板" append-to-body >
        <div class="sel-tmplist-box">
            <i-form :model="formData" ref="passwordForm" label-width="80px">
                <i-form-item label="模板名称:" :rules="[{required:true, message:'请选择模板', trigger:'blur'}]">
                    <i-radio-group v-model="formData.index" size="small">
                        <i-radio v-for="item in tmpList" :key="item.tempId" :label="item.tempId" >{{item.tempName}}</i-radio>
                    </i-radio-group>
                </i-form-item>
            </i-form>
            <div class="hpms-button-box">
                <div class="right-button-box" style="float: right;margin-bottom: 20px;">
                    <i-button @click.stop="close()">取消</i-button>
                    <i-button @click.stop="submit()"  type="primary">提交</i-button>
                </div>
            </div>
        </div>
    </i-dialog>
</template>

<script>
import dictsService from 'service/common';
import hpmsService from 'service/hpms-house';
import {
    mapMutations
} from 'vuex';

export default {
    data(){
        return {
            show:false,
            apartmentInfoId:'',
            roomIds:[],
            labelWidth:'95px',
            formData:{
                index:'',
            },
            tmpList:[],
            
        }
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        open(apartmentInfoId,roomIds){
            this.show=true;
            this.apartmentInfoId=apartmentInfoId;
            this.roomIds=roomIds;
            this.getTmpList();
        },
        close(){
            this.show=false;
        },
        submit(){
            if(this.formData.index==''){
                this.$message.error('请选择模板');return;
            }
			let params={tempId:this.formData.index,roomIds:this.roomIds}
			this.LOADING(true);
            hpmsService.saveBatchRoom(params).then(res=>{
				this.LOADING(false);
                if (res && res.code === 0) {
                    this.tmpList=res.data;
                    this.$message.success('操作成功')
                    this.$emit('submit')
                    this.close();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
            // this.$emit('submit',this.tmpList[this.formData.index]);
           
        },
        getTmpList(){
            let id=this.apartmentInfoId;
            hpmsService.getRoomTempsList(id).then(res=>{
                if (res && res.code === 0) {
                    this.tmpList=res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
    }
}
</script>

