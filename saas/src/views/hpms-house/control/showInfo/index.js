
import tpl from './index.html';
import './index.scss';
import imgBox from './house-info/imgbox';
import editHouse from './house-info/editHouse.js';
import changeRentType from './house-info/changeRentType.js';
import changePrice from './house-info/changePrice.js';
import changeRemark from './house-info/changeRemark.js';
import changeIllRoom from './house-info/changeIllRoom';
import monthContract from './../../../contract/component/monthContractAdd';

import facility from './facility';
import houseBillList from './bill/';
import houseHistory from './history';

import hpmsService from 'service/hpms-house';
import { mapState,mapMutations } from 'vuex';

export default {
    name: 'hpmsHouseInfo',
    template: tpl,
    props:{
        // id:String,
    },
   
    data () {
        return {
            activeName:'houseInfo',
            tabs:{
                house:{
                    imgBox:false,
                    editHouse:false,
                    rentType:false,
                    price:false,
                    remark:false,
					illRoom:false,
					monthContract:false,
					
					MContractButton:{
						step:1,
						signType:"ELECTRONIC_SIGN",
						tempBody:false,
					}
                }
            },
            roomId:'',
            // imgList:this.data.attachmentList,
			showInfo:false,
			reMonthContractNum:0,
			
            data:[],
            
        }
    },
    components:{
        'imgBox':imgBox,
        'editHouse':editHouse,
        'changeRentType':changeRentType,
        'changePrice':changePrice,
        'changeRemark':changeRemark,
        'changeIllRoom':changeIllRoom,
        'facility':facility,
        'houseBillList':houseBillList,
		'houseHistory':houseHistory,
		'monthContract':monthContract,
    },
    mounted(){
        // console.log(':Boolean',this.is_show_house_info)
        //this.init();
    },
    watch: {
        activeName(newval,oldval){
            if(newval=='bills'){
                this.$refs['houseBillList'].init(this.roomId);
            }else if(newval=='houseConfig'){
				this.$refs['facilityBox'].init(this.roomId);
			}else if(newval=='history'){
				this.$refs['houseHistory'].init(this.roomId);
			}
        }
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        // init(){
        //     console.log('show_house_info',this.show_house_info);
        //     console.log('is_show_house_info',this.is_show_house_info);
        // },
        showRoomsInfo(id){
            this.LOADING(true);
            hpmsService.getHouseInfo(id).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.roomId=id;
                    this.data = res.data;
                    this.activeName='houseInfo';
                    this.showInfo=true;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //重新从服务器获取数据
        resetRoomsInfo(){
            this.LOADING(true);
            hpmsService.getHouseInfo(this.roomId).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.data = res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        
        handleClose(){
            // console.log('132132131')
            // this.activeName='houseInfo';
            // // this.$confirm('确定关闭?').then(_ => {
            // // //   done();
            // // });
            // // this.$emit('hideHouseInfo')
        },
        imgsClose(none){
            this.tabs.house.imgBox=false;
            none();
        },
        //查看图片
        upImgs(){
            this.tabs.house.imgBox=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['showHouseImgList'].init()
            })
            // this.$refs.showHouseImgList.init();
        },
        //编辑房源
        editHouse(){
            this.tabs.house.editHouse=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['editHouseFrom'].init(this.data)
            })
        },
        hideEditHouse(){
            this.tabs.house.editHouse=false;
        },
        saveEditHouse(data){
            this.LOADING(true);
            hpmsService.saveEditHouse(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.tabs.house.editHouse=false;
                    this.resetRoomsInfo();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        changeRentType(){
            this.tabs.house.rentType=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['changeRentTypeFrom'].init(this.data)
            })
        },
        hideChangeRentType(){
            this.tabs.house.rentType=false;
        },
        saveChangeRentType(data){
            this.LOADING(true);
            hpmsService.saveChangeRentType(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.tabs.house.rentType=false;
                    this.resetRoomsInfo();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //变量定价
        changePrice(){
            this.tabs.house.price=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['changePriceFrom'].init(this.data)
            })
        },
        hideChangePrice(){
            this.tabs.house.price=false;
        },
        saveChangePrice(data){
            this.LOADING(true);
            hpmsService.saveChangePrice(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.tabs.house.price=false;
                    this.resetRoomsInfo();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //编辑备注
        changeRemark(){
            this.tabs.house.remark=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['changeRemarkFrom'].init(this.data)
            })
        },
        hideChangeRemark(){
            this.tabs.house.remark=false;
        },
        saveChangeRemark(data){
            this.LOADING(true);
            hpmsService.saveChangeRemark(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.tabs.house.remark=false;
                    this.resetRoomsInfo();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //设置问题房源
        changeIllRoom(){
            this.tabs.house.illRoom=true;
            this.resetRoomsInfo();
            this.$nextTick(() => {
                this.$refs['changeIllRoomFrom'].init(this.data)
            })
        },
        hideChangeIllRoom(){
            this.tabs.house.illRoom=false;
        },
        saveChangeIllRoom(data){
            this.LOADING(true);
            hpmsService.saveChangeIllRoom(data).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('保存成功');
                    this.tabs.house.illRoom=false;
                    this.resetRoomsInfo();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },

        

        showHouseInfo(){
            this.showInfo=true;
        },
        hideHouseInfo(){
            this.showInfo=false;
		},
		addMonthContract(){
			this.tabs.house.monthContract=true
			this.reMonthContractNum++;
			this.$nextTick(() => {
				var params={
					roomId:this.data.roomId,
					apartmentInfoId:this.data.apartmentInfoId,
					apartmentName:this.data.apartmentName,
					roomName:this.data.roomName,
					apartmentAddress:this.data.address,
					orderStatusCn:this.data.orderStatusCn,
				}
				this.$refs['monthContract'].init(params);
            })
			
		},
		//新增保存租约合同
		saveMonthContract(){
			this.$refs['monthContract'].saveContract();
		},
		showBill(){
			this.$refs['monthContract'].showBill();
			// this.tabs.house.MContractButton.step=2;
		},
		changeSignType(type){
			//电签:ELECTRONIC_SIGN;纸签:PAPER_SIGN
			this.tabs.house.MContractButton.signType=type
		},
		//打开返回修改
		retLastStep(){
			this.lastMContract();
		},
		//切换到模板页
		openTempListPane(){
			this.$refs['monthContract'].openTempListPane();
			this.nextMContract();
		},
		//打开选择框
		openTempListBox(){
			this.$refs['monthContract'].openTempListBox();
		},
		nextMContract(){
			if(this.tabs.house.MContractButton.step<3){
				this.tabs.house.MContractButton.step=this.tabs.house.MContractButton.step+1;
				if(this.tabs.house.MContractButton.step==3){
					this.$refs['monthContract'].showPirviewTab();
				}
			}
		},
		lastMContract(){
			let step=this.tabs.house.MContractButton.step;
			if(step>1){
				step=step-1;
				this.tabs.house.MContractButton.step=step
				if(step!=3){
					this.$refs['monthContract'].hidePirviewTab();
				}
				if(step==1){
					this.$refs['monthContract'].openContractInfo();
				}else if(step==2){
					this.$refs['monthContract'].openReceiptList();
				}
			}
		},
		successMContract(){
			this.tabs.house.monthContract=false;
		},
		changeTempBody(bool){
			this.tabs.house.MContractButton.tempBody=bool
		},
		

        
        
    }
}
