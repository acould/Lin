<template>
    <div id="price-list">
        <div class="operate-bar">
            <i-button type="primary" size="small" @click.stop="openAdd">新增房型</i-button>
        </div>
        <i-table :data="tableData"  style="width:100%" :height="tableHeight">
            <i-table-column prop="roomTypeName" label="房型" width="150" align="center"></i-table-column>
            <i-table-column label="价格策略" align="center" inline-template>
                <i-button type="text" class="btn-table-operate" @click.stop="openPriceStrategy(row)">基准价格</i-button>
            </i-table-column>
            <i-table-column prop="roomCount" label="房间数量" width="150" align="center"></i-table-column>
            <i-table-column prop="roomNames" label="房号" width="150" align="center" inline-template>
                <i-button type="text" class="btn-table-operate" @click.stop="openRoomType(row)">{{row.roomNames}}</i-button>
            </i-table-column>
            <i-table-column prop="creator" label="设置人" width="150" align="center"></i-table-column>
            <i-table-column prop="createTime" label="操作时间" width="150" align="center"></i-table-column>
            <i-table-column label="操作" width="150" align="center" inline-template>
                <div>
                    <i-button type="text" class="btn-table-operate" @click.stop="del(row)">删除</i-button>
                </div>
            </i-table-column>
        </i-table>
        <div style="position:relative;padding:10px;">
            <i-button type="text" size="small" @click.stop="extendRoomType" :disabled="tableData.length > 0" style="position:absolute">从房态总览中获取房间模板</i-button>
            <i-pagination ref="pagination" 
                          style="text-align:center"  
                          :page-size="size" 
                          :current-page="current" 
                          :total="total" 
                          @current-change="handleCurrentChange"
                          layout="prev,pager,next,total"></i-pagination>
        </div>
        <i-dialog v-model="addDialogStatus" :title="form.roomTypeId ? '编辑房型' :'新增房型'" size="small">
            <i-form :model="form" ref="form" label-width="100px">
                <i-form-item prop="roomTypeName" label="房型名称:" :rules="[{required:true, message:'请输入房型名称', trigger:'blur'},{max:20, message:'最多不能超过20个汉字'}]">
                    <i-input v-model="form.roomTypeName"></i-input>
                </i-form-item>
                <i-form-item prop="windowTypes" label="窗户描述:" :rules="[{required:true, message:'请选择窗户描述', trigger:'change'}]">
                    <i-select v-model="form.windowTypes">
                        <i-option v-for="(option, index) in dict['DayRentWindowsType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                    </i-select>
                </i-form-item>
                <i-form-item prop="bedTypes" label="床型:" :rules="[{required:true, message:'请选择床型', trigger:'change', type:'array'}]">
                    <i-checkbox-group v-model="form.bedTypes">
                        <i-checkbox v-for="(item, index) in dict['DayRentBedType']" :key="index" :label="item.dictKey">{{item.dictValue}}</i-checkbox>
                    </i-checkbox-group>
                </i-form-item>
                <i-form-item prop="roomList" label="房间号:" :rules="[{required:true, message:'请选择房间号', trigger:'change', type:'array'}]">
                    <div style="display:inline-block;height:30px;width:100%">
                        <i-tag v-for="(tag, index) in form.roomList" :key="index" :closable="true" @close="handleTagClose(tag, index)" type="primary">{{tag.roomName}}</i-tag>
                    </div>
                    <a @click.stop="openAddRoomDialog">添加房间</a>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="addDialogStatus = false">取消</i-button>
                <i-button type="primary" @click="saveRoom">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="roomDialogStatus" title="选择房间">
            <div class="select-room">
                <div style="overflow:hidden">
                    <a style="float:right;font-weight:normal;color:#20a0ff;margin-left:10px" @click.stop="selectCancelAll()">全部取消</a>

                    <a style="float:right;font-weight:normal;color:#20a0ff;" @click.stop="selectAll()">全选</a>

                </div>
                <div v-for="(floor, floorIndex) in roomList" :key="floorIndex" class="floor-item">
                    <h6 class="floor-name">
                        <span>{{floor.floorName}}</span>
                    </h6>
                    <ul>
                        <li class="room-item" :class="{disabled: (room.isIllRoom == true || room.isRoomTyped == true), active: _isInRooms(room)}" v-for="(room, roomIndex) in floor.roomInfoList" :key="floorIndex + roomIndex" @click.stop="selectRoom(room)">{{room.roomName}}</li>
                    </ul>
                </div>
            </div>
            <span slot="footer">
                <i-button @click.stop="roomDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveSelectRoom">确定</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="strategyDialogStatus" title="价格策略" size="large">
            <strategy-list ref="strategyList" :apartmentid="apartmentid"></strategy-list>
        </i-dialog>
    </div>
</template>
<script>
import houseService from 'service/house';
import strategyList from './strategyList';
import {mapState, mapMutations} from 'vuex';

