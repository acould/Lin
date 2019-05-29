<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="FleetPreviewBox" :show-close=false>
      <div class="dialog-header" slot="title">
        <p class="title">预览</p>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <div class="line-wrap">
          <div class="line">
            <div class="line-con">
              <div class="inp-wrap">
                <el-input type="text" ref="inp" @focus="inpFocus" v-model="previewInfo.url" readonly></el-input>
              </div>
              <a href="javascript:void(0);" class="btn" @click="copyUrl">复制链接</a>
            </div>
          </div>
          <div class="line">
            <div class="line-con">
              <div class="qrcode-wrap">
                <img :src="previewInfo.image" alt="" class="qrcode">
                <p>
                  <span>手机扫描二维码 </span>
                  <span class="txt">或</span>
                  <span>复制链接发送给好友</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        <p class="tip">您可以发布订单查看价格是否正确</p>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'fleetPreviewBox',
  props: {
    fleetPreviewBoxShow: {
      type: Boolean,
      default: false
    },
    previewInfo: Object
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:fleetPreviewBoxShow', newVal)
    },
    fleetPreviewBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.fleetPreviewBoxShow
    }
  },
  methods: {
    inpFocus () {
      this.$refs.inp.select()
    },
    copyUrl () {
      let inp = this.$refs.inp
      inp.focus()
      inp.select()
      document.execCommand('copy');
      this.$message({
        message: '复制成功',
        type: 'success'
      });
    },
    closeBox () {
      this.boxShow = false
    }
  }
}
</script>
<style lang="less" scope>
.FleetPreviewBox {
  & /deep/ .el-dialog {
    width: 446px;
    height: 263px;
    border-radius: 4px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      text-align: center;
      padding: 0;
    }
    .el-input__inner {
      height: 32px;
      line-height: 32px;
      border-right: 0;
      border-radius: 0;
    }
  }
  .dialog-header {
    position: relative;
    .title {
      font-size: @fontsize-large;
      color: @color-dark-grey;
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
    .line-wrap {
      padding: 0 30px 0 30px;
      text-align: center;
    }
    .line {
      display: inline-block;
      .line-con {
        margin-top: 10px;
        overflow: hidden;
        .inp-wrap {
          float: left;
          width: 230px;
        }
        .btn {
          float: left;
          width: 85px;
          border-radius: 0;
        }
        .qrcode-wrap {
          margin-left: 14px;
          .qrcode {
            width: 104px;
            height: 104px;
            margin-right: 47px;
            vertical-align: middle;
          }
          p {
            font-size: 14px;
            color: #333;
            display: inline-block;
            vertical-align: middle;
            .txt {
              display: block;
              margin: 10px 0;
            }
          }
        }
      }
    }
    .tip {
      margin-top: 15px;
      color: @red;
      text-align: center;
    }
  }
}
</style>

