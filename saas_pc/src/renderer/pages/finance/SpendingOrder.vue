<template>
  <div class="schedule-main" id="SpendingOrder">
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'AddSpending'}">新增支出</el-breadcrumb-item>
          <el-breadcrumb-item>选择订单</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap" key="username-input">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入司机姓名/客户名称"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="date-warp">
          <el-date-picker v-model="tableParams.start_time" type="date" placeholder="请选择时间" class="seldate" value-format="yyyy-MM-dd"></el-date-picker>
          <el-date-picker :disabled="tableParams.start_time ? false : true" v-model="tableParams.end_time" type="date" placeholder="请选择时间" class="seldate" value-format="yyyy-MM-dd"></el-date-picker>
        </div>
      </div>
      <div class="main-content">
        <dataBlank v-if="!tableData.length && maxPage === 0" dataType="mission"></dataBlank>
        <div class="table" v-if="tableData.length">
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark">
            <el-table-column label="客户" width="160" prop="client">
              <template slot-scope="scope">
                <p>{{ scope.row.company}}</p>
                <p>{{ scope.row.customer_mobile}}</p>
              </template>
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
            <el-table-column label="状态" width="100">
              <template slot-scope="scope">
                <div v-for="(car, index) in scope.row.car_info" :key="index" class="status">
                  <span class="task-status" :class="controlColor(car.driver_status)">{{car.driver_status_text}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btns">
                  <a href="javascript:void(0)" class="btn" @click="orderHandler(scope.row)">选择</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pages-wrap" v-if="tableData.length">
          <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'mission',
  data () {
    return {
      identityType: 'mission',
      tableData: [],
      hasCar: false,
      tableParams: {
        keywords: '',
        start_time: '',
        end_time: '',
        car_id: 0
      },
      page: 1,
      maxPage: null,
      keywords: '',
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() + 3600 * 1000 * 24 <= Date.now()
        }
      }
    }
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
    },
    start_time (newVal) {
      if (!newVal) this.tableParams.end_time = ''
    }
  },
  computed: {
    start_time () {
      return new Date(this.tableParams.start_time).getTime()
    },
    ...mapState({
      spendingInfo: state => state.finance.spendingInfo
    })
  },
  created () {
    this.getCarList()
  },
  methods: {
    ...mapActions([
      'refreshSpendingInfo',
    ]),
    getCarList () {
      this.$axios.get(this.$httpUrl.carCount).then(res => {
        if (res != false) {
          if (res.count) this.hasCar = true
        }
      })
    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
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
    controlColor (status) {
      let colorSty = 'status-fail'
      if (status == 0) {
        colorSty = 'status-wait'
      } else if (status == 1) {
        colorSty = 'status-ok'
      }
      return colorSty
    },
    orderHandler (info) {
      let obj = { ...this.spendingInfo }
      let car_info = info.car_info
      let arr = []
      car_info.forEach(ele => {
        arr.push(ele.car_number)
      });
      let car_number = arr.join(',')
      Object.assign(obj, { order_num: info.order_num.toString(), car_number: car_number.toString(), begin_at: info.travel_time, saas_order_id: info.order_id.toString() })
      this.refreshSpendingInfo(obj)
      this.$router.go(-1)
    }
  }
}
</script>
<style lang ="less" scoped>
.main-content {
  .handle-wrap {
    &/deep/.el-input__inner {
      height: 32px;
    }
    .date-warp {
      float: left;
      margin-left: 15px;
      &/deep/ .el-input__icon {
        line-height: 32px;
      }
      .seldate {
        width: 140px;
        height: 32px;
      }
    }
  }
  .table {
    & /deep/ .el-table {
      th:nth-last-of-type(5),
      td:nth-last-of-type(4),
      th:nth-last-of-type(2),
      td:nth-last-of-type(1) {
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
    .task-status {
      display: inline-block;
      height: 46px;
      line-height: 46px;
    }
    .status,
    .car,
    .driver {
      padding: 10px 0;
      &:last-of-type {
        padding-bottom: 0;
        &::after {
          background-color: transparent;
        }
      }
    }
    .driver,
    .status {
      position: relative;
      &::after {
        content: '';
        position: absolute;
        left: -10px;
        bottom: 0;
        width: 120px;
        height: 1px;
        background-color: #ddd;
      }
    }
  }
}
</style>
