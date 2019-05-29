<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="backlog-date-box" :close-on-click-modal=false :show-close=false>
      <div class="dialog-header" slot="title">
        <p class="title">温馨提示</p>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <p>
          您可以选择设置定时提醒的时间，方便及时安排任务!
        </p>
        <el-date-picker class="remind-date"  popper-class="redwarm-select2"   ref="remindDate" v-model="date" value-format="yyyy-MM-dd HH:mm" @change='getRemindDate' :picker-options="pickerOptions1" type="datetime">
        </el-date-picker>
      </div>
      <div slot="footer" class="dialog-foot">
        <a href="javascript:void(0);" class="btn cancel" @click="defaultHandler">取消</a>
        <a href="javascript:void(0);" class="btn" @click="submitForm">确认</a>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'backlogDateBox',
  props: {
    backlogDateBoxShow: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    boxShow (newVal) {
      this.clearDateVal ()
      this.$emit('update:backlogDateBoxShow', newVal)
    },
    backlogDateBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.backlogDateBoxShow,
      date: null,
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
    }
  },
  created () {

  },
  methods: {
    clearDateVal () {
      this.date = null
    },
    defaultHandler () {
      let optionObj = {
        type: 1
      }
      this.$emit('rtnBacklogSetOption', optionObj)
      this.closeBox()
    },
    submitForm () {
      this.$refs.remindDate.focus()
    },
    closeBox () {
      this.boxShow = false
    },
    getRemindDate (date) {
      const compareDate = this.$util.compareDate2
      if (!compareDate(date)) {
        let optionObj = {
           type: 2,
           date: date
        }
        this.$emit('rtnBacklogSetOption', optionObj)
        this.closeBox()
      } else {
        this.$message({
          message: '定时提醒时间必须大于当前时间',
          type: 'error'
        })
      }
    }
  }
}
</script>
<style lang="less" scoped>
.backlog-date-box {
  & /deep/ .el-dialog {
    width: 394px;
    height: 200px;
    border-radius: 4px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      text-align: center;
      padding: 0;
    }
  }
  .dialog-header {
    position: relative;
    .title {
      font-size: @fontsize-large;
      color: @color-dark-grey;
      padding-left: 0;
    }
    .iconfont-close {
      position: absolute;
      right: 0;
      top: 4px;
      padding: 10px;
      color: @color-grey;
      font-size: 18px;
      cursor: pointer;
    }
  }
  .dialog-bd {
    p {
      color: #333;
      margin: 30px 0;
      font-size: 14px;
      text-align: center;
    }
  }
  .dialog-foot {
    margin-bottom: 25px;
    .btn {
      width: 120px;
      height: 36px;
      display: inline-block;
      line-height: 36px;
    }
    .cancel {
      background-color: #fff;
      border: 1px solid #999999;
      color: #333;
      margin-right: 35px;
    }
  }
  .remind-date {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 9;
    opacity: 0;
  }
}
</style>

