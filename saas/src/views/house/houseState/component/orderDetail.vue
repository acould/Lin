<template>
    <div class="order-detail">
        <div class="info-wrap">
            <h6 class="info-title">基础信息</h6>
            <i-row :gutter="10">
                <i-col :span="8">
                    <label class="info-label">租客信息:</label>
                    <span class="info-text">{{form.prepayName + '-' + form.prepayPhone}}</span>
                    <a class="edit" @click.stop="editInfo"></a>
                </i-col>
                <i-col :span="8">
                    <label class="info-label">订单号:</label>
                    <span class="info-text">{{form.orderNum}}</span>
                </i-col>
                <i-col :span="8">
                    <label class="info-label">渠道订单号:</label>
                    <span class="info-text">{{form.thirdOrderNum}}</span>
                </i-col>
                <i-col :span="24">
                    <label class="info-label">备注:</label>
                    <span class="info-text">{{form.remark}}</span>
                </i-col>
            </i-row>
            <div class="order-wrap">
                <i class="order-tag" :class="form.dayRentOrderStatus">{{form.dayRentOrderStatusName}}</i>
                <p class="order-row">
                    <label class="info-label">入离时间:</label>
                    <span class="info-text">{{form.startTime + '至' + form.endTime + ', 共' + form.nightCount + '晚'}}</span>
                    <a class="edit" @click.stop="editCheckin"></a>
                </p>
                <p class="order-row">
                    <label class="info-label">入住人:</label>
                    <span class="info-text" v-for="(item,index) in form.checkInMemberList" :key="index">
                        {{item.name + '/' + item.phone}}
                    </span>
                    <a class="edit" @click.stop="editCheckinPerson"></a>
                </p>
                <div class="room-info">
                    <div>
                        <span class="room-no">{{form.roomName}}</span>
                        <img src="/static/imgs/house/shape.svg" alt="">
                    </div>
                    <p class="room-type-name">{{form.roomTypeName}}</p>
                </div>
            </div>
        </div>
        <div class="info-wrap" v-if="form.relationList.length > 0">
            <h6 class="info-title">关联订单</h6>
            <div class="order-wrap border" v-for="(order, orderIndex) in form.relationList" :key="orderIndex">
                <i class="order-tag" :class="order.orderStatus">{{order.orderStatusName}}</i>
                <p class="order-row">
                    <label class="info-label">入离时间:</label>
                    <span class="info-text">{{order.startTime + '至' + order.endTime + ', 共' + order.nightCount + '晚'}}</span>
                    <!-- <a class="edit"></a> -->
                </p>
                <p class="order-row">
                    <label class="info-label">入住人:</label>
                    <span class="info-text" v-for="(item,index) in order.checkInMemberList" :key="index">
                        {{item.name + '/' + item.phone}}
                    </span>
                    <!-- <a class="edit"></a> -->
                </p>
                <div class="room-info">
                    <div>
                        <span class="room-no">{{order.roomName}}</span>
                        <img src="/static/imgs/house/shape.svg" alt="">
                    </div>
                    <p class="room-type-name">{{order.roomTypeName}}</p>
                </div>
            </div>
        </div>
        <i-row :gutter="10">
            <i-col :span="5">
                <label>订单总额:</label>
                <span>{{form.totalPrice}}</span>
            </i-col>
            <i-col :span="5">
                <label>房间金额:</label>
                <span>{{form.roomPrice}}</span>
            </i-col>
            <i-col :span="5">
                <label>实收金额:</label>
                <span>{{form.receivedMoney}}</span>
            </i-col>
        </i-row>
        <div class="operate-bar">
            <i-button @click.stop="openCancel" v-if="form.dayRentOrderStatus == 'PREPAY'">取消订单</i-button>
            <i-button type="primary" @click.stop="openLeave" v-if="form.dayRentOrderStatus == 'CHECKIN'">办理退房</i-button>
            <i-button type="primary" @click.stop="openCheckin" v-if="form.dayRentOrderStatus == 'PREPAY'">办理入住</i-button>
        </div>
        <i-dialog v-model="orderDialogStatus" title="修改预定信息" size="small" append-to-body>
            <i-form :model="orderForm" label-width="100px" ref="orderForm">
                <i-form-item prop="prepayName" label="预订人姓名:">
                    <i-input v-model="orderForm.prepayName"></i-input>
                </i-form-item>
                <i-form-item prop="prepayPhone" label="预订人电话:">
                    <i-input v-model="orderForm.prepayPhone"></i-input>
                </i-form-item>
                <i-form-item prop="sourceType" label="渠道来源:">
                    <i-select v-model="orderForm.sourceType">
                        <i-option v-for="(option, index) in dict['DayCustSource']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                    </i-select>
                </i-form-item>
                <i-form-item prop="thirdOrderNum" label="渠道订单号:">
                    <i-input v-model="orderForm.thirdOrderNum"></i-input>
                </i-form-item>
                <i-form-item prop="remark" label="备注:">
                    <i-input type="textarea" v-model="orderForm.remark"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="orderDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="savePrepayInfo">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="checkinDialogStatus" title="修改入住信息" size="small" append-to-body>
            <div>
                <i-form :model="checkinForm" ref="checkinForm" label-width="100px">
                    <i-form-item label="入住日期:" prop="startTime">
                        <i-date-picker type="date" style="width:100%" v-model="checkinForm.startTime" @change="handleDateChange"></i-date-picker>
                    </i-form-item>
                    <i-form-item label="房号:" prop="type">
                        <i-cascader :options="roomTypeList" v-model="checkinForm.type" placeholder="入住房间" @change="handleRoomChange"></i-cascader>
                    </i-form-item>
                    <i-form-item label="入住天数" prop="nightCount">
                        <i-select v-model="checkinForm.nightCount" placeholder="入住时长" @change="handleDayChange">
                            <i-option v-for="(dictItem, dictIndex) in orderDayList" :key="dictIndex" :label="dictItem.label" :value="dictItem.value"></i-option>
                        </i-select>
                    </i-form-item>
                    <i-form-item label="价格" prop="roomPrice"  style="position:relative">
                        <i-input v-model="checkinForm.roomPrice" @focus="priceStatus = true" readonly></i-input>
                        <div class="price-wrap" v-if="priceList.length > 0" v-show="priceStatus">
                            <div style="text-align:right">
                                <a @click.stop="priceStatus = false">关闭</a>
                            </div>
                            <ul class="price-list" >
                                <li class="price-item" v-for="(price, priceIndex) in priceList" :key="priceIndex">
                                    <div>{{new Date(price.day).Format('MM-dd')}}</div>
                                    <div>
                                        <i-input v-model="price.price" @change="handleDayPriceChange"></i-input>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </i-form-item>
                </i-form>
                <div>
                    <span>入住人数: {{this.form.checkInMemberList.length}}人</span>
                    <i-button type="text" @click.stop="addPerson" :disabled="canCheckinEdit">添加</i-button>
                </div>
            </div>
            <span slot="footer">
                <i-button @click.stop="incomeDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveCheckin" :disabled="canCheckinEdit">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="personDialogStatus" title="入住人" size="small" append-to-body>
            <i-form :model="personForm" ref="personForm" label-width="0px">
                <i-row :gutter="10">
                    <div v-for="(item, itemIndex) in personForm.checkInMemberList" class="person-item" :key="itemIndex">
                        <i-col :span="2" style="line-height:36px;">
                            <i class="el-icon-arrow-right" style="font-size:12px" @click.stop="handleExpand($event)"></i>
                        </i-col>
                        <i-col :span="4">
                            <i-form-item :prop="'checkInMemberList[' + itemIndex + '].name'">
                                <i-input v-model="item.name" placeholder="姓名"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'checkInMemberList[' + itemIndex + '].cardType'">
                                <i-select v-model="item.cardType" placeholder="证件类型">
                                    <i-option v-for="(option, index) in dict['CertType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'checkInMemberList[' + itemIndex + '].cardValue'">
                                <i-input v-model="item.cardValue" placeholder="证件号码"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'checkInMemberList[' + itemIndex + '].phone'">
                                <i-input v-model="item.phone" placeholder="手机号码"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="3">
                            <i class="el-icon-delete" @click.stop="removePerson(itemIndex)" style="line-height:40px;cursor: pointer;"></i>
                        </i-col>
                        <i-col :span="24" class="second-row" style="padding:0 !important">
                            <i-col :span="2" style="line-height:36px;">&nbsp;</i-col>
                            <i-col :span="4">
                                <i-form-item :prop="'checkInMemberList[' + itemIndex + '].country'">
                                    <i-select v-model="item.country" placeholder="国籍">
                                        <i-option v-for="(option, index) in dict['Country']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'checkInMemberList[' + itemIndex + '].sex'">
                                    <i-select v-model="item.sex" placeholder="性别">
                                        <i-option v-for="(option, index) in dict['Sex']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'checkInMemberList[' + itemIndex + '].birthday'">
                                    <i-date-picker style="width:100%" type="date"  v-model="item.birthday" placeholder="生日"></i-date-picker>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'checkInMemberList[' + itemIndex + '].carNo'">
                                    <i-input v-model="item.carNo" placeholder="车牌号"></i-input>
                                </i-form-item>
                            </i-col>
                        </i-col>
                        
                    </div>
                </i-row>
                <div>
                    <a @click.stop="addPersonItem">添加</a>
                </div>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="personDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveCheckinMember" :disabled="canCheckinEdit">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="orderCheckinStatus" title="办理入住" append-to-body>
            <order-checkin ref="orderCheckin" @success="success"></order-checkin>
            <span slot="footer">
                <i-button @click.stop="orderCheckinStatus = false">取消</i-button>
                <i-button @click.stop="saveOrderCheckin" type="primary">确定</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="orderCancelStatus" title="取消订单" append-to-body>
            <order-cancel ref="orderCancel" @success="success"></order-cancel>
            <span slot="footer">
                <i-button @click.stop="orderCancelStatus = false">取消</i-button>
                <i-button @click.stop="saveOrderCancel" type="primary">确定</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="orderLeaveStatus" title="办理退房" append-to-body>
            <order-leave ref="orderLeave" @success="success"></order-leave>
            <span slot="footer">
                <i-button @click.stop="orderLeaveStatus = false">取消</i-button>
                <i-button @click.stop="saveOrderLeave" type="primary">确定</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import {mapState , mapMutations} from 'vuex';
