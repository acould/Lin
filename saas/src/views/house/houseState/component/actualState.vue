<template>
    <div class="actual-state">
        <ul class="legend">
            <li class="legend-item"  :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'CHECKIN'}"  @click.stop="setRealtimeStatus('CHECKIN')">
                <span class="legend-item-num blue">{{realtimeStatusList.checkInCount}}</span>
                <span>已入住</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'PREPAY'}" @click.stop="setRealtimeStatus('PREPAY')">
                <span class="legend-item-num green">{{realtimeStatusList.prePayCount}}</span>
                <span>已预约</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'LEAVE'}" @click.stop="setRealtimeStatus('LEAVE')">
                <span class="legend-item-num orange">{{realtimeStatusList.leaveCount}}</span>
                <span>预离店</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'REPAIR'}" @click.stop="setRealtimeStatus('REPAIR')">
                <span class="legend-item-num red">{{realtimeStatusList.repairCount}}</span>
                <span>维修中</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'DIRTY'}" @click.stop="setRealtimeStatus('DIRTY')">
                <span class="legend-item-num brown">{{realtimeStatusList.dirtyCount}}</span>
                <span>脏房</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'LOCK'}" @click.stop="setRealtimeStatus('LOCK')">
                <span class="legend-item-num gray">{{realtimeStatusList.lockCount}}</span>
                <span>锁定</span>
            </li>
            <li class="legend-item" :class="{legend_item_selected:this.searchForm.dayRentRoomStatus == 'EMPTY'}" @click.stop="setRealtimeStatus('EMPTY')">
                <span class="legend-item-num white">{{realtimeStatusList.emptyCount}}</span>
                <span>空置房</span>
            </li>
        </ul>
        <div class="floor-item" v-for="(floor, floorIndex) in filterList" :key="floorIndex">
            <h6>{{floor.roomTypeName}}</h6>
            <ul class="room-list">
                <li class="room-item" :class="room.status" v-for="(room, roomIndex) in floor.rooms" :key="floorIndex + roomIndex" @contextmenu.prevent="handleContextMenu(room, $event)">
                    <span class="room-name">{{room.roomName}}</span>
                    <span class="room-tag" v-if="room.status != 'EMPTY'">{{getTagName(room)}}</span>
                    <div class="room-info">
                        <span v-if="room.status == 'REPAIR'">维修中</span>
                        <span v-else-if="!room.orderDate" class="room-price">{{'￥' + room.price + '元/晚'}}</span>
                        <div v-else>
                            <p class="room-customer">{{room.customerName}}</p>
                            <p class="room-date">{{room.orderDate}}</p>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
import houseService from 'service/house';
import {mapState, mapMutations} from 'vuex';

