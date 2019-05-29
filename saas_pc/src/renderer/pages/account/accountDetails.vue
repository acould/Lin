<template>
  <div class="account-main">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb v-if="source=='bill'" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'revenue' }">{{lineText}}</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'revenueDetail',query: {customer_id:customer_id,companyName:companyName} }"
          >{{companyName}}</el-breadcrumb-item>
          <el-breadcrumb-item>查看详情</el-breadcrumb-item>
        </el-breadcrumb>
        <el-breadcrumb v-if="source=='mission'" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'mission' }">{{lineText}}</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'mission'}">任务</el-breadcrumb-item>
          <el-breadcrumb-item>查看详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="section-container">
      <div class="section-line">
        <h3 class="section-tt">行程信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">订单编号：{{orderInfo.order_num}}</span>
            <span class="block">订单类型：包车</span>
          </p>
          <p class="line">
            <span class="block">用车时间：{{orderInfo.travel_begin_at}}</span>
            <span class="block">预计结束时间：{{orderInfo.travel_end_at}}</span>
            <span class="block">集合地点：{{orderInfo.travel_from_place}}</span>
          </p>
          <div class="line trip-info">
            <span class="block">行程：</span>
            <p>{{orderInfo.travel_content}}</p>
          </div>
        </div>
      </div>
        <div
          class="section-line"
          v-if="orderInfo.travels.length"
          v-for="(travel, index) in orderInfo.travels"
          :key="'s'+index"
        >
          <h3 class="section-tt">{{travel.travel_at}}{{travel.car_number}}行程信息</h3>
          <div class="section-con">
            <p class="line">
              <span class="block">实际公里数：{{travel.practical_km}}</span>
              <span class="block">实际用车时间：{{travel.practical_travel_at}}</span>
              <span class="block">客户签字：
                <span class="lookImg" @click="checkImg(travel.image)">点击查看</span>
              </span>
            </p>
            <div class="line trip-info">
              <span class="block">司机评价：</span>
              <p>{{parseInt(travel.score)}}星</p>
            </div>
            <div class="line trip-info">
              <span class="block">行程：</span>
              <p>{{travel.travel_content}}</p>
            </div>
          </div>
        </div>
      <div
        class="section-line"
        v-if="orderInfo.real_travels.length"
        v-for="(truetravel, index) in orderInfo.real_travels"
        :key="'d'+index"
      >
        <h3 class="section-tt">{{truetravel.travel_at}}{{truetravel.car_number}}行程信息</h3>
        <div class="section-con">
          <p class="line">
            <span class="block">起点公里数：{{truetravel.start_km}}</span>
            <span class="block">终点公里数：{{truetravel.end_km}}</span>
            <span class="block">实际用车时间：{{truetravel.practical_travel_at}}</span>
          </p>
          <div class="line trip-info">
            <span class="block">行程：</span>
            <p>{{truetravel.travel_content}}</p>
          </div>
        </div>
        <h3 class="section-tt">自定义字段</h3>
        <div class="section-con">
          <p class="line"  v-if="truetravel.definable_fields.length">
            <span class="block" v-for="(itemMeg,indexMeg) in truetravel.definable_fields" :key="indexMeg"> 
              {{itemMeg.name}}:
              <span v-if="itemMeg.type_id!=6">
                {{itemMeg.value}}
              </span>
              <span v-if="itemMeg.type_id==6&&itemMeg.values.length" v-for="(img,imgIndex) in itemMeg.values" :key="imgIndex">
                <img class="dialogImg" @click="checkImg(img)" :src="img" alt="">
              </span>
            </span>
          </p>
        </div>
      </div>
      <div class="section-line" v-if="orderInfo.order_cars.length" v-for="(car, index) in orderInfo.order_cars" :key="index">
        <h3 class="section-tt">车辆和司机信息</h3>
        <router-link
          :to="{name: 'vehicleTrajectory',query:{order_car_id:car.id}}"
          class="color-green path"
          v-if="car.imei"
        >行车轨迹>></router-link>
        <div class="section-con">
          <p class="line">
            <span :class="controlColor(car.driver_status)" class="block">
              <span class="order_status">订单状态：</span>
              {{car.driver_status_text}}
            </span>
            <span v-if="car.car_source == 2" class="block">车队名称：{{car.out_company}}</span>
          </p>
          <p class="line">
            <span class="block">
              出行车辆：{{car.car_number}}
              <span class="source-sign" v-if="car.car_source == 2">外援</span>
            </span>
            <span class="block">开始时间：{{car.begin_at_date}} {{car.begin_at_time}}</span>
            <span class="block">结束时间：{{car.end_at_date}} {{car.end_at_time}}</span>
          </p>
          <p class="line" v-if="car.car_source == 1">
            <span class="block">实际开始时间：{{car.start_at}}</span>
            <span class="block">实际结束时间：{{car.finish_at}}</span>
          </p>
          <p class="line">
            <span class="block">实际用车时间：{{car.practical_time}}</span>
            <span class="block">起点：{{car.start_address}}</span>
            <span class="block">终点：{{car.end_address}}</span>
          </p>
          <p class="line" v-if="car.car_source == 2">
            <span class="block">外援费用：{{car.foreign_money}}元</span>
            <span class="block">代收款：{{car.collection_money}}元</span>
          </p>
          <p class="line">
            <span class="block">司机姓名：{{car.driver_name}}</span>
            <span class="block">联系方式：{{car.driver_mobile}}</span>
          </p>
          <p class="line" v-for="(driver, driverIndex) in car.vice_drivers" :key="driverIndex">
            <span class="block">司机姓名：{{driver.driver_name}}</span>
            <span class="block">联系方式：{{driver.driver_mobile}}</span>
          </p>
          <p class="line">
            <span v-if="car.car_source == 1" class="block">现收款：{{car.my_collection_money}}</span>
            <span class="block">司机备注信息：{{car.remark_to_driver}}</span>
          </p>
          <!-- <div class="line trip-info" v-else>
            <span class="block">司机备注信息：</span>
            <p>{{car.remark_to_driver}}</p>
          </div>-->
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
            <span class="block">未收：
              <span
                :class="orderInfo.receivable > 0? 'red' : orderInfo.receivable < 0 ? 'green' : ''"
              >{{orderInfo.receivable || 0}}</span>元
            </span>
          </p>
          <p class="line">
            <span class="block">结账方式：{{orderInfo.pay_type==1?'现结':'挂账'}}</span>
            <span class="block">结款状态：
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
      <div class="section-line" v-if="orderInfo.definable_fields.length">
        <h3 class="section-tt">自定义字段</h3>
        <div class="section-con">
          <div class="line">
            <div class="block" v-for="(field, index) in orderInfo.definable_fields" :key="'field'+index">
              <div v-if="field.type_id == 6" class="custom-block">
                {{field.name}}：
                <img class="custom-img"  @click="checkImg(src)"  v-if="src" :src="src" v-for="(src,index) in field.value.split(',')" :key="'src'+index">
              </div>
              <div v-else  class="custom-block">
                {{field.name}}：
                {{field.value}}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="btn-wrap" v-if="source=='bill'">
        <!-- <a href="javascript:void(0);" class="btn" v-if="orderInfo.bill_status == 1" @click="confirmPayed">确认结款</a> -->
        <!-- <a href="javascript:void(0);" class="btn btn-right" v-if="orderInfo.bill_status == 1" @click="popupModifyPriceBox">修改价格</a> -->
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
      </div>
      <div class="btn-wrap" v-if="source=='mission'">
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
    <modifyPriceBox
      :modifyPriceBoxShow.sync="modifyPriceBoxShow"
      @getNewMoney="getNewMoney"
      :orderId="order_id"
    ></modifyPriceBox>
    <imgDialog ref="imgDialog" :imgShow.sync="imgShow" :imgs="imgs"></imgDialog>
  </div>
