<template>
  <div class="contacts-main">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'carOrigin',query: {type: car_source } }">车辆管理</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'carOrigin',query: {type: car_source } }"
          >{{car_source == 1 ? '自有车辆' : '外援车辆'}}</el-breadcrumb-item>
          <el-breadcrumb-item>车辆详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="car-details">
        <div class="takespace"></div>
        <table border="1" class="table-con">
          <tr>
            <td>车辆品牌</td>
            <td>{{carInfo.car_type}}</td>
          </tr>
          <tr>
            <td>车牌号</td>
            <td>{{carInfo.car_number}}</td>
          </tr>
          <tr>
            <td>座位数</td>
            <td>{{carInfo.seat_num}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 1">
            <td>GPS设备号</td>
            <td>{{carInfo.imei}}</td>
          </tr>
          <!-- <tr v-if="carInfo.car_source == 1">
            <td>GPS设备号</td>
            <td>{{carInfo.gps_mobile}}</td>
          </tr> -->
          <tr>
            <td>类型</td>
            <td>{{carInfo.car_source==1?'自有车辆':'外援车辆'}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 2">
            <td>车队名称</td>
            <td>{{carInfo.out_company}}</td>
          </tr>
          <tr>
            <td>司机姓名</td>
            <td>{{carInfo.driver_name}}</td>
          </tr>
          <tr>
            <td>联系方式</td>
            <td>{{carInfo.driver_mobile}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 1">
            <td>保养时间</td>
            <td>{{carInfo.next_maintain_at}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 1">
            <td>保险时间</td>
            <td>{{carInfo.next_insurance_at}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 1">
            <td>年审时间</td>
            <td>{{carInfo.next_annual_audit_at}}</td>
          </tr>
          <tr v-if="carInfo.car_source == 1">
            <td>公里数</td>
            <td>{{carInfo.run_km}}</td>
          </tr>
          <tr v-for="(item,index) in carInfo.definable_fields" :key="index">
            <td>{{item.name}}</td>
            <td v-if="item.type_id!=6">{{item.value}}</td>
             <td v-if="item.type_id==6">
               <span  v-for="(imgItem,index) in item.values" :key="index+'img'">
                 <img @click="checkImg(imgItem)" class="imgDetails" :src="imgItem" alt="">
               </span>
               
             </td>
          </tr>
        </table>
        <div class="btn-wrap" v-if="carInfo.car_source == 1">
          <router-link :to="{name: 'increaseCar',query: {car_id: carInfo.car_id}}" class="btn">修改信息</router-link>
          <a href="javaScript:void(0);" class="btn" @click="deleteCar">删除车辆</a>
        </div>
        <div class="btn-wrap" v-else>
          <a href="javascript:void(0);" class="btn" @click="handlerOutCar(carInfo)">修改</a>
          <a href="javascript:void(0);" class="btn btn-nobg" @click="deleteCar">删除</a>
        </div>
      </div>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync="imgShow" :imgs="imgs"></imgDialog>
  </div>
</template>

<script>
import imgDialog from '@/components/imgDialog'
export default {
  name: 'carDetails',
  data () {
    return {
      car_source: 1,
      carInfo: {},
      imgShow: false,
      imgs: '',
    }
  },
  created () {
    this.watchIsType()
    this.getCarDetail()
  },
  methods: {
    watchIsType () {
      let type = this.$route.query.type
      if (!type) {
        type = 1
      }
      this.car_source = type.toString()
    },
    getCarDetail () {
      let car_id = this.$route.query.car_id
      if (car_id) {
        let params = {
          car_id: car_id
        }
        this.$axios.get(this.$httpUrl.carInfo, { params: params }).then(res => {
          if (res != false) {
            res.car_source = res.car_source.toString()
            this.carInfo = res
            for(let i=0;i<this.carInfo.definable_fields.length;i++){
              if(this.carInfo.definable_fields[i].type_id==6){
                this.carInfo.definable_fields[i].values = this.carInfo.definable_fields[i].value.split(',')
              }
            }
          }
        })
      }
    },
    deleteCar () {
      this.$confirm('是否删除该车辆', {
        type: 'warning'
      }).then(() => {
        let car_id = this.$route.query.car_id
        if (car_id) {
          let params = {
            car_id: car_id
          }
          this.$axios.get(this.$httpUrl.deleteCar, { params: params }).then(res => {
            if (res != false) {
              this.$router.go(-1)
            }
          })
        }
      })
    },
    handlerOutCar (info) {
      let params = {};
      let queryParams = { customer_id: info.customer_id, companyName: info.out_company }
      Object.assign(params, queryParams, info)
      this.$router.push({ name: 'IncreaseOutsideCar', query: params })
    },
     // 11.16点击查看图片
    checkImg (img) {
      //  console.log(img)
      this.imgShow = true
      this.$nextTick(() => {
        this.$refs.imgDialog.showToggle(img)
      })
    },
  },
  components: {
    imgDialog
  }
}
</script>
<style lang ="less" scoped>
.contacts-main{
  .imgDetails{
    width: 50px;
    height: 50px;
    display: inline-block;
    margin-right: 10px;
  }
}

</style>
