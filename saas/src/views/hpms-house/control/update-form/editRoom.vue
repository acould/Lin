<template>
    <i-dialog  size="large" v-model="show" title="编辑房间" append-to-body > 
        <div id="hpms-edit-room">
            <div class="room-list" v-for="(item,index) in data.floorList" :key="index">
                <div class="house-floor-box">
                    <div class="house-floor">{{item.floorName}}层</div>
                    <span><a href="javascript:;" @click.stop=showEditFloorName(item.floorId,item.floorName,index)>编辑</a></span>
                    <span><a href="javascript:;" @click.stop="delFloor(item.floorId,item.floorName,index)">删除</a></span>
                    <span v-if="item.active!=true"><a href="javascript:;" @click.stop="selAllFloorRoom(index)" >全选</a></span>
                    <span v-else><a href="javascript:;" @click.stop="selUnAllFloorRoom(index)"  >取消全选</a></span>
                </div>
                <ul>
                    <li v-for="(val,key) in item.roomList" :key="key">
                        <div class="rooms-card "  :class="{ active:item.roomList[key].active } "   @click.stop="selRoom(index,key)" @dblclick="openEditRoomData(val.roomId)">
                            <div class="card-info">
                                <div class="room-name" :title="val.roomName">{{val.roomName}}</div>
                                <div class="template-name" :title="val.tempName">{{val.tempName}}</div>
                            </div>
                            <div class=" card-del"  @click.stop="delRoom(val.roomId,index,key)">
                                <i class="el-icon-close"></i>
                            </div>
                        </div>
                    </li>
                    

                    <li>
                        <div class="rooms-card add-rooms"  @click.stop="addRoom(item.floorId,index)">
                            <i class="el-icon-plus"></i>
                        </div>
                    </li>

                </ul>
            </div>
            
            <addFloorName  ref="addFloorNameFrom" title="新增楼层"   @submit="saveNewFloorName"></addFloorName>
            <editFloorName  ref="editFloorName"  @submit="setEditFloorName"></editFloorName>
            <selTmpList  ref="selTmpListFrom"  @submit="init"></selTmpList>
            <editRoomData  ref="editRoomDataFrom"  @submit="init"></editRoomData>
        </div>
        <div style="height: 50px;margin-top:6px;">
            <div style="float: left;margin-left:20px; ">
                <i-button @click.stop="openSelTmp()">选择模板</i-button>
                <i-button @click.stop="openAddFloorName()">新增楼层</i-button>
            </div>
            <div style="float: right;margin-right:20px; ">
                <i-button @click.stop="close()">关闭</i-button>
            </div>
        </div>
    </i-dialog>
</template>
<style lang="scss" scoped>
#hpms-edit-room{
    border: solid 1px #ccc;
    margin: 12px 20px;
    border-radius: 2px;
    padding: 20px;
    height: 492px;
    overflow-y: scroll;
    .room-list{
        display: inline-block;
        width: 100%;
        .house-floor-box{
            margin-bottom: 20px;
            .house-floor{
                border-left: solid 2px #329DFF;
                display: inline-block;
                font-weight: 600;
                padding-left: 8px;
                margin-right: 20px;
            }
            span{
                margin-right: 20px;
                a{
                    color: #448AFF;
                    &:hover{
                        color: #448AFF;
                    }
                }
            }
        }
        ul{
            li{
                display: inline-block;
                float: left;
                .rooms-card{
                    width: 120px;
                    height: 100px;
                    margin-right: 12px;
                    margin-bottom: 12px;
                    border: solid 1px #EAEDF3;
                    display: inline-block;
                    &:hover{
                        .card-del{
                            i{
                                visibility:initial
                               // display: inline-block;
                            }
                        }
                        
                    }
                    .card-info{
                        .room-name{
                            font-size: 18px;
                            font-weight: 600;
                            min-height: 24px;
                            margin: 16px;
                            text-overflow: ellipsis;
                            white-space: nowrap;
                            overflow: hidden;
                        }
                        .template-name{
                            margin-left: 16px;
                            text-overflow: ellipsis;
                            white-space: nowrap;
                            overflow: hidden;
                            min-height: 19px;
                        }
                    }
                    
                    .card-del{
                        position: relative;
                        margin-top: -68px;
                        margin-left: 96px;
                        i{
                           // display: none;
                           visibility: hidden;
                        }
                    }
                }
                .active{
                    border: solid 1px #f11;
                }
                .add-rooms{
                    width: 120px;
                    height: 100px;
                    line-height: 100px;
                    text-align: center;
                    border: 1px dashed #c0ccda;
                    border-radius: 4px;
                    float: left;
                    margin-bottom: 30px;
                }
            }
        }
        
    }
   
    
}
</style>
<script>
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import addFloorName from './i-dialog/addFloorName';
import editFloorName from './i-dialog/editFloorName';
import selTmpList from './i-dialog/selTmpList';
import editRoomData from './i-dialog/editRoomData';
import {
    mapMutations
} from 'vuex';

