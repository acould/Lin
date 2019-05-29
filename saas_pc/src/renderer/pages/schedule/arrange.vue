<template>
  <div class="schedule-main arrange">
    <div class="main-header" ref="main-header">
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
      <div class="handle-wrap minWidth" ref="handle-wrap">
        <div class="inp-wrap" key="username-input">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入车牌号"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <el-select v-model="tableParams.seat_num" placeholder="请选择座位数" class="seatsnum-select">
          <el-option
            v-for="(item,index) in seatsOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-select v-model="tableParams.paginate" placeholder="请选择显示条数" class="seatsnum-select selectPage">
          <el-option
            v-for="(item,index) in pageOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <div class="date-warp">
          <el-date-picker
            ref="datePicker"
            v-model="dateArr"
            @change="getDate"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </div>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="rtnAddMission">添加任务</a>
        </div>
      </div>
      <div class="hint-warp" ref="hint-warp">
        <p class="arrange-status arrange-status-free hint-style">闲</p>
        <span class="text">全天可排班</span>
        <p class="arrange-status arrange-status-halfbusy hint-style">忙</p>
        <span class="text">还有空闲时间</span>
        <p class="arrange-status arrange-status-busy hint-style">忙</p>
        <span class="text">当天任务已排满</span>
      </div>
      <div class="table">
        <dataBlank v-if="!tableData.length && maxPage === 0" data-type="car"></dataBlank>
        <el-table border :data="tableData" v-if="tableData.length" :height="tableHeight">
          <el-table-column label="车牌号" width="110" fixed align="center">
            <template slot-scope="scope">
              <p>{{scope.row.car_number}}</p>
              <p>{{scope.row.car_type}}</p>
              <p>{{scope.row.seat_num}}座</p>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            :label="day.text_pc+ ''+ day.week"
            v-for="(day, index) in days"
            :key="index"
          >
            <template slot-scope="scope">
              <div class="curdateschedule">
                <p
                  @click="toggleArrangeStatus((tableData[scope.$index].days)[index],tableData[scope.$index])"
                >
                  <span
                    class="arrange-status arrange-status-free"
                    v-if="(tableData[scope.$index].days)[index].is_busy == 0"
                  >闲</span>
                  <span
                    class="arrange-status arrange-status-busy"
                    v-else-if="(tableData[scope.$index].days)[index].is_busy == 1"
                  >忙</span>
                  <span class="arrange-status arrange-status-halfbusy" v-else>忙</span>
                </p>
                <p
                  class="bottom-txt"
                  @click="taskRtn((tableData[scope.$index].days)[index].is_busy, (tableData[scope.$index].days)[index].text_pc,(tableData[scope.$index].days)[index].date,tableData[scope.$index])"
                >
                  <span
                    v-if="(tableData[scope.$index].days)[index].is_busy == 0"
                    class="grey limitlen"
                  >添加任务</span>
                  <span
                    v-else
                    class="limitlen"
                  >{{(tableData[scope.$index].days)[index].customer_name}}</span>
                </p>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="110" align="center">
            <template slot-scope="scope">
              <router-link
                :to="{name: 'carArrange',query: {car_number: scope.row.car_number, car_id: scope.row.car_id,car_source:scope.row.car_source}}"
              >查看更多</router-link>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
    </div>
    <scheduleBox :scheduleBoxShow.sync="scheduleBoxShow" :scheduleInfo="scheduleInfo"></scheduleBox>
  </div>
