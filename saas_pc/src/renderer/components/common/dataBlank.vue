<template>
  <div class="blank-wrap" id="blank-wrap">
    <div class="car" v-if="dataType == 'car'">
      <img :src="carSrc" alt="">
      <p>您还没有添加车辆哦~~~</p>
      <a href="javasscipt:void(0);" class="btn" @click="rtnLink()" v-if="nobtn">添加车辆</a>
    </div>
    <div class="account" v-else-if="dataType == 'account'">
      <img :src="accountSrc" alt="">
      <p v-if="!word">您还没有账单记录哦~~~</p>
      <p v-else>您还没有{{word}}哦~~~</p>
      <a href="javasscipt:void(0);" class="btn" @click="rtnLink()" v-if="nobtn">添加账单</a>
    </div>
    <div class="customer" v-else-if="dataType == 'customer'">
      <img :src="customerSrc" alt="">
      <p v-if="!word">您还没有添加客户哦~~~</p>
      <p v-else>您还没有添加{{word}}哦~~~</p>
      <a href="javasscipt:void(0);" class="btn" @click="rtnLink()" v-if="nobtn">添加客户</a>
    </div>
    <div class="driver" v-else-if="dataType == 'driver'">
      <img :src="driverSrc" alt="">
      <p>您还没有添加司机哦~~~</p>
      <a href="javasscipt:void(0);" class="btn" @click="rtnLink()" v-if="nobtn">添加司机</a>
    </div>
    <div class="order" v-else-if="dataType == 'order'">
      <img :src="orderSrc" alt="">
      <p>您还没有接单记录哦~~~</p>
    </div>
    <div class="order" v-else-if="dataType == 'photo'">
      <img :src="orderSrc" alt="">
      <p>您还没有图片哦~~~</p>
    </div>
    <div class="order" v-else-if="dataType == 'complaint'">
      <img :src="orderSrc" alt="">
      <p>您还没有投诉记录哦~~~</p>
    </div>
    <div class="mission" v-else>
      <img :src="missionSrc" alt="">
      <p>您还没有安排任务哦~~~</p>
      <a href="javasscipt:void(0);" class="btn" @click="rtnLink()" v-if="nobtn">添加任务</a>
    </div>
  </div>
</template>
<script>
import account from '@/assets/images/account.png'
import car from '@/assets/images/car.png'
import customer from '@/assets/images/customer.png'
import driver from '@/assets/images/driver.png'
import mission from '@/assets/images/mission.png'
import order from '@/assets/images/order.png'
export default {
  name: 'dataBlank',
  props: {
    dataType: String,
    pageType: String,
    missionQuery: Object,
    nobtn: true,
    word: '',
  },
  data () {
    return {
      accountSrc: account,
      carSrc: car,
      customerSrc: customer,
      driverSrc: driver,
      missionSrc: mission,
      orderSrc: order,
      hasCar: false
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      if (this.dataType != 'account' && this.dataType != 'mission') return
      this.$axios.get(this.$httpUrl.carCount).then(res => {
        if (res != false) {
          if (res.count) this.hasCar = true
        }
      })
    },
    rtnLink () {
      let dataType = this.dataType
      switch (dataType) {
        case 'car':
          this.$router.push({ name: 'increaseCar', query: { car_source: this.pageType } })
          break
        case 'customer':
          this.$router.push({ name: 'increaseCustomer' })
          break
        case 'driver':
          this.$router.push({ name: 'increaseDriver' })
          break
        default:
          if (!this.hasCar) {
            this.$message({ showClose: true, message: '请添加车辆', type: 'error' })
          } else {
            if (this.missionQuery && this.missionQuery.car_id) {
              let missionQuery = this.missionQuery
            } else if (this.missionQuery && this.missionQuery.customer_id) {
              this.$router.push({ name: 'addMission', query: this.missionQuery })
            } else {
              this.$router.push({ name: 'addMission' })
            }
          }
      }
    }
  }
}
</script>
<style lang ="less" scoped>
.blank-wrap {
  width: 222px;
  height: 180px;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  text-align: center;
  color: @color-grey;
  font-size: @fontsize-large-x;
  p {
    margin-top: 10px;
  }
  .btn {
    margin-top: 20px;
  }
}
</style>
