<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="notify">
      <div class="dialog-header" slot="title">
        <p class="title">请选择通知方式</p>
      </div>
      <div class="dialog-bd">
        <div class="autonotify">
          <el-radio v-model="radio" label="1">自动通知</el-radio>
          <el-popover placement="bottom-start" width="289" trigger="hover" content="系统自动将行程信息和客户信息
发送给司机
司机确认后,将司机和车辆信息发给订车人">
            <span class="iconfont icon-shuoming explain" slot="reference"></span>
          </el-popover>
        </div>
        <div class="marbot">
          <el-radio v-model="radio" label="2">只通知司机</el-radio>
        </div>
        <div class="marbot">
          <el-radio v-model="radio" label="3">只通知订车人</el-radio>
        </div>
        <div class="marbot">
          <el-radio v-model="radio" label="4">都不通知</el-radio>
        </div>
      </div>
      <div slot="footer" class="dialog-foot">
        <a href="javascript:void(0);" class="btn" @click="confirm">确认选择</a>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'notifyStyleBox',
  props: {
    notifyStyleBoxShow: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:notifyStyleBoxShow', newVal)
    },
    notifyStyleBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.notifyStyleBoxShow,
      radio: '1'
    }
  },
  methods: {
    confirm () {
      this.$emit('comfirmNotify', this.radio)
      this.boxShow = false
    }
  }
}
</script>
<style lang="less" scope>
.notify {
  & /deep/ .el-dialog {
    width: 400px;
    height: 331px;
  }
  & /deep/ .el-dialog__body {
    padding: 0px;
  }
  & /deep/ .el-dialog__footer {
    text-align: center;
    padding: 0;
  }
  .dialog-bd {
    margin: 26px 0 0 94px;
    .autonotify {
      .marbot;
      .explain {
        margin-left: 20px;
      }
    }
    .marbot {
      margin-bottom: 32px;
    }
  }
  .dialog-foot {
    margin-bottom: 22px;
    .btn {
      width: 160px;
      height: 40px;
      display: inline-block;
      line-height: 40px;
    }
    .cancel {
      background-color: #fff;
      border: 1px solid #999999;
      color: #333;
      margin-right: 41px;
    }
  }
}
</style>