</template>
<script>
import scheduleBox from '@/components/scheduleBox'
export default {
  name: 'arrange',
  components: {
    scheduleBox
  },
  data () {
    return {
      scheduleBoxShow: false,
      scheduleInfo: {},
      identityType: 'arrange',
      hasCar: null,
      tableParams: {
        keywords: '',
        start_time: '',
        end_time: '',
        seat_num: null,
        paginate:''
      },
      tableHeight: null,
      page: 1,
      maxPage: null,
      keywords: '',
      tableData: [],
      days: [],
      seatsOptions: [{
        value: '',
        label: '全部'
      }, {
        value: '5,7',
        label: '5-7座'
      }, {
        value: '7,9',
        label: '7-9座'
      }, {
        value: '10,15',
        label: '10-15座'
      }, {
        value: '16,29',
        label: '16-29座'
      }, {
        value: '30,39',
        label: '30-39座'
      }, {
        value: '40,70',
        label: '40-70座'
      }],
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
      dateArr: null,
      minDate: null,
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
    }
  },
  created () {
    this.getCarList()
  },
  mounted () {
    this.setTableHight()
    window.onresize = this.$util.debounce(this.setTableHight, 500)
  },
  beforeDestroy () {
    window.onresize = null
  },
  methods: {
    setTableHight () {
      this.tableHeight = this.$root.$el.clientHeight - this.$refs['main-header'].clientHeight -
        this.$refs['handle-wrap'].clientHeight - this.$refs['hint-warp'].clientHeight - 54 + 'px'
    },
    getCarList () {
      this.$axios.get(this.$httpUrl.carCount).then(res => {
        if (res != false) {
          if (res.count) this.hasCar = true
        }
      })
    },
    getDate (val) {
      if (Array.isArray(val)) {
        this.minDate = null
        Object.assign(this.tableParams, { start_time: val[0], end_time: val[1] })
      } else {
        this.minDate = null
        Object.assign(this.tableParams, { start_time: null, end_time: null })
      }
    },
    rtnAddMission () {
      if (!this.hasCar) {
        this.$message({ showClose: true, message: '请添加车辆', type: 'error' })
        return
      }
      this.$router.push({ name: 'addMission' })
    },
    taskRtn (status, date, ymd, carInfo) {
      // 0=闲，1=忙，2=半忙
      if (status == 0) {
        this.$router.push({ name: 'addMission', query: { car_source: 1,car_number: carInfo.car_number, car_id: carInfo.car_id, date: ymd, driver_id: carInfo.driver_id, driver_mobile: carInfo.driver_mobile, driver_name: carInfo.driver_name } })
      } else if (status == 1) {
        this.$router.push({ name: 'arrangeDetails', query: { car_number: carInfo.car_number, car_id: carInfo.car_id, date: ymd } })
      } else {
        this.$router.push({ name: 'carArrange', query: { car_source:1,car_number: carInfo.car_number, car_id: carInfo.car_id, yMd: ymd } })
        // this.scheduleInfo = Object.assign({}, carInfo, { date: ymd, textDate: date, car_number: carInfo.car_number, car_id: carInfo.car_id, })
        // this.scheduleBoxShow = true
      }
    },
    // 切换忙闲状态
    toggleArrangeStatus (day, carInfo) {
      let status = day.is_busy
      if (status == 2) return
      let willStatusText = status == 0 ? '忙' : '闲'
      this.$confirm(`您是否将${carInfo.car_number}车辆${day.text_pc}改为${willStatusText}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          car_id: carInfo.car_id,
          date: day.date,
          type: status == 0 ? 1 : 2
        }
        this.$axios.get(this.$httpUrl.busyChange, { params: params }).then(res => {
          if (res) {
            this.$message.success('操作成功')
            this.getTableList()
          }
        })
      }).catch(() => {
        console.log('取消操作')
      });
    },
    toggleIdentityType (name) {
      let identityType = this.identityType
      this.$router.push({ name: identityType });
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
      params = Object.assign(this.tableParams, params)
      this.$axios.get(this.$httpUrl.scheduleList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          let list = res.list
          let arr = []
          if (list.length != 0) {
            list.forEach(ele => {
              let obj = {}
              Object.assign(obj, ele.car_info)
              obj.days = ele.days
              arr.push(obj)
            })
            this.tableData = arr.filter(ele => ele.car_source == 1)
            this.days = arr[0].days
          } else {
            this.tableData = []
          }
          for(var j=0;j<this.days.length;j++){
            if(this.days[j].text == '全选'){
              this.days.splice(j,1)
            }
          }
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
  .arrange-status {
    display: inline-block;
    border-radius: 50%;
    color: @color-white;
    font-size: @fontsize-small;
    width: 18px;
    height: 18px;
    padding: 1px;
    text-align: center;
    line-height: 18px;
    &-busy {
      background-color: @red;
    }
    &-free {
      background: @theme-color;
    }
    &-halfbusy {
      background: @yellow;
    }
  }

  .handle-wrap {
    &/deep/.el-input__inner {
      height: 32px;
    }
    .inp-wrap {
      width: 206px;
    }
    .btn-search {
      margin: 0 12px;
    }
    .el-select {
      float: left;
      width: 129px;
    }
    .btn-wrap {
      margin-left: 12px;
    }
    .seatsnum-select {
      & /deep/ .el-input__inner {
        padding-right: 0;
      }
    }
    .date-warp {
      float: left;
      margin-left: 15px;
      & /deep/ .el-date-editor {
        width: 260px !important;
        .el-input__icon {
          line-height: 1;
        }

        .el-range-separator {
          line-height: 24px;
        }
        .el-input__inner {
          padding-right: 0;
        }
      }
    }
  }
  .hint-warp {
    padding: 0 0 13px 23px;
    background-color: @bg-grey;
    min-width: 600px;
    .text {
      color: @color-dark-grey;
      font-size: @fontsize-medium;
      margin-right: 11px;
    }
    .hint-style {
      display: inline-block;
    }
  }
  .table {
    & /deep/ .el-table {
      tbody .cell {
        padding: 0;
      }
      th,
      td {
        padding: 5px;
      }
      thead th:first-of-type {
        color: @color-dark-grey;
        font-size: @fontsize-large;
      }
    }
    .limitlen {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      display: inline-block;
      width: 100%;
    }
    .grey {
      color: #999;
    }
    ::-webkit-scrollbar {
      height: 12px;
    }
    .curdateschedule {
      cursor: pointer;
    }
    .bottom-txt {
      margin-top: 2px;
      line-height: 1.2;
    }
  }
}
</style>
<style>
.schedule-main .el-table__fixed::before,
.el-table__fixed-right::before {
  background-color: transparent;
}
.schedule-main .selectPage{
  margin-left: 20px;
}
.schedule-main .minWidth{
  min-width: 1000px;
}
</style>

