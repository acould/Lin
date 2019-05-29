<template>
  <div>
    <el-dialog
      :visible.sync="boxShow"
      class="carstyle"
      :close-on-click-modal="false"
      :show-close="false"
      top="5vh"
    >
      <div class="dialog-header" slot="title">
        <el-tabs v-model="car_source">
          <el-tab-pane label="自有车辆" name="1"></el-tab-pane>
          <el-tab-pane label="外援车辆" name="2"></el-tab-pane>
        </el-tabs>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <div class="sel-warp">
          <div class="sel-top">
            <p class="iconfont icon-tishi2 hint"></p>
            <p class="dis">以下为{{dateArea}}之间含有空闲时间的车辆</p>
          </div>
          <div class="search-wrap">
            <el-input
              class="inp-search"
              suffix-icon="iconfont icon-shujuchaxun"
              v-model="keywords"
              placeholder="请输入车牌号、司机、手机号"
            ></el-input>
            <a href="javascript:void(0)" class="btn-search btn" @click="searchFrom">搜索</a>
          </div>
          <el-autocomplete
            v-show="car_source == 2"
            type="text"
            value-key="out_company"
            v-model="fleetName"
            placeholder="请选择车队"
            :fetch-suggestions="querySearchName"
            @select="handleSelectName"
            auto-complete="off"
            @blur="clearFleetName"
          ></el-autocomplete>
          <div class="sel-bot">
            <el-radio-group v-model="carParams.seat_num">
              <el-radio-button label="0">全部</el-radio-button>
              <el-radio-button label="5,10">5-10座</el-radio-button>
              <el-radio-button label="11,25">11-25座</el-radio-button>
              <el-radio-button label="26,40">26-40座</el-radio-button>
              <el-radio-button label="41,70">41-70座</el-radio-button>
            </el-radio-group>
          </div>
        </div>
        <div class="details-warp" v-show="car_source == 1">
          <div class="sel-seat" v-for="(car, index) in inCarList" :key="index">
            <div class="seat-top">
              <p class="plate">{{car.car_info.car_number}} {{car.car_info.seat_num}}座</p>
              <p class="driver">司机：{{car.car_info.driver_name}}</p>
            </div>
            <div class="seat-bot">
              <a
                href="javascript:void(0);"
                class="selbut"
                :class="selectedClass(car, day ,index)"
                v-for="(day, index) in car.days"
                :key="index"
                @click="selectedCar(car, day, index)"
              >
                <div class="selected-wrap">
                  <span class="iconfont icon-gou5 iconfont-gou"></span>
                </div>
                <span :class="index != 0 ? 'fl': ''">{{day.text}}</span>
                <div class="fr" v-if="index != 0">
                  <p class="dis state state-free" v-if="day.is_busy == 0">闲</p>
                  <p class="dis state state-busy" v-else-if="day.is_busy == 1">忙</p>
                  <p class="dis state state-halfbusy" v-if="day.is_busy == 2">忙</p>
                </div>
              </a>
            </div>
          </div>
        </div>
        <div class="details-warp padding" v-show="car_source == 2">
          <div class="sel-seat" v-for="(car, index) in outCarList" :key="index">
            <div class="seat-top">
              <p class="plate">{{car.car_info.car_number}} {{car.car_info.seat_num}}座</p>
               <p class="fleet">{{car.car_info.out_company}}</p>
              <p class="driver">司机：{{car.car_info.driver_name}}</p>
            </div>
            <div class="seat-bot">
              <a
                href="javascript:void(0);"
                class="selbut"
                :class="selectedClass(car, day ,index)"
                v-for="(day, index) in car.days"
                :key="index"
                @click="selectedCar(car, day, index)"
              >
                <div class="selected-wrap">
                  <span class="iconfont icon-gou5 iconfont-gou"></span>
                </div>
                {{day.text}}
                <div class="fr" v-if="index != 0">
                  <p class="dis state state-free" v-if="day.is_busy == 0">闲</p>
                  <p class="dis state state-busy" v-else-if="day.is_busy == 1">忙</p>
                  <p class="dis state state-halfbusy" v-if="day.is_busy == 2">忙</p>
                </div>
              </a>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-foot">
        <el-badge :value="selectedArr.length" class="item">
          <el-button size="small" class="selected btn" @click="rtnSelectedList">已选择</el-button>
        </el-badge>
        <a href="javascript:void(0);" class="btn affirm" @click="confirm">确认选择</a>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'carStyleBox',
  props: {
    carStyleBoxShow: {
      type: Boolean,
      default: false
    },
    dateInterval: {
      start_time: null,
      end_time: null
    },
    carInfo: {
      type: Array,
      default: null
    }
  },
  data () {
    return {
      boxShow: this.carStyleBoxShow,
      car_source: '1',
      carParams: {
        seat_num: '0',
        keywords: null,
        customer_id: null
      },
      inCarList: [],
      outCarList: [],
      selectedArr: [],
      allList: [],
      keywords: null,
      fleetName: null,
    }
  },
  computed: {
    dateArea () {
      let dateInterval = this.dateInterval
      if (!dateInterval) return
      let reg = /(\d*)-(\d*)-(\d*)/

      let start = dateInterval.start_time.replace(reg, '$2月$3日')
      let end = dateInterval.end_time.replace(reg, '$2月$3日')
      return start + '-' + end
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:carStyleBoxShow', newVal)
    },
    car_source (newVal) {
      this.carParams = Object.assign({},this.carParams, {keywords: null, customer_id: 0})
      this.fleetName = null
      this.keywords = null
    },
    carStyleBoxShow (newVal) {
      this.boxShow = newVal
    },
    fleetName (newVal) {
      if (!newVal && newVal !== 0 && this.carParams.customer_id) {
         this.carParams.customer_id = null
      }
    },
    dateInterval: {
      handler () {
        this.getCarsInfo()
      },
      deep: true
    },
    carParams: {
      handler () {
        this.getCarsInfo()
      },
      deep: true
    },
    carInfo (newVal) {
      if (newVal[0].user_car_id && newVal[0].begin_at_date) {
        let arr = []
        newVal.forEach(ele => {
          let days = this.$util.generateArray(ele.begin_at_date, ele.end_at_date)
          let obj = { days: days }
          Object.assign(obj, ele)
          arr.push(obj)
        })
        arr.forEach((ele, index, item) => {
          this.$delete(ele, 'begin_at_date')
          this.$delete(ele, 'begin_at_time')
          this.$delete(ele, 'end_at_date')
          this.$delete(ele, 'end_at_time')
          this.$set(ele, 'car_id', ele.user_car_id)
        })
        let arr2 = []
        arr.forEach((ele, index, item) => {
          if (index != 0) {
            if (ele.user_car_id == item[index - 1].user_car_id) {
              let days1 = item[index - 1].days
              let days2 = ele.days
              let set = new Set(days1.concat(days2))
              item[index].days = [...set]
              // 若倒数一和倒数第二一个车时
              if (index == arr.length - 1) {
                arr2.push(item[index])
              }
            } else {
              arr2.push(item[index - 1])
              // 若倒数一和倒数第二不是一个车时
              if (index == arr.length - 1) {
                arr2.push(item[index])
              }
            }
          } else {
            if (index == arr.length - 1) {
              arr2.push(ele)
            }
          }
        })
        this.selectedArr = arr2

      } else {
        console.log(newVal)
        this.selectedArr = []
      }
    },
  },
  methods: {
    querySearchName (queryString, cb) {
      let params = {
        keywords: queryString,
      }
      this.$axios.get(this.$httpUrl.allfleetList, { params: params }).then(res => {
        if (res) {
          cb(res.list)
        }
      })
    },
    handleSelectName (item) {
      this.carParams.customer_id = item.customer_id
    },
    clearFleetName () {
      if (!this.carParams.customer_id) {
        this.fleetName = null
      }
    },
    searchFrom () {
      this.carParams.keywords = this.keywords
    },
    closeBox () {
      this.boxShow = false
    },
    confirm () {
      let selectedArr = this.selectedArr
      if (!selectedArr.length) {
        this.$message({ showClose: true, message: '请选择车辆日期', type: 'error' })
        return
      }
      for (var i in selectedArr) {
        selectedArr[i].days.sort()
      }
      this.filterDateArea()
      if(this.dateInterval.orderType!=3){
        let params = Object.assign({}, { car_info: this.selectedArr }, this.dateInterval)
          let order_id = this.$route.query.order_id
          if (order_id) params.order_id = order_id
          this.$axios.post(this.$httpUrl.carDays, params).then(res => {
            if (res != false) {
              selectedArr.forEach((ele, i, item) => {
                if (ele.is_busy_new && ele.is_busy_new !== 0) {
                  this.$alert('你所选车辆中间已有任务，系统给你自动拆分', {
                    confirmButtonText: '确定'
                  })
                  return
                }
              })
            
              var newRes = res.map(val => {
                val.vice_drivers = val.vice_drivers || []
                this.$delete(val, 'is_busy_new')
                return val
              })
              this.$emit('rtnCarDaysInfo', newRes)
            }
          })
      }else{
        this.$emit('rtnCarDaysInfo', this.selectedArr)
      }
      
    },
    filterDateArea () {
      let selectedArr = this.selectedArr
      let inCarList = this.inCarList
      let outCarList = this.outCarList
      selectedArr.forEach(ele => {
        // 区分自有和外援
        let commlist;
        if (ele.car_source == 1) {
          commlist = inCarList
        } else {
          commlist = outCarList
        }
        for (var i in commlist) {
          let car = commlist[i]
          if (car.car_info.car_id == ele.car_id) {
            let days = ele.days
            //过滤全选
            let carInfoDays = car.days.filter(day => day.date !== '')
            carInfoDays.forEach(dt => {
              // 日期格式固定，直接根据字符串比较日期大小
              if (days.length > 1 && days[0] < dt.date && days[days.length - 1] >= dt.date && dt.is_busy !== 0) {
                ele.is_busy_new = dt.is_busy
                return
              }
            })
          }
        }
      })
    },
    rtnSelectedList () {
      this.filterDateArea()
      this.$emit('rtnSelectedList', this.selectedArr)
    },
    selectedClass (car, day, index) {
      let selectedArr = this.selectedArr
      let car_id = car.car_info.car_id
      for (var i in selectedArr) {
        let item = selectedArr[i]
        let days = item.days
        for (var j in days) {
          let childItem = days[j]
          if (car_id == item.car_id && day.is_busy != 1 && childItem == day.date) {
            return 'selbut-selected'
          }
          let newDaysArr = []
          let carDays = car.days
          carDays.forEach(m => {
            if (m.date && m.is_busy != 1) {
              newDaysArr.push(m.date)
            }
          })
          if (car_id == item.car_id && index == 0 && days.length == newDaysArr.length) {
            return 'selbut-selected'
          }
        }
      }
    },
    selectedCar (car, day, index) {
      // 点击选择
      let selectedArr = this.selectedArr
      // 是否有数组
      if (selectedArr.length) {
        for (var i in selectedArr) {
          let item = selectedArr[i]
          // 是否有添加该车辆的日期
          if (car.car_info.car_id == item.car_id) {
            let days = item.days
            for (var j in days) {
              let childItem = days[j]
              // 是否点击全选
              if (index == 0) {
                // 是全选还是取消全选                  
                // 过滤掉忙碌和选中的数据
                let newDaysArr = []
                let carDays = car.days
                carDays.forEach(m => {
                  if (m.date && m.is_busy != 1) {
                    newDaysArr.push(m.date)
                  }
                })
                if (days.length == newDaysArr.length) {
                  this.selectedArr.splice(i, 1)
                  return
                } else {
                  selectedArr[i].days = newDaysArr
                  return
                }
              } else {
                // 是否是同样的日期              
                if (childItem == day.date) {
                  // 如果长度为1取消选中
                  if (days.length == 1) {
                    this.selectedArr.splice(i, 1)
                  } else {
                    days.splice(j, 1)
                  }
                  return
                } else {
                  if (days.length - 1 == j) {
                    if (day.is_busy != 1) {
                      days.push(day.date)
                    } else {
                      this.$message({ showClose: true, message: '无法选择繁忙日期', type: 'error' })
                    }
                    return
                  }
                }
              }
            }
          } else {
            // 1 还未循环到, 2确实未添加该车辆日期,         
            if (selectedArr.length - 1 > i) continue
            let days = []
            if (index == 0) {
              car.days.forEach(ele => {
                if (ele.date && ele.is_busy != 1) {
                  days.push(ele.date)
                }
              })
            } else {
              if (day.is_busy != 1) {
                days.push(day.date)
              } else {
                this.$message({ showClose: true, message: '无法选择繁忙日期', type: 'error' })
                return
              }
            }
            let obj = Object.assign({}, { days: days }, car.car_info, { user_car_id: car.car_info.car_id })
            this.selectedArr.push(obj)
            return
          }
        }
      } else {
        let days = []
        if (index == 0) {
          car.days.forEach(ele => {
            if (ele.date && ele.is_busy != 1) {
              days.push(ele.date)
            }
          })
        } else {
          if (day.is_busy != 1) {
            days.push(day.date)
          } else {
            this.$message({ showClose: true, message: '无法选择繁忙日期', type: 'error' })
            return
          }
        }
        let obj = Object.assign({}, { days: days }, car.car_info, { user_car_id: car.car_info.car_id })
        this.selectedArr.push(obj)
      }
    },
    getCarsInfo () {
      let params = Object.assign({}, this.dateInterval, this.carParams)
      let order_id = this.$route.query.order_id
      if(this.dateInterval.order_type == '2'){
        params.is_all_free='1'
      }
      if (order_id) params.order_id = order_id
      this.$axios.get(this.$httpUrl.scheduleList, { params: params }).then(res => {
        if (res != false) {
          let list = res.list
          let inCarList = []
          let outCarList = []
          if (list.length) {
            let selectedArr = this.selectedArr
            // 选择车辆的数组
            if (selectedArr.length) {
              // 选中的车辆置顶
              for (var i = selectedArr.length - 1; i >= 0; i--) {
                let ele = selectedArr[i]
                let obj = null;
                let itemIndex = list.findIndex(function (value, index, arr) {
                  obj = value
                  return ele.user_car_id == value.car_info.car_id
                })
                list.splice(itemIndex, 1)

                list.unshift(obj)
              }
            } else {
              let carInfoObj = this.carInfo[0]
              let user_car_id = carInfoObj.user_car_id
              if (user_car_id && !carInfoObj.begin_at_date) {
                let obj = null;
                let itemIndex = list.findIndex(function (value, index, arr) {
                  obj = value
                  return user_car_id == value.car_info.car_id
                })
                list.splice(itemIndex, 1)
                list.unshift(obj)
              }
            }
            list.forEach(ele => {
              if (ele.car_info.car_source == 1) {
                inCarList.push(ele)
              } else {
                outCarList.push(ele)
              }
            })
          } else {

          }
          this.inCarList = inCarList
          this.outCarList = outCarList
        } else {
          this.boxShow = false
        }

      })
    }
  }
}
</script>
<style lang="less" scope>
.carstyle {
  & /deep/ .el-dialog {
    width: 400px;
    height: 650px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      padding: 0px;
    }
    .el-tabs__nav-wrap::after {
      height: 0;
    }
    .el-tabs__header {
      margin: 0px;
    }
    .el-tabs__item {
      height: 50px;
      line-height: 50px;
    }
    .el-radio-button__inner {
      padding: 7px 13px;
      background-color: transparent;
      border: none;
    }
    .el-radio-button__orig-radio:checked + .el-radio-button__inner {
      background-color: @theme-color;
    }
  }
  .item {
    margin-top: 10px;
    margin-right: 170px;
  }
  .el-badge__content.is-fixed {
    top: -10px;
    right: 12px;
    background-color: @yellow;
  }
  .dis {
    display: inline-block;
  }
  .fr {
    float: right;
    margin-right: 9px;
  }
  .fl {
    float: left;
    margin-left: 8px;
  }
  .marl {
    margin-left: 21px;
  }
  .state {
    border-radius: 50px;
    color: @color-white;
    font-size: @fontsize-small;
    width: 18px;
    height: 18px;
    text-align: center;
    line-height: 18px;
    vertical-align: 1px;
    &-halfbusy {
      background-color: @yellow;
    }
    &-free {
      background-color: @theme-color;
    }
    &-busy {
      background-color: @red;
    }
  }
  .dialog-header {
    margin: 0 18px;
    position: relative;
    .iconfont-close {
      position: absolute;
      right: -20px;
      top: 0;
      font-size: 20px;
      padding: 10px;
      color: #666;
      cursor: pointer;
    }
  }
  .dialog-bd {
    background-color: #f6f6f6;
    height: 551px;
    .sel-warp {
      text-align: center;
      .sel-top {
        padding-top: 12px;
        .hint {
          .dis;
          width: 19px;
          height: 17px;
          color: @yellow;
        }
      }
      .sel-bot {
        margin: 13px 0 11px 0;
        .btn {
          width: 57px;
          height: 30px;
        }
      }
      .search-wrap {
        margin: 10px auto 0;
        .inp-search {
          width: 280px;
          margin-right: 10px;
        }
        .btn-search {
          display: inline-block;
          width: 60px;
        }
      }
      .el-autocomplete {
        width: 100%;
        box-sizing: border-box;
        margin-top: 10px;
        padding: 0 24px;
      }
    }
    .details-warp {
      overflow: auto;
      height: 420px;
      box-sizing: border-box;
      &.padding {
        padding-bottom: 32px;
      }
      .sel-seat {
        width: 372px;
        background-color: #fff;
        margin: 0 13px 10px 15px;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        .seat-top {
          border: 1px solid #eeeeee;
          height: 24px;
          padding: 10px 12px 0 19px;
          .plate {
            float: left;
            color: #333;
            font-size: 16px;
          }
          .driver {
            float: right;
            color: #666;
          }
          .fleet {
            float: left;
            margin-left: 10px;
          }
        }
        .seat-bot {
          overflow: hidden;
          padding-top: 10px;
          .selbut {
            float: left;
            overflow: hidden;
            background-color: #fff;
            width: 74px;
            height: 31px;
            line-height: 31px;
            border-radius: 4px;
            box-sizing: border-box;
            border: 1px solid #d4d4d4;
            margin: 0 0 10px 40px;
            color: #333;
            position: relative;
            text-align: center;
            &.selbut-selected {
              border-color: @theme-color;
              .selected-wrap {
                display: block;
              }
            }
            .selected-wrap {
              position: absolute;
              top: -15px;
              left: -20px;
              width: 30px;
              height: 30px;
              background-color: @theme-color;
              transform: rotate(45deg);
              display: none;
              & .iconfont-gou {
                position: absolute;
                right: -4px;
                top: -4px;
                color: #fff;
                transform: scale(0.8) rotate(-45deg);
              }
            }
          }
        }
      }
    }
  }
  .dialog-foot {
    position: absolute;
    bottom: 0;
    left: 0;
    height: 48px;
    background-color: #fff;
    width: 100%;
    .selected {
      border-radius: 50px;
      height: 58px;
      width: 58px;
      line-height: 58px;
      margin-top: -20px;
      padding: 0;
    }
    .affirm {
      width: 149px;
      height: 50px;
      line-height: 50px;
      float: right;
      margin-bottom: 0px;
      border-radius: 0;
    }
  }
}
</style>

