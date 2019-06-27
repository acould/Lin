<template>
    <i-dialog  size="large" v-model="show" title="编辑基础信息" append-to-body > 
        <div id="edit-template">
            <div class="room-template" v-for="(item,index)  in templateList" :key=index>
                <i-form  :ref="getRef(index)" :label-width="labelWidth">
                    <div class="template-head">
                        <h4>模板{{index+1}}</h4>
                        <span>
                            <a href="javascript:;" @click="delTemplate(index,item.tempId)">删除</a>
                        </span>
                    </div>
                    <div  class="template-body">
                        <i-row>
                            <i-col :span="8">
                                <i-form-item label="模板名称：" class="is-required" >
                                    <i-input v-model="item.name"></i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="户型：" class="is-required">
                                    <i-input v-model="item.rooms" style="display: inline-block;width: 40px;" placeholder="室"></i-input>
                                    <i-input v-model="item.halls" style="display: inline-block;width: 40px;" placeholder="厅"></i-input>
                                    <i-input v-model="item.kitchens" style="display: inline-block;width: 40px;" placeholder="厨"></i-input>
                                    <i-input v-model="item.toilets" style="display: inline-block;width: 40px;" placeholder="卫"></i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="面积：" class="is-required">
                                    <i-input v-model="item.area">
                                            <template slot="append">m²</template>
                                    </i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="朝向：" class="is-required">
                                        <i-select v-model="item.orientation">
											<i-option v-for="item in dict['Orientation']" :value="item.dictKey" :label="item.dictValue"  :key="item.dictKey"></i-option>
                                        </i-select>
                                </i-form-item>
                            </i-col>

                            <i-col :span="8">
                                <i-form-item label="装修情况：" class="is-required">
                                    <i-select v-model="item.decoration">
										<i-option v-for="item in dict['DecorationType']"  :value="item.dictKey" :label="item.dictValue"  :key="item.dictKey"></i-option>
                                    </i-select>
                                </i-form-item>
                            </i-col>

                            <i-col :span="8" class="rent-type">
                                <i-form-item label="出租方式：" class="is-required">
                                    <i-checkbox-group  v-model="item.rentType">
                                        <i-checkbox v-for="item in dict['RentType']" :label="item.dictKey" :key="item.dictKey">{{item.dictValue}}</i-checkbox>
                                    </i-checkbox-group>
                                </i-form-item>
                            </i-col>

                            <i-col :span="8">
                                <i-form-item label="长租定价：" class="is-required">
                                    <i-input v-model="item.monthPrice">
                                            <template slot="append">元/月</template>
                                    </i-input>
                                </i-form-item>
                            </i-col>

                            <i-col :span="8">
                                <i-form-item label="日租定价：">
                                    <i-input v-model="item.dayPrice">
                                            <template slot="append">元/晚</template>
                                    </i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="24">
                                <div class="room-facility" style="display: block;">
                                        <div class="head">
                                            <p>房源配套：</p>
                                        </div>
                                
                                        <div class="info-box">
                                            <div class="room-facility">
                                                <i-checkbox-group  v-model="item.roomFacility">
													<i-checkbox v-for="item in dict['RoomFacility']" :key="item.dictKey" :label="item.dictKey" >{{item.dictValue}}</i-checkbox>
                                                </i-checkbox-group>
                                            </div>
                                        </div>
                                </div>
                            </i-col>

                            <i-col :span="24" style="margin-top: 20px;">
                                <div class="imgs-box ">
                                    <div class="head is-required-img">
                                        <p>室内图片：</p>
                                    </div>
                                    <i-img-list v-model="item.showImg"  :props="{url:'src'}" :perfix="imgPerfix" :crop="crop" ></i-img-list>
                                    <div class="template-img" @click="openUpImgBox(index)">
                                        <i class="el-icon-plus"></i>
                                    </div>
                                </div>
                            </i-col>
                        </i-row>
                    </div>
                </i-form>

                
            </div>

            <i-dialog v-model="isShowImgBox"  size="large" title="上传图片" append-to-body >
                    <imgBox ref="showImgList" 
                        @closeUpImgBox="closeUpImgBox"
                        @imgSubmit="imgSubmit"
                    >
                    </imgBox>
            </i-dialog>

            <i-col :span="24">
                <div class="button-box" style="height: 50px;margin-top:6px;">
                    <div style="float: left;margin-left:20px; ">
                        <i-button @click.stop="addTemplate()">+新增模板</i-button>
                    </div>
                    <div style="float: right;margin-left:20px; ">
                        <i-button @click.stop="close()">取消</i-button>
                        <i-button @click.stop="submit()" type="primary">保存</i-button>
                    </div>
                </div>
            </i-col>
            
        </div>
    </i-dialog>
