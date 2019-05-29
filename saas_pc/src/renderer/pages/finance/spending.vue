<template>
  <div class="spending">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'outSettlement'}">支出</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="3"></financeOptions>
    <div class="main-content">
      <div class="conditions profit">
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords" placeholder="请输入车牌号/司机等"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <el-select v-model="tableParams.pay_type" class="spending-select" placeholder="请选择">
          <el-option label="全部" :value="0">
          </el-option>
          <el-option label="车辆支出" :value="1">
          </el-option>
          <el-option label="其他支出" :value="2">
          </el-option>
        </el-select>
        <div class="date-box">
          <div class="date-wrap">
            <el-date-picker class="el-date" v-model="tableParams.time" type="month" value-format="yyyy-MM" placeholder="选择年月">
            </el-date-picker>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="medium-line">
          <div class="tabs">
            <el-tabs v-model="tableParams.type" type="card">
              <el-tab-pane label="全部" name="1"></el-tab-pane>
              <el-tab-pane label="待审批" name="2"></el-tab-pane>
              <el-tab-pane label="审批通过" name="3"></el-tab-pane>
              <el-tab-pane label="审批失败" name="4"></el-tab-pane>
            </el-tabs>
          </div>
          <p class="money-wrap">
            <span class="money-lb">全部：</span>
            <span class="money-txt">{{all_count}}</span>
            单
          </p>
          <!-- <a href="javascript:void(0);" class="import-bill btn" @click="downTableList">导出表格</a> -->
          <div class="link">
            <span class="iconfont icon-close iconfont-close"></span>
            <span>新增支出</span>
            <el-select v-model="speningTypeVal" placeholder="请选择" class="hid-select">
              <el-option v-for="item in speningTypeList" :key="item.value" :value="item.value" :label="item.label">
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="table">
          <el-table class="timeTable" :data="tableData" style="width: 100%">
            <el-table-column label="订单编号">
              <template slot-scope="scope">
                <div v-if="scope.row.order_num" @click="jumpDetails(scope.row.saas_order_id,scope.row.order_type)" class="order-id">
                  {{scope.row.order_num }}
                </div>
                <span v-else>{{scope.row.str}}</span>
              </template>
            </el-table-column>
            <el-table-column label="订单类型">
              <template slot-scope="scope">
                <p v-if="scope.row.order_type==1">包车</p>
                <p v-if="scope.row.order_type==2">接送</p>
                <p v-if="scope.row.order_type==3">班车</p>
              </template>
            </el-table-column>
            <el-table-column label="费用支出">
              <template slot-scope="scope">
                <span class="red">{{scope.row.total_money}}</span>元
              </template>
            </el-table-column>
            <el-table-column label="客户" prop="customer_company">
            </el-table-column>
            <el-table-column label="时间">
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.begin_at}}</p>
                <p class="line" v-if="scope.row.end_at">结束:{{scope.row.end_at}}</p>
              </template>
            </el-table-column>
            <el-table-column label="司机">
              <template slot-scope="scope">
                <div class="driver" v-for="(car, index) in scope.row.cars" :key="index">
                  {{car.name}}
                  <div class="iconfont-wrap" v-if="car.name">
                    <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                      {{car.mobile}}
                      <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                    </el-popover>
                  </div>
                  <p class="line">{{car.car_number}}</p>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="状态">
              <template slot-scope="scope">
                <p v-if="scope.row.audit_status == 1">
                  <span class="dot dot-ok"></span>审批通过</p>
                <p v-else-if="scope.row.audit_status == 0">
                  <span class="dot dot-wait"></span>待审批</p>
                <p v-else>
                  <span class="dot dot-fail"></span>审批失败</p>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <a v-if="scope.row.audit_status == 0" href="javascript:void(0)" class="btn" @click="orderHandler(scope.row.fee_id,1)">通过</a>
                  <a v-if="scope.row.audit_status == 0" href="javascript:void(0)" class="btn btn-fffbg" @click="orderHandler(scope.row.fee_id,-1)">拒绝</a>
                  <router-link v-if="scope.row.cars.length && scope.row.cars[0].car_number" key="daily" class="btn btn-nobg" :to="{name: 'SpendingDetail',query:{order_num:scope.row.order_num,fee_id :scope.row.fee_id }}">
                    费用详情
                  </router-link>
                  <router-link v-else key="other" class="btn btn-nobg" :to="{name: 'OtherSpendingDetail',query:{fee_id :scope.row.fee_id }}">
                    费用详情
                  </router-link>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pages-row">
          <div class="pages-wrap time" v-if="tableData.length">
            <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
          </div>
        </div>
      </div>
    </div>
    <RefuseReasonBox @passReason="getReason" :send-order-info="sendOrderInfo" :refuse-reason-box-show.sync="refuseReasonBoxShow" />
  </div>
</template>
<script>
import RefuseReasonBox from '@/components/RefuseReasonBox'
export default {
  name: 'spending',
  components: {
    RefuseReasonBox
  },
  data () {
    return {
      keywords: '',
      page: 1,
      maxPage: null,
      tableParams: {
        keywords: null,
        type: "1", // 1全部 2待审批 3审批通过 4审批失败
        order_type: 0,
        time: null,
        pay_type: 0
      },
      refuseReasonBoxShow: false,
      sendOrderInfo: {},
      tableData: [],
      all_count: null,
      speningTypeVal: null,
      speningTypeList: [{
        value: '1',
        label: '车辆支出'
      }, {
        value: '2',
        label: '其他支出'
      }
      ]
    }
  },
  watch: {
    tableParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    },
    speningTypeVal (newVal) {
      if (newVal == 1) {
        this.$router.push({ name: 'AddSpending' })
      } else {
        this.$router.push({ name: 'AddOtherSpending' })
      }
    }
  },
  created () {
    this.getTableData()
  },
  methods: {
    searchKeywords () {
      this.tableParams.keywords = this.keywords
    },
    changePage (page) {
      this.page = page
      this.getTableData()
    },
    getTableData () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.feeQuery
      this.$axios.post(url, params).then(res => {
        if (res) {
          this.tableData = res.list
          this.maxPage = res.pages,
            this.all_count = res.all_count
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
    auditOrder (params) {
      let url
      url = this.$httpUrl.feeAudit
      this.$axios.post(url, params).then(res => {
        if (res) {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          this.getTableData()
        }
      })
    },
    downTableList () {
      this.$axios.get(this.$httpUrl.payListDownload, { params: this.tableParams }).then(res => {
        if (res != false) {
          if (res.url) {
            window.location.href = res.url
          } else {
            this.$message({
              message: '没有可以导出的数据',
              type: 'info'
            })
          }
        }
      })
    },
    jumpDetails(order_id,orderType){
      if(orderType==1){
        this.$router.push({name: 'accountDetails', query: {order_id:order_id,source:'bill'}})
      }else if(orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {order_id:order_id,source:'bill'}})
      }else if(orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang="less" scope>
.link {
  position: relative;
  .hid-select {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 9;
    opacity: 0;
  }
}
.spending {
  .spending-select {
    margin-top: 9px;
    .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }
}
</style>