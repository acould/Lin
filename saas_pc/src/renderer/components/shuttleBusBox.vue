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
          <div v-for="(car, index) in inCarList" :key="index">
            <div  class="sel-seat" href="javascript:void(0);" :class="selectedClass(car,index)"   @click="selectedCar(car, index)">
              <div class="selected-wrap">
                  <span class="iconfont icon-gou5 iconfont-gou"></span>
              </div>
              <div class="seat-top">
                <span class="plate">{{car.car_number}}</span>
                <span class="seatNum">{{car.seat_num}}座</span>
                <span class="carName">{{car.car_type}}</span>
              </div>
              <div class="seat-bot">
                <span class="driverName">司机：{{car.driver_name}}</span>
                <span class="carMobile">{{car.driver_mobile}}</span>
              </div>
            </div>
              
          </div>
        </div>
        <div class="details-warp" v-show="car_source == 2">
          <div v-for="(car, index) in outCarList" :key="index">   
              <div class="sel-seat" href="javascript:void(0);" :class="selectedClass(car,index)"  @click="selectedCar(car, index)">
                <div class="selected-wrap">
                    <span class="iconfont icon-gou5 iconfont-gou"></span>
                  </div>
                <div class="seat-top">
                  <span class="plate">{{car.car_number}}</span>
                  <span class="seatNum">{{car.seat_num}}座</span>
                  <span class="carName">{{car.car_type}}</span>
                </div>
                <div class="seat-bot">
                  <span class="driverName">司机：{{car.driver_name}}</span>
                  <span class="carMobile">{{car.driver_mobile}}</span>
                </div>
              </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-foot">
        <el-badge :value="carsArr.length" class="item">
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
    carInfoArr: {
      type: Array,
      default: null
    },
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
      carsArr:[]
    }
  },
  computed: {
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
    carInfoArr (newVal) {
      
      if(newVal.length){
          this.carsArr =newVal
      }else{
        this.carsArr=[]
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
      this.$emit('rtnCarDaysInfo', this.selectedArr)
    },
    filterDateArea () {
      let selectedArr = this.carsArr
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
        
      })
    },
    rtnSelectedList () {
      this.filterDateArea()
      this.$emit('rtnSelectedList', this.carsArr)
    },
    selectedClass (car,index) {     
      let selectedArr = this.carsArr
      let user_car_id= car.user_car_id
      for (var k in selectedArr) {
        let item = selectedArr[k]
          if (user_car_id == item.user_car_id) {
            return 'selbut-selected'     
          }
      }
    },
    selectedCar (car, index) {
      let selectedArr =this.carsArr
      // 是否有数组
      if (selectedArr.length) {
          var addCarId= []
          for(let z=0;z<selectedArr.length;z++){
            addCarId.push(selectedArr[z].user_car_id)
            if(selectedArr[z].user_car_id==car.user_car_id){
              selectedArr.splice(z,1)
            }    
          }
          if(!addCarId.join(',').includes(car.user_car_id)){selectedArr.push(car)}
       }else{
        selectedArr.push(car)
       }
       this.selectedArr = selectedArr
    },
    // 置顶数组
    getCarsInfo () {
      let params = Object.assign({}, this.dateInterval, this.carParams)
      let order_id = this.$route.query.order_id
      if (order_id) params.order_id = order_id
      this.$axios.get(this.$httpUrl.shuttleBusList, { params: params }).then(res => {
        if (res != false) {
          let list = res.list
          let inCarList = []
          let outCarList = []
          if (list.length) {
            let selectedArr = this.carsArr
            // 选择车辆的数组
            if (selectedArr.length) {
              // 选中的车辆置顶
              for (var i = selectedArr.length - 1; i >= 0; i--) {
                let ele = selectedArr[i]
                let obj = null;
                let itemIndex = list.findIndex(function (value, index, arr) {
                  obj = value
                  return ele.user_car_id == value.user_car_id
                })
                list.splice(itemIndex, 1)

                list.unshift(obj)
              }
            } 
            list.forEach(ele => {
              if (ele.car_source == 1) {
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
    .selbut-selected{
      border:1px solid #0db592;
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
        border-radius: 4px;
        position: relative;
        overflow: hidden;
        &.selbut-selected {
          border-color:1px solid red;
          .selected-wrap {
            display: block;
          }
        }
         .selected-wrap {
            position: absolute;
            top: -17px;
            left: -15px;
            width: 30px;
            height: 30px;
            background-color:#0db592;
            transform: rotate(45deg);
            display: none;
            & .iconfont-gou {
              position: absolute;
              right: -3px;
              top: 7px;
              color: #fff;
              transform: scale(0.8) rotate(-45deg);
            }
          }
        .seat-top {
          border-bottom: 1px solid #eeeeee;
          height: 24px;
          padding: 10px 12px 0 19px;
          .plate{
            font-size: 16px;
            color:#333333;
          }
          .seatNum{
            margin-left: 10px;
          }
          .carName{
            margin-left: 10px;
          }
        }
        .seat-bot {
          overflow: hidden;
          padding-top: 10px;
          padding-bottom: 10px;
          .driverName{
              margin-left: 21px;
            }
            .carMobile{
              margin-left: 23px;
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