export default {
    props:{
        apartmentid:{
            type:String,
            default:'4028853C6A9BE441016A9BE441BD0000'
        },
        content:{
            type: String,
            default: ''
        },
        contenttype:{
            type: String,
            default: ''
        },
    },
    data(){
        return {
            realtimeStatusList:{
                checkInCount:'',
                leaveCount:'',
                prePayCount:'',
                lockCount:'',
                emptyCount:'',
                dirtyCount:'',
                repairCount:''
            },
            searchForm:{
                apartmentInfoId: this.apartmentid,
                selectType:this.contenttype,
                typeValue:this.content,
                dayRentRoomStatus:'',
                current:1,
                size:100
            },
            realTimeList:[],
            filterList:[]
        }
    },
    watch:{
        apartmentid(val){
            this.search();
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        getRealTimeStatus(){
            let params = {
                apartmentInfoId:this.apartmentid
            }

            houseService.getRealtimeStatus(params).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'realtimeStatusList', res.data);
                }
            });
        },
        setRealtimeStatus(status){
            if(this.searchForm.dayRentRoomStatus !== status){
                this.searchForm.dayRentRoomStatus = status;
            }else{
                this.searchForm.dayRentRoomStatus = '';
            }
            this.getGrid();
        },
        getGrid(){
            this.LOADING(true);
            let params = JSON.parse(JSON.stringify(this.searchForm));
            params.apartmentInfoId = this.apartmentid;
            params.typeValue = this.content;
            params.selectType = this.contenttype;
            houseService.getRealtimeList(params).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'realTimeList', res.data);
                    this.$set(this, 'filterList', res.data);
                }
                this.LOADING(false);
            });
        },
        search(){
            this.getRealTimeStatus();
            this.getGrid();
        },
        getTagName(room){
            let res = '';
            if (room.status == 'LOCK' || room.status == 'REPAIR') {
                res = '锁定'
            } else if (room.status == 'PREPAY' || room.status == 'LEAVE' || room.status == 'CHECKIN') {
                res = room.customerSourceName;
            } else if (room.status == 'DIRTY') {
                res = '脏房'
            }

            return res;
        },
        setRoomStatus(room, status, flg){
            let params = {
                roomIds: [room.roomId],
                type: status,
                startTime: new Date(),
                endTime: new Date(),
                value:flg
            }

            houseService.setRoomStatus(params).then(res => {
                if (res.code === 0) {
                    this.search();
                    this.$message.success('设置成功');
                }
            });
        },
        handleContextMenu(room, event){
            let list = [];

            if (room.status == 'EMPTY') {
                list.push({
                    label:'设为维修', handler: () => { this.setRoomStatus(room, 'REPAIR', true) }
                });
                list.push({
                    label:'设为停用', handler: () => { this.setRoomStatus(room, 'LOCK', true) }
                });
                list.push({
                    label:'转脏房', handler: () => { this.setRoomStatus(room, 'DIRTY', true) }
                });
            }

            if (room.status == 'REPAIR'){
                list.push({
                    label:'取消维修', handler: () => { this.setRoomStatus(room, 'REPAIR', false) }
                });
            }

            if (room.status == 'LOCK') {
                list.push({
                    label:'取消停用', handler: () => { this.setRoomStatus(room, 'LOCK', false) }
                })
            }

            if (room.status == 'DIRTY') {
                list.push({
                    label:'转净房', handler: () => { this.setRoomStatus(room, 'DIRTY', false) }
                });
            }

            this.$showContextMenu(list, event);
        }
    }
}
</script>
<style lang="scss" scoped>
.actual-state{
    padding:20px;
    background: #fff;
    border: 1px solid #ddd;
    height: 100%;
    overflow: auto;

    .legend{
        display: flex;
        margin-bottom: 24px;

        .legend-item{
            line-height: 24px;
            padding: 8px;
            cursor: pointer;
        }

        .legend-item + .legend-item{
            margin-left: 24px;
        }
        .legend_item_selected{

            background-color: #E6F1FC;
            border-radius: 4px;
        }

        .legend-item-num{
            display: inline-block;
            width: 24px;
            height: 24px;
            line-height: 24px;
            border-radius: 50%;
            vertical-align: bottom;
            color:#fff;
            text-align: center;

            &.blue{
                background: #329dff;
            }

            &.green{
                background: #21d9b1;
            }

            &.orange{
                background: #ffb54d;
            }

            &.red{
                background: #fa6485;
            }

            &.brown{
                background: #e3bc86;
            }

            &.gray{
                background: #ccc;
            }

            &.white{
                background: #fff;
                color:#333;
                border:1px solid #ddd;
            }
        }
    }

    .floor-item{
        margin-bottom:50px;

        h6{
            color: #333;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .room-list{
            display: flex;
            flex-wrap: wrap;
        }

        .room-item{
            position:relative;
            padding:8px;
            width:120px;
            height: 100px;
            border:1px solid #eaedf3;

            .room-name{
                font-size:18px;
                line-height: 25px;
                font-weight: bold;
                color:#333;
            }

            .room-tag{
                position: absolute;
                height: 21px;
                width: 40px;
                background: #fff;
                border:1px solid #aaa;
                border-radius: 2px;
                text-align: center;
                line-height: 21px;
                right:8px;
                top:8px;
                font-size:12px;
                overflow: hidden;
            }

            .room-info{
                position:absolute;
                bottom:8px;
                left: 0;
                width: 100%;
                padding:0 8px;
            }

            .room-price{
                line-height: 20px;
            }

            .room-customer{
                line-height: 20px;
                margin: 0;
            }

            .room-date{
                font-size: 12px;
                line-height: 14px;
            }

            &.CHECKIN{
                background: #329dff;
                border-color:#329dff;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#1a7ed9;
                }
            }

            &.PREPAY{
                background: #21D9B1;
                border-color:#21D9B1;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#11BF99;
                }
            }

            &.LEAVE{
                background: #FFB54D;
                border-color:#FFB54D;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#DC8913;
                }
            }

            &.REPAIR{
                background: #FA7D98;
                border-color:#FA7D98;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#E01642;
                }
            }

            &.DIRTY{
                background: #E3BC86;
                border-color:#E3BC86;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#CD8119;
                }
            }

            &.LOCK{
                background: #CCCCCC;
                border-color:#CCCCCC;
                color:#fff;

                .room-name{
                    color:#fff;
                }

                .room-tag{
                    border-color:#fff;
                    color:#716C6C;
                }
            }
        }

        .room-item {
            margin-right:20px;
            margin-bottom:20px;
        }
    }
}
</style>


