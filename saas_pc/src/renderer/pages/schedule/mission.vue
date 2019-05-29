<template>
  <div class="schedule-main mission">
    <div class="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="identityType" @tab-click="toggleIdentityType">
          <el-tab-pane label="自有" name="arrange"></el-tab-pane>
          <el-tab-pane label="外援" name="outcar"></el-tab-pane>
          <el-tab-pane label="任务" name="mission"></el-tab-pane>
          <el-tab-pane label="监控" name="monitor"></el-tab-pane>
        </el-tabs>
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
          <el-date-picker
            v-model="tableParams.start_time"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-date-picker
            :disabled="tableParams.start_time ? false : true"
            v-model="tableParams.end_time"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </div>
        <el-select v-model="tableParams.paginate" placeholder="请选择显示条数" class="pageselect">
          <el-option
            v-for="(item,index) in pageOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="rtnAddMission">添加任务</a>
        </div>
        <div class="c-sort-wrap">
          <div
            class="sort-line"
            :class="{'selected': tableParams.order_by_time === 1}"
            @click="sortToggle(1)"
          >下单时间排序
            <div class="arrow-list">
              <span class="iconfont icon-jiantouarrow486"></span>
              <span class="iconfont icon-jiantouarrow486"></span>
            </div>
          </div>
          <div
            class="sort-line"
            :class="{'selected': tableParams.order_by_time === 2}"
            @click="sortToggle(2)"
          >出发时间排序
            <div class="arrow-list">
              <span class="iconfont icon-jiantouarrow486"></span>
              <span class="iconfont icon-jiantouarrow486"></span>
            </div>
          </div>
        </div>
      </div>
      <div class="main-content">
        <dataBlank v-if="!tableData.length && maxPage === 0" data-type="mission"></dataBlank>
        <div class="table" v-if="tableData.length">
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark">
            <el-table-column label="订单编号" width="160" prop="client">
              <template slot-scope="scope">
                <div
                  class="color-green cursor"
                  @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)"
                >{{scope.row.order_num}}</div>
              </template>
            </el-table-column>
            <el-table-column label="订单类型" width="100">
              <template slot-scope="scope">
                <p v-if="scope.row.order_type==1">包车</p>
                <p v-if="scope.row.order_type==2">接送</p>
                <p v-if="scope.row.order_type==3">班车</p>
              </template>
            </el-table-column>
            <el-table-column label="客户" width="160" prop="client">
              <template slot-scope="scope">
                <p>{{ scope.row.company}}</p>
                <p>{{ scope.row.customer_mobile}}</p>
              </template>
            </el-table-column>
            <el-table-column label="时间" width="170" prop="travel_time">
              <template slot-scope="scope">
                <p>开始：<span :class="timeCompare(scope.row.travel_begin_at)">{{ scope.row.travel_begin }}</span></p>
                <p>结束：{{ scope.row.travel_end }}</p>
              </template>
            </el-table-column>
            <el-table-column prop="travel_content" label="行程"></el-table-column>
            <!-- 12.6 新增车价 -->
            <el-table-column prop="money" label="车价" width="100">
              <template slot-scope="scope">
                <p>{{ scope.row.money}}</p>
              </template>
            </el-table-column>

            <el-table-column label="车辆" width="100">
              <template slot-scope="scope">
                <div
                  v-for="(car, index) in scope.row.car_info"
                  :key="index"
                  class="car"
                  :class="car.car_source == 1 ? 'car-inside': 'car-outside'"
                >
                  <p class="car-number">{{car.car_type}}</p>
                  <p class="car-number">{{car.car_number}}</p>
                  <p class="car-number">{{car.seat_num}}座</p>
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
                  <span
                    class="task-status"
                    :class="controlColor(car.driver_status)"
                  >{{car.driver_status_text}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <!-- :to="{name: 'orderDetails', query: {companyName: scope.row.company,customerId:scope.row.customer_id,order_id:scope.row.order_id,source:'mission'}}" -->
                <div class="btns">
                  <div
                    class="btn btn-nobg btn-nobg-theme"
                    @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)"
                  >查看</div>
                  <router-link
                    v-if="scope.row.can_edit"
                    class="btn"
                    :to="{name: 'addMission', query: {order_id:scope.row.order_id,order_type: scope.row.order_type}}"
                  >修改</router-link>
                  <router-link
                    class="btn btn-nobg"
                    :to="{name: 'logDetail', query: {orderId: scope.row.order_id,orderType: scope.row.order_type}}"
                  >操作日志</router-link>
                  <a
                    href="javascript:void(0);"
                    v-if="scope.row.can_edit && scope.row.finished == 0 "
                    class="btn"
                    @click="finishOrder(scope.row.order_num)"
                  >结束任务</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pages-wrap" v-if="tableData.length">
          <el-pagination
            :current-page="page"
            @current-change="changePage"
            :page-count="maxPage"
            background
            layout="prev, pager, next"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "mission",
  data () {
    return {
      identityType: "mission",
      tableData: [],
      hasCar: false,
      tableParams: {
        keywords: "",
        start_time: "",
        end_time: "",
        car_id: 0,
        paginate: '',
        order_by_time: 1,
        order_by_sort: 1
      },
      pageOptions: [{
        value: '10',
        label: '10'
      }, {
        value: '20',
        label: '20'
      }, {
        value: '50',
        label: '50'
      }],
      page: 1,
      maxPage: null,
      keywords: "",
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() + 3600 * 1000 * 24 <= Date.now();
        }
      },
    };
  },
  watch: {
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage();
        }
        this.getTableList();
      },
      deep: true,
      immediate: true
    },
    start_time (newVal) {
      if (!newVal) this.tableParams.end_time = "";
    },
  },
  computed: {
    start_time () {
      return new Date(this.tableParams.start_time).getTime();
    }
  },
  created () {
    this.getCarList();
  },
  methods: {
    // 样式函数
    timeCompare (timestamp) {
      let curDate = new Date(new Date().toLocaleDateString()).getTime()
      let missionDate =  new Date(new Date(timestamp * 1000).toLocaleDateString()).getTime()
      if (missionDate >= curDate) {
        return 'color-red'
      } 
      return ''
    },
    sortToggle (sortNum) {
      let sort = this.tableParams.order_by_sort
      let time = this.tableParams.order_by_time
      time === sortNum ? 
      (this.tableParams.order_by_sort = sort === 1 ? 2 : 1 ) :
      (this.tableParams.order_by_time = time === 1 ? 2 : 1 )
    },
    getCarList () {
      this.$axios.get(this.$httpUrl.carCount).then(res => {
        if (res != false) {
          if (res.count) this.hasCar = true;
        }
      });
    },
    rtnAddMission () {
      if (!this.hasCar) {
        this.$message({
          showClose: true,
          message: "请添加车辆",
          type: "error"
        });
        return;
      }
      this.$router.push({ name: "addMission" });
    },
    toggleIdentityType () {
      let identityType = this.identityType
      this.$router.push({ name: identityType });
    },
    resetPage () {
      this.page = 1;
      this.tableData = [];
    },
    searchFrom () {
      this.tableParams.keywords = this.keywords;
    },
    getTableList () {
      let params = {
        page: this.page
      };
      params = Object.assign({}, this.tableParams, params);
      this.$axios.get(this.$httpUrl.orderList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      });
    },
    changePage (page) {
      this.page = page;
      this.getTableList();
    },
    controlColor (status) {
      let colorSty = "status-fail";
      if (status == 0) {
        colorSty = "status-wait";
      } else if (status == 1) {
        colorSty = "status-ok";
      }
      return colorSty;
    },
    finishOrder (order_num) {
      this.$confirm('是否确认结束任务', {
        type: 'warning'
      }).then(() => {
        let params = {
          order_num: order_num
        }
        this.$axios.get(this.$httpUrl.orderFinish, { params: params }).then(res => {
          if (res != false) {
            this.$message({
              message: '任务结束成功可安排新任务',
              type: 'success'
            })
            this.getTableList()
          }
        })
      }).catch(() => {
        console.log("取消删除")
      });
    },
    jumpDetails (orderType, company, customer_id, order_id) {
      if (orderType == 1) {
        this.$router.push({ name: 'accountDetails', query: { companyName: company, customerId: customer_id, order_id: order_id, source: 'mission' } })
      } else if (orderType == 2) {
        this.$router.push({ name: 'TransportStationDetail', query: { companyName: company, customerId: customer_id, order_id: order_id, source: 'mission' } })
      } else if (orderType == 3) {
        this.$router.push({ name: 'shuttleBusDetail', query: { companyName: company, customerId: customer_id, order_id: order_id, source: 'mission' } })
      }
    }
  }
};
</script>
<style lang ="less" scoped>
.main-content {
  .cursor {
    cursor: pointer;
  }
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
      height: 112px;
      &:last-of-type {
        padding-bottom: 0;
        &::after {
          background-color: transparent;
        }
      }
    }
    .driver,
    .car,
    .status {
      position: relative;
      &::after {
        content: "";
        position: absolute;
        left: -10px;
        bottom: 0;
        width: 120px;
        height: 1px;
        background-color: #ddd;
      }
    }
  }
  .pageselect {
    width: 130px;
    margin-left: 20px;
  }
}
</style>
