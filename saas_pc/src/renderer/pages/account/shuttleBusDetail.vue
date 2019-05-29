<template>
<!-- 班车详情页面 -->
  <div class="account-main">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'mission' }">{{companyName}}</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'mission'}">任务</el-breadcrumb-item>
          <el-breadcrumb-item>查看详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="section-container">
      <div class="section-line">
        <h3 class="section-tt">订单信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">订单编号：{{orderInfo.order_num}}</span>
            <span class="block">订单类型：班车</span>
          </p>
          <p class="line">
            <span class="block">出行信息：{{orderInfo.travel_begin_at}}-{{orderInfo.travel_end_at}}</span>
          </p>
        </div>
      </div>
      <div class="section-line" v-if="orderInfo.bus_order_cars.length" v-for="(item,index) in orderInfo.bus_order_cars" :key="index" >
        <h3 class="section-tt">行程信息</h3>
        <div class="section-con" v-if="item.date_travels"  v-for="(dateItem,dateIndex) in item.date_travels" :key="dateIndex">
          <p class="line">
            <span class="block">开始时间：{{dateItem.begin_at_time}}</span>
            <span class="block">结束时间：{{dateItem.end_at_time}}</span>
            <span class="sportTime" @click="chekcSportTime(10000,10000,dateItem.cycle_date_pc,dateItem.cycle_date)">点击查看运行周期</span>
          </p>
          <p class="line">
            <span class="block">起点：{{dateItem.travels.travel_from_place}}</span>
            <span class="block">终点：{{dateItem.travels.travel_to_place}}</span>
          </p>
          <p class="line" v-if="dateItem.travels.travel_pathway.length">
            <span
              class="block"
              v-if="dateItem.travels.travel_pathway"
              v-for="(pathWay,pathWayIndex) in dateItem.travels.travel_pathway"
              :key="pathWayIndex"
            >途经点：{{pathWay.travel_from_place}}</span>
          </p>
          <p class="line">
            <span class="block">行程：{{dateItem.travels.travel_content}}</span>
          </p>
        </div>
        <div class="section-con" v-if="item.driver_cars"  v-for="(placeItem,placeIndex) in item.driver_cars" :key="placeIndex">
          <p class="line">
            <span class="block">出行车辆：{{placeItem.car_number}}<span class="source-sign" v-if="placeItem.car_source == 2">外援</span></span>
            <span :class="controlColor(placeItem.driver_status)" class="block">订单状态：{{placeItem.driver_status_text}}</span>
            <span class='block' v-if="placeItem.car_source == 2">车队名称：{{placeItem.out_company}}</span>
          </p>
          <p class="line">
            <span class="block">司机姓名：{{placeItem.driver_name}}</span>
            <span class="block">联系方式：{{placeItem.driver_mobile}}</span>
          </p>
          <p class="line" v-if="placeItem.vice_drivers" v-for="(voiceDriver,index) in placeItem.vice_drivers" :key="'voiceDriver'+index">
            <span class="block">司机姓名：{{voiceDriver.driver_name}}</span>
            <span class="block">联系方式：{{voiceDriver.driver_mobile}}</span>
          </p>
          <p class="line">
            <span class="block" v-if="placeItem.car_source == 1">现收款：{{placeItem.my_collection_money}}</span>
            <span class="block" v-if="placeItem.car_source == 2">代收款：{{placeItem.collection_money}}</span>
            <span class="block" v-if="placeItem.car_source == 2">外援费用：{{placeItem.foreign_money}}</span>
            <span class="block">司机备注信息：{{placeItem.remark_to_driver}}</span>
          </p>
        </div>
      </div>
      <div class="section-line" v-if="orderInfo.travels.length" v-for="(travels,index) in orderInfo.travels" :key="'travels'+index">
        <h3 class="section-tt">{{travels.travel_at}}车辆{{travels.car_number}}出行信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">实际用车公里数：{{travels.practical_km}}</span>
            <span class="block">实际用车时间：{{travels.practical_travel_at}}</span>
            <span class="block">客户签字：<span class='lookImg' @click="checkImg(travels.image)">点击查看</span></span>
          </p>
          <p class="line">
            <span class="block">司机评价：{{travels.score}}星</span>
          </p>
          <p class="line">
            <span class="block">行程：{{travels.travel_content}}</span>
          </p>
        </div>
      </div>
      <div class="section-line" v-if="orderInfo.real_travels.length" v-for="(realTravels,index) in orderInfo.real_travels" :key="'real_travels'+index">
        <h3 class="section-tt">{{realTravels.travel_at}}车辆{{realTravels.car_number}}出行信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">起点公里数：{{realTravels.start_km}}</span>
            <span class="block">终点公里数：{{realTravels.end_km}}</span>
          </p>
          <p class="line">
            <span class="block">行程：{{realTravels.travel_content}}</span>
          </p>
        </div>
        <h3 class="section-tt">自定义字段</h3>
        <div class="section-con">
          <p class="line">
            <span class="block" v-if="realTravels.definable_fields.length>0" v-for="(itemMeg,indexMeg) in realTravels.definable_fields" :key="indexMeg"> 
              {{itemMeg.name}}:
              <span v-if="itemMeg.type_id!=6">
                {{itemMeg.value}}
              </span>
              <span v-if="itemMeg.type_id==6&&itemMeg.values.length>0" v-for="(img,imgIndex) in itemMeg.values" :key="imgIndex">
                <img class="dialogImg" @click="checkImg(img)" :src="img" alt="">
              </span>
            </span>
          </p>
        </div>
      </div>
      <div class="section-line">
        <h3 class="section-tt">客户信息</h3>
        <div class="section-con">
          <p class="line">单位名称：{{orderInfo.customer_company}}</p>
          <p class="line">
            <span class="block">联系人：{{orderInfo.customer_name}}</span>
            <span class="block">联系方式：{{orderInfo.customer_mobile}}</span>
          </p>
          <p class="line">
            <span class="block">乘客姓名：{{orderInfo.tour_guide_name}}</span>
            <span class="block">联系方式：{{orderInfo.tour_guide_mobile}}</span>
            <span class="block">团号：{{orderInfo.team_num}}</span>
          </p>
        </div>
      </div>
      <div class="section-line">
        <h3 class="section-tt">价格信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">价格信息：{{orderInfo.money}}元</span>
            <span class="block">已收：{{orderInfo.deposit || 0}}元</span>
            <span class="block">
              未收：
              <span
                :class="orderInfo.receivable > 0? 'red' : orderInfo.receivable < 0 ? 'green' : ''"
              >{{orderInfo.receivable || 0}}</span>元
            </span>
          </p>
          <p class="line">
            <span class="block">结账方式：{{orderInfo.pay_type==1?'现结':'挂账'}}</span>
            <span class="block">
              结款状态：
              <span
                :class="orderInfo.bill_status==1? 'red' : ''"
              >{{orderInfo.bill_status==1?'未结款':'已结款'}}</span>
            </span>
            <span class="block">备用金：{{orderInfo.imprest}}元</span>
          </p>
          <p class="line">
            <span class="block">业务员：{{orderInfo.salesman_name}}</span>
            <span class="block">是否开票：{{orderInfo.need_invoice==1?'需要':'不需要'}}</span>
            <span class="block" v-if="orderInfo.need_invoice">开票金额：{{orderInfo.invoice_money}}元</span>
          </p>
          <p class="line">
            <span class="block">客户返佣：{{orderInfo.rebate}}元</span>
            <span class="block">业务员返佣：{{orderInfo.salesman_rebate}}元</span>
          </p>
          <p class="line">
            <span class="block">备注：{{orderInfo.remark}}</span>
          </p>
        </div>
      </div>
      <div class="section-line defindeValue" v-if="orderInfo.definable_fields.length">
        <h3 class="section-tt">自定义字段</h3>
        <div class="section-con">
          <div class="line">
            <div class="block" v-for="(field, index) in orderInfo.definable_fields" :key="'field'+index">
              <div v-if="field.type_id == 6" class="custom-block">
                {{field.name}}：
                <img class="custom-img"  @click="checkImg(src)" v-if="src" :src="src" v-for="(src,index) in field.value.split(',')" :key="'src'+index">
              </div>
              <div v-else  class="custom-block">
                {{field.name}}：
                {{field.value}}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="btn-wrap">
        <a
          href="javascript:void(0);"
          class="btn"
          v-if="orderInfo.can_edit && orderInfo.finished == 0 "
          @click="finishOrder"
        >结束任务</a>
        <router-link
          v-if="orderInfo.can_edit"
          class="btn btn-nobg btn-nobg-theme"
          :to="{name: 'addMission', query: {order_id:order_id}}"
        >修改任务</router-link>
        <a
          href="javascript:void(0);"
          class="btn btn-right"
          v-if="orderInfo.can_cancel"
          @click="deleteOrder"
        >删除</a>
        <a href="javascript:void(0);" @click="jumpCarDetails()" class="btn btn-right">车辆支出</a>
      </div>
    </div>
       <calendar ref="calendar"  :cycleShow.sync="cycleShow"></calendar>
       <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>

