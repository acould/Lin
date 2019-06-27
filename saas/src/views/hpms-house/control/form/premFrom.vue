<template>
    <div class="hpms-prem-box">
        <i-form :model="form1" ref="form1" :label-width="labelWidth"  :rules="formRules">
            <i-col :span="11">
                <i-form-item label="公寓名称" prop="apartmentId"  >
                    <i-select v-model="form1.apartmentId" filterable placeholder="请选择" @change="changeApartmentId()">
                        <i-option v-for="item in select.premOptions"  :label="item.apartmentName" :value="item.apartmentId" :key="item.apartmentId" ></i-option>
                    </i-select>
                </i-form-item>
            </i-col>

            <i-col :span="11">
                <i-form-item label="所在城市" prop="areaData"  class="is-required"  :rules="[{validator:_areaData, trigger:'change'}]">
                    <i-cascader :options="select.selectedOptions"  :props="{value:'id',label:'name',children:'children',disabled:'disabled'}" v-model="areaData" @change="handleChange"></i-cascader>
                </i-form-item>
            </i-col>


            <i-col :span="11">
                <i-form-item label="坐落地址" prop="address"  >
                    <i-input  v-model.trim='form1.address' ></i-input>
                </i-form-item>
            </i-col>

            <i-col :span="11">
                <i-form-item label="物业类型" prop="propertyType"  >
                    <i-select v-model="form1.propertyType" filterable>
                        <i-option v-for="option in dict['PropertyType']"  :value="option.dictKey" :label="option.dictValue" :key="option.dictKey" ></i-option>
                    </i-select>
                </i-form-item>
            </i-col>

            <i-col :span="11">
                <i-form-item label="总楼层" prop="floors"  class="is-required" :rules="[{validator:_floors,trigger:'change'}]">
                    <i-input v-model.trim='form1.floors'></i-input>
                </i-form-item>
            </i-col>

            <i-col :span="11">
                <i-form-item label="有无电梯" prop="elevator"  >
                    <i-radio-group v-model="form1.elevator"  >
                        <i-radio label="HAVE" >有</i-radio>
                        <i-radio label="NONE" >无</i-radio>
                    </i-radio-group>
                </i-form-item>
            </i-col>
            <i-col :span="22">
                    <i-form-item label="所在楼层"  class="is-required" >
                        <div class="room-input-box" v-for="(item,index) in form1.floorList" :key="index" >
                            <div class="one">
                                <div class="input-block"> 
                                    <i-input v-model.trim='item.floorName' class="room-input"></i-input>
                                    <span>层</span>
                                </div>
                                <div class="input-block"> 
                                    <i-input v-model.trim='item.rooms' class="room-input"></i-input>
                                    <span>间</span>
                                </div>
                            </div>
                            <div class="two">
                                <span>房号生成特殊规则</span>
                                <i-checkbox-group id="checkbox-two" v-model="item.hasRoomPrefix">
                                    <i-checkbox id="checkbox-span" label="Y">加前缀</i-checkbox>
                                </i-checkbox-group>
                                <i-input v-model.trim='item.roomPrefix' class="room-input"></i-input>
                                <i-checkbox-group id="checkbox-two"  v-model="item.removeFour">
                                    <i-checkbox id="checkbox-span" label="Y">房间号去掉"4"</i-checkbox>
                                </i-checkbox-group>
                                <i-checkbox-group id="checkbox-two"  v-model="item.fillZero">
                                    <i-checkbox id="checkbox-span" label="Y">个位数楼层按2位生成</i-checkbox>
                                </i-checkbox-group>
                            </div>
                            <div class="delRooms" @click="delRooms(index)">
                                <img src="/static/imgs/hmps-house/del.png">
                            </div>
                        
                        </div>
                    </i-form-item>
            </i-col>
            <i-col :span="11">
                <i-form-item  >
                    <i-button @click.stop="addRooms()" style="color:#20a0ff">  + 新增楼层</i-button>
                </i-form-item>
            </i-col>

            

        </i-form>

    </div>
</template>



<script>
import {
    mapState,mapMutations
} from 'vuex';

import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';

