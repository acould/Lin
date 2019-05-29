<template>
  <div class="contacts-main customerDetails">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">支出</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'customerOrigin',query: {customerType:customerType} }">{{companyTypeTxt}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{customerInfo.company}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="order-info">
        <div class="info-t">
          <div class="left-side">
            <h3 class="order-num">基本信息</h3>
          </div>
        </div>
        <div class="info-b" v-if="customerType == 1">
          <div class="info-list">
            <div class="col">
              <p class="line">单位名称：{{customerInfo.company}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
            </div>
          </div>
          <p class="remark">联系地址：{{customerInfo.address}}</p>
        </div>
        <div class="info-b" v-else>
          <div class="info-list">
            <div class="col">
              <p class="line">车队名称：{{customerInfo.company}}</p>
              <p class="line">计调姓名：{{customerInfo.name}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
              <p class="line">联系地址：{{customerInfo.address}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">联系方式：{{customerInfo.mobile}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="container car-container">
        <div class="handler">
          <el-radio-group v-model="carParams.seat_num">
            <el-radio label="">全部</el-radio>
            <el-radio label="5,10">5-10座</el-radio>
            <el-radio label="11,25">11-25座</el-radio>
            <el-radio label="26,40">26-40座</el-radio>
            <el-radio label="41,70">41-70座</el-radio>
          </el-radio-group>
          <div class="inp">
            <el-input class="inp-search" suffix-icon="iconfont icon-shujuchaxun" v-model="keywords" placeholder="请输入车牌号"></el-input>
            <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
          </div>
        </div>
        <div class="table">
          <el-table key="car" v-if="tableData.length" :data="tableData" style="width: 100%">
            <el-table-column prop="car_number" label="车牌号">
            </el-table-column>
            <el-table-column prop="seat_num" label="座位数">
            </el-table-column>
            <el-table-column prop="driver_name" label="司机姓名">
            </el-table-column>
            <el-table-column prop="driver_mobile" label="联系方式">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btns">
                  <a href="javascript:void(0);" class="btn" @click="carHandler(scope.row)">选择</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
      <dataBlank :nobtn="false" v-if="!tableData.length && customerType == 1 && maxPage === 0" dataType="customer"></dataBlank>
      <dataBlank :nobtn="false" v-if="!tableData.length && customerType == 2 && maxPage === 0" dataType="car"></dataBlank>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'customerDetails',
  data () {
    return {
      companyTypeTxt: '',
      customerType: null,
      customerInfo: {},
      page: 1,
      maxPage: null,
      customer_id: '',
      keywords: '',
      carParams: {
        keywords: '',
        customer_id: '',
        seat_num: ''
      },
      travelParams: {
        customer_id: '',
        keywords: ''
      },
      tableData: []
    }
  },
  watch: {
    carParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    },
    travelParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    }
  },
  computed: {
    queryParams () {
      return { customer_id: this.customer_id, companyName: this.customerInfo.company }
    },
    ...mapState({
      spendingInfo: state => state.finance.spendingInfo
    })
  },
  created () {
    this.getQueryParams()
    this.getCustomerDetail()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      let customerType = query.customerType
      this.customerType = customerType
      this.customer_id = query.customer_id
      if (customerType == 1) {
        this.companyTypeTxt = '客户'
        this.travelParams.customer_id = query.customer_id
      } else {
        this.companyTypeTxt = '外援车队'
        this.carParams.customer_id = query.customer_id
      }
    },
    handlerCar (type, info) {
      let car_id = info.car_id
      if (type == 1) {
        let params = {};
        Object.assign(params, this.queryParams, info)
        this.$router.push({ name: 'IncreaseOutsideCar', query: params })
      } else {
        this.$confirm('是否确定删除该车辆', {
          type: 'warning'
        }).then(() => {
          let params = { car_id: car_id }
          this.$axios.get(this.$httpUrl.deleteCar, { params: params }).then(res => {
            if (res != false) {
              this.getTableData()
            }
          })
        })
      }
    },
    searchFrom () {
      let customerType = this.customerType
      let keywords = this.keywords
      if (customerType == 1) {
        Object.assign(this.travelParams, { keywords })
      } else {
        Object.assign(this.carParams, { keywords })
      }
    },
    getCustomerDetail () {
      let customer_id = this.$route.query.customer_id
      let params = {
        customer_id: customer_id
      }
      this.$axios.get(this.$httpUrl.customerInfo, { params: params }).then(res => {
        if (res != false) {
          this.customerInfo = res
        }
      })
    },
    getTableData () {
      let params, url
      let customerType = this.customerType
      params = { page: this.page }
      if (customerType == 1) {
        Object.assign(params, this.travelParams)
        url = this.$httpUrl.yardmenList
      } else {
        Object.assign(params, this.carParams)
        url = this.$httpUrl.fleetCarList
      }
      this.$axios.get(url, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getTableData()
    },
    ...mapActions([
      'refreshSpendingInfo',
    ]),
    carHandler (info) {
      let obj = { ...this.spendingInfo }
      Object.assign(obj, { saas_car_id: info.car_id.toString(), car_number: info.car_number })
      this.refreshSpendingInfo(obj)
      this.$router.go(-3)
    },
  }
}
</script>
<style lang ="less" scoped>
.contacts-main {
  height: 100%;
  background-color: #f5f5f5;
}
.main-content {
  padding-top: 20px;
  height: calc(100% - 80px);
  box-sizing: border-box;
  background-color: transparent;
  .order-info {
    padding: 20px 30px 0 30px;
    background-color: #fff;
    .info-t {
      overflow: hidden;
    }
    .btn-fffbg {
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
  #blank-wrap {
    top: 150px;
  }
  .container {
    margin: 10px 16px 0;
    background-color: #fff;
    padding: 0 10px;
    &.car-container {
      min-height: calc(100% - 180px);
      .handler {
        overflow: hidden;
        margin-left: 10px;
        & /deep/ .el-radio-group {
          display: inline-block;
          .el-radio + .el-radio {
            margin-left: 10px;
          }
          .el-radio .el-radio__input.is-checked .el-radio__inner {
            background-color: @theme-color;
            border-color: @theme-color;
          }
        }
        .inp {
          display: inline-block;
          margin-left: 10px;
          & /deep/ .inp-search {
            width: 160px;
            .el-input__inner {
              height: 32px;
              line-height: 32px;
            }
            .el-input__suffix {
              top: -4px;
            }
          }
          .btn-search {
            display: inline-block;
            margin: 0 20px 0 10px;
            height: 32px;
            line-height: 30px;
            width: 60px;
            box-sizing: border-box;
            text-align: center;
            border-radius: 4px;
            color: @color-dark-grey;
            font-size: @fontsize-medium;
            border: 1px solid #999;
          }
        }
      }
    }
    &.travel-container {
      min-height: calc(100% - 150px);
      .handler {
        .inp {
          display: inline-block;
          margin-left: 10px;
          & /deep/ .inp-search {
            width: 272px;
            .el-input__inner {
              height: 32px;
              line-height: 32px;
            }
            .el-input__suffix {
              top: -4px;
            }
          }
          .btn-search {
            display: inline-block;
            margin: 0 20px 0 10px;
            height: 32px;
            line-height: 30px;
            width: 60px;
            box-sizing: border-box;
            text-align: center;
            border-radius: 4px;
            color: @color-dark-grey;
            font-size: @fontsize-medium;
            border: 1px solid #999;
          }
        }
        .btn-import {
          display: inline-block;
          width: 80px;
          margin: 0 30px 0 10px;
        }
      }
    }
    .handler {
      height: 32px;
      line-height: 32px;
      padding: 16px 0;
      min-width: 800px;
      .btn-add {
        width: 110px;
        height: 32px;
        display: inline-block;
      }
    }
    .table {
      padding: 0 10px;
      & /deep/ .el-table {
        min-width: auto;
        .el-table__header {
          thead {
            th {
              background-color: #fafafa;
              .cell {
                font-weight: bold;
                span {
                  font-weight: bold;
                }
              }
            }
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
}
</style>
