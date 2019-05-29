<template>
  <div class="contacts-main">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'driverOrigin' }">通讯录</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'driverOrigin' }">司机</el-breadcrumb-item>
          <el-breadcrumb-item>司机详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="contacts-details">
        <div class="takespace"></div>
        <table border="1" class="table-con">
          <tr>
            <td>姓名</td>
            <td>{{driverInfo.name}}</td>
          </tr>
          <tr>
            <td>联系方式</td>
            <td>{{driverInfo.mobile}}</td>
          </tr>
          <tr>
            <td>驾驶车辆</td>
            <td>
              <span v-for="(car, index) in driverInfo.car_info" :key="index">{{car.car_number}}&nbsp;</span>
            </td>
          </tr>
          <tr>
            <td>驾驶证编号</td>
            <td>{{driverInfo.driving_licence}}</td>
          </tr>
          <tr>
            <td>入职时间</td>
            <td>{{driverInfo.join_at}}</td>
          </tr>
          <tr>
            <td>紧急联系人</td>
            <td>{{driverInfo.urgent_name}}</td>
          </tr>
          <tr>
            <td>联系方式</td>
            <td>{{driverInfo.urgent_mobile}}</td>
          </tr>
          <tr v-for="(item,index) in driverInfo.definable_fields" :key="index">
            <td>{{item.name}}</td>
            <td v-if="item.type_id!=6">{{item.value}}</td>
            <td v-if="item.type_id==6">
              <span  v-for="(itemImg,index) in item.values" :key="index+'img'">
                <img class="definedImg" @click="checkImg(itemImg)"  :src="itemImg" alt="">
              </span>
              
            </td>
          </tr>
        </table>
        <div class="btn-wrap">
          <router-link :to="{name: 'increaseDriver',query: {driver_id: driverInfo.driver_id}}" class="btn">修改信息</router-link>
          <a href="javascript:void(0);" class="btn" @click="deleteDriver">删除司机</a>
        </div>
      </div>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>

<script>
import imgDialog from '@/components/imgDialog'
export default {
  name: 'driverDetails',
  data () {
    return {
      driverInfo: {},
      imgShow:false,
      imgs:''
    }
  },
  created () {
    this.getDriverDetail()
  },
  methods: {
    getDriverDetail () {
      let driver_id = this.$route.query.driver_id
      if (driver_id) {
        let params = {
          driver_id: driver_id
        }
        this.$axios.get(this.$httpUrl.driverInfo, { params: params }).then(res => {
          if (res != false) {
            this.driverInfo = res
            for(let i=0;i<this.driverInfo.definable_fields.length;i++){
              if(this.driverInfo.definable_fields[i].type_id==6){
                this.driverInfo.definable_fields[i].values=this.driverInfo.definable_fields[i].value.split(',')
              }
            }
          }
        })
      }
    },
    deleteDriver () {
      this.$confirm('是否删除该司机', {
        type: 'warning'
      }).then(() => {
        let driver_id = this.$route.query.driver_id
        if (driver_id) {
          let params = {
            driver_id: driver_id
          }
          this.$axios.get(this.$httpUrl.deleteDriver, { params: params }).then(res => {
            if (res != false) {
              this.$router.go(-1)
            }
          })
        }
      })
    },
    checkImg(img){
    //  console.log(img)
     this.imgShow =true
     this.$nextTick(()=>{
       this.$refs.imgDialog.showToggle(img)
     })
   }
  },
  components: {
    imgDialog
  }
}
</script>
<style lang ="less" scoped>
@import './contactsDetails.less';
.contacts-main{
  .definedImg{
    width: 40px;
    height: 40px;
    margin-left: 15px;
    vertical-align: middle;
  }
}
  
</style>