export default {
    props:{
        cacheForm:Object,
    },
    data(){
        return {
            areaData:[],
            form1:{
                address:'',
                apartmentId:'',
                propertyType:'',
                elevator:'',
                floorList:[
                    // ...{
                    //     hasRoomPrefix:[],
                    //     removeFour:[],
                    //     fillZero:[],
                    // }
                ],
            },//页面表单数据
            serverParams:{}, //给后台的数据
            isOne:true,//是否是第一次创建
            select:{
                premOptions:[],
                selectedOptions:[],
                propertyType:[],
            },
            selectValLab:{
                label:'name',
                value:'id'
            },
            
            labelWidth:'95px',
            formRules:{
                apartmentId:[
                    { required:true, message:'请选择公寓', trigger:'change' }
                ],
                address:[
                    { required:true, message:'请输入地址', trigger:'change' }
                ],
                elevator:[
                    { required:true, message:'请选择是否有电梯', trigger:'change' }
                ],
                propertyType:[
                    { required:true, message:'请选择物业类型', trigger:'change' }
                ],
           }
        }
    },
    watch: {
    },
    mounted(){
        this.getListByCompany()
        this.getSelectData();
        // this.init();
	},
	computed:{
        ...mapState(['dict'])
    },
     methods: {
        ...mapMutations(['LOADING']),
        _areaData(rule, value, callback){
            if(this.areaData.length!=3){
                callback(new Error('请选择城市信息'));
            }else if( typeof this.areaData[0] =='undefined' || this.areaData[0] ==null ||  typeof this.areaData[1] =='undefined' || this.areaData[1] ==null || typeof this.areaData[2] =='undefined' || this.areaData[2] ==null){
                callback(new Error('请选择完整城市信息'));
            }
            // callback(new Error('请输入室厅厨卫数量'));
            callback();
        },
        _floors(rule, value, callback){
            var ra=/^[0-9]{0,4}$/
            if(!value){
                callback(new Error('请选择输入总楼层'));
            }else if( value<0 || value>1000 || !ra.test(value)){
                callback(new Error('请选择输入0-1000的整数'));
            }
            // callback(new Error('请输入室厅厨卫数量'));
            callback();
        },
        init(bool=true){
            hpmsService.getApartmentInfos().then(res=>{
                console.log('pf init ',res.data )
                if (res && res.code === 0) {
                    this.cacheData=res.data;
                    this.formatData(res.data)
                    if( res.data!=null){
                        this.$emit('setApartmentId',res.data.apartmentId);
                    }
                    if(bool && res.data!=null){
                        this.$emit('gotoStep',res.data.step);
                    }
                    
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
         //转化为页面数据
        formatData(cd){
            if(typeof cd =='undefined' || cd ==null){
                return ;
            }
            this.isOne=false;
            //第一步数据
            this.form1.apartmentId=cd.apartmentId;
            this.form1.address=cd.address;
            this.form1.elevator=cd.elevator;
            this.form1.propertyType=cd.propertyType;
            this.form1.floors=cd.floors;
            this.areaData=[cd.cityId,cd.regionId,cd.areaId];
            
            this.form1.floorList=[];
            for(let i=0;i<cd.floorList.length;i++){
                let ld=cd.floorList[i];
                let rd={
                    floorName:ld.floorName,
                    rooms:ld.rooms,
                    roomPrefix:ld.roomPrefix,
                    hasRoomPrefix:[],
                    removeFour:[],
                    fillZero:[],
                }
                if(ld.hasRoomPrefix=='Y'){
                    rd.hasRoomPrefix=['Y'];
                }
                if(ld.removeFour=='Y'){
                    rd.removeFour=['Y'];
                }
                if(ld.fillZero=='Y'){
                    rd.fillZero=['Y'];
                }

                this.form1.floorList.push(rd);
            }

        },
        getSelectOption(val){

        },
       
        handleChange(val){
            console.log(val);
        },
        changeApartmentId(){
            //console.log('isone',this.isOne)
            if(!this.isOne){ return ;}else{ this.isOne=false;}
            let id=this.form1.apartmentId;
            let obj=this.select.premOptions.find(
                function (obj) { 
                    if (obj.apartmentId== id) { 
                        return obj; 
                    } 
                }
            );

            if(obj){
                this.form1.address=obj.address;
                this.form1.elevator=obj.elevator;

                this.areaData=[obj.cityId,obj.regionId,obj.areaId];
            }
            
        },
        getListByCompany(){
            this.init_prem=0;
            name = '';
            hpmsService.getListByCompany(name).then(res=>{
                if (res && res.code === 0) {
                    this.select.premOptions = res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        getSelectData(){
            dictsService.getCityAreaAll().then(res=>{
                if (res && res.code === 0) {
                    this.select.selectedOptions=res.data
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        // clickRule(val,index){
        //     if(val==this.form1.floorList[index].rule){
        //         this.form1.floorList[index].rule=''   
        //     }else{
        //         this.form1.floorList[index].rule=val
        //     }
        // },
        addRooms(){
            let room = {};
            let len=this.form1.floorList.length;
            if(len>0){
                let nextData=this.form1.floorList[len-1];
              room = {
                    floorName:nextData.floorName,
                    rooms:nextData.rooms,
                    roomPrefix:nextData.roomPrefix,
                    hasRoomPrefix:nextData.hasRoomPrefix,
                    removeFour:nextData.removeFour,
                    fillZero:nextData.fillZero
                };
            }else{
                room = {
                    floorName:'',
                    rooms:'',
                    roomPrefix:'',
                    hasRoomPrefix:[],
                    removeFour:[],
                    fillZero:[]
                };
            }

            this.form1.floorList.push(room);
        },
        delRooms(index){
            this.form1.floorList.splice(index, 1);
        },
        updata(){
            if(typeof this.form1.floorList =='undefined' || !this.form1.floorList || this.form1.floorList.length==0){
                this.$message.error('请完善所在楼层信息'); 
                return ;
            }
           
            //
            // var params=this.form1;
            var params=this.serverParams;
            params={
                apartmentId:this.form1.apartmentId,
                address:this.form1.address,
                elevator:this.form1.elevator,
                propertyType:this.form1.propertyType,
                floors:this.form1.floors,
                floorList:[]
            }
            params.cityId=this.areaData[0];
            params.regionId=this.areaData[1];
            params.areaId=this.areaData[2];
            //
            this.$emit('setApartmentId',this.form1.apartmentId);

            var ru={
                floorName:/^((-\d+)|(0+)|(\d+))$/, //正负整数包含0
                rooms:/^((0+)|(\d+))$/, //正负整数包含0
			};
			this.serverParams=JSON.parse(JSON.stringify(this.form1));
            for(var i=0;i<this.serverParams.floorList.length;i++){
                let item=this.serverParams.floorList[i];
                if( !ru.floorName.test(item.floorName) || item.floorName<-5 || item.floorName>100 ){
                    this.$message.error('楼层请输入-5到100之间的整数'); 
                    return ;
                }
                if( !ru.rooms.test(item.rooms) || item.rooms<0 || item.rooms>1000 ){
                    this.$message.error('楼层请输入0到1000之间的整数'); 
                    return ;
                }
                let floor={};
                floor.floorName=item.floorName;
                floor.rooms=item.rooms;

                if(item.hasRoomPrefix==''){
                    item.hasRoomPrefix='N';
                }else{
                    item.hasRoomPrefix='Y';
                }
                if(item.removeFour==''){
                    item.removeFour='N';
                }else{
                    item.removeFour='Y';
                }
                if(item.fillZero==''){
                    item.fillZero='N';
                }else{
                    item.fillZero='Y';
                }

                floor.roomPrefix=item.roomPrefix;
                floor.hasRoomPrefix=item.hasRoomPrefix;
                floor.removeFour=item.removeFour;
                floor.fillZero=item.fillZero;
                params.floorList.push(floor);
            }
            hpmsService.saveApartmentInfos(params).then(res=>{
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.$emit('gotoStep',2);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })

        },
        submit(){
            console.log('submit premfrom')
            this.$refs['form1'].validate(valid => {
                if (valid) {
                  this.updata();
                    //this.$emit('next');
                }
            });
        }
       
    },
}
</script>