export default {
    components:{
        // 'selTmpList':selTmpList,
        'editFloorName':editFloorName,
        'addFloorName':addFloorName,
        'selTmpList':selTmpList,
        'editRoomData':editRoomData,
    },
    data(){
        return {
            show:false,
            data:[],
            apartmentInfoId:'',
            floorList:[], //接口需要的参数
            select:{
                'room':[],
                'templateList':[]
            },
            tmpNum:0,
			userSelRooms:{...[]},
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        open(apartmentInfoId){
            this.show=true;
            this.apartmentInfoId=apartmentInfoId;
            this.init()
        },
        init(){
            let apartmentInfoId=this.apartmentInfoId;
            hpmsService.getApartmentInfo(apartmentInfoId).then(res=>{
                if (res && res.code === 0) {
                    this.data=res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        formatData(data){
            

        },
        close(){
            this.show=false;
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
        //删除房间
        delRoom(roomId,index,key){
			this.LOADING(true);
            hpmsService.deleteHouseInfo(roomId).then(res=>{
				this.LOADING(false);
                if (res && res.code === 0) {
                    this.$message.success('删除成功');
                    this.data.floorList[index].roomList.splice(key,1);
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
		//打开编辑楼层页
        showEditFloorName(floorId,floorName,index){
            this.$nextTick(() => {
                this.$refs['editFloorName'].open(floorId,floorName,index)
            });
        },
        //修改楼层名
        setEditFloorName(floorId,floorName,index){
			let params={
				"floorId":floorId,
				"floorName":floorName,
			};
			this.LOADING(true);
            hpmsService.editFloorName(params).then(res=>{
				this.LOADING(false);
                if (res && res.code === 0) {
                    this.data.floorList[index].floorName=floorName;
                    this.$refs['editFloorName'].close();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        openAddFloorName(){
            this.$refs['addFloorNameFrom'].open()
		},
		//新增楼层
        saveNewFloorName(params){
			params.apartmentInfoId=this.apartmentInfoId;
			console.log('saveNewFloorName',params)

			this.LOADING(true);
            hpmsService.saveFloorName(params).then(res=>{
				this.LOADING(false);
                if (res && res.code === 0) {
					this.data.floorList.push(res.data);
                    this.$refs['addFloorNameFrom'].close();
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        //删除楼层
        delFloor(floorId,floorName,index){
            this.$confirm('确认删除'+floorName+'层数据吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
				this.LOADING(true);
                hpmsService.deleteFloorName(floorId).then(res=>{
					this.LOADING(false);
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
			if(typeof this.data.floorList[index].roomList == 'undefined' || this.data.floorList[index].roomList==null){
				this.data.floorList[index].roomList=null;
			}
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
        openEditRoomData(id){
            this.$nextTick(()=>{
                this.$refs['editRoomDataFrom'].init(id);
            })
            
        },
        //添加一个房间
        addRoom(floorId,index){
			let params={floorId:floorId}
			this.LOADING(true);
            hpmsService.addRoom(params).then(res=>{
				this.LOADING(false);
                if (res && res.code === 0) {
					if(this.data.floorList[index].roomList ==null || typeof this.data.floorList[index].roomList=='undefined'){
						this.$set(this.data.floorList[index],'roomList',[res.data]);
					}else{
						this.data.floorList[index].roomList.push(res.data);
					}
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
            
            // let json=JSON.stringify(this.defaultRoom);
            // this.data.floorList[index].roomList.push(JSON.parse(json));
        },
        //修改房间模板
        openSelTmp(){
            let roomIds=[];
            for(let key in this.userSelRooms){
                for(let k in this.userSelRooms[key]){
					if(typeof this.data.floorList[key].roomList[k] !=='undefined'){
						roomIds.push(this.data.floorList[key].roomList[k].roomId);
					}
                    
                }
            }
            this.$refs['selTmpListFrom'].open(this.data.apartmentInfoId,roomIds);
            // this.$nextTick(() => {
                //
            // });
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


    }
}
</script>
