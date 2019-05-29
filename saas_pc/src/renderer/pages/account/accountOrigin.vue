<template>
  <div class="account-main">
    <div class='main-header'>
      <h3 class="single-tt">账单</h3>
    </div>
    <div class="main-content">
      <div class="account-medium-row handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入客户名称"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadBillList">全部导出</a>
        </div>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="rtnAddAccount">添加账单</a>
        </div>
        <div class="account-money">
          <p class="line account-wait">未结
            <span class="mark">
              <span class="iconfont icon-jiage1"></span>{{customerInfo.will_income_money}}元</span>
          </p>
          <p class="line account-ok">
            营收
            <span class="mark">
              <span class="iconfont icon-jiage1"></span>{{customerInfo.total_income_money}}元</span>
          </p>
        </div>
      </div>
      <div class="table">
        <dataBlank v-if="!tableData.length && maxPage === 0" dataType="account"></dataBlank>
        <el-table :data="tableData" v-if="tableData.length" @sort-change="sortChange">
          <el-table-column prop="company" label="客户名称" align="center">
          </el-table-column>
          <el-table-column label="全部" align="center">
            <el-table-column prop="total_order_num" label="订单数" align="center">
            </el-table-column>
            <el-table-column prop="total_order_money" label="金额" sortable="custom" align="center">
            </el-table-column>
          </el-table-column>
          <el-table-column label="已结" align="center">
            <el-table-column prop="has_pay_order_num" label="订单数" align="center">
            </el-table-column>
            <el-table-column prop="has_pay_money" label="金额" sortable="custom" align="center">
            </el-table-column>
            <el-table-column prop="payed_month" label="月份" align="center">
            </el-table-column>
          </el-table-column>
          <el-table-column label="未结" align="center">
            <el-table-column prop="not_pay_order_num" label="订单数" align="center">
            </el-table-column>
            <el-table-column prop="not_pay_money" label="金额" sortable="custom" align="center">
            </el-table-column>
            <el-table-column prop="not_pay_month" label="月份" align="center">
            </el-table-column>
          </el-table-column>
          <el-table-column prop="handle" label="操作" align="center" width="120">
            <template slot-scope="scope">
              <div class='btn-wrap'>
                <router-link class="btn" :to="{name: 'companyAccount',query: {companyName: scope.row.company,customer_id: scope.row.customer_id}}">查看详情</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'accountOrigin',
  data () {
    return {
      tableParams: {
        keywords: '',
        type: null,
        sort: null
      },
      page: 1,
      maxPage: null,
      keywords: '',
      customerInfo: {},
      tableData: [],
      hasCar: false
    }
  },
  created () {
    this.getCarList()
  },
  watch: {
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getTableList()
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    getCarList () {
      this.$axios.get(this.$httpUrl.carCount).then(res => {
        if (res != false) {
          if (res.count) this.hasCar = true
        }
      })
    },
    sortChange (o) {  
      let tableParams = this.tableParams
      let type;
      switch (o.prop) {
        case 'total_order_money':
          type = 0
          break
        case 'has_pay_money':
          type = 2
          break
        case 'not_pay_money':
          type = 1
          break
      }
      let sort = o.order == 'ascending' ? 1 : 0
      Object.assign(this.tableParams, { sort: sort, type: type })
    },
    rtnAddAccount () {
      if (!this.hasCar) {
        this.$message({ showClose: true, message: '请添加车辆', type: 'error' })
        return
      }
      this.$router.push({ name: 'addMission' })

    },
    downloadBillList () {
      this.$axios.get(this.$httpUrl.downloadALLBillList).then(res => {
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
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.billList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
          this.customerInfo = res.info
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getTableList()
    }
  }
}
</script>
<style lang ="less" scoped>
.main-content {
  .account-medium-row {
    .account-money {
      margin-bottom: 10px;
      float: left;
      color: @color-dark-grey;
      font-size: @fontsize-medium;
      margin-left: 25px;
      height: 32px;
      line-height: 32px;
      width: 480px;
      .line {
        width: 228px;
        padding: 0 10px;
        box-sizing: border-box;
        float: left;
        background-color: #fff;
        margin-right: 14px;
        border-radius: 4px;
        overflow: hidden;
        &:last-of-type {
          margin-right: 0;
        }
        .mark {
          float: right;
          color: #f00;
          line-height: 32px;
          font-size: @fontsize-large-xx;
          vertical-align: -1px;
          .iconfont {
            vertical-align: 1px;
            margin-right: 4px;
          }
        }
        &.account-ok {
          .mark {
            color: @theme-color;
          }
        }
        &.account-wait {
          .mark {
            color: @yellow;
          }
        }
      }
    }
  }
  .btn-wrap {
    .btn {
      width: 100px;
    }
  }
}
</style>