import tpl from './roomTemplate.html';
import './room-template.scss';
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import imgBox from './imgbox';

import {
    mapState,mapMutations
} from 'vuex';

export default {
    name:"roomTemplate",
    template: tpl,
    components:{
        'imgBox':imgBox,
    },
    props:{
        // apartmentId:String,
	},
	computed:{
        ...mapState(['dict'])
    },
    data(){
        return {
            templateList:[
                {
                    name:'',
                    roomFacility:[],
                    rentType:[],
                    attachmentList:[],
                    showImg:[],
                }
            ],
            cacheData:[],
            select:{
                decoration:[],
                orientation:[],
                roomFacility:[],
            },
            labelWidth:'82px',
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            isShowImgBox:false,
        }
    },
    methods: {
        ...mapMutations(['LOADING']),
        init(bool=true){
            hpmsService.getApartmentInfos().then(res=>{
                if (res && res.code === 0) {
                    this.cacheData=res.data.tempList;
                    this.formatData(res.data.tempList)
                    this.$emit('setApartmentId',res.data.apartmentId);
                    if(bool){
                        this.$emit('gotoStep',3)
                    }
                    
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        formatData(cd){
            console.log('room formatdata')
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
        addTemplate(){
            if(this.templateList.length>10){
                this.$message.error('模板不能超过10个')
                return;
            }
            this.templateList.push({
                name:'',
                roomFacility:[],
                rentType:[],
                attachmentList:[],
                showImg:[],
            });
        },
        delTemplate(index){
            this.templateList.splice(index, 1);
        },
        getRef(index){
            return 'form'+index;
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
        submit(id){
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
            let overParams={"apartmentId":id,tempList:params};

            hpmsService.saveRoomTemp(overParams).then(res=>{
                if (res && res.code === 0) {
                    this.$emit('gotoStep',3)
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })

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
        }
    },
    created(){
       
    }
}

