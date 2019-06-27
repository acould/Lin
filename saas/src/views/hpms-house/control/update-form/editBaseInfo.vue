<template>
    <i-dialog  size="large" v-model="show" title="编辑基础信息" append-to-body > 
        <div class="prem-box" >
            <i-form :model="form1" ref="form1" :label-width="labelWidth"  :rules="formRules">
                <i-col :span="12">
                    <i-form-item label="公寓名称：" prop="apartmentId"  >
                        <i-select v-model="form1.apartmentId" filterable placeholder="请选择" @change="changeApartmentId()">
                            <i-option v-for="item in select.premOptions"  :label="item.apartmentName" :value="item.apartmentId" :key="item.apartmentId" ></i-option>
                        </i-select>
                    </i-form-item>
                </i-col>

                <i-col :span="12">
                    <i-form-item label="所在城市：" prop="areaData"  class="is-required"  :rules="[{validator:_areaData, trigger:'change'}]">
                        <i-cascader :options="select.selectedOptions"  :props="{value:'id',label:'name',children:'children',disabled:'disabled'}" v-model="areaData" @change="handleChange"></i-cascader>
                    </i-form-item>
                </i-col>


                <i-col :span="12">
                    <i-form-item label="坐落地址：" prop="address"  >
                        <i-input  v-model.trim='form1.address' ></i-input>
                    </i-form-item>
                </i-col>

                <i-col :span="12">
                    <i-form-item label="物业类型：" prop="propertyType"  >
                        <i-select v-model="form1.propertyType" filterable>
							<i-option v-for="item in dict['PropertyType']"  :value="item.dictKey" :label="item.dictValue"  :key="item.dictKey"></i-option>
                        </i-select>
                    </i-form-item>
                </i-col>

                <i-col :span="12">
                    <i-form-item label="总楼层：" prop="floors"  class="is-required" :rules="[{validator:_floors,trigger:'change'}]">
                        <i-input v-model.trim='form1.floors'></i-input>
                    </i-form-item>
                </i-col>

                <i-col :span="12">
                    <i-form-item label="有无电梯：" prop="elevator"  >
                        <i-radio-group v-model="form1.elevator"  >
                            <i-radio label="HAVE" >有</i-radio>
                            <i-radio label="NONE" >无</i-radio>
                        </i-radio-group>
                    </i-form-item>
                </i-col>
                <i-col :span="24">
                    <div class="hpms-button-box">
                        <div class="right-button-box">
                            <i-button @click.stop="close()">取消</i-button>
                            <i-button @click.stop="submit()" type="primary"  class="submit-button">确认</i-button>
                        </div>
                    </div>
                </i-col>
            </i-form>

        </div>
    </i-dialog>
</template>

<script>
import {
    mapState,mapMutations
} from 'vuex';

import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';

export default {
    props:{
    },
    data(){
        return {
            show:false,
            areaData:[],
            form1:{
                apartmentId:'',
                propertyType:'',
                elevator:'',
            },//页面表单数据
            serverParams:{}, //给后台的数据
            select:{
                premOptions:[],
                selectedOptions:[],
                propertyType:[],
            },
            selectValLab:{
                label:'name',
                value:'id'
            },
            isOne:true,
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
    created(){
        this.getListByCompany()
        this.getSelectData();
	},
	computed:{
        ...mapState(['dict'])
    },
    mounted(){
       
        // this.init();
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
        open(apartmentInfoId){
            this.show=true;
            this.init(apartmentInfoId);
        },
        
        init(apartmentInfoId){
            hpmsService.getApartmentInfo(apartmentInfoId).then(res=>{
                if (res && res.code === 0) {
                    this.cacheData=res.data;
                    this.formatData(res.data)
                    
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
			console.log('formatData')
            this.isOne=true;
            //第一步数据
            this.form1.apartmentInfoId=cd.apartmentInfoId;
            this.form1.apartmentId=cd.apartmentId;
            this.form1.address=cd.address;
            this.form1.elevator=cd.elevator;
            this.form1.propertyType=cd.propertyType;
            this.form1.floors=cd.floors;
            this.areaData=[cd.cityId,cd.regionId,cd.areaId];
            
            

        },
        getSelectOption(val){

        },
       
        handleChange(val){
            console.log(val);
        },
        changeApartmentId(){
            console.log('isone',this.isOne)
            if(this.isOne){ this.isOne=false;return ; }
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
            hpmsService.getListByCompany().then(res=>{
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
       
       
        updata(){
            //
            // var params=this.form1;
            var params=this.serverParams;
            params={
                apartmentInfoId:this.form1.apartmentInfoId,
                apartmentId:this.form1.apartmentId,
                address:this.form1.address,
                elevator:this.form1.elevator,
                propertyType:this.form1.propertyType,
                floors:this.form1.floors,
            }
            params.cityId=this.areaData[0];
            params.regionId=this.areaData[1];
            params.areaId=this.areaData[2];
            //
           
            hpmsService.updateApartmentInfos(params).then(res=>{
                if (res && res.code === 0) {
					this.$message.success('保存成功');
					this.show=false;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })

        },
        submit(){
            this.$refs['form1'].validate(valid => {
                if (valid) {
                  this.updata();
                    //this.$emit('next');
                }
            });
        },
        close(){
            this.show=false;
        }
       
    },
}
</script>

