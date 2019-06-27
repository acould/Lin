<template>
    <div class="strategy-list hi-calender" style="min-height:208px" :style="{height:height}">
        <div class="hi-calender-picker-wrap">
            <div class="hi-calender-date-picker">
                
            </div>
        </div>
        <div class="hi-calender-header" ref="calenderHeader">
            <table class="hi-calender-header-table">
                <tr>
                    <th v-for="(header, headerIndex) in headers" :key="headerIndex" class="header-table-item">
                        <div class="header-table-item-date" :class="{weekend: header.weekday == 6 || header.weekday == 7}">
                            <p>{{header.date + '(' + getWeekDay(header.weekday) + ')'}}</p>
                            <p>{{header.holidayName}}</p>
                        </div>
                    </th>
                </tr>
            </table>
        </div>
        <div class="hi-calender-body">
            <div class="hi-calender-left-list" ref="calenderLeftList">
                <div class="hi-calender-left-item" v-for="(item, itemIndex) in strategyList" :key="itemIndex + 'left'">
                    <div class="hi-calender-left-item-name">
                        <p style="text-align:center">{{item.apartmentRoomTypeName}}</p>
                    </div>
                    <div class="hi-calender-left-item-children">
                        <div class="hi-calender-left-item-room" @click.stop="openStrategy(item.apartmentRoomTypeId)">基础价格</div>
                    </div>
                </div>
            </div>
            <div class="hi-calender-status-list" ref="statusList" @scroll="handleScroll">
                <table class="hi-calender-status-table">
                    <tbody>
                        <tr v-for="(item, itemIndex) in strategyList" :key="itemIndex + 'tr'">
                            <template v-for="(child,childIndex) in item.items">
                                <td class="hi-calender-day" :key="itemIndex  + 'day' + childIndex" @click.stop="openDayDialog(item.apartmentRoomTypeId, child)">
                                    <div>{{child.price}}</div>
                                </td>
                            </template>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <i-dialog v-model="dayDialogStatus" title="修改价格" size="small" append-to-body>
            <i-form :model="dayForm" label-width="100px" ref="dayForm">
                <i-form-item prop="price" label="房价格:" :rules="[{required:true, message:'请输入房价格', trigger:'blur'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model="dayForm.price"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="dayDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveDayPrice">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="setPriceDialogStatus" title="设置价格策略" append-to-body>
            <div>
                <div class="table-list">
                    <i-button type="primary" style="margin-bottom:10px" @click.stop="normalType = 'add';addNormalPriceDialogStatus = true">新增</i-button>
                    <i-table :data="normalData"  style="width:100%" height="300">
                        <i-table-column prop="name" label="名称" width="150" align="center"></i-table-column>      
                        <i-table-column prop="startDate" label="开始时间" width="150" align="center"></i-table-column>
                        <i-table-column prop="endDate" label="结束时间" width="150" align="center"></i-table-column>
                        <i-table-column prop="mondayPrice" label="周一" width="80" align="center"></i-table-column>
                        <i-table-column prop="tuesdayPrice" label="周二" width="80" align="center"></i-table-column>
                        <i-table-column prop="wednesdayPrice" label="周三" width="80" align="center"></i-table-column>
                        <i-table-column prop="thursdayPrice" label="周四" width="80" align="center"></i-table-column>
                        <i-table-column prop="fridayPrice" label="周五" width="80" align="center"></i-table-column>
                        <i-table-column prop="saturdayPrice" label="周六" width="80" align="center"></i-table-column>
                        <i-table-column prop="sundayPrice" label="周日" width="80" align="center"></i-table-column>
                        <i-table-column label="操作" width="150" align="center" inline-template>
                            <div>
                                <i-button type="text" class="btn-table-operate" @click.stop="editNormal(row)">编辑</i-button>
                                <i-button type="text" class="btn-table-operate" @click.stop="delStrategy(row, 'normal')">删除</i-button>
                            </div>
                        </i-table-column>
                    </i-table>
                </div>
                <p style="margin-top:20px">节假日特殊设置</p>
                <div class="table-list">
                    <i-button type="primary" style="margin-bottom:10px;" @click.stop="holidayType = 'add';addHoliydayPriceDialogStatus = true">新增</i-button>
                    <i-table :data="holidayData"  style="width:100%" height="300">
                        <i-table-column prop="name" label="名称" width="150" align="center"></i-table-column>      
                        <i-table-column prop="startDate" label="开始时间" width="150" align="center"></i-table-column>
                        <i-table-column prop="endDate" label="结束时间" width="150" align="center"></i-table-column>
                        <i-table-column prop="price" label="价格" align="center"></i-table-column>
                        <i-table-column label="操作" width="150" align="center" inline-template>
                            <div>
                                <i-button type="text" class="btn-table-operate" @click.stop="editHoliday(row)">编辑</i-button>
                                <i-button type="text" class="btn-table-operate" @click.stop="delStrategy(row)">删除</i-button>
                            </div>
                        </i-table-column>
                    </i-table>
                </div>
            </div>
        </i-dialog>
        <i-dialog v-model="addNormalPriceDialogStatus" title="" size="small" append-to-body>
            <i-form :model="normalForm" label-width="100px" ref="normalForm">
                <i-form-item label="名称描述:" prop="name" :rules="[{required:true, message:'请输入名称描述', trigger:'blur'},{max:20, message:'最多不能超过20个汉字'}]">
                    <i-input v-model="normalForm.name"></i-input>
                </i-form-item>
                <i-form-item prop="date" label="生效周期:" :rules="[{required:true, message:'请选择生效周期', trigger:'change', type:'array'}]">
                    <i-date-picker type="daterange" v-model="normalForm.date"  style="width:100%"></i-date-picker>
                </i-form-item>
                <i-form-item prop="mondayPrice" label="周一价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.mondayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="tuesdayPrice" label="周二价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.tuesdayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="wednesdayPrice" label="周三价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.wednesdayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="thursdayPrice" label="周四价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.thursdayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="fridayPrice" label="周五价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.fridayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="saturdayPrice" label="周六价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.saturdayPrice"></i-input>
                </i-form-item>
                <i-form-item prop="sundayPrice" label="周日价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur', type:'number'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model.number="normalForm.sundayPrice"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="addNormalPriceDialogStatus =false">取消</i-button>
                <i-button type="primary" @click.stop="saveNormal">保存</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="addHoliydayPriceDialogStatus" title="" size="small" append-to-body>
            <i-form :model="holidayForm" label-width="100px" ref="holidayForm">
                <i-form-item label="名称描述:" prop="name" :rules="[{required:true, message:'请输入名称描述', trigger:'blur'},{max:20, message:'最多不能超过20个汉字'}]">
                    <i-input v-model="holidayForm.name"></i-input>
                </i-form-item>
                <i-form-item prop="date" label="生效周期:" :rules="[{required:true, message:'请选择生效周期', trigger:'change', type:'array'}]">
                    <i-date-picker type="daterange" v-model="holidayForm.date" style="width:100%"></i-date-picker>
                </i-form-item>
                <i-form-item prop="price" label="价格:" :rules="[{required:true, message:'请输入价格', trigger:'blur'},{pattern:/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/, message:'请输入正确格式'}]">
                    <i-input v-model="holidayForm.price"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="addHoliydayPriceDialogStatus =false">取消</i-button>
                <i-button type="primary" @click.stop="saveHoliday">保存</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import houseService from 'service/house';
