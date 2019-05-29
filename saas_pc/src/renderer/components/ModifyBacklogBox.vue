<template>
  <el-dialog :visible.sync="boxShow" class="modifybacklog">
    <div class="dialog-header" slot="title">
    </div>
    <div class="con-wrap">
      <el-input class="mark" maxlength="120" type="textarea" :rows="4" placeholder="请输入文字信息添加待办事项" v-model="content">
      </el-input>
      <p class="tip">{{content.length}}/120</p>
    </div>
    <div slot="footer" class="dialog-foot">
      <a href="javascript:void(0);" class="hanlder cancel" @click="closeBox">取消</a>
      <a href="javascript:void(0);" class="hanlder" @click="submitForm">保存</a>
    </div>
  </el-dialog>
</template>
<script> 
import qrCode from '@/assets/images/QRcode.png'
export default {
  name: 'modifyBacklogBox',
  props: {
    modifyBacklogShow: {
      type: Boolean,
      default: false
    },
    editIndex: {
      type: Number,
      default: null
    },
    previousCon: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      boxShow: this.modifyBacklogShow,
      qrCodeSrc: qrCode,
      content: ''
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:modifyBacklogShow', newVal)
    },
    modifyBacklogShow (newVal) {
      this.boxShow = newVal
    },
    previousCon (newVal) {
      this.content  = newVal
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    submitForm () {
      if (!this.content) {
        this.$message({
          message: '内容不能为空',
          type: 'error'
        })
        return
      }
      let info = { index: this.editIndex, con: this.content }
      this.$emit('sendCon', info)
    }
  }
}
</script>
<style lang="less" scoped>
.modifybacklog {
  & /deep/ .el-dialog {
    width: 343px;
    height: 218px;
    padding: 0;
    .el-dialog__body {
      padding: 0;
    }
    .el-dialog__footer {
      padding: 0;
    }
    .el-textarea__inner {
      padding-bottom: 23px;
      box-sizing: border-box;
      letter-spacing: 1px;
    }
  }
  .dialog-header {
    height: 40px;
  }
  .con-wrap {
    margin: 0 auto;
    width: 316px;
    position: relative;
    .tip {
      position: absolute;
      bottom: 10px;
      right: 30px;
      color: #999;
      font-size: 14px;
    }
  }
  .dialog-foot {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    overflow: hidden;
    border-top: 1px solid #eee;
    .hanlder {
      float: left;
      width: 50%;
      height: 46px;
      display: inline-block;
      line-height: 46px;
      font-size: 14px;
      color: #444;
      text-align: center;
    }
    .cancel {
      background-color: #fff;
      border-right: 1px solid #eee;
      box-sizing: border-box;
      color: @theme-color;
    }
  }
}
</style>
