<template>
    <i-drawer title="新增项目" v-model="show" width="60%" :before-close="handleClose" >
        <div id="hpms-house-data-form">
            <div class="step-box">
                <i-steps  :active="stepData.step"  space="345px"  align-center center finish-status="3" style="margin-top: 20px">
                    <i-step title="新建公寓" :status="getStepStatus(1)"></i-step>
                    <i-step title="创建房间模板" :status="getStepStatus(2)"></i-step>
                    <i-step title="生成房间信息" :status="getStepStatus(3)"></i-step>
                </i-steps> 
            </div>
            <div class="hpms-form-box">
                <premFrom ref="premForm" :cacheForm="formData" v-if="stepData.step==1" 
                @next="next" @gotoStep="gotoStep" @setApartmentId="setApartmentId">
                </premFrom>
                <roomTemplate ref="roomTemplate" @gotoStep="gotoStep"  v-else-if="stepData.step==2"></roomTemplate>
                <houseRoom ref="houseRoom" @gotoStep="gotoStep"  @closeForm="closeForm" v-else-if="stepData.step==3"></houseRoom>
            </div>
            <div class="button-box">
                <div class="button-left">
                    <i-button :ripple="true" v-if="stepData.step==2" @click="addTemplate()">新增模板</i-button>
                    <i-button :ripple="true" v-else-if="stepData.step==3" @click="selTemplate()">选择模板</i-button>
                </div>
                <div class="button-right">
                    <i-button :ripple="true" v-if="stepData.step==1" @click="closeForm()">取消</i-button>
                    <i-button :ripple="true"  @click.stop="last()" v-else>上一步</i-button>
                    <i-button type="primary" :ripple="true"  @click.stop="submit()" v-if="stepData.step<stepData.stepTotal">保存并下一步</i-button>
                    <i-button type="primary" :ripple="true"  @click.stop="submit()" v-else >提交项目</i-button>
                </div>
                
            </div>
        </div>
    </i-drawer>
</template>


<script>
import './form.scss'
import premFrom from './premFrom';
import roomTemplate from './roomTemplate.js';
import houseRoom from './house.js';
import hpmsService from 'service/hpms-house';
import { mapState } from 'vuex';
// import House from './house';
// import Rooms from './room';
export default {
    components: {
        'premFrom':premFrom,
        'roomTemplate':roomTemplate,
        'houseRoom':houseRoom,
        // 'house-box':House,
        // 'rooms-box':Rooms,
	},
	computed:{
        ...mapState(['dict'])
    },
    mounted(){
    },
    data(){
        return {
            
            stepData:{
                step:1,
                stepTotal:3,
                isSubmit:false
            },
            apartmentId:'',
            formData:{},
            cacheData:{},
            show:false,
        }
    },
    methods:{
        showForm(){
            this.show=true;
            this.$nextTick(() => {
                this.$refs['premForm'].init();
            })
            // this.$refs['premForm'].init();
        },
        closeForm(){
            this.show=false;
        },
        handleClose(){

        },
       
        gotoStep(step){
            this.stepData.step=step;
            if(step==1){
                this.$nextTick(() => {
                    this.$refs['premForm'].init(false);
                })
            }else if(step ==2 ){
                 this.$nextTick(() => {
                    this.$refs['roomTemplate'].init(false);
                })
            }else if(step ==3 ){
                 this.$nextTick(() => {
                    this.$refs['houseRoom'].init(false);
                })
            }
        },
        setApartmentId(apartmentId){
            console.log('setApartmentId',apartmentId)
            this.apartmentId=apartmentId;
        },
        getStepStatus(step){
            //wait / process / finish / error / success
            if(this.stepData.step==step){
                return 'process';
            }else if(this.stepData.step>step){
                return 'finish';
            }else{
                return 'wait';
            }
            
        },
        last(){
            if(this.stepData.step>1){
               this.stepData.step=this.stepData.step-1;
            }
            if(this.stepData.step==1){
                this.$nextTick(() => {
                    this.$refs['premForm'].init(false);
                })
            }else if(this.stepData.step ==2 ){
                 this.$nextTick(() => {
                    this.$refs['roomTemplate'].init(false);
                })
            }else if(this.stepData.step ==3 ){
                 this.$nextTick(() => {
                    this.$refs['houseRoom'].init(false);
                })
            }
        },
        
        next(){
            console.log('next')
            if(this.stepData.step+1<this.stepData.stepTotal){
                this.stepData.step=this.stepData.step+1;
            }else{
                console.log('success')
            }

        },

        submit(){
            if(this.stepData.step==1){
                this.$refs['premForm'].submit();
            }else if(this.stepData.step==2){
                this.$refs['roomTemplate'].submit(this.apartmentId);
            }else{
               this.$refs['houseRoom'].submit();
            }
        },
        addTemplate(){
            this.$refs['roomTemplate'].addTemplate();
        },
        selTemplate(){
            this.$refs['houseRoom'].showTemplateBox();
        }
        
    },
    
   

}
</script>
