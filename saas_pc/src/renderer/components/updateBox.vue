<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="update-box" :show-close=false :close-on-click-modal=false :close-on-press-escape=false>
      <div class="dialog-header" slot="title">
        <img :src="updateBgsrc" class="bg">
        <span class="iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <h3>升级到新版本</h3>
        <div class="update-con">
          {{updateInfo && updateInfo.new_text}}
        </div>
        <span class="tip-mark" v-if="isWaitUpdate">请等待安装包下载完成后，重新安装车队助手</span>
      </div>
      <div slot="footer" class="dialog-foot">
        <a href="javascript:void(0);" class="btn" :class="isTiming ? 'btn-disabled': ''" @click="update">{{btnText}}</a>
      </div>
    </el-dialog>
    <div class="mask-layer" v-show="updateInfo && updateInfo.force_update">
    </div>
  </div>
</template>
<script>
import updateBg from '@/assets/images/updateBg.png'
export default {
  name: 'updateBox',
  props: {
    updateBoxShow: {
      type: Boolean,
      default: false
    },
    updateInfo: {
      type: Object,
      default: null
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('updateBoxShow', newVal)
    },
    updateBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.updateBoxShow,
      updateBgsrc: updateBg,
      btnText: '立即升级',
      isWaitUpdate: false,
      isTiming: false,
      timer: null
    }
  },
  created () {

  },
  methods: {
    closeBox () {
      if (this.updateInfo.force_update) {
        this.$confirm('该版本不更新无法继续使用，是否确认退出？', {
          type: 'warning'
        }).then(() => {
          this.$windowUtil.closeAllWin()
        }).catch();
      } else {
        this.boxShow = false
      }  
    },
    countdown (s) {
      if (s === 0) {
        this.isTiming = false
        this.btnText = '确定'
      } else {
        this.isTiming = true
        this.btnText = '确定(' + s + ')s'
        this.timer = setTimeout(function () {
          this.countdown(--s)
        }.bind(this), 1000)
      }
    },
    update () {
      if (this.updateInfo.force_update) {
        if (!this.isWaitUpdate) {
          this.isWaitUpdate = true
          window.location.href = this.updateInfo.url
          this.countdown(60)
        } else {
          if (this.isTiming) return
          else {
            this.$windowUtil.closeAllWin()
          }
        }
      } else {
        if (this.updateInfo.need_update) {
          window.location.href = this.updateInfo.url
        }             
      }          
    }
  }
}
</script>
<style lang="less" scope>
.mask-layer {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 99;
}
.update-box {
  & /deep/ .el-dialog {
    width: 279px;
    height: 360px;
    border-radius: 8px;
    .el-dialog__body {
      padding: 0px;
      padding-top: 20px;
    }
    .el-dialog__footer {
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
  .dialog-header {
    position: relative;
    .bg {
      width: 279px;
      height: 146px;
      margin-top: -24px;
    }
    .iconfont-close {
      width: 27px;
      height: 27px;
      position: absolute;
      right: -26px;
      top: -12px;
      padding: 10px;
      background: url('@{componentImgSrc}/close.png') no-repeat left top / 27px
        27px;
      cursor: pointer;
    }
  }
  .dialog-bd {
    text-align: center;
    h3 {
      color: #000;
      font-size: 16px;
    }
    .update-con {
      height: 144px;
      word-break: break-all;
      color: #333;
      font-size: 14px;
      padding: 20px 20px 30px 20px;
      box-sizing: border-box;
      position: relative;
    }
    .tip-mark {
      position: absolute;
      width: 100%;
      left: 0;
      bottom: 60px;
      color: red;
      text-align: center;
      font-size: 13px;
    }
  }
  .dialog-foot {
    margin-bottom: 25px;
    text-align: center;
    .btn {
      width: 165px;
      height: 35px;
      display: inline-block;
      line-height: 35px;
    }
  }
}
</style>

