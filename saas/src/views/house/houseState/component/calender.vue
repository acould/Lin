<template>
    <div class="hi-calender">
        <div class="hi-calender-picker-wrap" v-show="headers.length > 0">
            <div class="hi-calender-date-picker">
                <span>{{new Date(dates[0]).Format('MM-dd') + '/' + new Date(dates[1]).Format('MM-dd')}}</span>
                <i class="el-icon-date"></i>
                <i-date-picker type="date" v-model="selectDate"></i-date-picker>
            </div>
            <div class="hi-calender-select">
                <i-select v-model="filter" @change="houseChange">
                    <i-option label="全部房源" value=""></i-option>
                    <i-option v-for="(item, itemIndex) in data" :key="itemIndex" :label="item.roomTypeName" :value="item.roomTypeName"></i-option>
                </i-select>
            </div>
        </div>
        <div class="hi-calender-header" ref="calenderHeader">
            <table class="hi-calender-header-table">
                <tr>
                    <th v-for="(header, headerIndex) in headers" :key="headerIndex" class="header-table-item">
                        <div class="header-table-item-date" :class="{holiday: header.weekday == 6 || header.weekday == 7, active:headerIndex === dateIndex}">
                            <p>{{header.date + '(' +(getWeekDay(header.weekday)) + ')'}}</p>
                            <p>{{header.holidayName}}</p>
                        </div>
                        <div class="header-table-item-rooms">
                            <span>剩余{{countList[headerIndex].count || 0}}间</span>
                        </div>
                    </th>
                </tr>
            </table>
        </div>
        <div class="hi-calender-body">
            <div class="hi-calender-left-list" ref="calenderLeftList">
                <template v-for="(item, itemIndex) in data">
                    <div class="hi-calender-left-item"  :key="itemIndex + 'left'" v-if="filterStatus(item)" >
                        <div class="hi-calender-left-item-name">
                            <p>{{item.roomTypeName}}</p>
                        </div>
                        <div class="hi-calender-left-item-children">
                            <div class="hi-calender-left-item-room" :class="{active: (roomTypeIndex === itemIndex && roomIndex === childIndex)}" v-for="(child, childIndex) in item.rooms" :key="itemIndex + childIndex + 'left-child'">
                                <span>{{child.roomName}}</span>
                                <i class="tag" v-if="child.isDirty">脏房</i>
                                <div class="operate">
                                    <a :class="{dirty: child.isDirty}" @click.stop="setRoomHStatus(child)">{{ child.isDirty ? '转净' : '转脏' }}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>
            </div>
            <div class="hi-calender-status-list" ref="statusList" @scroll="handleScroll">
                <table class="hi-calender-status-table">
                    <tbody>
                        <template v-for="(item, itemIndex) in data">
                            <template v-if="filterStatus(item)">
                                <tr v-for="(child, childIndex) in item.rooms" :key="JSON.stringify(item) + childIndex + 'tr'" >
                                    <template v-for="(subItem,attrIndex) in child.roomItems">
                                        <td class="hi-calender-day" 
                                            :status="subItem.status" 
                                            :room="subItem.date + child.roomId" 
                                            :key="itemIndex + subItem.date + 'day' + attrIndex" 
                                            v-if="attrIndex != 'room'" 
                                            @click="handleCellClick(subItem, item, child)" 
                                            @contextmenu.prevent="handleRightMenu(subItem,item, child, $event)" 
                                            @mouseenter.stop="handleDayEnter(itemIndex,childIndex, attrIndex)"
                                            @mouseleave.stop="handleDayLeave">
                                            <div v-if="subItem.status == 'EMPTY' || subItem.status == 'LEAVE'">{{ subItem.price ? '￥' + subItem.price : '' }}</div>
                                            <div v-else-if="subItem.status == 'REPAIR'" style="color:#fa7d98;">维修中</div>
                                            <div v-else-if="subItem.status == 'LOCK'" style="color:#fa7d98;">停用中</div>
                                            <div v-else-if="subItem.status == 'DIRTY'" style="color:#fa7d98;">脏房</div>
                                            <div v-else></div>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                        </template>
                    </tbody>
                </table>
                <template v-if="orders.length > 0">
                    <template v-for="(item, itemIndex) in orders" >
                        <div class="order-item" 
                            :class="getOrderClass(item)" 
                            v-if="filterStatus(item)"
                            :key="itemIndex" 
                            :style="[getOrderStyle(item)]"
                            @click.stop="openDetail(item)">
                            <p class="source">{{item.sourceName}}</p>
                            <p class="user-name">{{item.name}}</p>
                        </div>
                    </template>
                    
                </template>
                
            </div>
        </div>
        <i-drawer v-model="addOrderDrawerStatus" title="新增订单" width="40%" :has-mask="false" :is-cover="false">
            <div class="order-container">
                <i-form :model="form" label-width="80px" ref="form">
                    <i-row :gutter="10">
                        <i-col :span="24">
                            <div class="divider">预定人</div>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item prop="prepayName" label="联系人姓名:">
                                <i-input v-model="form.prepayName"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item prop="prepayPhone" label="联系电话:">
                                <i-input v-model="form.prepayPhone"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="24">
                            <div class="divider">渠道来源</div>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item prop="source" label="客户来源:">
                                <i-select v-model="form.source">
                                    <i-option v-for="(option, index) in dict['DayCustSource']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item prop="thirdOrderNum" label="订单号:">
                                <i-input v-model="form.thirdOrderNum"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="24">
                            <div class="divider">入住信息</div>
                        </i-col> 
                        <i-col :span="24">
                            <div v-for="(item, itemIndex) in form.info" :key="itemIndex">
                                <i-col :span="24" style="margin-bottom:10px">
                                    <span>入住人数: {{ item.checkInMemberList.length }}人</span>
                                    <a @click.stop="addPerson(itemIndex)" style="color:#329dff">添加</a>
                                </i-col>
                                <i-col :span="7">
                                    <i-form-item :prop="'info[' + itemIndex + '].date'" label-width="0px">
                                        <i-date-picker type="date" v-model="item.date" placeholder="入住日期" style="width:100%" @change="handleDateChange($event, itemIndex)"></i-date-picker>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="6">
                                    <i-form-item :prop="'info[' + itemIndex + '].type'" label-width="0px">
                                        <i-cascader :options="roomTypeList" v-model="item.type" placeholder="入住房间" @change="handleRoomChange($event, itemIndex)"></i-cascader>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="5">
                                    <i-form-item :prop="'info[' + itemIndex + '].day'" label-width="0px">
                                        <i-select v-model="item.day" placeholder="入住时长" @change="handleDayChange($event, itemIndex)">
                                            <i-option v-for="(dictItem, dictIndex) in orderDayList" :key="dictIndex" :label="dictItem.label" :value="dictItem.value"></i-option>
                                        </i-select>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="4">
                                    <i-form-item :prop="'info[' + itemIndex + '].totalPrice'" label-width="0px" style="position:relative" ref="price_wrap">
                                        <i-input v-model="item.totalPrice" placeholder="金额" readonly @focus="showPriceList(itemIndex)"></i-input>
                                        <div class="price-wrap" v-if="item.priceList.length > 0" v-show="item.priceStatus">
                                            <div style="text-align:right">
                                                <a @click.stop="item.priceStatus = false">关闭</a>
                                            </div>
                                            <ul class="price-list" >
                                                <li class="price-item" v-for="(price, priceIndex) in item.priceList" :key="priceIndex">
                                                    <div>{{new Date(price.day).Format('MM-dd')}}</div>
                                                    <div>
                                                        <i-input v-model="price.price" @change="handleDayPriceChange($event, itemIndex)"></i-input>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        
                                    </i-form-item>
                                </i-col>
                                <i-col :span="2">
                                    <i class="el-icon-delete" @click.stop="removeRoom(itemIndex)" style="line-height:40px;cursor: pointer;"></i>
                                </i-col>
                                
                            </div>
                            <div>
                                <i-button style="width:100%;color:#329dff" @click.stop="addRoom" icon="plus">添加房间</i-button>
                            </div>
                        </i-col>
                        <i-col :span="24">
                            <div class="divider">备注</div>
                        </i-col>
                        <i-col :span="24">
                            <i-form-item prop="remark" label-width="0px">
                                <i-input type="textarea" v-model="form.remark"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="24">
                            <div class="divider">财务收支</div>
                        </i-col>
                        <i-col :span="24" style="margin-bottom:20px">
                            <i-button @click.stop="businessDialog = true" :disabled="form.info.length == 0">添加收支</i-button>
                        </i-col>
                        <i-col :span="24" class="business-item" v-for="(item, itemIndex) in form.dayRentRoomOrderFinanceVOs" :key="itemIndex">
                            <span>{{'商户' + (item.receivePayType == 'RECEIVE' ? '实收金额:' : '实退金额:') + '￥' + item.money}}</span>
                            <i class="el-icon-arrow-right" style="position:absolute;right:20px;font-size:12px;top:15px;color:#ddd"></i>
                        </i-col>
                    </i-row>
                </i-form>
                
            </div>
            <div slot="footer">
                <i-row>
                    <i-col :span="8" style="line-height:36px;text-align:left">
                        <label>订单总额:</label>
                        <span>￥{{totalPrice}}</span>
                    </i-col>
                    <i-col :span="16">
                        <i-button @click.stop="saveOrder(false)" v-if="btnStatus">补录</i-button>
                        <i-button @click.stop="saveOrder(true)" v-if="btnStatus2">直接入住</i-button>
                        <i-button @click.stop="saveOrder(false)" v-if="btnStatus2">预定</i-button>
                    </i-col>
                </i-row>
            </div>
        </i-drawer>
        <i-drawer v-model="detailOrderDrawerStatus" title="订单详情" width="890px">
            <order-detail ref="orderDetail" @success="search"></order-detail>
        </i-drawer>
        <i-dialog v-model="settingDialog" :title="title" size="small">
            <i-form :model="settingForm" label-width="50px">
                <i-form-item label="房号:">
                    <span>{{settingForm.name}}</span>
                </i-form-item>
                <i-form-item label="时间:" prop="date">
                    <i-row>
                        <i-col :span="11">
                            <i-date-picker v-model="settingForm.date[0]" type="date" style="width:100%"></i-date-picker>
                        </i-col>
                        <i-col :span="2" style="text-align:center">-</i-col>
                        <i-col :span="11">
                            <i-date-picker v-model="settingForm.date[1]" type="date" style="width:100%"></i-date-picker>
                        </i-col>
                    </i-row>     
                </i-form-item>
                <i-form-item label="原因:" prop="reason">
                    <i-input v-model="settingForm.reason" type="textarea"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button v-if="settingState == 'edit'" type="primary" @click.stop="cancelSetting2" style="float:left">{{settingForm.type == 'REPAIR' ? '取消维修' : '取消停用'}}</i-button>
                <i-button @click.stop="settingDialog = false">取消</i-button>
                <i-button type="primary" @click.stop="saveSetting">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="personDialog" title="入住人" size="small">
            <i-form :model="personForm" ref="personForm" label-width="0px">
                <i-row :gutter="10">
                    <div v-for="(item, itemIndex) in personForm.personList" class="person-item" :key="itemIndex">
                        <i-col :span="2" style="line-height:36px;">
                            <i class="el-icon-arrow-right" style="font-size:12px" @click.stop="handleExpand($event)"></i>
                        </i-col>
                        <i-col :span="4">
                            <i-form-item :prop="'personList[' + itemIndex + '].name'">
                                <i-input v-model="item.name" placeholder="姓名"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'personList[' + itemIndex + '].cardType'">
                                <i-select v-model="item.cardType" placeholder="证件类型">
                                    <i-option v-for="(option, index) in dict['CertType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'personList[' + itemIndex + '].cardValue'">
                                <i-input v-model="item.cardValue" placeholder="证件号码"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="5">
                            <i-form-item :prop="'personList[' + itemIndex + '].phone'">
                                <i-input v-model="item.phone" placeholder="手机号码"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="3">
                            <i class="el-icon-delete" @click.stop="removePerson(itemIndex)" style="line-height:40px;cursor: pointer;"></i>
                        </i-col>
                        <i-col :span="24" class="second-row" style="padding:0 !important">
                            <i-col :span="2" style="line-height:36px;">&nbsp;</i-col>
                            <i-col :span="4">
                                <i-form-item :prop="'personList[' + itemIndex + '].country'">
                                    <i-select v-model="item.country" placeholder="国籍">
                                        <i-option v-for="(option, index) in dict['Country']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'personList[' + itemIndex + '].sex'">
                                    <i-select v-model="item.sex" placeholder="性别">
                                        <i-option v-for="(option, index) in dict['Sex']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'personList[' + itemIndex + '].birthday'">
                                    <i-date-picker style="width:100%" type="date"  v-model="item.birthday" placeholder="生日"></i-date-picker>
                                </i-form-item>
                            </i-col>
                            <i-col :span="5">
                                <i-form-item :prop="'personList[' + itemIndex + '].carNo'">
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
                <i-button @click.stop="personDialog = false">取消</i-button>
                <i-button type="primary" @click.stop="savePerson">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="incomeDialog" title="新增收款" size="small">
            <div>
                <i-form :model="incomeForm" ref="incomeForm" label-width="80px">
                    <i-row :gutter="10">
                        <i-col :span="12">
                            <i-form-item label="收支类别:" prop="receivePayType">
                                <i-select v-model="incomeForm.receivePayType">
                                    <i-option v-for="(option, index) in dict['DayRentReceivePayType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="款项类型:" prop="moneyType">
                                <i-select v-model="incomeForm.moneyType">
                                    <i-option v-for="(option, index) in dict['DayRentMoneyType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="收款渠道:" prop="receiveChannelType">
                                <i-select v-model="incomeForm.receiveChannelType">
                                    <i-option v-for="(option, index) in dict['ReceivableChannel']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>
                                </i-select>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item :label="incomeForm.receivePayType == 'RECEIVE' ? '收款金额:' : '退款金额:'" prop="money">
                                <i-input v-model="incomeForm.money"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item :label="incomeForm.receivePayType == 'RECEIVE' ? '收款日期:' : '退款日期:'" prop="operatorTime">
                                <i-date-picker v-model="incomeForm.operatorTime" type="date" style="width:100%"></i-date-picker>
                            </i-form-item>
                        </i-col>
                        <i-col :span="12">
                            <i-form-item label="交易流水号:" prop="thirdStreamNo">
                                <i-input v-model="incomeForm.thirdStreamNo"></i-input>
                            </i-form-item>
                        </i-col>
                        <i-col :span="24">
                            <i-form-item label="备注:" prop="remark">
                                <i-input v-model="incomeForm.remark"></i-input>
                            </i-form-item>
                        </i-col>
                    </i-row>
                </i-form>
                <div class="divider">费用明细</div>
                <i-table :data="costData">
                    <i-table-column prop="roomNo" label="房号" width="80"></i-table-column>
                    <i-table-column label="入离时间" width="120" inline-template>
                        <div>
                            <div>{{new Date(row.startTime).Format('yyyy-MM-dd')}}</div>
                            <div>{{new Date(row.endTime).Format('yyyy-MM-dd')}}</div>
                            <div>{{row.day + '晚'}}</div>
                        </div>
                    </i-table-column>
                    <i-table-column prop="status" label="状态" width="70"></i-table-column>
                    <i-table-column prop="totalPrice" label="订单金额" width="80"></i-table-column>
                    <i-table-column label="已实收" width="80" inline-template>
                        <div>0</div>
                    </i-table-column>
                    <i-table-column prop="price" :label="incomeForm.receivePayType == 'RECEIVE' ? '本次入账': '本次退还'" width="90" inline-template>
                        <i-input v-model="row.price"></i-input>
                    </i-table-column>
                </i-table>
            </div>
            <span slot="footer">
                <i-button @click.stop="incomeDialog = false">取消</i-button>
                <i-button type="primary" @click.stop="saveBusiness">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="businessDialog" title="商户实收" size="small">
            <div>
                <i-button type="text" @click.stop="addBusiness">添加</i-button>
                <i-table :data="businessData">
                    <i-table-column label="收支类型" inline-template>
                        <div>{{_getDictName('DayRentReceivePayType', row.receivePayType)}}</div>
                    </i-table-column>
                    <i-table-column label="款项类型" inline-template>
                        <div>{{_getDictName('DayRentMoneyType', row.moneyType)}}</div>
                    </i-table-column>
                    <i-table-column label="收支渠道" inline-template>
                        <div>{{_getDictName('ReceivableChannel', row.receiveChannelType)}}</div>
                    </i-table-column>
                    <i-table-column prop="money" label="金额"></i-table-column>
                    <i-table-column prop="createDate" label="收付日期"></i-table-column>
                    <i-table-column prop="createName" label="操作人"></i-table-column>
                    <i-table-column label="操作" inline-template>
                        <div>
                            <i-button type="text" class="btn-table-operate" @click.stop="businessEdit(row)">编辑</i-button>
                            <i-button type="text" class="btn-table-operate" @click.stop="businessDel(row)">删除</i-button>
                        </div>
                    </i-table-column>
                </i-table>
            </div>
        </i-dialog>
    </div>
