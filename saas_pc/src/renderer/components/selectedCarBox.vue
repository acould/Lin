<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="selectCar" :close-on-click-modal="false" :show-close="false" top="5vh">
      <div class="dialog-header" slot="title">
        <p class="dis sign">|</p>
        <p class="title dis">已选择车辆和出行日期</p>
        <span class="iconfont icon-close iconfont-close" @click="rtnCarArrangeBox"></span>
      </div>
      <div class="dialog-bd">
        <ul>
          <li class="list-warp" v-for="(car, index) in arrangeInfoArr" :key="index">
            <div class="list-left">
              <p class="dis limitwidth">{{car.car_number}} {{car.seat_num}}座</p>
              <p class="dis" :class="car.car_source == 1 ? 'owner' : 'foreign'">{{car.car_source == 1 ? '自有' : '外援'}}</p>
            </div>
            <div class="list-medium">
              <span v-for="(day ,index) in car.days" :key="index">
                {{day.substring(5).replace('-','.')}} {{(car.days.length -1 > index) ? ',' : ''}}
              </span>
            </div>
            <div class="list-right">
              <span class="icon-jian iconfont iconfont" @click="removeCarArrange(index)"></span>
            </div>
          </li>
        </ul>
      </div>
      <div slot="footer" class="dialog-foot">
        <el-badge :value="arrangeInfoArr.length" class="item ">
          <el-button size="small" class="selected btn" @click="rtnCarArrangeBox">已选择</el-button>
        </el-badge>
        <a href="javascript:void(0);" class="btn affirm" @click="confirm">确认选择</a>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'selectedCarBox',
  props: {
    selectedCarBoxShow: {
      type: Boolean,
      default: false
    },
    arrangeInfo: {
      type: Array,
      default: null
    },
    dateInterval: {
      start_time: null,
      end_time: null
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:selectedCarBoxShow', newVal)
    },
    selectedCarBoxShow (newVal) {
      this.boxShow = newVal
    },
    arrangeInfo (newVal) {
      this.arrangeInfoArr = newVal
    }
  },
  data () {
    return {
      boxShow: this.selectedCarBoxShow,
      arrangeInfoArr: [],
    }
  },
  methods: {
    rtnCarArrangeBox () {
      this.boxShow = false
    },
    removeCarArrange (index) {
      this.arrangeInfoArr.splice(index, 1)
    },
    confirm () {
      if (!this.arrangeInfo.length) {
        this.$message({ showClose: true, message: '请选择车辆日期', type: 'error' })
        return
      }
      let params = Object.assign({}, { car_info: this.arrangeInfo }, this.dateInterval)
      let order_id = this.$route.query.order_id
      if (order_id) params.order_id = order_id
      
      this.$axios.post(this.$httpUrl.carDays, params).then(res => {
        if (res != false) {
          this.arrangeInfo.forEach((ele, i, item) => {
            if (ele.is_busy_new && ele.is_busy_new !== 0) {
              this.$alert('你所选车辆中间已有任务，系统给你自动拆分', {
                confirmButtonText: '确定'
              })
              return
            }
          })
          var newRes = res.map(val => {
            val.vice_drivers = []
            this.$delete(val,'is_busy_new')
            return  val
          })
          this.$emit('rtnCarDaysInfo', newRes)
        }
      })
    }
  }
}
</script>
<style lang="less" scope>
.selectCar {
  & /deep/ .el-dialog {
    height: 650px;
    width: 399px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      padding: 0px;
    }
    .item {
      margin-top: 10px;
      margin-right: 170px;
    }
  }
  .el-badge__content.is-fixed {
    top: -10px;
    right: 12px;
    background-color: @yellow;
  }
  .dis {
    display: inline-block;
  }
  .dialog-header {
    text-align: left;
    position: relative;
    .sign {
      color: @theme-color;
      background-color: @theme-color;
      margin: 0 6px 0 13px;
      line-height: 20px;
      height: 20px;
      .title {
        padding: 0px;
      }
    }
    .iconfont-close {
      position: absolute;
      right: 0;
      top: 0;
      font-size: 20px;
      padding: 10px;
      color: #666;
      cursor: pointer;
    }
  }
  .dialog-bd {
    height: 552px;
    background-color: #f5f5f5;
    .list-warp {
      display: table-row;
      border-bottom: 1px solid #ffffff;
      .list-left {
        padding-left: 29px;
        width: 178px;
        box-sizing: border-box;
        .foreign {
          width: 38px;
          height: 18px;
          background-color: @yellow;
          color: @color-white;
          line-height: 18px;
          margin-left: 6px;
          text-align: center;
        }
        .owner {
          width: 38px;
          height: 18px;
          background-color: @theme-color;
          color: @color-white;
          line-height: 18px;
          margin-left: 6px;
          text-align: center;
        }
      }
      .list-left,
      .list-medium,
      .list-right {
        display: table-cell;
        vertical-align: middle;
        padding-top: 10px;
        padding-bottom: 10px;
      }
      .list-medium {
        width: 170px;
        box-sizing: border-box;
        padding-left: 10px;
        padding-right: 10px;
      }
      .list-right {
        width: 40px;
        text-align: center;
      }
      .icon-jian {
        color: #ff7200;
        padding: 10px 17px 10px 9px;
        cursor: pointer;
      }
      .limitwidth {
        width: 100px;
      }
    }
  }
  .dialog-foot {
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

