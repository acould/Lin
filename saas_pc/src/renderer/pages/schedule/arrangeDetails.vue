<template>
  <div class='account-main arrangeDetails'>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'arrange' }">调度</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'arrange' }">排班</el-breadcrumb-item>
          <el-breadcrumb-item>{{car_number}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="date-wrap">
          <el-date-picker :picker-options="pickerOptions1" v-model="tableParams.date" type="date" placeholder="请选择开始时间" class="seldate" value-format="yyyy-MM-dd"></el-date-picker>
        </div>
        <div class="btn-wrap">
          <router-link :to="{name: 'addMission'}" class="btn">添加任务</router-link>
        </div>
      </div>
      <div class="table">
        <el-table :data="tableData" tooltip-effect="dark">
          <el-table-column label="客户" width="160" prop="client">
            <template slot-scope="scope">
              <p>{{ scope.row.customer_name}}</p>
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
              <div v-for="(car, index) in scope.row.car_info" :key="index">
                <span class="task-status" :class="controlColor(car.driver_status)">{{car.driver_status_text}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link class="btn" :to="{name: 'accountDetails', query: {companyName: scope.row.company,customerId:scope.row.customer_id,order_id:scope.row.order_id,source:'mission'}}">查看</router-link>
                <router-link v-if="scope.row.can_edit" class="btn" :to="{name: 'addMission', query: {order_id:scope.row.order_id}}">修改</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- <div class="pages-wrap">
        <el-pagination @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'arrangeDetails',
  data () {
    return {
      car_number: null,
      tableParams: {
        date: null,
        car_id: null
      },
      maxPage: null,
      tableData: [],
      page: 1,
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() + 3600 * 1000 * 24 <= Date.now()
        }
      }
    }
  },
  computed: {
    start_time (newVal) {
      return this.tableParams.date
    }
  },
  watch: {
    start_time: {
      handler (val) {      
        this.getTableList()
      }    
    },
    identityType (newVal) {
      if (newVal != this.$router.name) {
        this.$router.push({ name: newVal })
      }
    }
  },
  created () {
    if (!this.tableParams.car_id) {
      this.car_number = this.$route.query.car_number
      let date = this.$route.query.date
      this.tableParams = {
        date: date,
        car_id: this.$route.query.car_id
      }
    }   
  },
  methods: {
    controlColor (status) {
      let colorSty = 'status-fail'
      if (status == 0) {
        colorSty = 'status-wait'
      } else if (status == 1) {
        colorSty = 'status-ok'
      }
      return colorSty
    },
    getTableList () {
      let params = {
        page: this.page,
        type: 1
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.scheduleDay, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
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
<style lang ='less' scoped>
.main-content {
  .handle-wrap {
    &/deep/.el-input__inner {
      height: 32px;
    }
    .date-wrap {
      float: left;
      margin-left: 15px;
      .line {
        color: #666;
      }
      & /deep/ .el-date-editor {
        .el-input__icon {
          line-height: 32px;
        }
        .el-input__inner {
          padding-right: 0;
        }
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
    .task-status,
    .car,
    .driver {
      margin-bottom: 5px;
      &:last-of-type {
        margin-bottom: 0;
      }
    }
  }
}
</style>