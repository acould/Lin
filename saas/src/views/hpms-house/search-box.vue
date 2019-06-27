<template>
    <div class="search-box">
        <i-form  ref="searchForm" :label-width="labelWidth">
            <i-row :gutter="10" style="margin-bottom: 10px;">
                <i-col :span="4">
                    <i-select v-model="searchForm.roomQueryType" placeholder="关键词" >
                        <i-option v-for="item in inputSelectData.roomQueryType" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>

                <i-col :span="6">
                    <i-input v-model.trim="searchForm.queryName" placeholder="房源编号/房号/租客姓名/租客号码"  ></i-input>
                </i-col>

                <i-col :span="3">
                    <i-select v-model="searchForm.orientation" placeholder="朝向">
                        <i-option v-for="item in inputSelectData.orientation" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>

                <i-col :span="3">
                    <i-select v-model="searchForm.hasImage" placeholder="图片">
                        <i-option v-for="item in inputSelectData.hasImage" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>

                <i-col :span="4">
                    <i-input v-model.trim="searchForm.areaStart" placeholder="面积起"  ></i-input>
                </i-col>

                <i-col :span="4">
                    <i-input v-model.trim="searchForm.areaEnd" placeholder="面积止"  ></i-input>
                </i-col>
            </i-row>
            <i-row :gutter="10" style="margin-bottom: 10px;">
                <i-col :span="4">
                    <i-select v-model="searchForm.rentType" placeholder="出租方式">
                        <i-option v-for="item in inputSelectData.rentType" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>
                <i-col :span="4">
                    <i-select v-model="searchForm.vacantTime" placeholder="空置时长">
                        <i-option v-for="item in inputSelectData.vacantTime" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>
                <i-col :span="3">
                    <i-select v-model="searchForm.endTime" placeholder="将到期">
                        <i-option v-for="item in inputSelectData.endTime" :key="item.value" :label="item.label" :value="item.value" ></i-option>
                    </i-select>
                </i-col>
            </i-row>
            <i-row :gutter="10" style="margin-top: 20px;">
                 <i-col :span="24">
                    <i-checkbox-group v-model="searchForm.queryLabel">
                        <i-checkbox  v-for="item in inputSelectData.queryLabel" :key="item.value" :label="item.value">{{item.label}}</i-checkbox>
                    </i-checkbox-group>
                </i-col>
            </i-row>
            <i-row :gutter="10" style="margin-top: 20px;">
                <i-col :span="24">
                    <div class="hpms-button-box">
                        <div class="right-button-box" >
                            <i-button @click.stop="unset()">清空</i-button>
                            <i-button @click.stop="search()"  type="primary" class="submit-button">搜索</i-button>
                        </div>
                    </div>
                </i-col>
            </i-row>
        </i-form>
        
    </div>
</template>

