<template>
    <div id="hpms-update-form">
        <i-drawer title="公寓信息" v-model="show" width="60%"  :before-close="handleClose">
        <i-tabs  v-model="activeName" ref="tab" type="border-card">
            <i-tab-pane label="房源详情" name="baseInfo" >
                <div class="show-info">
                    <div class="head">
                        <p>基础信息</p>
                        <p class="operation-box"></p>
                    </div>
					<i-form>
						
						<i-row :gutter="10">
							<i-col :span="8">
								<label>公寓名称：</label>
                				<span>{{data.apartmentName}}</span>
							</i-col>
							<i-col :span="8">
								<label>所在城市：</label>
                				<span>{{data.cityName}}-{{data.regionName}}-{{data.areaName}}</span>
							</i-col>
							<i-col :span="8">
								<label>总楼层：</label>
                				<span>{{data.floors}}</span>
							</i-col>
						</i-row>
						<i-row :gutter="10" style="margin-top: 20px;">
							
							<i-col :span="8">
								<label>有无电梯：</label>
                				<span>{{data.elevatorCn}}</span>
							</i-col>
							<i-col :span="8">
								<label>物业类型：</label>
                				<span>{{data.propertyTypeCn}}</span>
							</i-col>
						</i-row>
						<i-row :gutter="10" style="margin-top: 20px;">
							<i-col :span="24">
								<label>项目地址：</label>
                				<span>{{data.address}}</span>
							</i-col>
						</i-row>
					</i-form>
                </div>

                <div class="show-info">
                    <div class="head">
                        <p>房间信息</p>
                        <p class="operation-box"> 
                        </p>
                    </div>
                    <div class="info-box">
                        <div class="room-list" >
                            <ul>
                                <li v-for="(item,index) in data.floorList" :key=index>
                                    <div class="house-floor-box">
                                        <div class="house-floor">{{item.floorName}}层</div>
                                    </div>
                                    
                                    <div class="rooms-card " v-for="(v,vIndex) in item.roomList" :key=vIndex >
                                        <div class="card-info card-color-blue" v-if="v.rentStatus=='RENT' && v.monthRent=='Y'">
                                            <div class="card-head" >  
                                                <div class="card-title">
                                                    <div class="room-name" :class="[{'card-long4-title':v.roomName.length>4},{'card-long6-title':v.roomName.length>6}]">{{v.roomName}}</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="card-info  card-color-yellow" v-else-if="v.rentStatus=='RENT' && v.dayRent=='Y'">
                                            <div class="card-head" >  
                                                <div class="card-title">
                                                    <div class="room-name" :class="[{'card-long4-title':v.roomName.length>4},{'card-long6-title':v.roomName.length>6}]">{{v.roomName}}</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="card-info  card-color-red" v-else-if="v.vacantDays>60">
                                            <div class="card-head" >  
                                                <div class="card-title" >
                                                    <div class="room-name" :class="[{'card-long4-title':v.roomName.length>4},{'card-long6-title':v.roomName.length>6}]">{{v.roomName}}</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="card-info  card-white" v-else>
                                            <div class="card-head" >  
                                                <div class="card-title">
                                                    <div class="room-name"  :class="[{'card-long4-title':v.roomName.length>4},{'card-long6-title':v.roomName.length>6}]">{{v.roomName}}</div>
                                                </div>
                                            </div>
                                        </div>


                                    </div>

                                    
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="hpms-button-box">
                        <div class="right-button-box">
                            <i-button @click.stop="editTemplate()">管理房型模板</i-button>
                            <i-button @click.stop="editRoom()">编辑房间</i-button>
                            <i-button  type="primary" @click.stop="editBaseInfo()" >编辑基础信息</i-button>
                        </div>
                    </div>
                </div>
                <editBaseInfo  ref="editBaseInfoForm" ></editBaseInfo>
                <editTemplate  ref="editTemplateForm" ></editTemplate>
                <editRoom  ref="editRoomForm" ></editRoom>
            </i-tab-pane>
            
            <!-- <i-tab-pane label="历史数据" name="history">
                <houseHistory ref="houseHistory" :id="data.roomId"></houseHistory>
            </i-tab-pane> -->
        </i-tabs>
    </i-drawer>

     

   
</div>
</template>


<script>
import './update-form.scss'
import hpmsService from 'service/hpms-house';
import editBaseInfo from './editBaseInfo';
import editTemplate from './editTemplate';
import editRoom from './editRoom';
import { mapState,mapMutations } from 'vuex';
// import House from './house';
// import Rooms from './room';
export default {
    components: {
        // 'house-box':House,
        'editBaseInfo':editBaseInfo,
        'editTemplate':editTemplate,
        'editRoom':editRoom,
    },
    mounted(){
	},
	
    data(){
        return {
            show:false,
            activeName:'baseInfo',
            apartmentInfoId:'',
            data:[],
			cardLongTitleClass:true,

        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        init(parId){
            console.log(parId)
        },
        open(apartmentInfoId){
            this.LOADING(true);
            this.show=true;
            this.apartmentInfoId=apartmentInfoId;
            hpmsService.getApartmentInfo(apartmentInfoId).then(res=>{
                this.LOADING(false);
                if (res && res.code === 0) {
                    this.data = res.data;
                } else {
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        },
        
        handleClose(){
            this.show=false;
        },
        editBaseInfo(){
            this.$refs['editBaseInfoForm'].open(this.apartmentInfoId);
        },
        editTemplate(){
            this.$refs['editTemplateForm'].open(this.apartmentInfoId);
        },
        editRoom(){
            this.$refs['editRoomForm'].open(this.apartmentInfoId);
        },
        
        
    },
    
   

}
</script>
