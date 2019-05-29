<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="prevewInfoBox" :close-on-click-modal=false :show-close=false>
      <div class="dialog-header" slot="title">
        <p class="title">预览</p>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <div class="line-wrap" >
          <div class="line">
            <p class="line-tt">电脑端链接分享</p>
            <div class="line-con">
              <div class="inp-wrap">
                <el-input type="text" ref="inp" @focus="inpFocus" v-model="previewInfo.web_url" readonly></el-input>
              </div>
              <a href="javascript:void(0);" class="btn" @click="copyUrl">复制链接</a>
            </div>
          </div>
          <div class="line">
            <p class="line-tt">微信小程序预览</p>
            <div class="line-con">
              <div class="qrcode-wrap">
                <img :src="previewInfo.qrcode" alt="" class="qrcode">
                <p>
                  <span class="txt">扫描二维码，打开页面 </span>
                  <span class="txt">后点击右上角
                    <span class="dot dot-s"></span>
                    <span class="dot dot-b"></span>
                    <span class="dot dot-s"></span>分享
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'prevewInfoBox',
  props: {
    prevewInfoBoxShow: {
      type: Boolean,
      default: false
    },
    previewInfo: Object
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:prevewInfoBoxShow', newVal)
    },
    prevewInfoBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.prevewInfoBoxShow 
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
.prevewInfoBox {
  & /deep/ .el-dialog {
    width: 483px;
    height: 390px;
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
      padding: 0 30px 0 36px;
    }
    .line {
      .line-tt {
        font-size: 14px;
        color: #333;
        position: relative;
        padding: 22px 0 22px 15px;
        &::before {
          content: '';
          position: absolute;
          top: 22px;
          left: 0;
          width: 4px;
          height: 15px;
          background-color: @theme-color;
        }
      }
      .line-con {
        overflow: hidden;
        .inp-wrap {
          float: left;
          margin-left: 14px;
          width: 318px;
          padding-bottom: 15px;
        }
        & /deep/ .el-input__inner {
          height: 32px;
          line-height: 32px;
          border-right: 0;
          border-radius: 0;
        }
        .btn {
          float: left;
          width: 85px;
          border-radius: 0;
        }
        .qrcode-wrap {
          margin-left: 14px;
          .qrcode {
            width: 138px;
            height: 138px;
            margin-right: 47px;
            vertical-align: middle;
          }
          p {
            font-size: 14px;
            color: #333;
            display: inline-block;
            .txt {
              display: block;
            }
            .dot {
              background-color: #000;
              border-radius: 50%;
              font-size: 0;
              vertical-align: middle;
              &.dot-b {
                padding: 3px;
                margin: 0 2px;
              }
              &.dot-s {
                padding: 2px;
                &:first-of-type {
                  margin-left: 4px;
                }
                &:last-of-type {
                  margin-right: 4px
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>

