<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="reason" :close-on-click-modal= false>
      <div class="dialog-header" slot="title">
        <p class="title">拒绝原因</p>
      </div>
      <div class="dialog-bd">
        <el-input type="textarea" :rows="3" v-model="info" placeholder="请输入原因"></el-input>
      </div>
      <div slot="footer" class="dialog-foot">
        <a href="javascript:void(0);" class="btn cancel" @click="closeBox">取消</a>
        <a href="javascript:void(0);" class="btn" @click="submitForm">确认</a>
      </div>
    </el-dialog>
  </div>

</template>
<script>
export default {
  name: 'RefuseReasonBox',
  props: {
    refuseReasonBoxShow: {
      type: Boolean,
      default: false
    },
    sendOrderInfo: {
      type: Object,
      default: {}
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:refuseReasonBoxShow', newVal)
      this.totalprice = ''
    },
    refuseReasonBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.refuseReasonBoxShow,
      info: ''
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    submitForm () {
      let info = this.info
      if (!info) {
        this.$message('原因不能为空')
        return
      }
      let obj = Object.assign({},this.sendOrderInfo, {remark: info})
      this.$emit("passReason", obj)
    }
  }
}
</script>
<style lang="less" scope>
.reason {
  & /deep/ .el-dialog {
    width: 354px;
    height: 238px;
    .el-dialog__body {
      padding: 0px;
      height: 76px;
      padding-top: 31px;
    }
    .el-dialog__footer {
      text-align: center;
      padding: 0;
    }
    .dialog-bd {
      margin: 0 20px;
      .el-input__inner {
        width: 220px;
        height: 36px;
        background-color: #f5f5f5;
        margin-left: 67px;
      }
    }
  }
  .dialog-foot {
    margin-top: 20px;
    margin-bottom: 10px;
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
}
</style>

