<template>
  <div class="account-main companyAccount">
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'accountOrigin' }">账单</el-breadcrumb-item>
          <el-breadcrumb-item>{{companyName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <el-date-picker v-model="accountParams.date" value-format="yyyy-MM" type="month" placeholder="选择年月">
        </el-date-picker>
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入计调姓名/联系电话"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadBillList">全部导出</a>
        </div>
      </div>
      <div class="tab-wrap">
        <el-tabs v-model="accountParams.bill_status">
          <el-tab-pane :label="'全部 ('+billMoney.total_order_money+'元)'" name="0"></el-tab-pane>
          <el-tab-pane :label="'已结 ('+billMoney.has_pay_money+'元)'" name="2"></el-tab-pane>
          <el-tab-pane :label="'未结 ('+billMoney.not_pay_money+'元)'" name="1"></el-tab-pane>
        </el-tabs>
      </div>
      <div class="takespace"></div>
      <dataBlank v-if="!tableData.length && maxPage === 0" dataType="account"></dataBlank>
      <div class="table" v-if="tableData.length">
        <el-table ref="multipleTable" :class=" accountParams.bill_status == 1 ? 'fix-bottom': ''"   :data="tableData" tooltip-effect="dark" @selection-change="handleSelectionChange" >
          <el-table-column width="55" type="selection" v-if="accountParams.bill_status == 1">
          </el-table-column>
          <el-table-column label="时间" width="170" prop="travel_time">
            <template slot-scope="scope">
              <p>开始：{{ scope.row.travel_begin }}</p>
              <p>结束：{{ scope.row.travel_end }}</p>
            </template>
          </el-table-column>
          <el-table-column prop="travel_content" label="行程">
          </el-table-column>
          <el-table-column label="车辆" width="100">
            <template slot-scope="scope">
              <div v-for="(car, index) in scope.row.car_info" :key="index" class="car" :class="car.car_source == 1 ? 'car-inside': 'car-outside'">
                <p class="car-number">{{car.car_number}}</p>
                <p class="car-type">{{car.car_source ==1?'自有':'外援'}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="司机" width="120">
            <template slot-scope="scope">
              <div v-for="(car, index) in scope.row.car_info" :key="index" class="driver">
                <p>{{car.driver_name}}</p>
                <p>{{car.driver_mobile}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="money" label="总价" width="100">
            <template slot-scope="scope">
              <span class="money">{{scope.row.money}}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <span :class="scope.row.bill_status == 1 ? 'status-wait': 'status-ok'">{{scope.row.bill_status == 1 ? '未结款': '已结款' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <div  @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)" class="btn">查看</div>
                <a href="javascript:void(0);" v-if="scope.row.bill_status == 1" class="btn" @click="confirmPayed(scope.row.order_id)">确认结款</a>
                <a href="javascript:void(0);" v-if="scope.row.bill_status == 1" class="btn" @click="popupModifyPriceBox(scope.row.order_id)">修改价格</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" :class=" accountParams.bill_status == 1 ? 'fixtop': ''" v-if="tableData.length">
        <el-pagination @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
      <div class="pay-footer" v-show="accountParams.bill_status == 1">
        <el-checkbox v-model="allChecked">全选</el-checkbox>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="allConfirm">确认</a>
        </div>
      </div>
    </div>
    <modifyPriceBox :modifyPriceBoxShow.sync="modifyPriceBoxShow" :orderId="modifyMoneyOrderId" @getNewMoney="getNewMoney"></modifyPriceBox>
  </div>
</template>
<script>
import modifyPriceBox from '@/components/modifyPriceBox'
export default {
  name: 'companyAccount',
  components: {
    modifyPriceBox
  },
  data () {
    return {
      modifyPriceBoxShow: false,
      companyName: null,
      selectOptions: [],
      allChecked: false,
      tableData: [],
      page: 1,
      maxPage: null,
      keywords: '',
      accountParams: {
        customer_id: null,
        date: '',
        keywords: '',
        bill_status: '0'
      },
      billMoney: {
        total_order_money: 0,
        not_pay_money: 0,
        has_pay_money: 0
      },
      confirmIds: [],
      modifyMoneyOrderId: 0,
      neesClearSelection: true
    }
  },
  created () {
    this.watchQueryVal()
    this.getBillMoney()
  },
  watch: {
    accountParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getTableList()
        this.getBillMoney()
      },
      deep: true
    },
    allChecked (newVal, oldVal) {
      this.toggleSelection(newVal)
    }
  },
  methods: {
    popupModifyPriceBox (order_id) {
      this.modifyPriceBoxShow = true
      this.modifyMoneyOrderId = order_id
    },
    watchQueryVal () {
      this.accountParams.customer_id = this.$route.query.customer_id
      let companyName = this.$route.query.companyName
      if (companyName) {
        this.companyName = companyName
      }
      // 获取当前年月
      let date = new Date
      this.accountParams.date = date.getFullYear() + '-' + (date.getMonth() + 1)
    },
    handleSelectionChange (val) {
      this.confirmIds = val
      if (this.confirmIds.length == this.tableData.length) {
        this.allChecked = true
      } else {
        this.allChecked = false
        this.neesClearSelection = false
      }
    },
    toggleSelection (newVal) {
      if (newVal == false) {
        if (this.neesClearSelection) {
          this.$refs.multipleTable.clearSelection()
        }
      } else {
        this.tableData.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row, true)
        })
      }
      this.neesClearSelection = true
    },
    getNewMoney (money) {
      let data = {
        'money': money
      }
      this.modifyData(this.modifyMoneyOrderId, data)
      this.getBillMoney()
    },
    modifyData (order_id, data) {
      for (var i in this.tableData) {
        let orderInfo = this.tableData[i]
        if (order_id == orderInfo.order_id) {
          Object.assign(orderInfo, data)
          this.tableData[i] = orderInfo
          break
        }
      }
    },
    allConfirm () {
      var arr = []
      let confirmIds = this.confirmIds
      if (confirmIds.length) {
        confirmIds.forEach(ele => {
          arr.push(ele.order_id)
        })
        let order_ids = arr.join(',')
        this.confirmPayed(order_ids)
      } else {
        this.$message({
          message: '请勾选账单',
          type: 'error'
        })
      }
    },
    confirmPayed (order_ids) {
      this.$confirm('是否确认结款', {
        type: 'warning'
      }).then(() => {
        let params = {
          'order_id': order_ids
        }
        this.$axios.post(this.$httpUrl.billModifyStatus, params).then(res => {
          if (res != false) {
            this.getBillMoney()
            this.$message({
              message: '结款操作成功',
              type: 'success'
            })
            order_ids = order_ids.toString().split(',')
            for (var i in order_ids) {
              let data = {
                'bill_status': 2
              }
              this.modifyData(order_ids[i], data)
            }
          }
        })
      })

    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.accountParams.keywords = this.keywords
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign(this.accountParams, params)
      this.$axios.get(this.$httpUrl.orderList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getTableList()
    },
    downloadBillList () {
      let params = {
        'date': this.accountParams.date,
        'bill_status': this.accountParams.bill_status,
        'customer_id': this.accountParams.customer_id,
        'keywords': this.accountParams.keywords
      }
      this.$axios.get(this.$httpUrl.downloadBillList, { params: params }).then(res => {
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
    getBillMoney () {
      let params = {
        'customer_id': this.accountParams.customer_id,
        'date': this.accountParams.date
      }
      this.$axios.get(this.$httpUrl.billMonthMoney, { params: params }).then(res => {
        if (res != false) {
          this.billMoney = res
        }
      })
    },
    jumpDetails(orderType,company,customer_id,order_id){
      console.log(1)
      if(orderType==1){
        this.$router.push({name: 'accountDetails', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }else if(orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }else if(orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang ="less" scoped>
@tab-height: 50px;
.account-main {
  position: relative;
  width: 100%;
  height: 100%;
}
.main-content {
  .handle-wrap {
    .el-select {
      float: left;
      width: 120px;
      height: 32px;
      margin-left: 20px;
      & /deep/ .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    & /deep/ .el-date-editor {
      float: left;
      margin-left: 20px;
      .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
      .el-input__prefix,
      .el-input__suffix {
        top: -4px;
      }
    }
  }
  .tab-wrap {
    padding-left: 15px;
    & /deep/ .el-tabs {
      .el-tabs__header {
        margin: 0;
      }
      .el-tabs__nav-wrap::after {
        background-color: transparent;
      }
      .el-tabs__item {
        height: @tab-height;
        line-height: @tab-height;
        font-size: @fontsize-large;
        text-align: center;
        min-width: 200px;
      }
      .el-tabs__item.is-top:nth-child(2) {
        padding-left: 0;
      }
    }
  }
  .table {
    & /deep/ .el-table {
      th:nth-last-of-type(4),
      td:nth-last-of-type(3),
      th:nth-last-of-type(6),
      td:nth-last-of-type(5) {
        border-left: 1px solid #ebeef5;
      }
    }
    .status {
      &-ok {
        color: @theme-color;
      }
      &-wait {
        color: @yellow;
      }
    }
    .money {
      color: #f00;
    }
    .car {
      &.car-inside {
        color: @theme-color;
        .car-type {
          background-color: @theme-color;
        }
      }
      &.car-outside {
        color: @yellow;
        .car-type {
          background-color: @yellow;
        }
      }
      .car-type {
        display: inline-block;
        padding: 2px 5px;
        line-height: 1;
        border-radius: 2px;
        color: @color-white;
      }
    }
    .car,
    .driver {
      margin-bottom: 10px;
      &:last-of-type {
        margin-bottom: 0;
      }
    }
  }
  .btns {
    .btn {
      &:nth-child(3) {
        background-color: transparent;
        color: @color-dark-grey;
        border: 1px solid @color-light-grey;
      }
    }
  }
  .pay-footer {
    position: fixed;
    bottom: 0;
    left: @sidebar-width;
    width: calc(~"100% - @{sidebar-width}");
    background-color: @bg-white;
    height: 54px;
    box-shadow: 2px 0px 3px rgba(0, 0, 0, 0.24);
    & /deep/ .el-checkbox {
      margin: 16px 0 0 21px;
      .el-checkbox__inner {
        width: 16px;
        height: 16px;
        vertical-align: 3px;
      }
      .el-checkbox__label {
        font-size: @fontsize-large-x;
        color: @color-dark-grey;
      }
      .el-checkbox__inner::after {
        left: 5px;
        top: 2px;
      }
    }
    .btn-wrap {
      float: right;
      width: 169px;
      .btn {
        height: 54px;
        line-height: 54px;
        font-size: @fontsize-large-x;
        border-radius: 0;
      }
    }
  }
  .fixtop {
    bottom: 54px;
  }
}
</style>