export default {
    components:{strategyList},
    props:{
        apartmentid:{
            type:String,
            default:'4028853C6A9BE441016A9BE441BD0000'
        }
    },
    data(){
        return {
            tableHeight:'',
            tableData:[],
            current:1,
            size:100,
            total:0,
            form:{
                apartmentInfoId:'',
                roomTypeId:'',
                roomTypeName:'',
                bedTypes:[],
                windowTypes:'',
                roomList:[]
            },
            addDialogStatus:false,
            roomDialogStatus:false,
            roomList:[],
            selectedRoomList:[],
            strategyDialogStatus:false,
        }
    },
    watch:{
        'apartmentid'(val){
            this.getGrid();
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        handleCurrentChange(val){
            this.current = val;
            this.getGrid();
        },
        getGrid(){
            this.LOADING(true);
            let params = {
                apartmentInfoId: this.apartmentid,
                current:this.current,
                size:this.size
            }

            houseService.getRoomList(params).then(res => {
                this.LOADING(false);
                
                if (res.code === 0) {
                    this.total = res.total;
                    this.$set(this, 'tableData', res.data || []);
                    this.getRoomType();
                }
            });
        },
        openPriceStrategy(row){
            this.strategyDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['strategyList'].getList();
            });
        },
        openRoomType(row){
            this.LOADING(true);
            houseService.getRoomTypeDetail(row.roomTypeId).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    res.data.bedTypes = res.data.bedTypes.split(',');
                    res.data['windowTypes'] = res.data.windowType;
                    this.addDialogStatus = true;
                    this.$set(this, 'form', res.data);
                }
            });
        },
        del(row){
            this.$confirm('确定要删除这个房型吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                houseService.deleteRoomType(row.roomTypeId).then(res => {
                    this.LOADING(false);

                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getGrid();
                    }
                });
            }).catch(() => {});
        },
        openAdd(){
            this.addDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['form'].resetFields();
                this.form.roomTypeName = '';
                this.form.windowTypes = '';
                this.form.roomTypeId = '';
            });
        },
        openAddRoomDialog(){
            this.roomDialogStatus = true;
            let roomList = JSON.parse(JSON.stringify(this.form.roomList));
            this.$set(this, 'selectedRoomList', roomList);
        },
        extendRoomType(){
            this.$confirm('自动获取会覆盖现有房型数据及价格策略,您是否继续', '提示', {
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);

                houseService.extendRoomType({apartmentInfoId: this.apartmentid}).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getGrid();
                    }
                });
            });
        },
        handleTagClose(tag, index){
            this.form.roomList.splice(index, 1);
        },
        getRoomType(){
            houseService.getRoomType(this.apartmentid).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'roomList', res.data);
                }
            });
        },
        selectRoom(room){
            if (room.isIllRoom || room.isRoomTyped) {
                return;
            }

            if (!this._isInRooms(room)) {
                this.selectedRoomList.push(room);
            } else {
                let index = 0;
                this.selectedRoomList.forEach((roomItem, roomIndex) => {
                    if (roomItem.roomId == room.roomId) {
                        index = roomIndex;
                    }
                });

                this.selectedRoomList.splice(index, 1);
            }
        },
        selectAll(){
            this.$set(this, 'selectedRoomList', []);
            this.roomList.forEach((floor, floorIndex) => {
                floor.roomInfoList.forEach((room, roomIndex) => {
                    if (!room.isIllRoom && !room.isRoomTyped) {
                        this.selectedRoomList.push(room);
                    }
                });
            });
        },
        selectCancelAll(){
            this.$set(this, 'selectedRoomList', []);
        },
        _isInRooms(room){
            var flg = false;
            this.selectedRoomList.forEach(roomItem => {
                if (roomItem.roomId == room.roomId) {
                    flg = true;
                }
            });

            return flg;
        },
        saveSelectRoom(){
            let roomList = JSON.parse(JSON.stringify(this.selectedRoomList));
            this.$set(this.form, 'roomList', roomList);
            this.$set(this, 'selectedRoomList', []);
            this.roomDialogStatus = false;
        },
        saveRoom(){
            this.$refs['form'].validate(valid => {
                if (valid) {
                    this.LOADING(true);
                    let params = JSON.parse(JSON.stringify(this.form));
                    params.apartmentInfoId = this.apartmentid;
                    params.bedTypes = params.bedTypes.join(',');
                    params['roomIds'] = [];

                    params.roomList.forEach(room => {
                        params['roomIds'].push(room.roomId);
                    });

                    delete params['roomList'];

                    houseService.saveRoomType(params).then(res => {
                        this.LOADING(false);
                        if (res.code === 0) {
                            this.getGrid();
                            this.$message.success('操作成功');
                            this.getRoomType();
                            this.addDialogStatus = false;
                        }
                    });
                }
            });
            
        }
    },
    computed:{
        ...mapState(['dict'])
    },
    mounted(){
        this.getGrid();
        this.getRoomType();
    }
}
</script>
<style lang="scss">
    #price-list{
        background: #fff;

        .operate-bar{
            padding: 10px;
            text-align: right;
        }
    }

    .select-room{
        .floor-item{
            border:1px solid #ddd;
            margin-bottom: 10px;
            padding:10px;
            border-radius: 4px;

            .floor-name{
                margin-bottom: 10px;
                font-weight: bold;
            }
        }
        ul{
            display: flex;
            flex-wrap: wrap;
            .room-item{
                width: 88px;
                height: 66px;
                line-height: 66px;
                text-align: center;
                border:1px solid #ddd;
                border-radius: 4px;
                cursor: pointer;
                margin-right:10px;
                margin-bottom: 10px;

                &.disabled{
                    cursor: not-allowed;
                    background: #eef1f6;
                    border-color: #d1dbe5;
                    color: #bfcbd9;
                }

                &.active{
                    color:#20a0ff;
                    border-color:#20a0ff;
                }
            }

            // .room-item + .room-item{
            //     margin-left:10px;
            // }
        }
       
    }

    .el-tag  {
        margin-right:  10px;
    }
    
</style>