<script>
import calendar from '@/components/calendar'
import imgDialog from '@/components/imgDialog'
export default {
  data () {
    return {
      orderInfo: {},
      order_id: null,
      pick_order_cars: { driver_cars: [{}], travels: { travel_pathway: [] } },
      send_order_cars: { driver_cars: [{}], travels: { travel_pathway: [] } },
      companyName: null,
      customer_id: null,
      cycleShow:false,
      imgShow:false,
      imgs:'',
      carNumber:[]
    }
  },
  created () {
    this.watchQueryVal()
    this.getOrderDetail()
  },
  methods: {
    watchQueryVal () {
      this.order_id = Number.parseFloat(this.$route.query.order_id)
    },
    getOrderDetail () {
      if (this.$route.query.saas_order_id > 0) {
        this.order_id = this.$route.query.saas_order_id
      }
      let params = {
        'order_id': this.order_id
      }
      this.$axios.get(this.$httpUrl.orderDetail, { params: params }).then(res => {
        if (res != false) {
          this.orderInfo = res
          if(this.orderInfo.real_travels.length){
            for(let i=0;i<this.orderInfo.real_travels.length;i++){
              if(this.orderInfo.real_travels[i].definable_fields.length){
                for(let j=0;j<this.orderInfo.real_travels[i].definable_fields.length;j++){
                  if(this.orderInfo.real_travels[i].definable_fields[j].type_id==6){
                    this.orderInfo.real_travels[i].definable_fields[j].values = this.orderInfo.real_travels[i].definable_fields[j].value.split(',')
                  }
                }
              }   
            }
          }
          this.companyName = res.customer_company
          this.customer_id = res.customer_id
        }
      })
    },
    controlColor (status) {
      let colorSty = 'status-fail'
      if (status == 0) {
        colorSty = 'status-wait'
      } else if (status == 1) {
        colorSty = 'status-ok'
      }
      return colorSty
    },
    finishOrder () {
      this.$confirm('是否确认结束任务', {
        type: 'warning'
      }).then(() => {
        let params = {
          order_num: this.orderInfo.order_num
        }
        this.$axios.get(this.$httpUrl.orderFinish, { params: params }).then(res => {
          if (res != false) {
            this.$message({
              message: '任务结束成功可安排新任务',
              type: 'success'
            })
            this.getOrderDetail()
          }
        })
      }).catch(() => {
        console.log("取消删除")
      });
    },
    deleteOrder () {
      this.$confirm('是否确认删除改任务', {
        type: 'warning'
      }).then(() => {
        let params = {
          'order_id': this.order_id
        }
        this.$axios.get(this.$httpUrl.orderCancel, { params: params }).then(res => {
          if (res != false) {
            this.$message({
              message: '订单删除成功',
              type: 'success'
            })
            if (this.source == 'bill') {
              this.$router.go(-1)
            } else {
              this.$router.push({ name: 'mission' })
            }
          }
        })
      }).catch(() => {
        console.log("取消删除")
      });
    },
    chekcSportTime(index,dateIndex,cycle_dates_pc,date){
      if(cycle_dates_pc==undefined){
        cycle_dates_pc=[]
      }
      this.cycleShow = true
      this.$nextTick(()=>{
        this.$refs.calendar.showToggle(index,dateIndex,cycle_dates_pc,date)
      })
    },
    checkImg(img){
    //  console.log(img)
     this.imgShow =true
     this.$nextTick(()=>{
       this.$refs.imgDialog.showToggle(img)
     })
   },
   jumpCarDetails(){
    //  console.log(this.orderInfo)
     for(let i=0;i<this.orderInfo.bus_order_cars.length;i++){
         for (let j = 0; j < this.orderInfo.bus_order_cars[i].driver_cars.length; j++) {
           if(this.orderInfo.bus_order_cars[i].driver_cars[j].car_number){
             this.carNumber.push(this.orderInfo.bus_order_cars[i].driver_cars[j].car_number)
           }   
         }
     }
     this.$router.push({name:'AddSpending',query:{order_num:this.orderInfo.order_num,useTime:this.orderInfo.travel_begin_at+'至'+this.orderInfo.travel_end_at,carNumber:this.carNumber.join(','),order_id:this.orderInfo.id}})
   }
  },
  components:{
    calendar,
    imgDialog
  }
}
</script>
<style lang="less" scoped>
@import "./orderDetails.less";
.account-main{
  .sportTime{
    width: 150px;
    text-align: center;
    border-radius: 4px;
    background: #0db592;
    color:#fff;
    cursor: pointer;
  }
  .block{
    height:19px;
  }
  .dialogImg{
    width: 40px;
    height: 40px;
    margin-right: 10px;
    display:inline-block;
  }
  .section-container{
    .defindeValue{
      .section-con{
        .line{
          padding:10px 0;
        }
      }
    }
  }
}

</style>