import {mapMutations} from 'vuex';

export default {
    props:{
        apartmentid:{
            type:String,
            default:'4028853C6A9BE441016A9BE441BD0000'
        }
    },
    data(){
        return {
            strategyList:[],
            headers:[],
            dayForm:{
                settingId:'',
                date:'',
                price:''
            },
            dayDialogStatus:false,
            setPriceDialogStatus:false,
            normalData:null,
            holidayData:null,
            roomTypeId:'',
            current:1,
            size:100,
            current2:1,
            addNormalPriceDialogStatus:false,
            normalForm:{
                id:'',
                roomTypeId:'',
                name:'',
                date:['', ''],
                startDate:'',
                endDate:'',
                mondayPrice:'',
                tuesdayPrice:'',
                wednesdayPrice:'',
                thursdayPrice:'',
                fridayPrice:'',
                saturdayPrice:'',
                sundayPrice:''
            },
            addHoliydayPriceDialogStatus:false,
            holidayForm:{
                id:'',
                roomTypeId:'',
                name:'',
                date:['',''],
                startDate:'',
                endDate:'',
                price:''
            },
            normalType:'add',
            holidayType:'add'
        }
    },
    watch:{
        addNormalPriceDialogStatus(val){
            if (!val) {
                this.$refs['normalForm'].resetFields();
            }
        },
        addHoliydayPriceDialogStatus(val){
            if (!val) {
                this.$refs['holidayForm'].resetFields();
            }
        },
        setPriceDialogStatus(val){
            if (!val) {
                this.getList();
            }
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        getList(){
            this.LOADING(true);
            let params = {
                apartmentInfoId: this.apartmentid,
                startDate: new Date().Format('yyyy-MM-dd')
            }
            houseService.getStrategyList(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {         
                    this.$set(this, 'strategyList', res.data.list);
                    this.$set(this, 'headers', res.data.headers);
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
        openStrategy(id){
            this.roomTypeId = id;
            this.getNormalData();
            this.getHolidayData();
            this.setPriceDialogStatus = true;
        },
        openDayDialog(id, item){
            this.dayForm.settingId = id;
            this.dayForm.date = item.date;
            this.dayForm.price = item.price;
            this.dayDialogStatus = true;
        },
        saveDayPrice(){
            this.$refs['dayForm'].validate(valid => {
                if (valid) {
                    this.LOADING(true);
                    let params = JSON.parse(JSON.stringify(this.dayForm));
                    houseService.saveStrategyPrice(params).then(res => {
                         this.LOADING(false);
                        if (res.code === 0) {
                            this.$message.success('操作成功');
                            this.dayDialogStatus = false;
                        }
                    });
                }
            });
        },
        getNormalData(){
            this.LOADING(true);
            let params = {
                roomTypeId:this.roomTypeId,
                current:this.current,
                size:this.size
            }
            houseService.getStrategyNormalList(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'normalData', res.data);
                }
            });
        },
        getHolidayData(){
            this.LOADING(true);
            let params = {
                roomTypeId:this.roomTypeId,
                current:this.current2,
                size:this.size
            }
            houseService.getStrategyHolidayList(params).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'holidayData', res.data);
                }
            });
        },
        delStrategy(row, type){
            this.$confirm('确定要删除这个价格策略吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                houseService.deleteStrategyPrice(row.id).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        if (type == 'normal') {
                            this.getNormalData();
                        } else {
                            this.getHolidayData();
                        }
                        this.$message.success('操作成功');
                    }
                });
            }).catch(() => {});
        },
        editNormal(row){

            this.addNormalPriceDialogStatus = true;            
            this.$nextTick(() => {
                this.$refs['normalForm'].resetFields();
                this.normalType = 'edit';
                this.normalForm.id = row.id;
                this.normalForm.name = row.name;
                this.normalForm.date[0] = new Date(row.startDate);
                this.normalForm.date[1] = new Date(row.endDate);
                this.normalForm.mondayPrice = row.mondayPrice;
                this.normalForm.tuesdayPrice = row.tuesdayPrice;
                this.normalForm.thursdayPrice = row.thursdayPrice;
                this.normalForm.wednesdayPrice = row.wednesdayPrice;
                this.normalForm.fridayPrice = row.fridayPrice;
                this.normalForm.saturdayPrice = row.saturdayPrice;
                this.normalForm.sundayPrice = row.sundayPrice;
            });
        },
        editHoliday(row){
            this.addHoliydayPriceDialogStatus = true;        
            this.$nextTick(() => {
                this.$refs['holidayForm'].resetFields();
                this.holidayType = 'edit';
                this.holidayForm.id = row.id;
                this.holidayForm.name = row.name;
                this.holidayForm.date[0] = row.startDate;
                this.holidayForm.date[1] = row.endDate;
                this.holidayForm.price = row.price;
            });
        },
        saveNormal(){
            this.$refs['normalForm'].validate(valid => {
                if (valid) {
                    this.LOADING(true);
                    let params = JSON.parse(JSON.stringify(this.normalForm));

                    params.startDate = new Date(params.date[0]).Format('yyyy-MM-dd'),
                    params.endDate = new Date(params.date[1]).Format('yyyy-MM-dd');
                    
                    params.roomTypeId = this.roomTypeId;


                    houseService.saveNoramlPrice(params).then(res => {
                        this.LOADING(false);
                        if (res.code == 0) {
                            this.getNormalData();
                            this.$message.success('操作成功');
                            this.addNormalPriceDialogStatus=false;
                        }
                    });
                }
            });
        },
        saveHoliday(){
            this.$refs['holidayForm'].validate(valid => {
                if (valid) {
                    this.LOADING(true);
                    let params = JSON.parse(JSON.stringify(this.holidayForm));

                    params.startDate = new Date(params.date[0]).Format('yyyy-MM-dd'),
                    params.endDate = new Date(params.date[1]).Format('yyyy-MM-dd');
                    params.roomTypeId = this.roomTypeId;

                    houseService.saveHolidayPrice(params).then(res => {
                        this.LOADING(false);
                        if (res.code == 0) {
                            this.getHolidayData();
                            this.$message.success('操作成功');
                            this.addHoliydayPriceDialogStatus=false;
                        }
                    });
                }
            });
        }
    },
    computed:{
        height(){
            return (this.strategyList.length * 60 + 60 + 8) + 'px'
        }
    },
    mounted(){
        this.getList();
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
        width: 200px;
        background: #fff;

        .hi-calender-date-picker{
            height: 60px;
            line-height: 60px;
            text-align: center;
            border:1px solid #ddd;
        }

        .hi-calender-select{
            height: 60px;
            border:1px solid #ddd;
            border-top:none;
        }
    }

    .hi-calender-header{
        position: absolute;
        left: 200px;
        right: 0;
        top:0;
        overflow-x: hidden;
        overflow-y: scroll;
        

        .hi-calender-header-table{
            width: 100%;
            table-layout: fixed;

            .header-table-item{
                width: 120px;
                background: #fff;
            }

            .header-table-item-date{
                height: 60px;
                border-right:1px solid #ddd;
                border-top:1px solid #ddd;
                border-bottom: 1px solid #ddd;
                transform: translateZ(0);
                display: flex;
                align-items: center;
                flex-direction: column;
                justify-content: center;

                &.weekend{
                    color:red;
                }

                p{
                    line-height: 25px;
                }
            }
        }
    }

    .hi-calender-body{
        position: absolute;
        top:60px;
        bottom: 0;
        width: 100%;

        .hi-calender-left-list{
            padding-bottom:8px;
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
                    width: 100px;
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
                    border-left:1px solid #ddd;
                    font-size: 14px;
                    color: #333;
                }

                .hi-calender-left-item-children{
                    display: inline-block;
                    vertical-align: top;
                    width: 100px;
                    margin-left: 100px;
                    height: auto;
                }

                .hi-calender-left-item-room{
                    height: 60px;
                    line-height: 60px;
                    text-align: center;
                    border-bottom: 1px solid #ddd;
                    border-right:1px solid #ddd;
                    font-size: 14px;
                    color:#20a0ff;
                    cursor: pointer;
                }
            }
        }

        .hi-calender-status-list{
            will-change: transform;
            transform: translateZ(0);
            position: absolute;
            left:200px;
            right: 0;
            top:0;
            bottom: 0;
            overflow: scroll;
        }

        .hi-calender-status-table{
            width: 100%;
            table-layout: fixed;

            tr{
                display: table-row;
            }

            td{
                position: relative;
                height: 60px;
                width: 120px;
                background: #fff;
                text-align:center;
                line-height:59px;
                font-size:16px;
                color:#999;
                cursor: pointer;

                div{
                    height: 100%;
                    border-bottom: 1px solid #ddd;
                    border-right: 1px solid #ddd;
                }
                
            }
        }
    }
    // table{
    // overflow: auto;

    //     border:1px solid #ddd;
    //     background: #fff;

    //     td,
    //     th{
    //         border:1px solid #ddd;
    //         height: 100px;
    //     }

    //     tbody{
    //         td{
    //             text-align: center;
    //         }
    //     }
    // }
}

</style>

