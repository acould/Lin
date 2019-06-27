
import tpl from './index.html';
import hpmsService from 'service/hpms-house';
import searchBox from './search-box';
import houseInfo from './control/showInfo';
import createForm from './control/form';
import updateForm from './control/update-form';

import {
    mapMutations
} from 'vuex';

import './index.scss'
export default {
    name:'hpmsHouse',
    template: tpl,
    data(){
        return {
            comHouseList:[], //左侧房源列表
            houseList:[], //右侧房源信息
            comHouseInfo:{}, //当前选中房源信息
            apartmentInfoId:'', //当前选中房源信息
            apartmentInfoIndex:0, //
            houseFromData:{}, //筛选条件
            searchWhere:[],
            is_left_list:true, //左侧列表显示
            is_show_search:false,
            is_create_form:false,
            search:{
                queryName:''
            },
            houseSearch:{},
            show_search:false,
            show_house_data:{}, //详情数据
            init_prem:0,
            activeCardClass:[...true],//展开样式 
            activeCardt:false,//奇怪的bug,一定要数组中两个值且判断都会变量才能在:class中使用
            maxCradNum:12,//大于12个显示 展开
            showMoreCard:[...false],
        }
    },
    components:{
        'search-box':searchBox,
        'house-info-box':houseInfo,
        'createForm':createForm,
        'updateForm':updateForm,
    },
    mounted(){
        this.getComHouseList();
        // this.$refs.searchName=(e)=>{
        //     if (e.keyCode === 13 ){
        //         console.log('tttt')
        //     }
        // }
        // window.vue=this;
        // this.bodyEvents();
    },
    methods: {
        ...mapMutations(['LOADING']),
        //获取当前公司所有公寓列表
        getComHouseList(){
            this.init_prem=0;
            let name=this.search.queryName;
            name = (typeof name !== "undefined") ? name : '';
            this.LOADING(true);
            hpmsService.getComHouseList(name).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.comHouseList = res.data;
                    if(res.data[0] && typeof res.data[0].apartmentInfoId !== "undefined"){
                        this.showHouseInfo(this.comHouseList[0].apartmentInfoId,0);
                    }
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
       
        //指定公寓房源列表-右侧数据
        showHouseInfo(id,index){
            this.apartmentInfoId=id;
            this.apartmentInfoIndex=index;
            this.getHosetData();
            
        },
        getHosetData(){
            let params={};
            if(this.houseFormData){
                params={
                    apartmentInfoId:this.apartmentInfoId,
                    roomQueryType:this.houseFormData.roomQueryType,
                    queryName:this.houseFormData.queryName,
                    orientation:this.houseFormData.orientation,
                    hasImage:this.houseFormData.hasImage,
                    rentType:this.houseFormData.rentType,
                    vacantTime:this.houseFormData.vacantTime,
                    endTime:this.houseFormData.endTime,
                    areaStart:this.houseFormData.areaStart,
                    areaEnd:this.houseFormData.areaEnd,
                    queryLabel:this.houseFormData.queryLabel,
                }
            }else{
                params={
                    apartmentInfoId:this.apartmentInfoId,
                }
            }
            this.LOADING(true);
            hpmsService.getHouseList(params).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.comHouseInfo=this.comHouseList[this.apartmentInfoIndex];
                    this.houseList = res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        
        searchForm(searchForm,searchWhere){
            this.houseFormData=searchForm;
            this.searchWhere=searchWhere;
            this.is_show_search=false;
            this.getHosetData();
        },
        delWhere(index){
            let selData=this.searchWhere[index];
            if(typeof selData !=='undefined'){
                if( selData.type=='areaStart' || selData.type=='areaEnd'){
                    this.houseFormData[selData.type]='';
                }else if(selData.type=='roomQueryType'){
                    this.houseFormData['queryName']='';
                    this.houseFormData['roomQueryType']=null;
                }else if(selData.type=='queryLabel'){
                    let deli=this.houseFormData[selData.type].indexOf(selData.obj.value);
                    if(deli>-1){
                        this.houseFormData[selData.type].splice(deli,1)
                    }
                }else{
                    this.houseFormData[selData.type]=null;
                }
            }
            
            this.getHosetData();
            this.searchWhere.splice(index,1);
            //this.getHosetData();
        },
       
        //获取空置或到期日
        getRoomInfoText(v){
            let text='';
			if(v.rentStatus=='RENT' && v.monthRent=='Y'){ //已出租
				let now=new Date();
				let endDate=new Date(v.rentEndTime);

				let day=Math.floor((endDate.getTime()-now.getTime())/86400000)+1;
				if (day<15){
					text='<span style="color:#FF0030">'+day+'</span>天后到期';
				}else if (day<=30){
					text='<span style="color:#ffb54d">'+day+'</span>天后到期';
				}else if(day<=60){
					text='<span style="color:#fff">'+day+'</span>天后到期';
				}
			}else if(v.rentStatus=='RENT' && v.dayRent=='Y'){
				text='<span style="color:#fff">'+v.rentStartTime+'~'+v.rentEndTime+'</span>';
            }else{
                text='空置<span>'+v.vacantDays+'</span>天'
            }
            return text;
        },
        getRoomTagText(v){
            let text='';
            if(v.monthRent=='Y' && v.dayRent=='Y'){
                text='<div class="tag">长</div><div class="tag">日</div>'
            }else if(v.monthRent=='Y'){
                text='<div class="tag">长租</div>'
            }else if(v.dayRent=='Y'){
                text='<div class="tag">日租</div>'
            }
            return text

        },
        getRoomPriceText(v){
            let text='';
            if(v.monthRent=='Y' && v.dayRent=='Y'){
                text=this.toFloat(v.monthPrice)+'/月-'+this.toFloat(v.dayPrice)+'/晚';
            }else if(v.monthRent=='Y'){
                text=this.toFloat(v.monthPrice)+'/月'
            }else if(v.dayRent=='Y'){
                text=this.toFloat(v.dayPrice)+'/晚'
            }

            return text;
        },
        showRoomsAll(e){
            console.log(e);
		},
		getUserPone(roomId,v){
			this.LOADING(true);
            hpmsService.getRoomsPhone(roomId).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    if(typeof res.data !== "undefined"){
						// v.customerPhone=res.data;
						v.customerPhoneOutPass=res.data;
						// v.customerPhoneShowTime=3000;
						this.$set(v,'customerPhoneShowTime',3000)
						var interv=setInterval(()=>{
							this.$set(v,'customerPhoneShowTime',0)
							clearInterval(interv)
						},3000)
						// this.$message({
						// 	showClose:true,                    
						// 	message:res.data
						// })
                    }
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
		},
        
        toSearchBox(){
            this.is_show_search=!this.is_show_search;
        },
        //
        retText(t,val){
            if(t==1){
                if(val.rentStatus=='RENT'){
                    return val.customerName;
                }else{
                    return this.retRoomsStr(val)
                }
            }else if(t==2){
                if(val.rentStatus=='RENT'){
					
                    return val.customerPhone;
                }else{
                    return this.getRoomPriceText(val)
                }
            }
        },
        //户型
        retRoomsStr(item){
			let str='';
			str=this.toInt(item.rooms)+'-'+this.toInt(item.halls)+'-'+this.toInt(item.kitchens);
			if(typeof item.orientationCn !='undefined' && item.orientationCn!=null && item.orientationCn!='' ){
				str+='-'+item.orientationCn
			}
			str+='-'+this.toInt(item.area)+'㎡'
			
			return str;
        },
        //

        toInt(v){
            if(typeof v=='undefined' || v==null){
                return 0;
            }else{
                return parseInt(v);
            }
        },
        toFloat(v){
            if(typeof v=='undefined' || v==null){
                return 0;
            }else{
                return parseFloat(v);
            }
        },
        toLeftfList(){
            this.is_left_list=!this.is_left_list;
        },
        // handleClose(){
        //     console.log('close');
        // },

        showRoomsInfo(id){
            this.$refs['houseInfoBox'].showRoomsInfo(id);
        },
        createForm(){
            this.$refs['createForm'].showForm();
        },
        hideHouseForm(){
            console.log(312);
            // this.is_create_form=false;
        },
        updateForm(){
            this.$refs['updateForm'].open(this.apartmentInfoId);
        },
        //获取展开样式
        
        clickMoreCard(index){
            if(this.activeCardClass[index]){
                this.activeCardClass[index]=false;
                this.showMoreCard[index]=false;
                this.activeCardt=false;
            }else{
                this.activeCardClass[index]=true;
                this.showMoreCard[index]=true;
                this.activeCardt=true;
            }
        },
        
        

        
        // 回车刷新
        // bodyEvents(){
        //     document.body.addEventListener('keydown', (e) => {
        //         if (e.keyCode === 13) {
        //             this.getComHouseList();
        //         }
        //     });
        // }

    }
}


