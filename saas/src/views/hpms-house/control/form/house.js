import tpl from './house.html';
import './house.scss';

import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import selTmpList from './house-control/selTmpList';
import editFloorName from './house-control/editFloorName';
import editRoomData from './house-control/editRoomData';

import {
    mapMutations
} from 'vuex';

export default{
    name:"hpmsFormHouse",
    template: tpl,
    components:{
        'selTmpList':selTmpList,
        'editFloorName':editFloorName,
        'editRoomData':editRoomData,
    },
    data(){
        return {
            data:[],
            floorList:[], //接口需要的参数
            select:{
                'room':[],
                'templateList':[]
            },
            defaultRoom:{
                tempId:null,
                tempName:null,
                area:null,
                dayPrice:null,
                dayRent:null,

                facilityList:null,
                floorId:null,
                halls:null,
                kitchens:null,

                monthPrice:null,
                monthRent:null,
                rooms:null,
                toilets:null,
                attachmentList:null,

                rentType:[],
                orientation:null,
                decoration:null,
                showImg:[],
            },
            tmpNum:0,
            userSelRooms:{...[]},
            dialog:{
                'showTemplateBox':false,
                'showEditFloorName':false,
                'showEditRoomData':false,
            },
            
        }
    },
    watch: {
        // //监听全选状态
        // userSelRooms(val,old){
        //     console.log('userSelRooms',val,old);

        // }
       
    },
    // computed:{
    //     userSelData(){
            
    //     }
    // },
    methods: {
        ...mapMutations(['LOADING']),
        init(bool=true){
            console.log('hose');
            hpmsService.getApartmentInfos().then(res=>{
                if (res && res.code === 0) {
                    this.data=res.data;
                    this.formatData(res.data)
                    this.$emit('setApartmentId',res.data.apartmentId);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        formatData(data){
            let tempList=[]
            data.tempList.forEach((val,index)=>{
                tempList.push(
                    {
                        "value":index,
                        "label":val.tempName
                    }
                )
            })
            this.select.templateList=tempList;

        },
        setTemplateDataToRooms(templateIndex){
            this.dialog.showTemplateBox=false;
            if(typeof this.data.tempList =='undefined' && this.data.tempList.length==0){
                return ;
            }

            let data=this.data.tempList[templateIndex];
            for(let key in this.userSelRooms){
                for(let k in this.userSelRooms[key]){
                    // console.log('json data',data)
                    let json=JSON.stringify(data)
					data=JSON.parse(json);
					// let flrdata=this.data.floorList[key].roomList[k];
                    this.data.floorList[key].roomList[k].tempId=data.tempId;
                    this.data.floorList[key].roomList[k].tempName=data.tempName;

                    
                    this.data.floorList[key].roomList[k].area=data.area;
                    
                    this.data.floorList[key].roomList[k].dayPrice=data.dayPrice;
                    this.data.floorList[key].roomList[k].dayRent=data.dayRent;
                    this.data.floorList[key].roomList[k].decoration=data.decoration;
                    this.data.floorList[key].roomList[k].facilityList=data.facilityList;
                    this.data.floorList[key].roomList[k].floorId=data.floorId;
                    this.data.floorList[key].roomList[k].halls=data.halls;
                    this.data.floorList[key].roomList[k].kitchens=data.kitchens;
                    this.data.floorList[key].roomList[k].monthPrice=data.monthPrice;
                    this.data.floorList[key].roomList[k].monthRent=data.monthRent;
                    this.data.floorList[key].roomList[k].orientation=data.orientation;
                    this.data.floorList[key].roomList[k].rooms=data.rooms;
                   
                    this.data.floorList[key].roomList[k].toilets=data.toilets;
					this.data.floorList[key].roomList[k].attachmentList=data.attachmentList;

					
					this.data.floorList[key].roomList[k].rentType=[];
					if(data.monthRent=='Y'){
						this.data.floorList[key].roomList[k].rentType.push('MONTH_RENT')
					}
					if(data.dayRent=='Y'){
						this.data.floorList[key].roomList[k].rentType.push('DAY_RENT')
					}

                     // this.data.floorList[key].roomList[k].tempId=data.tempId;
                    // this.data.floorList[key].roomList[k].tempName=data.tempName;

                    
                }
            }
            //TODO 数据渲染
            // console.log('it',templateIndex)
        },
        showTemplateBox(){
            this.dialog.showTemplateBox=true;
            this.$nextTick(() => {
                this.$refs['selTmpList'].init(this.select.templateList)
            });
        },
        closeTemplateBox(){
            this.dialog.showTemplateBox=false;
        },
        //编辑楼层
        setEditFloorName(floorId,floorName,index){
            console.log('setEditFloorName',floorId,floorName,index);
            let params={"floorId":floorId,"floorName":floorName};
            hpmsService.updateTempFloor(params).then(res=>{
                if (res && res.code === 0) {
                    this.data.floorList[index].floorName=floorName;
                    this.data.floorList[index].roomList=res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
            
           
            // this.floorList[index].floorName=floorName;
            this.dialog.showEditFloorName=false;
        },

        showEditFloorName(floorId,floorName,index){
            this.dialog.showEditFloorName=true;
            this.$nextTick(() => {
                this.$refs['editFloorName'].init(floorId,floorName,index)
            });
        },
        closeEditFloorName(){
            this.dialog.showEditFloorName=false;
        },
        
        //单选方法
        selRoom(index,key){
            if(this.data.floorList[index].roomList[key].active){
                this.data.floorList[index].roomList[key].active=false;
                this.cacheUresData('del',index,key);
            }else{
                this.cacheUresData('add',index,key);
                this.$set(this.data.floorList[index].roomList[key],'active',true);
            }
        },
        //缓存用户选中信息
        cacheUresData(type='add',i,k){
            if(type=='add'){
                if(typeof this.userSelRooms[i] == 'undefined'){
                    this.userSelRooms[i]={};
                    this.userSelRooms[i][k]=true;
                }else{
                    this.userSelRooms[i][k]=true;
                }
            }else{
                if(typeof this.userSelRooms[i] == 'undefined'){
                    ///this.userSelRooms[i]={k:true};
                }else{
                   delete this.userSelRooms[i][k];
                }
            }
        },
        //添加一个空值
        addRoom(floorId,index){
            let params={floorId:floorId}
            hpmsService.saveRoomData(params).then(res=>{
                if (res && res.code === 0) {
                    this.data.floorList[index].roomList.push(res.data);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
            
            // let json=JSON.stringify(this.defaultRoom);
            // this.data.floorList[index].roomList.push(JSON.parse(json));
        },
        //删除房间
        delRoom(roomId,index,key){
            hpmsService.deleteRoomData(roomId).then(res=>{
                if (res && res.code === 0) {
                    this.$message.success('删除成功');
                    this.data.floorList[index].roomList.splice(key,1);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //编辑楼层
        setEditRoomData(data,index,key){
            console.log(data,index,key);
            console.log(this.data.floorList[index].roomList);
            this.data.floorList[index].roomList[key]=data;
            this.dialog.showEditRoomData=false;

            console.log(this.data.floorList[index].roomList);
        },

        showEditRoomData(index,key){
            let roomData=this.data.floorList[index].roomList[key];
            this.dialog.showEditRoomData=true;
            this.$nextTick(() => {
                this.$refs['editRoomData'].init(roomData,index,key)
            });
        },
        closeEditRoomData(){
            this.dialog.showEditRoomData=false;
        },
        delFloor(floorId,floorName,index){
            this.$confirm('确认删除'+floorName+'层数据吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                hpmsService.deleteTempFloor(floorId).then(res=>{
                    if (res && res.code === 0) {
                        // this.data.floorList[index].floorName=floorName;
                        this.data.floorList.splice(index,1);
                    } else {
                        this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                    }
                })
            }).catch(() => {});
        },
        
        //全选
        selAllFloorRoom(index){
            this.$set(this.data.floorList[index],'active',true);
            for(let i in this.data.floorList[index].roomList){
                this.$set(this.data.floorList[index].roomList[i],'active',true);
                this.cacheUresData('add',index,i);
            }
            //追回选中的值
        },
        //取消全选 
        selUnAllFloorRoom(index){
            this.$set(this.data.floorList[index],'active',false);
            this.userSelRooms[index]=[];
            for(let i in this.data.floorList[index].roomList){
                this.$set(this.data.floorList[index].roomList[i],'active',false)
            }
            //取消追回选中的值
        },
        verification(){
            let name={};
            this.tmpNum=0;
            for(let k in this.data.floorList){
                for(let i in this.data.floorList[k].roomList){
                    let tmp=this.data.floorList[k].roomList[i];
                    if(name[tmp.roomName]){
                        this.$message.error(tmp.roomName+'房号重复,请修改!')
                        return;
                    }else{
                        name[tmp.roomName]=true;
                    }
                    if(tmp.tempId==null || typeof tmp.tempId =='undefined'){
                        this.tmpNum++;
                    }
                }
            }
            return true;
        },
        submit(){
            if(!this.verification()){
                return ;
            }
            if(this.tmpNum>0){
                this.$confirm('还有'+this.tmpNum+'个房间未设置模板，是否现在就创建项目？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type:'error'
                }).then(() => {
                    this.updata();
                }).catch(() => {});
            }else{
                this.updata();
            }
           
            
        },
        updata(){
            let params={}
            // params.apartmentId=this.data.apartmentId;
            params=this.data.floorList;
            for(let k in this.data.floorList){
                for(let i in this.data.floorList[k].roomList){
                    let tmp=this.data.floorList[k].roomList[i];
                    this.data.floorList[k].roomList[i].floorId=this.data.floorList[k].floorId;
                    tmp.monthRent='N';
                    tmp.dayRent='N';
                    if(typeof tmp.rentType!=='undefined' && tmp.rentType!=null){
                        tmp.rentType.forEach(val=>{
                            if(val=='MONTH_RENT'){
                                this.data.floorList[k].roomList[i].monthRent='Y'
                            }
                            if(val=='DAY_RENT'){
                                this.data.floorList[k].roomList[i].dayRent='Y'
                            }
                        });
                    }

                }
            }
			// console.log({"floorList":params});
			// return ;
			
            this.LOADING(true);
             hpmsService.createApartmentInfo({"floorList":params}).then(res=>{
				 if (res && res.code === 0) {
					 this.$message.success({message:'保存成功',duration:1000,onClose:function(){
						 location.reload();
						 this.LOADING(false);
					}});
					this.$emit('closeForm');
					
					
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            }) 
        }
    }
}