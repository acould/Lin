<template>
    <div class="edit-room-data">
        <!-- <i-form-item label="模板名称" > -->
         <i-form :model="formData"  label-width="85px">
            <i-col :span="12">
                <i-form-item label="房间名称:"  :rules="[{required:true,message:'请输入房间名称'},{pattern:new RegExp(/^[a-zA-Z0-9\u4e00-\u9fa5]{1,9}$/),message:'请输入正确格式'}]">
                    <i-input v-model="formData.roomName"></i-input>
                </i-form-item>
            </i-col>

            <i-col :span="12">
                <i-form-item label="面积:" >
                    <i-input v-model="formData.area"></i-input>
                </i-form-item>
            </i-col>
            
            <i-col :span="24">
                <i-form-item label="户型 :" style="display: inline-block" >
                    <i-input v-model="formData.rooms" style="display: inline-block;width: 40px;" ></i-input>
                    <span>室</span>
                    <i-input v-model="formData.halls" style="display: inline-block;width: 40px;"></i-input>
                    <span>厅</span>
                    <i-input v-model="formData.kitchens" style="display: inline-block;width: 40px;"></i-input>
                    <span>厨</span>
                    <i-input v-model="formData.toilets" style="display: inline-block;width: 40px;"></i-input>
                    <span>卫</span>
                </i-form-item>
            </i-col>

        
            <i-col :span="12">
                <i-form-item label="朝向" >
                    <i-select v-model="formData.orientation">
						<i-option v-for="item in dict['Orientation']" :value="item.dictKey" :label="item.dictValue"  :key="item.dictKey"></i-option>
                    </i-select>
                </i-form-item>
            </i-col>

            <i-col :span="12">
                <i-form-item label="装修类型"  >
                    <i-select v-model="formData.decoration">
						<i-option v-for="item in dict['DecorationType']"  :value="item.dictKey" :label="item.dictValue"  :key="item.dictKey"></i-option>
                    </i-select>
                </i-form-item>
            </i-col>

           <i-col :span="24" class="rent-type">
                <i-form-item label="出租方式：">
                    <i-checkbox-group  v-model="formData.rentType">
						<i-checkbox v-for="item in dict['RentType']" :label="item.dictKey" :key="item.dictKey">{{item.dictValue}}</i-checkbox>
                    </i-checkbox-group>
                </i-form-item>
            </i-col>

            <i-col :span="12">
                <i-form-item label="长租定价：" >
                    <i-input v-model="formData.monthPrice">
                        <template slot="append">元/月</template>
                    </i-input>
                </i-form-item>
            </i-col>

            <i-col :span="12">
                <i-form-item label="日租定价：">
                    <i-input v-model="formData.dayPrice">
                        <template slot="append">元/晚</template>
                    </i-input>
                </i-form-item>
            </i-col>

            
            <i-col :span="24" style="margin-top: 20px;">
                <div class="imgs-box ">
                    <div class="head is-required-img">
                        <p>室内图片：</p>
                    </div>
                    <i-img-list  v-model="formData.showImg"  :props="{url:'src'}" :perfix="imgPerfix" :crop="crop" ></i-img-list>
                    <div class="template-img" @click="openUpImgBox()">
                        <i class="el-icon-plus"></i>
                    </div>
                </div>
            </i-col>

            

         </i-form>
        <div class="hpms-button-box">
            <div class="right-button-box" style="padding-bottom: 20px;">
                <i-button @click.stop="unset()">取消</i-button>
                <i-button @click.stop="submit()" type="primary">提交</i-button>
            </div>
        </div>
        <i-dialog v-model="isShowImgBox"  size="large" title="上传图片" append-to-body >
            <imgBox ref="showImgList" 
                @closeUpImgBox="closeUpImgBox"
                @imgSubmit="saveUpImgBox"
            >
            </imgBox>
        </i-dialog>
    </div>
</template>

<style lang="scss" scoped>
.edit-room-data{
	.template-img{
		width: 120px;
		height: 120px;
		line-height: 120px;
		text-align: center;
		border: 1px dashed #c0ccda;
		border-radius: 4px;
		float: left;
		margin-bottom: 30px;
	}
}

</style>

<script>
import dictsService from 'service/common';
import config from 'config';
import imgBox from '../imgbox';
import {
    mapState,mapMutations
} from 'vuex';

export default {
    components:{
        'imgBox':imgBox,
	},
	computed:{
        ...mapState(['dict'])
    },
    data(){
        return {
            labelWidth:'95px',
            dataIndex:{
                index:0,
                key:0
            },
            formData:{

                orientation:[],
                decoration:[],
                rentType:[],
                showImg:[],

            },
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            isShowImgBox:false,
        }
    },
    mounted(){
    },
    methods: {
        ...mapMutations(['LOADING']),
        init(data,index,key){
            console.log('erd,init');
            this.dataIndex.index=index;
            this.dataIndex.key=key;
            this.formData=data;
           
            // this.formData=data;
            this.$set(this.formData,'rentType',[])
            this.$set(this.formData,'showImg',[])
            
            let rentType=[]
            if(this.formData.monthRent=='Y'){
                rentType.push('MONTH_RENT')
            }
            if(this.formData.dayRent=='Y'){
                rentType.push('DAY_RENT')
            }
            this.formData.rentType=rentType;
            
            let showImg=[];
            for( let i in this.formData.attachmentList){
                if(this.formData.attachmentList[i].attachmenType=='BEDROOM'){
                    if(this.formData.attachmentList[i].attachmentList.length>0){
                        showImg.push(this.formData.attachmentList[i].attachmentList[0])
                    }
                }
            }
            this.formData.showImg=showImg;

        },
        openUpImgBox(){
            console.log('tt',this.formData.attachmentList);
            this.isShowImgBox=true;
            this.$nextTick(() => {
                this.$refs['showImgList'].init(this.formData.attachmentList,0)
            })
        },
        closeUpImgBox(){
            this.isShowImgBox=false;
        },
        saveUpImgBox(data){
            this.formData.attachmentList=data;
            console.log('saveUpImgBox',data)
            this.isShowImgBox=false;
            let showImg=[];

            for( let i in this.formData.attachmentList){
                if(this.formData.attachmentList[i].attachmenType=='BEDROOM'){
                    if(this.formData.attachmentList[i].attachmentList.length>0){
                        showImg.push(this.formData.attachmentList[i].attachmentList[0])
                    }
                }
            }
            this.formData.showImg=showImg;
        },
        unset(){
            this.$emit('closeDialog');
        },
        submit(){
            let data=this.formData;
            data.rentType.forEach(val=>{
                if(val=='MONTH_RENT'){
                    data.monthRent='Y'
                }
                if(val=='DAY_RENT'){
                    data.dayRent='Y'
                }
            });

            // var ru={
            //     floorName:/^((-\d+)|(0+)|(\d+))$/, //正负整数包含0
            // };
            // let formData=this.formData;
            // if( !ru.floorName.test(formData.floorName) || formData.floorName<-5 || formData.floorName>100 ){
            //     this.$message.error('楼层请输入-5到100之间的整数'); 
            //     return ;
            // }
            this.$emit('submit',data,this.dataIndex.index,this.dataIndex.key);
        }
       
    },
    created(){
       
    }
}
</script>