import houseService from 'service/house';
import orderCheckin from './orderCheckin.vue';
import orderCancel from './orderCancel.vue';
import orderLeave from './orderLeave.vue';

export default {
    components:{orderCheckin, orderCancel, orderLeave},
    data(){
        return {
            apartmentInfoId:'',
            form:{
                orderId:'',
                orderInfoId:'',
                prepayName:'',
                prepayPhone:'',
                orderNum:'',
                thirdOrderNum:'',
                sourceType:'',
                remark:'',
                startTime:'',
                endTime:'',
                nightCount:'',
                roomName:'',
                roomId:'',
                roomTypeId:'',
                dayRentOrderStatus:'',
                dayRentOrderStatusName:'',
                roomTypeName:'',
                checkInMemberList:[],
                relationList:[],
                totalPrice:'',
                receivedMoney:''
            },
            orderDialogStatus:false,
            orderForm:{
                orderId:'',
                prepayName:'',
                prepayPhone:'',
                sourceType:'',
                thirdOrderNum:'',
                remark:''
            },
            checkinDialogStatus:false,
            roomTypeList:[],
            orderDayList:[],
            checkinForm:{
                startTime:'',
                endTime:'',
                type:[],
                roomId:'',
                roomTypeId:'',
                nightCount:'',
                roomPrice:0,
                priceList:[],
                totalPrice:0
            },
            personDialogStatus:false,
            personForm:{
                checkInMemberList:[],
            },
            priceList:[],
            priceStatus:false,
            canCheckinEdit:false,
            orderCheckinStatus:false,
            orderCancelStatus:false,
            orderLeaveStatus:false
        }
    },
    methods:{
        success(type){
            this[type] = false;
            this.$emit('success');
        },
        openCheckin(){
            this.orderCheckinStatus = true;
            this.$nextTick(() => {
                this.$refs['orderCheckin'].getDetail(this.form.orderId, this.form.roomId);
            });
        },
        saveOrderCheckin(){
            this.$refs['orderCheckin'].save();
        },
        openCancel(){
            this.orderCancelStatus = true;
            this.$nextTick(() => {
                this.$refs['orderCancel'].getInfo(this.form.orderId);
            });
        },
        saveOrderCancel(){
            this.$refs['orderCancel'].save();
        },
        openLeave(){
            this.orderLeaveStatus = true;
            this.$nextTick(() => {
                this.$refs['orderLeave'].getDetail(this.form.orderId, this.form.roomId);
            });
        },
        saveOrderLeave(){
            this.$refs['orderLeave'].save();
        },
        getDetail(apartmentInfoId, orderId, orderInfoId, roomId){
            this.apartmentInfoId = apartmentInfoId;
            let params = {
                apartmentInfoId:apartmentInfoId,
                orderId,orderId,
                orderInfoId:orderInfoId,
                roomId:roomId
            }

            houseService.getRoomOrderDetail(params).then(res => {
                if (res.code === 0) {
                    res.data.checkInMemberList = res.data.checkInMemberList || [];
                    this.$set(this, 'form', res.data);
                    this.$emit('status', res.data.dayRentOrderStatus);
                }
            });

            this.getRoomDropList();
            this.getRoomOrderDay();
        },
        editInfo(){
            let form = {
                orderId:this.form.orderId,
                prepayName:this.form.prepayName,
                prepayPhone:this.form.prepayPhone,
                sourceType:this.form.sourceType,
                thirdOrderNum:this.form.thirdOrderNum,
                remark:this.form.remark
            }

            this.$set(this, 'orderForm', form);
            this.orderDialogStatus = true;
        },
        editCheckin(){
            let item = JSON.parse(JSON.stringify(this.form));
            houseService.getCheckInfo({
                orderId:item.orderId,
                orderInfoId:item.orderInfoId,
                roomId:item.roomId
            }).then(res => {
                if (res.code === 0) {
                    this.checkinForm.startTime = res.data.startTime;
                    this.checkinForm.type =[item.roomTypeId, item.roomId];
                    this.checkinForm.roomId = item.roomId;
                    this.checkinForm.roomTypeId = item.roomTypeId;
                    this.checkinForm.nightCount = res.data.nightCount.toString();
                    this.checkinForm.roomPrice = res.data.totalPrice;
                    this.$set(this.checkinForm, 'priceList', res.data.priceList);
                    this.canCheckinEdit = res.data.canModify;
                    this.checkinDialogStatus = true;
                }
            });
           
        },
        editCheckinPerson(){
            let item = JSON.parse(JSON.stringify(this.form));
            houseService.getCheckMember({
                orderId:item.orderId,
                orderInfoId:item.orderInfoId,
                roomId:item.roomId
            }).then(res => {
                if (res.code === 0) {
                    this.$set(this.personForm, 'checkInMemberList', res.data.checkInMembers);
                    this.personDialogStatus = true;
                }
            });
        },
        handleExpand(event){
            let $el = event.target.parentElement.parentElement;
            if ($el.className.indexOf('active') > 0) {
                $el.setAttribute('class', 'person-item');
            } else {
                $el.setAttribute('class', 'person-item active');                
            }
        },
        addPersonItem(){
            this.personForm.checkInMemberList.push({
                name:'',
                cardType:'',
                cardValue:'',
                phone:'',
                country:'',
                sex:'',
                birthday:'',
                carNo:''
            });
        },
        removePerson(index){
            this.personForm.checkInMemberList.splice(index, 1);
        },
        savePrepayInfo(){
            let params = JSON.parse(JSON.stringify(this.orderForm));

            houseService.savePrepayInfo(params).then(res => {
                if (res.code === 0) {
                    this.form.prepayName = params.prepayName;
                    this.form.prepayPhone = params.prepayPhone;
                    this.form.sourceType = params.sourceType;
                    this.form.thirdOrderNum = params.thirdOrderNum;
                    this.form.remark = params.remark;
                    this.$message.success('修改成功');
                    this.orderDialogStatus = false;
                }
            });
        },
        saveCheckin(){
            let params = JSON.parse(JSON.stringify(this.checkinForm));
            params['totalPrice'] = params.roomPrice;
            params['orderId'] = this.form.orderId;
            params['orderInfoId'] = this.form.orderInfoId;
            params['roomId'] = this.form.roomId;
            params['startTime'] = new Date(params['startTime']).Format('yyyy-MM-dd');
            params['priceList'] = this.priceList;
            houseService.saveCheckInfo(params).then(res => {
                if (res.code === 0) {
                    this.getDetail(this.apartmentInfoId, this.form.orderId, this.form.orderInfoId, this.form.roomId);
                    this.$message('操作成功');
                }
            }); 
        },
        saveCheckinMember(){
            let params = JSON.parse(JSON.stringify(this.personForm));
            params['orderId'] = this.form.orderId;
            params['orderInfoId'] = this.form.orderInfoId;
            params['roomId'] = this.form.roomId;
            houseService.saveCheckMember(params).then(res => {
                if (res.code === 0) {
                    this.getDetail(this.apartmentInfoId, this.form.orderId, this.form.orderInfoId, this.form.roomId);
                    this.$message('操作成功');
                }
            });
        },
        getRoomOrderDay(){
            houseService.getRoomOrderDay({apartmentInfoId: this.apartmentInfoId}).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'orderDayList', res.data.selections)
                }
            })
        },
        getRoomDropList(){
            let apartmentid = this.apartmentInfoId;
            houseService.getRoomAllList(apartmentid).then(res => {
                if (res.code === 0) {
                    let data = res.data;
                    if (res.data) {
                        res.data.forEach((item, index) => {
                            if (!item.children) {
                                data.splice(index, 1);
                            }
                        });
                    }
                    this.$set(this, 'roomTypeList', data);
                }
            });
        },
        handleDateChange(val){
            this.getDayPrice();
        },
        handleRoomChange(val){
            this.checkinForm.roomId = val[1];
            this.getDayPrice();
        },
        handleDayChange(val){
            this.getDayPrice();
        },
        getDayPrice(){
            let item = this.checkinForm;

             if (!item.roomId) {
                return;
            }

            if (!item.startTime) {
                return;
            }

            if (!item.nightCount) {
                return;
            }
            let params = {
                roomId: item.roomId,
                startDate: new Date(item.startTime).Format('yyyy-MM-dd'),
                days: item.nightCount
            }

            houseService.getRoomDayPrice(params).then(res => {
                if (res.code === 0){
                    let price = 0;
                    res.data.dayPrices.forEach(i => {
                        price += i.price;
                    });
                    this.$set(this, 'priceList', res.data.dayPrices);
                    this.checkinForm.roomPrice = price;
                    this.checkinForm.endTime = this.$moment(item.startTime).add(item.nightCount, 'days').format('YYYY-MM-DD');
                }
            });
        },
        addPerson(){

        },
        handleDayPriceChange(val){
            let price = 0;
            this.priceList.forEach(i => {
                price += parseFloat(i.price);
            });

            this.checkinForm.roomPrice = price;
        },
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>
<style lang="scss">
.order-detail{
.info-wrap{
    margin-bottom: 40px;

    .info-title{
        font-size:18px;
        line-height: 22px;
        color: #333;
        margin-bottom: 20px;
    }

    .info-label,
    .info-text{
        font-size: 14px;
        color: #333;
        line-height: 28px;
    }

    .edit{
        background: url('/static/imgs/house/edit.svg') no-repeat;
        display: inline-block;
        width: 14px;
        height: 14px;
        vertical-align: middle;
        margin-left: 4px;
    }
}

.order-wrap{
    position: relative;
    border-radius: 8px;
    padding:20px;
    margin-top: 20px;
    background: #f5f5f5;

    &.border{
        background: #fff;
        border:1px solid #eaedf3
    }

    .order-row{
        line-height: 20px;
    }

    .order-tag{
        display: inline-block;
        padding:0 8px;
        line-height: 20px;
        border-radius: 12px;
        color:#fff;
        font-size: 12px;
        margin-bottom: 16px;

        &.CHECKIN{
            background: #329dff;
        }

        &.PREPAY{
            background: #ffb54d;
        }

        &.LEAVE{
            background: #999;
        }
    }

    .room-info{
        position: absolute;
        right:32px;
        top:0;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;

        .room-no{
            font-size: 40px;
            color: #333;
            line-height: 48px;
        }

        .room-type-name{
            font-size: 16px;
            line-height: 22px;
            color: #999;
        }
    }
}

.price-wrap{
        position: absolute;
        padding:10px;
        background: #fff;
        border:1px solid #ddd;
        top:calc(100% + 10px);
        right:0;
        z-index: 1;
    }

    .price-list{
        display: flex;
        

        .price-item{
            width: 80px;
            margin-left:10px;
        }
    }
    .person-item{

        .el-icon-arrow-right{
            transform: rotate(0deg);
            transition: transform .3s;
        }

        .second-row{
            display: none;
        }

        &.active{
            .el-icon-arrow-right{
                transform: rotate(90deg);
            }

            .second-row{
                display: block;
            }
        }
        
    } 
    
    .operate-bar{
        position: absolute;
        bottom:0;
        left: 0;
        padding: 20px;
        text-align: right;
        width: 100%;
        border-top:1px solid #ddd;
    }
}


</style>
