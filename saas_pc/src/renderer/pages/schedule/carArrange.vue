<template>
  <div class="carArrange" @click="bodyClickHandler">
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
    <div class="calendar-wrap">
      <div class="date-wrap">
        <div class="date-header">
          <span class="iconfont icon-jiantou2 iconfont-arrow" @click="changeMonth(-1)"></span>
          <span>{{yy}}年</span>
          <span>{{MM}}月</span>
          <span class="iconfont icon-jiantou2 iconfont-arrow-right iconfont-arrow" @click="changeMonth(1)"></span>
        </div>
        <div class="date-main">
          <div class="week-wrap">
            <span>日</span>
            <span>一</span>
            <span>二</span>
            <span>三</span>
            <span>四</span>
            <span>五</span>
            <span>六</span>
          </div>
          <div class="day-wrap">
            <div class="day" :class="activeClass(day)" v-for="(day, index) in date" :key="index" @click="seletedDate(day)">
              <span>{{day.text}}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="clock-wrap">
        <div class="dialog-header" v-show="yyMM">
          <p class="title">
            {{yyMM}}
            <span class="tip">(红色为繁忙时间)</span>
          </p>
        </div>
        <div class="dialog-bd" v-show="yyMM" @mouseleave="timeBlurHandler" @mouseenter="timeFocusHandler">
          <div class="hour-wrap">
            <span class="hour-status" :class="timeClassFunc(time)" @click="selectTimeRange(time)" @mousemove="GetHoverTimeVal(time)" v-for="(time, index) in times" :key="index">
              {{time.text}}
            </span>
          </div>
        </div>
        <div class="dialog-foot" v-show="yyMM">
          <a href="javascript:void(0);" class="btn" :class=" curDatestatus == 1 ? 'btn-disabled' : ''" @click="rtnAddMission">添加任务</a>
        </div>
      </div>
    </div>
    <div class="main-content">
      <div class="table">
        <dataBlank v-if="!tableData.length && maxPage === 0" dataType="mission" :missionQuery="missionQuery"></dataBlank>
        <el-table v-if="tableData.length" ref="multipleTable" :data="tableData" tooltip-effect="dark">
          <el-table-column label="订单编号" width="100">
            <template slot-scope="scope">
                <div class="color-green cursor"  @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)" >{{scope.row.order_num}}</div>
            </template>  
          </el-table-column>
          <el-table-column label="订单类型"  width="80">
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
              <p>开始：{{ scope.row.travel_begin }}</p>
              <p>结束：{{ scope.row.travel_end }}</p>
            </template>
          </el-table-column>
          <el-table-column prop="travel_content" label="行程">
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
                <div class="btn" @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)">查看</div>
                <router-link v-if="scope.row.can_edit" class="btn" :to="{name: 'addMission', query: {order_id:scope.row.order_id,order_type: scope.row.order_type}}">修改</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination @current-change="changePage" :current-page="page" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'carArrange',
  components: {
  },
  data () {
    return {
      car_number: null,
      dayParams: {
        date: null,  // 年月日
        car_id: null,
        type: 1
      },
      monthParams: {
        date: null,  // 年月
        car_id: null
      },
      date: [],
      times: [],
      tableData: [],
      curDatestatus: false,
      missionQuery: {},
      page: 1,
      maxPage: null,
      dateRange: [],
      hoverTimeVal: null,
      timeBlurStatus: false
    }
  },
  created () {
    this.getCurDate()
    this.getQueryParams()
    this.missionQuery = { car_number: this.car_number, date: this.dayParams.date, car_id: this.dayParams.car_id }
  },
  watch: {
    monthParams: {
      handler () {
        this.getMonthArrange()
      },
      deep: true
    },
    dayParams: {
      handler () {
        this.getDayArrange()
      },
      deep: true
    }
  },
  computed: {
    yy () {
      let monthParams = this.monthParams
      if (monthParams.date) {
        return monthParams.date.split('-')[0]
      }
    },
    MM () {
      let monthParams = this.monthParams
      if (monthParams.date) {
        let m = monthParams.date.split('-')[1]
        return m < 10 ? m[1] : m
      }
    },
    yyMM () {
      if (!this.dayParams.date) return null
      let dateArr = this.dayParams.date.split('-')
      if (dateArr.length === 3) {
        let month = dateArr[1] < 10 ? dateArr[1][1] : dateArr[1]
        let day = dateArr[2] < 10 ? dateArr[2][1] : dateArr[2]
        return month + '月' + day + '日'
      } else {
        return null
      }
    }
  },
  mounted () {
    let bd = document.querySelector('body')
    bd.onclick = function () {
      // this.
    }
  },
  methods: {
    // 跳转到添加任务页面
    rtnAddMission () {
      if (this.curDatestatus == 1) return
      let dayParams = this.dayParams
      this.$router.push({ name: 'addMission', query: {car_source:this.$route.query.car_source, car_number: this.car_number, date: dayParams.date, car_id: dayParams.car_id } })
    },
    // 更换日期
    changeMonth (i) {
      let dateArr = this.monthParams.date.split('-')
      let MM = +dateArr[1]
      let yy = +dateArr[0]
      let newMM = MM + i
      let newyy = yy
      if (newMM == 0) {
        newMM = 12
        newyy = yy - 1
      } else if (newMM == 13) {
        newyy = yy + 1
        newMM = 1
      }
      newMM = newMM < 10 ? '0' + newMM : newMM
      this.monthParams.date = this.dayParams.date = newyy + '-' + newMM

    },
    // 选择日历中的日期
    seletedDate (day) {
      if (!day.text) return
      this.dayParams.date = day.date
      this.missionQuery = { car_number: this.car_number, date: this.dayParams.date, car_id: this.dayParams.car_id }
    },
    // 渲染表格中的数据样式
    controlColor (status) {
      let colorSty = 'status-fail'
      if (status == 0) {
        colorSty = 'status-wait'
      } else if (status == 1) {
        colorSty = 'status-ok'
      }
      return colorSty
    },
    // 渲染日历中日期样式
    activeClass (day) {
      let newClass;
      if (day.is_busy == 1) {
        newClass = 'busy'
      } else if (day.is_busy == 2) {
        newClass = 'half-busy'
      }
      if (this.dayParams.date == day.date && day.text) {
        this.curDatestatus = day.is_busy
        newClass += ' selected'
      }
      return newClass
    },
    // 初始化日历安排数据
    getMonthArrange () {
      this.$axios.get(this.$httpUrl.scheduleMonth, { params: this.monthParams }).then(res => {
        if (res != false) {
          this.date = res.days
        }
      })
    },
    // 选中的那天的安排数据
    getDayArrange () {
      let obj = Object.assign({}, this.dayParams, { page: this.page })
      this.$axios.get(this.$httpUrl.scheduleDay, { params: obj }).then(res => {
        if (res != false) {
          this.times = res.times
          this.tableData = res.list
          this.maxPage = res.pages
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getDayArrange()
    },
    getCurDate () {
      let yM = this.$util.formatTime().yM;
      this.monthParams.date = yM
    },
    getQueryParams () {
      let query = this.$route.query
      this.car_source = query.car_source 
      let dayObj = {}
      let car_number = query.car_number
      if (car_number) this.car_number = car_number
      let car_id = query.car_id
      if (car_id) {
        dayObj.car_id = car_id
        this.monthParams.car_id = car_id
      }
      let yMd = query.yMd
      if (yMd) {
        dayObj.date = yMd
      } else {
        let yM = this.$util.formatTime().yM;
        dayObj.date = yM
      }
      this.dayParams = Object.assign({}, this.dayParams, dayObj)
    },
    GetHoverTimeVal (time) {
      this.hoverTimeVal = Number.parseInt(time.text)
    },
    leaveTimeRange () {
      this.hoverTimeVal = null
      this.dateRange = []
    },
    // body点击事件
    bodyClickHandler () {
      if (!this.timeBlurStatus && this.hoverTimeVal) {
        this.leaveTimeRange()
      }
    },
    // 鼠标移出time框
    timeBlurHandler () {
      this.timeBlurStatus = false
    },
    // 鼠标进入time框
    timeFocusHandler () {
      this.timeBlurStatus = true
    },
    timeClassFunc (time) {
      if (time.is_busy == 1) {
        return 'hour-status-busy'
      } else if (time.is_busy == 2) {
        return 'hour-status-halfbusy'
      } else {
        let dateRange = this.dateRange
        let strClass = 'hour-status-free'
        if (dateRange[0] == time.text) {
          strClass += ' start-date'
        }
        let hoverTimeVal = this.hoverTimeVal
        if ((hoverTimeVal || hoverTimeVal == 0) && ((time.text > dateRange[0] && time.text <= hoverTimeVal) || (time.text < dateRange[0] && time.text >= hoverTimeVal))) {
          strClass += ' in-range'
        }
        return strClass
      }
    },
    selectTimeRange (time) {
      if (!time.is_busy == 0) return
      let dateRange = this.dateRange
      let len = dateRange.length
      let dtNumber = Number.parseInt(time.text)
      if (len == 1) {
        let firstDate = dateRange[0]
        let timeArr = this.times
        if (firstDate === dtNumber) {
          return
        } else if (firstDate > dtNumber) {
          for (var i in timeArr) {
            let ele = timeArr[i]
            if (ele.text > dtNumber && ele.text < firstDate && ele.is_busy != 0) {
              this.$message.error('当前选择时间区间内，已有任务安排');
              return
            }
          }
          dateRange.unshift(dtNumber)
        } else {
          for (var i in timeArr) {
            let ele = timeArr[i]
            if (ele.text > firstDate && ele.text < dtNumber && ele.is_busy != 0) {
              this.$message.error('当前选择时间区间内，已有任务安排');
              return
            }
          }
          dateRange.push(dtNumber)
        }
        let newDateArr =  dateRange.map(val => val + ':00')
        let dayParams = this.dayParams
        let queryObj = {
          car_number: this.car_number, 
          date: dayParams.date, 
          car_id: dayParams.car_id,
          begin_at_time: newDateArr[0],
          car_source: this.car_source,
          end_at_time: newDateArr[1]
        }
        this.$router.push({ name: 'addMission', query: queryObj })
      } else if (len == 0) {
        this.dateRange.push(dtNumber)
      }

    },
    jumpDetails(orderType,company,customer_id,order_id){
      if(orderType==1){
        this.$router.push({name: 'accountDetails', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'mission'}})
      }else if(orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'mission'}})
      }else if(orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'mission'}})
      }
    }
  }
}  
</script>
<style>
.carArrange .blank-wrap {
  top: 260px !important;
}
</style>
<style lang="less" scope>
.calendar-wrap {
  .cursor{
    cursor: pointer;
  }
  background-color: @bg-grey;
  padding: 10px 23px;
  overflow: hidden;
  position: relative;
  .date-wrap {
    margin-right: 1.8%;
    min-height: 239px;
    width: 48.2%;
    .date-header {
      height: 48px;
      line-height: 48px;
      text-align: center;
    }
    .date-main {
      .week-wrap {
        overflow: hidden;
        text-align: center;
        span {
          float: left;
          width: 14.286%;
          font-size: 12px;
          color: #666;
        }
      }
      .day-wrap {
        margin-top: 4px;
        text-align: center;
        overflow: hidden;
      }
      .day-wrap .day {
        float: left;
        width: 14.286%;
        color: #cbccce;
        height: 30px;
        line-height: 30px;
        cursor: pointer;
        position: relative;
        padding-bottom: 2px;
        span {
          display: inline-block;
          width: 20px;
          height: 20px;
          font-size: @fontsize-medium;
          line-height: 20px;
          color: #8e8e8e;
          border-radius: 50%;
        }
        &.selected {
          &::after {
            content: "";
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            margin: 0 auto;
            width: 19px;
            height: 3px;
            background-color: @theme-color;
            border-radius: 2px;
          }
        }
        &.busy {
          span {
            background-color: @red;
            color: #fff;
          }
        }
        &.half-busy {
          span {
            background-color: @yellow;
            color: #fff;
          }
        }
      }
    }
    .iconfont-arrow {
      color: #555;
      font-weight: bold;
      display: inline-block;
      font-weight: bold;
      cursor: pointer;
      padding: 0 10px;
      &.iconfont-arrow-right {
        transform: rotate(180deg);
      }
    }
  }
  .clock-wrap,
  .date-wrap {
    background-color: #fff;
  }
  .clock-wrap {
    position: absolute;
    top: 10px;
    right: 23px;
    width: calc(~"48.2% - 23px");
    height: calc(~"100% - 20px");
    .dialog-header {
      height: 47px;
      line-height: 47px;
      text-align: center;
      box-sizing: border-box;
      border-bottom: 1px solid #eee;
      color: #555;
      .tip {
        color: #666;
      }
    }
    .hour-wrap {
      margin: 20px auto 0;
      overflow: hidden;
      padding: 0 15px;
      .hour-status {
        float: left;
        width: 12.5%;
        height: 20px;
        line-height: 20px;
        margin-bottom: 20px;
        text-align: center;
        color: #555;
        &-busy {
          background-color: @red;
          color: @color-white;
        }
        &-halfbusy {
          background-color: @yellow;
          color: @color-white;
        }
        &-free {
          cursor: pointer;
          &:hover,
          &.start-date {
            background-color: @theme-color;
            color: #fff;
          }
          &.in-range {
            background-color: rgba(13, 181, 146, 0.3);
            color: #fff;
          }
        }
      }
    }
    .dialog-foot {
      text-align: center;
      margin: 0 auto;
      .btn {
        width: 260px;
        height: 36px;
        line-height: 36px;
        margin: 8px auto 0;
      }
    }
  }
}
.main-content {
  .task-status {
    display: inline-block;
    height: 46px;
    line-height: 46px;
  }
  .table {
    & /deep/ .el-table {
      th:nth-last-of-type(4),
      td:nth-last-of-type(3),
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
}
</style>