<script>
import './search.scss'
import dictsService from 'service/common';
export default {
    
    data(){
        return {
            formDefault:[

            ], 
            labelWidth:'195px', 
            searchForm:{
                roomQueryType:null,
                queryName:'',
                orientation:null,
                hasImage:null,
                rentType:null,
                vacantTime:null,
                endTime:null,
                areaStart:'',
                areaEnd:'',
                queryLabel:[],
                
            },
            inputSelectData:{
                roomQueryType:[{
                    value:null,
                    label:'全部'
                }],
                orientation:[{
                    value:null,
                    label:'朝向'
                }],
                hasImage:[{
                    value:null,
                    label:'图片'
                }],
                rentType:[{
                    value:null,
                    label:'出租方式'
                }],
                vacantTime:[{
                    value:null,
                    label:'空置时长'
                }],
                endTime:[{
                    value:null,
                    label:'将到期'
                }],
                areaStart:'',
                areaEnd:'',
                queryLabel:[],
            }

        }
    },
    mounted(){
        // this.getComHouseList();
        // this.bodyEvents();
        this.init();
    },
    methods: {
        //初始化input数据
        init(){
            dictsService.getDictsAll().then(res=>{
                if (res && res.code === 0) {
                    this.setSelectData(res.data);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        unset(){
            this.searchForm={
                roomQueryType:null,
                queryName:'',
                orientation:null,
                hasImage:null,
                rentType:null,
                vacantTime:null,
                endTime:null,
                areaStart:'',
                areaEnd:'',
                queryLabel:[],
            };
        },
        search(){
            let where=[];
            let tmpObj;
            if(this.searchForm.queryName!=null &&  this.searchForm.queryName!=''){
                tmpObj=this.inputSelectData.roomQueryType.find((item)=>{return item.value==this.searchForm.roomQueryType})
                if(typeof tmpObj!==undefined && this.searchForm.roomQueryType!=null){
                    where.push(
                        {name:''+tmpObj.label+' - '+this.searchForm.queryName,type:'roomQueryType',obj:tmpObj}
                    )
                }else{
                    where.push(
                        {name:''+this.searchForm.queryName,type:'roomQueryType',obj:{}}
                    )
                }
            }
            if(this.searchForm.orientation!=null){
                tmpObj=this.inputSelectData.orientation.find((item)=>{return item.value==this.searchForm.orientation})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:''+tmpObj.label,type:'orientation',obj:tmpObj}
                    )
                }
            }
            if(this.searchForm.hasImage!=null){
                tmpObj=this.inputSelectData.hasImage.find((item)=>{return item.value==this.searchForm.hasImage})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:''+tmpObj.label,type:'hasImage',obj:tmpObj}
                    )
                }
            }

            if(this.searchForm.areaStart!='' && this.searchForm.areaStart!=null){
                where.push(
                    {name:'面积起:'+this.searchForm.areaStart+'㎡',type:'areaStart',obj:{}}
                )
            }

            if(this.searchForm.areaEnd!='' &&  this.searchForm.areaEnd!=null){
                where.push(
                    {name:'面积止:'+this.searchForm.areaEnd+'㎡',type:'areaEnd',obj:{}}
                )
            }

            if(this.searchForm.rentType!=null){
                tmpObj=this.inputSelectData.rentType.find((item)=>{return item.value==this.searchForm.rentType})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:''+tmpObj.label,type:'rentType',obj:tmpObj}
                    )
                }
            }
            if(this.searchForm.vacantTime!=null){
                tmpObj=this.inputSelectData.vacantTime.find((item)=>{return item.value==this.searchForm.vacantTime})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:''+tmpObj.label,type:'vacantTime',obj:tmpObj}
                    )
                }
            }

            if(this.searchForm.endTime!=null){
                tmpObj=this.inputSelectData.endTime.find((item)=>{return item.value==this.searchForm.endTime})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:''+tmpObj.label,type:'endTime',obj:tmpObj}
                    )
                }
            }

            for(let i in this.searchForm.queryLabel){
                tmpObj=this.inputSelectData.queryLabel.find((item)=>{return item.value==this.searchForm.queryLabel[i]})
                if(typeof tmpObj!=='undefined'){
                    where.push(
                        {name:tmpObj.label,type:'queryLabel',obj:tmpObj}
                    )
                }
            }



            tmpObj=this.inputSelectData.roomQueryType.find((item)=>{return item.dictKey==this.searchForm.roomQueryType})
            if(typeof tmpObj!=='undefined' && this.searchForm.roomQueryType!=null){
                where.push(
                    {name:tmpObj.label,type:'roomQueryType',obj:tmpObj}
                )
            }
            console.log('search',this.searchForm,where)
            this.$emit('searchForm', this.searchForm,where);
            //  this.$emit(this,'houseSearch',this.searchForm);
        },
        setSelectData(data){
            if(data.RoomQueryType){
                data.RoomQueryType.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.roomQueryType.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.Orientation.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.orientation.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.HasImage.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.hasImage.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.RentType.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.rentType.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.VacantTime.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.vacantTime.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.EndTime.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.endTime.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })
                data.RoomQueryLabel.forEach(items=>{
                    if(items.enabled){
                        this.inputSelectData.queryLabel.push({
                            value:items.dictKey,
                            label:items.dictValue
                        })
                    }
                })

                
                

            }
            
        }
    }
}
</script>