</template>

<script>
import modifyPriceBox from '@/components/modifyPriceBox'
import imgDialog from '@/components/imgDialog'
export default {
  name: 'accountDetails',
  data () {
    return {
      modifyPriceBoxShow: false,
      companyName: null,
      customer_id: null,
      orderInfo: {},
      order_id: 0,
      source: '',
      lineText: '调度',
      imgShow: false,
      imgs: '',
      orderNumber: []
    }
  },
  created () {
    this.watchQueryVal()
    this.getOrderDetail()
    // alert(this.$route.query.saas_order_id)
  },
  methods: {
    popupModifyPriceBox () {
      this.modifyPriceBoxShow = true
    },
    watchQueryVal () {
      this.customer_id = this.$route.query.customerId
      this.companyName = this.$route.query.companyName
      this.order_id = Number(this.$route.query.order_id)
      this.source = this.$route.query.source
      if (this.source == 'bill') {
        this.lineText = '营收'
      }
    },
    getNewMoney (money) {
      this.orderInfo.money = money
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
          console.log(this.orderInfo)
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
    confirmPayed () {
      this.$confirm('是否确认结款', {
        type: 'warning'
      }).then(() => {
        let params = {
          'order_id': this.order_id
        }
        this.$axios.post(this.$httpUrl.billModifyStatus, params).then(res => {
          if (res != false) {
            this.$message({
              message: '结款操作成功',
              type: 'success'
            })
            this.orderInfo.bill_status = 2
          }
        })
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
    // 11.16点击查看图片
    checkImg (img) {
      //  console.log(img)
      this.imgShow = true
      this.$nextTick(() => {
        this.$refs.imgDialog.showToggle(img)
      })
    },
    jumpCarDetails () {
      for (let i = 0; i < this.orderInfo.order_cars.length; i++) {
        this.orderNumber.push(this.orderInfo.order_cars[i].car_number)
      }
      this.$router.push({ name: 'AddSpending', query: { order_num: this.orderInfo.order_num, useTime: this.orderInfo.travel_begin_at + '至' + this.orderInfo.travel_end_at, carNumber: this.orderNumber.join(','), order_id: this.orderInfo.id } })
    }
  },
  components: {
    modifyPriceBox,
    imgDialog
  },
}
</script>
<style lang ='less' scoped>
@import "../account/orderDetails.less";
.account-main {
  .order_status {
    color: black;
  }
  .dialogImg{
    width: 40px;
    height: 40px;
    margin-right: 10px;
    display:inline-block;
  }
}
</style>