</template>
<script>
import houseService from 'service/house';
import {mapState , mapMutations} from 'vuex';

import orderDetail from './orderDetail.vue';
import orderCancel from './orderCancel.vue';

export default {
    name:'hi-calender',
    components:{orderDetail, orderCancel},
    props:{
        apartmentid:{
            type: String,
            default: ''
        },
        content:{
            type: String,
            default: ''
        },
        contenttype:{
            type: String,
            default: ''
        },
        columnWidth:{
            type:String | Number,
            default:120
        },
    },
    data(){
        return {
            data:[],
            headers:[],
            countList:[],
            orders:[],
            dateIndex:'',
            roomTypeIndex:'',
            roomIndex:'',
            searchForm:{
                apartmentInfoId:'',
                startDate:'2019-05-01',
                selectType:'',
                typeValue:'',
                roomTypeId:'',
                current:1,
                size:100
            },
            selectDate:'',
            filter:'',
            addOrderDrawerStatus:false,
            detailOrderDrawerStatus:false,
            form:{
                apartmentInfoId:'',
                prepayName:'',
                prepayPhone:'',
                source:'',
                thirdOrderNum:'',
                info:[],
                dayRentRoomOrderFinanceVOs:[],
                isDirectCheckin: false,
                remark:''
            },
            roomTypeList:[],
            orderDayList:[],
            personForm:{
                personList:[],
            },
            personActive:0,
            personDialog:false,
            incomeDialog:false,
            incomeForm:{
                receivePayType:'RECEIVE',
                moneyType:'',
                receiveChannelType:'',
                money:'',
                operatorTime:'',
                thirdStreamNo:'',
                remark:'',
                payments:[]
            },
            costData:null,
            businessDialog:false,
            businessData:[],
            title:'设置停用',
            settingState:'add',
            settingDialog:false,
            settingForm:{
                name:'',
                roomIds:[],
                type:'',
                value:'',
                date:[],
                startTime:'',
                endTime:'',
                reason:''
            },
            repairDialog:false,
            orderDetailStatus:'',
            orderCancelStatus:false,
            btnStatus:false,
            btnStatus2:false
        }
    },
    watch:{
        'selectDate'(val){
            if (val) {
                this.dates[0] = this.$moment(val).format('YYYY-MM-DD');
                this.dates[1] = this.$moment(val).add('months', 1).format('YYYY-MM-DD');
                this.getRoomData();
            }
        },
        'apartmentid'(val){
            this.searchForm.apartmentInfoId = this.apartmentid;
            this.search();
        },
        'addOrderDrawerStatus'(val){
            if (val) {
                this.$nextTick(() => {
                    let d = new Date();
                    
                    if (this.$moment(this.form.info[0].date).isBefore(d)) {
                        this.btnStatus = true;
                    } else {
                        this.btnStatus = false;
                    }

                    if (this.$moment(this.form.info[0].date).isAfter(d)) {
                        this.btnStatus2 = true;
                    } else {
                        this.btnStatus2 = false;
                    }
                    if (d.Format('yyyy-MM-dd')  == new Date(this.form.info[0].date).Format('yyyy-MM-dd')) {
                        this.btnStatus = false;
                        this.btnStatus2 = true;
                    }
                });
            }
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
       
        handleExpand(event){
            let $el = event.target.parentElement.parentElement;
            if ($el.className.indexOf('active') > 0) {
                $el.setAttribute('class', 'person-item');
            } else {
                $el.setAttribute('class', 'person-item active');                
            }
        },
        getOrderStyle(item){
            let start = item.start;
            let end = item.end;
            if (this.$moment(start).isBefore(this.dates[0]) ) {
                start = this.dates[0];
            }

            if (this.$moment(end).isAfter(this.dates[1])) {
                end = this.$moment(this.dates[1]).add(1, 'days');
            }

            var el = document.querySelector('.hi-calender-day[room="' + new Date(start).Format('yyyy-MM-dd') + item.roomId + '"]');    
            if (el) {
                var left = (el.offsetLeft + 1) + 'px';
                var top = (el.offsetTop + 1) + 'px';
                start = this.$moment(start);
                end = this.$moment(end);
                var days = Math.abs(start.diff(end, 'days'));
                return {left:left, top:top, width: (100 * (days || 1) - 3) + 'px'};
            }
            return {display:'none'}
        },
        getOrderClass(item){
            let res = '';
            switch (item.dayRentOrderStatus) {
                case 'CHECKIN':
                    res = 'blue';
                    break;
                case 'EMPTY':
                    res = 'blue';
                    break;    
                case 'LEAVE':
                    res = 'gray';
                    break;
                case 'PREPAY':
                    res = 'green';
                    break;  
                case 'DIRTY':
                    res = 'green';
                    break;                
            }
            
            return res;
        },
        search(){
            this.filter = '';
            this.getRoomData();
            this.getRoomOrderDay();
            this.getRoomDropList();
        },
        getRoomData(){
            this.LOADING(true);
            let params =  JSON.parse(JSON.stringify(this.searchForm));
            params.startDate = this.dates[0];
            params.apartmentInfoId = this.apartmentid;
            params.selectType = this.contenttype;
            params.typeValue = this.content;
            houseService.getRoomMonthList(params).then(res => {
                this.LOADING(false);
                if (res.code == 0) {
                    this.$set(this, 'headers', res.data.headers);
                    this.$set(this, 'countList', res.data.remainingList);
                    this.$set(this, 'data', res.data.list);
                    this.$nextTick(() => {
                        this.$set(this, 'orders', res.data.orders || []);
                    });
                    
                }
            });
        },
        getWeekDay(num){
            let res = '';

            switch(num){
                case 1:
                    res = '一';
                    break;
                case 2:
                    res = '二';
                    break;
                case 3:
                    res = '三';
                    break;
                case 4:
                    res = '四';
                    break;
                case 5:
                    res = '五';
                    break;
                case 6:
                    res = '六';
                    break;
                case 7:
                    res = '日'; 
                    break;                       
            }       

            return res;
        },
        handleScroll(e){
            this.$refs.calenderHeader.scrollLeft = e.target.scrollLeft;
            this.$refs.calenderLeftList.scrollTop = e.target.scrollTop;
        },
        houseChange(val){
            this.dateIndex = val;
        },
        filterStatus(item){
            if (!this.filter) {
                return true;
            }
            return (this.filter == item.roomTypeName);
        },
        openDetail(item){

            this.detailOrderDrawerStatus = true;
            this.$nextTick(() => {
                this.$refs['orderDetail'].getDetail(this.apartmentid, item.orderId, item.orderInfoId, item.roomId);
            });
        },
        addRoomInfo(roomItems, roomTypes, rooms){
             this.form.info.push({
                date:roomItems.date,
                startTime:'',
                endTime:'',
                roomId:rooms.roomId,
                totalPrice:'',
                priceList: [],
                priceStatus:false,
                status:'',
                price:0,
                type:[roomTypes.roomTypeId, rooms.roomId],
                day:'',
                roomNo:'',
                checkInMemberList:[]
            });
        },
        openAddOrder(roomItems, roomTypes, rooms){
            if (this.addOrderDrawerStatus == false) {
                this.$set(this.form, 'info', []);
            }
            this.addOrderDrawerStatus = true;
            this.$nextTick(() => {
                this.addRoomInfo(roomItems, roomTypes, rooms);
            });
        },
        handleCellClick(roomItems,  roomTypes, rooms){
            switch(roomItems.status){
                case 'EMPTY':
                    this.openAddOrder(roomItems, roomTypes, rooms);
                    break;
                case 'DIRTY':
                    this.openAddOrder(roomItems, roomTypes, rooms);
                    break;   
                case 'LEAVE':
                    this.openAddOrder(roomItems, roomTypes, rooms);
                    break;     
                case 'LOCK':
                    this.getRoomStatusDetail(rooms.roomId,roomItems.date, roomItems.status, roomTypes.roomTypeName + ' ' + rooms.roomName ,'设为停用');
                    break;
                case 'REPAIR':
                    this.getRoomStatusDetail(rooms.roomId,roomItems.date, roomItems.status, roomTypes.roomTypeName + ' ' + rooms.roomName ,'设为维修');
                    break;    
            }
        },
        handleDayEnter(roomTypeIndex, roomIndex, dateIndex ){
            this.dateIndex = dateIndex;
            this.roomTypeIndex = roomTypeIndex,
            this.roomIndex = roomIndex;            
        },
        handleDayLeave(){
            this.dateIndex = '';
            this.roomTypeIndex = '',
            this.roomIndex = '';
        },
        getRoomStatusDetail(roomId, day, status, name, title){
            this.LOADING(true);

            let params = {
                roomId:roomId,
                day:day,
                status:status
            }

            houseService.getRoomStatusDetail(params).then(res => {
                this.LOADING(false);

                if (res.code === 0) {

                    this.settingState = 'edit';
                    this.title = title;
                    this.settingForm.name = name;
                    this.settingForm.roomIds = [res.data.roomId];
                    this.settingForm.type = status;
                    this.settingForm.value = false;
                    this.settingForm.date[0] = res.data.startTime;
                    this.settingForm.date[1] = res.data.endTime;
                    this.settingForm.reason = res.data.reason;
                    this.settingDialog = true;

                }
            });
        },
        setRoomStatus(room, status, id ,flg, start, end, reason){
            this.LOADING(true);

            let params = {
                roomIds: id,
                value:flg,
                type: status,
                startTime: start,
                endTime:end,
                reason:reason
            }

            houseService.setRoomStatus(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.getRoomData();
                    this.$message.success('设置成功');
                }
            });
        },
        openSetting(roomItems, rooms, roomTypes, status , flg, title ){
            this.settingForm.name = roomTypes.roomTypeName + ' ' + rooms.roomName;
            this.settingForm.roomIds = [rooms.roomId];
            this.settingForm.type = status;
            this.settingForm.value = flg;
            this.settingForm.date[0] = roomItems.date;
            this.settingForm.date[1] = '';
            this.title = title;
            this.settingState = 'add';
            this.settingDialog = true;
        },
        saveSetting(){
            this.LOADING(true);
            let params = JSON.parse(JSON.stringify(this.settingForm));
            params.startTime = params.date[0];
            params.endTime = params.date[1];

            houseService.setRoomStatus(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.getRoomData();
                    this.$message.success('设置成功');
                }
            });
        },
        setRoomHStatus(child){
            let params = {
                type: !child.isDirty ? 'DIRTY' : 'EMPTY',
                value: true,
                startTime:new Date().Format('yyyy-MM-dd'),
                endTime:new Date().Format('yyyy-MM-dd'),
                roomIds:[child.roomId],
                reason:''
            }

            houseService.setRoomStatus(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.search();
                    this.$message.success('设置成功');
                }
            });
            console.log('set room health status')
        },
        cancelSetting2(){
            let msg = this.settingForm.type == 'REPAIR' ? '确定取消此次维修吗?' : '确定取消此次停用吗?';
            this.$confirm(msg, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(res => {
                this.LOADING(true);

                let params = JSON.parse(JSON.stringify(this.settingForm));    
                params.startTime = params.date[0];
                params.endTime = params.date[1];            
                params.value = false;

                houseService.setRoomStatus(params).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.getRoomData();
                        this.$message.success('设置成功');
                    }
                });
            }).catch(() => {});
        },
        cancelSetting(roomItems, rooms, roomTypes, status, flg, title){
            this.$confirm(title, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(res => {
                this.LOADING(true);
                let params = JSON.parse(JSON.stringify(this.settingForm));                
                params.roomIds = [rooms.roomId];
                params.type = status;
                params.value = flg;
                params.startTime = roomItems.date;
                params.endTime = roomItems.date;

                houseService.setRoomStatus(params).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.getRoomData();
                        this.$message.success('设置成功');
                    }
                });
            }).catch(() => {});
        },
        handleRightMenu(roomItems, roomTypes, rooms ,event){
            let list = [];
            if (roomItems.status == 'EMPTY' || roomItems.status == 'DIRTY') {
                list.push({
                    label:'设为维修', handler: () => { this.openSetting(roomItems, rooms, roomTypes , 'REPAIR', true, '设为维修') }
                });
                list.push({
                    label:'设为停用', handler: () => { this.openSetting(roomItems, rooms, roomTypes , 'LOCK', true, '设为停用') }
                });
            }

            if (roomItems.status == 'REPAIR'){
                list.push({
                    label:'取消维修', handler: () => { this.cancelSetting(roomItems, rooms, roomTypes , 'REPAIR', false, '确定取消此次维修吗?') }
                });
            }

            if (roomItems.status == 'LOCK') {
                list.push({
                    label:'取消停用', handler: () => { this.cancelSetting(roomItems, rooms, roomTypes , 'LOCK', false, '确定取消此次停用吗?') }
                })
            }

            this.$showContextMenu(list, event);
        },
        addRoom(){
            this.form.info.push({
                date:'',
                startTime:'',
                endTime:'',
                roomId:'',
                totalPrice:'',
                priceList:[],
                priceStatus:false,
                status:'',
                price:0,
                type:[],
                day:'',
                roomNo:'',
                checkInMemberList:[]
            });
        },
        getRoomNo(id){
            let res = '';
            this.roomTypeList.forEach(list => {
                list.children.forEach(item => {
                    if (item.value == id) {
                        res = item.label;
                    }
                });
            });
            return res;
        },
        handleDateChange(val, index){
            if (this.$moment(val).isBefore(new Date)) {
                this.form.info[index].status = '已预订';
            } else {
                this.form.info[index].status = '已入住';                
            }
            this.getDayPrice(index);
        },
        handleRoomChange(val, index){
            this.form.info[index].roomId = val[1];
            this.form.info[index].roomNo = this.getRoomNo(this.form.info[index].roomId);
            this.getDayPrice(index);
        },
        handleDayChange(val, index){
            this.getDayPrice(index);
        },
        getDayPrice(index){
            let item = this.form.info[index];

            if (!item.roomId) {
                return;
            }

            if (!item.date) {
                return;
            }

            if (!item.day) {
                return;
            }
            let params = {
                roomId: item.roomId,
                startDate: new Date(item.date).Format('yyyy-MM-dd'),
                days: item.day
            }

            this.form.info[index].startTime = this.$moment(item.date).format('YYYY-MM-DD');
            this.form.info[index].endTime =  this.$moment(item.date).add(item.day, 'days').format('YYYY-MM-DD')
            this.LOADING(true);
            houseService.getRoomDayPrice(params).then(res => {
                this.LOADING(false);
                if (res.code === 0){
                    let price = 0;
                    res.data.dayPrices.forEach(i => {
                        price += i.price;
                    });
                    this.$set(this.form.info[index], 'priceList', res.data.dayPrices);
                    this.form.info[index].totalPrice = price;
                }
            });
        },
        handleDayPriceChange(val, itemIndex){
            let item = this.form.info[itemIndex];
            let price = 0;
            item.priceList.forEach(i => {
                price += parseFloat(i.price);
            });

            this.form.info[itemIndex].totalPrice = price;
        },
        removeRoom(index){
            this.form.info.splice(index, 1);
        },
        addPerson(index){
            this.personActive = index;
            let list = JSON.parse(JSON.stringify(this.form.info[index]['checkInMemberList']));
            this.$set(this.personForm, 'personList', list);
            this.personDialog = true;
        },
        addPersonItem(){
            this.personForm.personList.push({
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
            this.personForm.personList.splice(index, 1);
        },
        savePerson(){
            let list = JSON.parse(JSON.stringify(this.personForm.personList));
            this.$set(this.form.info[this.personActive], 'checkInMemberList', list);
            this.personDialog = false;
        },
        addBusiness(){
            let data = JSON.parse(JSON.stringify(this.form.info));
            this.$set(this, 'costData', data);
            this.incomeDialog = true;
        },
        saveBusiness(){
            let params = JSON.parse(JSON.stringify(this.incomeForm));
            params['createDate'] = new Date().Format('yyyy-MM-dd');
            params['createName'] = this.account.userName;
            params['payments'] = this.costData;
            this.form.dayRentRoomOrderFinanceVOs.push(params);
            this.businessData.push(params);
            this.incomeDialog = false;
        },
        businessEdit(){

        },
        businessDel(){

        },
        getRoomDropList(){
            let apartmentid = this.apartmentid;
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
        getRoomOrderDay(){
            houseService.getRoomOrderDay({apartmentInfoId: this.apartmentid}).then(res => {
                if (res.code === 0) {
                    this.$set(this, 'orderDayList', res.data.selections)
                }
            })
        },
        showPriceList(itemIndex){
            console.log(itemIndex);
            this.form.info[itemIndex].priceStatus = true;
        },
        saveOrder(flg){
            let params = JSON.parse(JSON.stringify(this.form));
            params.apartmentInfoId = this.apartmentid;
            params.isDirectCheckin = flg;
            houseService.addOrder(params).then(res => {
                if (res.code === 0) {
                    this.$message.success('操作成功');
                    this.search();
                }
            });
        },
        _getDictName(dictName, key){
            let res = '';
            this.dict[dictName].forEach(item => {
                if (key == item.dictKey) {
                    res = item.dictValue;
                }
            });

            return res;
        }
    },
    computed:{
        ...mapState(['dict', 'account']),
        width(){
            return (this.headers.length + 2) * parseInt(this.columnWidth) + 'px';
        },
        dates(){
            let res = [this.$moment().add(-1, 'days').format('YYYY-MM-DD'), this.$moment().add(-1, 'days').add(1, 'months').add(-1, 'days').format('YYYY-MM-DD')];
            return res;
        },
        totalPrice(){
            let res = 0 ;
            this.form.info.forEach(item => {
                res += parseFloat(item.totalPrice || 0);
            });

            return res;
        }
    },
    mounted(){
      
    }
}
</script>
<style lang="scss" scoped>
.hi-calender{
    position: relative;
    height: 100%;

    table{
        border-collapse: collapse;
        border-spacing: 0;
    }

    .hi-calender-picker-wrap{
        position: absolute;
        width: 180px;
        background: #fff;

        .hi-calender-date-picker{
            height: 60px;
            line-height: 60px;
            border:1px solid #ddd;
            position: relative;
            padding:0 16px;
            color:#333;
            cursor: pointer;

            .el-icon-date{
                position: absolute;
                right: 16px;
                top:24px;
                z-index: 1;
            }

            .el-date-editor{
                position: absolute;
                left: 0;
                top:0;
                right: 0;
                bottom: 0;
                width: 100%;
                height: 60px;
                opacity: 0;
                z-index: 2;
                cursor: pointer;

            }
        }

        .hi-calender-select{
            height: 45px;
            border:1px solid #ddd;
            border-top:none;
            // padding-top: 10px;
        }
    }

    .hi-calender-header{
        position: absolute;
        left: 180px;
        right: 0;
        top:0;
        overflow-x: hidden;
        overflow-y: scroll;
        

        .hi-calender-header-table{
            width: 100%;
            table-layout: fixed;

            .header-table-item{
                width: 100px;
                background: #fff;
            }

            .header-table-item-date{
                height: 59px;
                border-right:1px solid #ddd;
                border-top:1px solid #ddd;
                display: flex;
                align-items: center;
                flex-direction: column;
                justify-content: center;
                color:#333;

                &.holiday{
                    color:#FF0030;
                }

                &.active{
                    background: #f5f5f5;
                }
            }

            .header-table-item-rooms{
                height:46px;
                line-height: 45px;
                border-right:1px solid #ddd;
                border-top:1px solid #ddd;
                border-bottom: 1px solid #ddd;
                background: #f5f5f5;
                font-weight: normal;
            }
        }
    }

    .hi-calender-body{
        position: absolute;
        top:105px;
        bottom: 0;
        width: 100%;

        .hi-calender-left-list{
            margin-bottom:8px;
            will-change: transform;
            transform: translateZ(0);
            position: absolute;
            top:0;
            left: 0;
            bottom: 0;
            width: 200px;
            overflow: hidden;

            .hi-calender-left-item{
                position: relative;
                width: 100%;
                height: auto;
                background: #fff;

                .hi-calender-left-item-name{
                    width: 80px;
                    top:0;
                    left: 0;
                    bottom: 0;
                    position: absolute;
                    display: flex;
                    align-items: center;
                    flex-direction: column;
                    justify-content: center;
                    border-right: 1px solid #ddd;
                    border-bottom: 1px solid #ddd;
                    border-left: 1px solid #ddd;
                    color: #333;
                }

                .hi-calender-left-item-children{
                    display: inline-block;
                    vertical-align: top;
                    width: 100px;
                    margin-left: 80px;
                    height: auto;
                    border-right: 1px solid #ddd;
                }

                .hi-calender-left-item-room{
                    position: relative;
                    height: 70px;
                    line-height: 70px;
                    text-align: center;
                    border-bottom: 1px solid #ddd;
                    font-size: 14px;
                    cursor: pointer;
                    // display: flex;
                    // flex-direction: column;
                    // justify-content: center;
                    // align-items: center;

                    &.active{
                        background: #f5f5f5;

                    }

                    .tag{
                        width: 52px;
                        padding:0 12px;
                        line-height: 20px;
                        color: #fff;
                        border-radius: 11px;
                        background: #e3bc86;
                        display: block;
                        margin: 0 auto;
                        margin-top: -25px;
                    }

                    .operate{
                        position: absolute;
                        left: 0;
                        right: 0;
                        top: 0;
                        bottom: 0;
                        background: rgba(50,157,255,.2);
                        display: none;
                        transition: display .5s;
                        color: #fff;

                        a{
                            padding:0 12px;
                            line-height: 20px;
                            color: #fff;
                            border-radius: 11px;
                            background: #e3bc86;
                            display: inline-block;
                            margin-top:45px;

                            &.dirty{
                                background: #329dff
                            }
                        }
                    }

                    &:hover{
                        .operate{
                            display: block;
                        }
                    }
                }
            }
        }

        .hi-calender-status-list{
            will-change: transform;
            transform: translateZ(0);
            position: absolute;
            left:180px;
            right: 0;
            top:0;
            bottom: 0;
            overflow: scroll;

            .order-item{
                position: absolute;
                height: 67px;
                padding:5px 0 0 10px;
                color: #fff;

                .source{
                    font-size:16px;
                    line-height: 29px;
                }

                .user-name{
                    font-size: 12px;
                }

                &.green{
                    background: #21d9b1;
                }

                &.blue{
                    background: #329dff;
                }

                &.gray{
                    background: #ccc;
                }
            }
        }

        .hi-calender-status-table{
            width: 100%;
            table-layout: fixed;

            tr{
                display: table-row;
            }

            td{
                position: relative;
                height: 70px;
                width: 100px;
                background: #fff;
                text-align:center;
                line-height:69px;
                font-size:14px;
                color:#c3c2c2;
                cursor: pointer;


                div{
                    height: 100%;
                    border-bottom: 1px solid #ddd;
                    border-right: 1px solid #ddd;

                    &:hover{
                        background: rgba(50,157,255,.2);
                    }
                }
                
            }
        }
    }
    
    .divider{
        border-left:4px solid #329dff;
        padding-left:15px;
        margin:15px 0;
        font-weight: bold
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

    .business-item{
        padding:10px 20px !important;
        position: relative;
        border:1px solid #ddd;
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
}
</style>
