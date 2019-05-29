<template>
  <div>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'spending'}">支出</el-breadcrumb-item>
          <el-breadcrumb-item>费用详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="order-info">
        <div class="info-t">
          <div class="left-side">
            <span class="iconfont icon-dingdan"></span>
            <router-link tag="h3" class="order-num" :to="{name: 'accountDetails',query: {order_id:info.saas_order_id,source:'bill'}}">
              订单编号：
              <span class="green"> {{order_num}}</span>
             
              </router-link>
          </div>
          <div class="right-side">
            <div class="btns">
              <a v-if="info.audit_status == 0" href="javascript:void(0)" class="btn btn-fffbg" @click="orderHandler(info.fee_id,-1)">拒绝</a>
              <a v-if="info.audit_status == 0" href="javascript:void(0)" class="btn btn-bg" @click="orderHandler(info.fee_id,1)">通过</a>
            </div>
          </div>
        </div>
        <div class="info-b">
          <div class="info-list">
            <div class="col">
              <p class="line">上报时间：{{info.created_at}}</p>
              <p class="line">车牌号：{{info.car_number}}</p>
              <p class="line">油费：{{info.oil_money}}元</p>
              <p class="line">餐费：{{info.meal_money}}元</p>
              <p class="line">水费：{{info.water_money}}元</p>
              <p class="line">保养：{{info.maintain_money}}元</p>
              <p class="line">维修：{{info.repair_money}}元</p>         
            </div>
            <div class="col">
              <p class="line">审批时间：{{info.audit_at || '待审批'}}</p>
              <p class="line">使用日期：{{info.begin_at}}
                <span v-if="info.end_at">-{{info.end_at}}</span>
              </p>
              <p class="line">过路费：{{info.toll_money}}元</p>
              <p class="line">停车费：{{info.park_money}}元</p>
              <p class="line">住宿费：{{info.hotel_money}}元</p>            
              <p class="line">其他费用：{{info.other_money}}元</p>
            </div>
            <div class="col">
              <p class="line" v-for="(driver,driverIndex) in info.driver" :key="driverIndex">
                司机姓名：{{driver.driver_name}}联系方式：{{driver.driver_mobile}}
              </p>
              
            </div>
            <div class="col">
              
            </div>
          </div>
          <div>
            <span class="definedValue" v-for="(item,index) in info.definable_fields" :key='index'>
                {{item.name}}:
                <span v-if="item.type_id!=6">{{item.value}}</span>
                <span v-if="item.type_id==6" v-for="(imgItem,index) in item.values" :key="'img'+index">
                  <img @click="checkImg(imgItem)" :src="imgItem" alt="">
                </span>
            </span>
          </div>
            
          <p class="remark" v-if="info.remark">备注：{{info.remark}}</p>
          <p class="remark" v-if="info.audit_status == -1">审批失败理由：{{info.audit_remark}}</p>
          <div class="summary">
            <p>
              合计
              <span>{{info.total_money}}元</span>
            </p>
            <p>
              状态
              <span v-if="info.audit_status == 0">待审核</span>
              <span v-else-if="info.audit_status == 1">审核通过</span>
              <span v-else>审核失败</span>
            </p>
          </div>
        </div>
      </div>
      <div class="image-box">
        <h3>费用凭据</h3>
        <div class="image-wrap">
          <img  :src="imgSrc" alt=""  @click="checkImg(imgSrc)" v-for="(imgSrc, index) in info.images" :key="index">
        </div>
      </div>
      <div class='btn-wrap'>
        <router-link v-if="info.audit_status == 1" :to="{name: 'AddSpending',query:{fee_id:this.fee_id}}" class='btn'>修改</router-link>
        <a v-if="info.audit_status == 1" href="javascript:void(0);" class="btn btn-nobg" @click="deleteSpendingInfo">删除</a>
      </div>
    </div>
    <RefuseReasonBox @passReason="getReason" :send-order-info="sendOrderInfo" :refuse-reason-box-show.sync="refuseReasonBoxShow" />
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
import RefuseReasonBox from '@/components/RefuseReasonBox'
import imgDialog from '@/components/imgDialog'
export default {
  name: 'SpendingDetail',
  components: {
    RefuseReasonBox,
    imgDialog
  },
  data () {
    return {
      fee_id: null,
      order_num: null,
      info: {},
      refuseReasonBoxShow: false,
      sendOrderInfo: {},
      imgShow:false,
      imgs:''
    }
  },
  created () {
    this.getQueryParams()
    this.getOrderInfo()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      this.order_num = query.order_num
      this.fee_id = query.fee_id
    },
    getOrderInfo () {
      let params, url
      params = { id: this.fee_id }
      url = this.$httpUrl.feeDetail
      this.$axios.post(url, params).then(res => {
        if (res) {
          this.info = res
          for(let i=0;i<this.info.definable_fields.length;i++){
            if(this.info.definable_fields[i].type_id==6){
              this.info.definable_fields[i].values=this.info.definable_fields[i].value.split(',')
            }
          }
        }
      })
    },
    orderHandler (fee_id, type) {
      let params = { id: fee_id, type: type }
      if (type === -1) {
        this.sendOrderInfo = params
        this.refuseReasonBoxShow = true
      } else {
        this.$confirm('是否通过', {
          type: 'warning'
        }).then(() => {
          this.auditOrder(params)
        })
      }
    },
    getReason (obj) {
      this.refuseReasonBoxShow = false
      this.auditOrder(obj)
    },
    deleteSpendingInfo () {
      this.$confirm('是否删除', {
        type: 'warning'
      }).then(() => {
        let params = {
          id: this.fee_id,
          type: -2
        }
        this.auditOrder(params)
      })

    },
    auditOrder (params) {
      let url
      url = this.$httpUrl.feeAudit
      this.$axios.post(url, params).then(res => {
        if (res) {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          if (params.type == -2) {
            this.$router.go(-1)
          } else {
            this.getOrderInfo()
          }
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
  }
}
</script>
<style lang="less" scoped>
.main-content {
  margin-top: 15px;
  background-color: transparent;
  .order-info {
    padding: 20px 30px 0 30px;
    background-color: #fff;
    .info-t {
      overflow: hidden;
    }
    .btn-bg {
      margin-right: 0;
    }
    .left-side {
      float: left;
      margin-bottom: 20px;
      .icon-dingdan {
        color: @theme-color;
        font-size: 22px;
        vertical-align: -2px;
        margin-right: 14px;
      }
      .order-num {
        font-size: 20px;
        color: #000;
        font-weight: bold;
        display: inline-block;
        cursor: pointer;
      }
    }
    .right-side {
      float: right;
    }
    .info-b {
      position: relative;
      .info-list {
        margin-left: 42px;
        overflow: hidden;
      }
      .col {
        float: left;
        width: 33.33%;
        .line {
          margin-bottom: 10px;
        }
      }
      .summary {
        position: absolute;
        top: 10px;
        right: 0;
        p {
          float: left;
          text-align: right;
          color: #333;
          margin-left: 30px;
          span {
            margin-top: 10px;
            display: block;
            color: #000;
            font-size: 20px;
          }
        }
      }
    }
  }
  .remark {
    margin-left: 42px;
    padding-bottom: 16px;
    word-break: break-all;
  }
  .image-box {
    margin-top: 18px;
    background-color: #fff;
    h3 {
      height: 54px;
      line-height: 54px;
      border-bottom: 1px solid #e9e9e9;
      box-sizing: border-box;
      font-weight: bold;
      padding-left: 30px;
    }
    .image-wrap {
      margin: 25px 25px 0 25px;
      padding-bottom: 10px;
      overflow: hidden;
      img {
        float: left;
        width: 176px;
        height: 103px;
        margin-right: 13px;
        margin-bottom: 15px;
      }
    }
  }
  .btn-wrap {
    margin-top: 55px;
    text-align: center;
    .btn {
      display: inline-block;
      width: 154px;
      height: 36px;
      line-height: 36px;
      margin-right: 30px;
      &:last-of-type {
        margin-right: 0;
      }
    }
  }
  .definedValue{
    width: 40%;
    margin-left: 40px;
    display: inline-block;
    img{
      width: 25px;
      height: 25px;
      display: inline-block;
      margin-left: 10px;
      vertical-align: middle;
    }
  }
}
</style>