</template>
<style lang="scss" scoped>
#edit-template{
    .template-head{
        margin-top:24px;
        margin-bottom: 12px;
        h4{
            display: inline-block;
            margin: 0 12px 0 30px;
            font-size: 18px;
        }
        a{
            color: #329DFF;
            &:hover{
                color: #329DFF;
            }
        }
    
    }
    .template-body{
        border: solid 1px #ccc;
        padding: 26px 12px;
        margin: 8px;
        border-radius: 4px;
        .rent-type{
            display: inline-block;
            .el-form-item{
                display: inline-table;
            }
        }
        
        .room-facility,.imgs-box{
            .el-checkbox{
                margin-left: 30px;
            }
            .head{
                display: inline-block;
                float: left;
                p{
                    color: #5e6d82;
                    font-size: 12px;
                    margin-left: 20px;
                    margin-right: 6px;
                }
            
            }
            .info-box{
                display: inline-block;
                width: 90%;
                margin-left: -20px;
            }
        }
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
        .is-required-img{
            p{
                &:before{
                    content: "*";
                    color: #ff4949;
                    margin-right: 4px;
                }
            }
            
        }
        
    }
    
}
</style>
<script>
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import imgBox from './imgbox';
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
            apartmentInfoId:'',
            show:false,
            templateList:[
                {
                    name:'',
                    roomFacility:[],
                    rentType:[],
                    attachmentList:[],
                    orientation:[],
                    decoration:[],
                    showImg:[],
                }
            ],
            cacheData:[],
            select:{
                decoration:[],
                orientation:[],
                roomFacility:[],
            },
            labelWidth:'95px',
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            isShowImgBox:false,
        }
    },
    methods: {
        ...mapMutations(['LOADING']),
        open(apartmentInfoId){
            this.apartmentInfoId=apartmentInfoId;
            this.show=true;
            hpmsService.getRoomTempsList(apartmentInfoId).then(res=>{
                if (res && res.code === 0) {
                    this.cacheData=res.data;
                    this.formatData(res.data)
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        formatData(cd){
            console.log(cd);
            if(typeof cd =='undefined' || cd ==null){
                return ;
            }
            let templateList=[];
            for(let i=0; i<cd.length;i++){
                let list={
                    tempId:cd[i].tempId,
                    name:cd[i].tempName,
                    rooms:cd[i].rooms,
                    halls:cd[i].halls,
                    kitchens:cd[i].kitchens,
                    toilets:cd[i].toilets,
                    area:cd[i].area,
                    orientation:cd[i].orientation,
                    decoration:cd[i].decoration,
                    rentType:[],
                    monthPrice:cd[i].monthPrice,
                    dayPrice:cd[i].dayPrice,
                    roomFacility:cd[i].facilityList,
                    attachmentList:cd[i].attachmentList,
                    showImg:[]
                }
                if(cd[i].monthRent=='Y'){
                    list.rentType.push('MONTH_RENT')
                }
                if(cd[i].dayRent=='Y'){
                    list.rentType.push('DAY_RENT')
                }
                
                for( let key in list.attachmentList){
                    if(list.attachmentList[key].attachmenType=='BEDROOM'){
                        if(list.attachmentList[key].attachmentList.length>0){
                            list.showImg.push(list.attachmentList[key].attachmentList[0])
                        }
                    }
                }

                templateList.push(list);
            }
            this.templateList=templateList;
        },
        getRef(index){
            return 'form'+index;
        },
        addTemplate(){
            if(this.templateList.length>10){
                this.$message.error('模板不能超过10个')
                return;
            }
            this.templateList.push({
                name:'',
                roomFacility:[],
                rentType:[],
                orientation:[],
                decoration:[],
                attachmentList:[],
                showImg:[],
            });
        },
        delTemplate(index,tempId){
			if(typeof tempId=='undefined'){
				this.templateList.splice(index, 1);return;
			}
            hpmsService.delRoomTemp(tempId).then(res=>{
                if (res && res.code === 0) {
                    this.templateList.splice(index, 1);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
            
            
        },
        openUpImgBox(index){
            this.isShowImgBox=true;
            console.log('index',index);
            console.log('this.templateList[index]',this.templateList[index]);
            this.$nextTick(() => {
                this.$refs['showImgList'].init(this.templateList[index].attachmentList,index)
            })
        },
        closeUpImgBox(){
            console.log('closeUpImgBox')
            this.isShowImgBox=false;
        },
        verification(){
            let len=this.templateList.length;
            var params=[];
            for(var i=0;i<len;i++){
                var item=this.templateList[i];
                let re;//正则变量
                if(item.name.length==0){
                    this.$message.error('请输入模板名称')
                    return;
                }
                re=/^\S{1,20}$/;
                if(!re.test(item.name)){
                    this.$message.error('请输入不超过20个汉字长度的任务字符')
                    return;
                }
              
                

                if(typeof item.rooms=='undefined' || item.rooms.length==0){
                    this.$message.error('请输入户型 室')
                    return;
                }
                if(typeof item.halls=='undefined' || item.halls.length==0){
                    this.$message.error('请输入户型 厅')
                    return;
                }
                if(typeof item.kitchens=='undefined' || item.kitchens.length==0){
                    this.$message.error('请输入户型 厨')
                    return;
                }
                if(typeof item.toilets=='undefined' || item.toilets.length==0){
                    this.$message.error('请输入户型 卫')
                    return;
                }
                re=/^\d{1,2}$/;
                if(
                    !re.test(item.rooms) || item.rooms>10 
                    || !re.test(item.halls) || item.halls>10
                    || !re.test(item.kitchens) || item.kitchens>10
                    || !re.test(item.toilets) || item.toilets>10
                ){
                    this.$message.error('请输入0-10之间的整数')
                    return;
                }

                if(typeof item.area=='undefined' ||item.area.length==0){
                    this.$message.error('请输入面积')
                    return;
                }
                re=/^\d{0,8}(\.\d{0,2})?$/
                if(!re.test(item.area)){
                    this.$message.error('请输入8位以内整数,可保留2位小数的面积')
                    return;
                }

                if(typeof item.orientation=='undefined'){
                    this.$message.error('请选择朝向')
                    return;
                }

                if(typeof item.decoration=='undefined'){
                    this.$message.error('请选择装修情况')
                    return;
                }

                re=/^\d{0,6}(\.\d{0,2})?$/
                if(typeof item.monthPrice=='undefined' ||item.monthPrice.length==0){
                    this.$message.error('请输入长租定价')
                    return;
                }
                if(!re.test(item.monthPrice)){
                    this.$message.error('请输入6位以内整数,可保留2位小数的长租定价')
                    return;
                }
                //日租格式验证
                console.log(item.dayPrice);
                console.log(typeof item.dayPrice);
                if(typeof item.dayPrice !=='undefined' && item.monthPrice.length>0){
                    if(!re.test(item.dayPrice)){
                        this.$message.error('请输入6位以内整数,可保留2位小数的日租定价')
                        return;
                    }
                }
                
                //图片验证
                if(item.attachmentList.length==0){
                    this.$message.error('请上传室内图')
                    return;
                }
                
                
                

                var rentTypeV=false;


                item.rentType.forEach(val=>{
                    if(val=='MONTH_RENT'){
                        rentTypeV=true;
                    }
                    if(val=='DAY_RENT'){
                        rentTypeV=true;
                    }
                })
                if(!rentTypeV){
                    this.$message.error('请选择出租方式!')
                    return;
                }
                
                return true;
            }
        },
        imgSubmit(data,index){
            for(let i in data){
                if(data[i].attachmenType=='BEDROOM'){
                    if(this.templateList[index].showImg.length ==0){
                        this.templateList[index].showImg.push(data[i].attachmentList[0]);
                    }else{
                        this.templateList[index].showImg[0]=data[i].attachmentList[0];
                    }
                }
            }
            // this.showImg[0]=data.BEDROOM[0];
            this.templateList[index].attachmentList=data;
            this.closeUpImgBox();
        },
        submit(){
            if(!this.verification()){
                return ;
            }
            let len=this.templateList.length;
            var params=[];
            for(var i=0;i<len;i++){
                var item=this.templateList[i];
                var data={
                    tempName:item.name,
                    rooms:item.rooms,
                    kitchens:item.kitchens,
                    halls:item.halls,
                    toilets:item.toilets,
                    orientation:item.orientation,
                    area:item.area,
                    decoration:item.decoration,
                    facilityList:item.roomFacility,
                    monthPrice:item.monthPrice,
                    dayPrice:item.dayPrice,
                    monthRent:'N',
                    dayRent:'N',
                    attachmentList:item.attachmentList
                }
                
                if(typeof item.tempId !=='undefined'){
                    data.tempId=item.tempId;
                }
                item.rentType.forEach(val=>{
                    if(val=='MONTH_RENT'){
                        data.monthRent='Y'
                    }
                    if(val=='DAY_RENT'){
                        data.dayRent='Y'
                    }
                })
                params.push(data);
            }
            let overParams={"apartmentInfoId":this.apartmentInfoId,tempList:params};

            hpmsService.editRoomTemp(overParams).then(res=>{
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.close();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })

        },
        close(){
            this.show=false;
        },



    },
    created(){
       
    }
}
    
</script>

