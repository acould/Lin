<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="modify">
      <div class="dialog-header" slot="title">
        <p class="title">修改价格</p>
      </div>
      <div class="dialog-bd">
        <el-input v-if="!isFleetPrice" key="other" type="text" v-model="totalprice" placeholder="请输入订单总价"></el-input>
        <el-input v-if="isFleetPrice" key="fleet" type="text" v-model="totalprice" placeholder="请输入退款金额"></el-input>
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
  name: 'modifyPriceBox',
  props: {
    modifyPriceBoxShow: {
      type: Boolean,
      default: false
    },
    orderId: {
      type: Number,
      default: 0
    },
    isFleetPrice: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:modifyPriceBoxShow', newVal)
      if (newVal === false) {
        this.totalprice = null
      }
    },
    modifyPriceBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.modifyPriceBoxShow,
      totalprice: ''
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    submitForm () {
      let totalprice = this.totalprice
      let reg = /^([0]|([1-9]\d*))((\.\d*){0,1})$/g
      if (!reg.test(totalprice) && totalprice) {
        let msg;
        if (totalprice < 0) {
          msg = '订单金额不能小于0'
        } else {
          msg = '请输入正确的订单金额'
        }
        this.$message({
          message: msg,
          type: 'error'
        })
        return
      }
      if (this.isFleetPrice) {
        this.$emit('getNewMoney', this.totalprice)
        this.closeBox()
        return
      }
      if (this.totalprice <= 0) {
        this.$message({
          message: '请输入新的订单金额',
          type: 'error'
        })
        return false
      }
      let params = {
        'order_id': this.orderId,
        'money': this.totalprice
      }
      this.$axios.post(this.$httpUrl.billModifyMoney, params).then(res => {
        if (res != false) {
          this.$message({
            message: '订单金额修改成功',
            type: 'success'
          })
          this.$emit('getNewMoney', this.totalprice)
          this.closeBox()
        }
      })
    }
  }
}
</script>
<style lang="less" scope>
.modify {
  & /deep/ .el-dialog {
    width: 354px;
    height: 218px;
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
      .el-input__inner {
        width: 220px;
        height: 36px;
        background-color: #f5f5f5;
        margin-left: 67px;
      }
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
}
</style>

