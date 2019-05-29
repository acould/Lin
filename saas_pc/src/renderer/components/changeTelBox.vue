<template>
  <el-dialog :visible.sync="boxShow" class="resetTelbox" :close-on-click-modal="false">
    <div class="dialog-header" slot="title">
      <p class="title">更换手机号</p>
    </div>
    <div class="dialog-bd">
      <div class="curtel-box tel-box" v-show="step == 1">
        <el-form :model="curTelForm" status-icon :rules="changeTelRules" ref="boxStep1">
          <el-form-item prop="mobile" :rules="changeTelRules.mobile">
            <div class="inp-wrap">
              <el-input placeholder="请输入手机号码" readonly v-model="curTelForm.mobile" maxlength="11"></el-input>
            </div>
          </el-form-item>
          <el-form-item prop="code" :rules="changeTelRules.code">
            <div class="inp-wrap inp-wrap-code">
              <el-input placeholder="请输入验证码" v-model="curTelForm.code" maxlength="4"></el-input>
              <span class="code" @click="sendCode('boxStep1')">{{verifyCodeVal}}</span>
            </div>
          </el-form-item>
          <el-form-item>
            <div class="btn-wrap">
              <el-button type="primary" class="btn" @click="resetTel('boxStep1')">下一步</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div class="newtel-box tel-box" v-show="step != 1">
        <el-form :model="newTelForm" status-icon :rules="changeTelRules" ref="boxStep2">
          <el-form-item prop="mobile" :rules="changeTelRules.mobile">
            <div class="inp-wrap">
              <el-input placeholder="请输入新手机号" v-model="newTelForm.mobile" maxlength="11"></el-input>
            </div>
          </el-form-item>
          <el-form-item prop="code" :rules="changeTelRules.code">
            <div class="inp-wrap inp-wrap-code">
              <el-input placeholder="请输入验证码" v-model="newTelForm.code" maxlength="4"></el-input>
              <span class="code" @click="sendCode('boxStep2')">{{verifyCodeVal}}</span>
            </div>
          </el-form-item>
          <el-form-item>
            <div class="btn-wrap">
              <a href="javascript:void(0);" class="btn" @click="resetTel('boxStep2')">确定</a>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </el-dialog>
</template>
<script> 
export default {
  name: 'changeTelBox',
  props: {
    changeTelBoxShow: {
      type: Boolean,
      default: false
    }
  },
  data () {
    var validateCode = (rule, value, callback) => {
      let reg = /[0-9]{4}/
      if (!reg.test(value)) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!reg.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    return {
      boxShow: this.changeTelBoxShow,
      changeTelRules: {
        mobile: [
          { validator: validateMobile, trigger: 'blur' }
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ]
      },
      curTelForm: {
        mobile: null,
        code: null
      },
      newTelForm: {
        old_mobile: null,
        old_code: null,
        mobile: null,
        code: null
      },
      verifydisabled: false,
      verifyCodeVal: '获取验证码',
      timer: null, // 计时器
      step: 1
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:changeTelBoxShow', newVal)
      if (!newVal) this.step = 1
      this.resetData()
    },
    changeTelBoxShow (newVal) {
      this.boxShow = newVal
    },
    step (newVal) {
      this.resetData()
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      let user = JSON.parse(localStorage.getItem('user'))
      this.curTelForm.mobile = user.mobile
    },
    // 清空验证写入数据
    resetData () {
      clearTimeout(this.timer)
      this.timer = null
      this.$nextTick(function () {
        this.verifyCodeVal = '获取验证码'
        this.verifydisabled = false
        let step = this.step
        if (step == 2) {
          this.$refs.boxStep1.resetFields()
        } else {
          this.$refs.boxStep2.resetFields()
        }
      })
    },
    // 倒计时
    countdown (s) {
      if (s === 0) {
        this.verifydisabled = false
        this.verifyCodeVal = '重新发送'
      } else {
        this.verifydisabled = true
        this.verifyCodeVal = '已发送(' + s + ')s'
        this.timer = setTimeout(function () {
          this.countdown(--s)
        }.bind(this), 1000)
      }
    },
    // 发送验证码
    sendCode (formName) {
      this.$refs[formName].validateField('mobile', (valid) => {
        if (!valid) {
          if (this.verifydisabled) return
          let loading = this.$loading({ text: 'Loading' })
          let step = this.step
          let params = { type: 'modify' }
          if (step == 1) {
            params.mobile = this.curTelForm.mobile
          } else {
            params.mobile = this.newTelForm.mobile
          }
          this.$axios.post(this.$httpUrl.code, params).then(res => {
            loading.close()
            if (res) {
              this.countdown(60)
            }
          })
        }
      })
    },
    resetTel (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let params, url
          let step = this.step
          if (step == 1) {
            params = this.curTelForm
            url = this.$httpUrl.resetTel1
          } else {
            params = this.newTelForm
            url = this.$httpUrl.resetTel2
          }
          let loading = this.$loading({ text: 'Loading' })
          this.$axios.post(url, params).then(res => {
            loading.close()
            if (res) {
              if (step == 1) {
                this.newTelForm.old_mobile = params.mobile
                this.newTelForm.old_code = params.code
                this.step = 2
              } else {
                this.$message({
                  message: '重置手机成功',
                  type: 'success'
                })
                this.boxShow = false

              }
            }
          })
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.resetTelbox {
  & /deep/ .el-dialog {
    width: 354px;
    height: 256px;
    .el-dialog__body {
      padding: 20px;
    }
    .el-form-item {
      .el-form-item__error {
        left: 30px;
      }
      .el-input__suffix {
        right: 95px;
      }
      .el-input__inner {
        padding-right: 90px;
      }
    }
    .el-input {
      .el-input__inner {
        height: 36px;
        border-color: @color-light-grey;
      }
    }
  }
  .mark {
    color: @theme-color;
    text-decoration: underline;
  }
  .dialog-bd {
    .tel-box {
      text-align: center;
      .el-form-item {
        margin-bottom: 20px;

        .inp-wrap-code {
          position: relative;
          .code {
            position: absolute;
            right: 36px;
            top: 0;
            z-index: 9;
            color: @theme-color;
            font-size: @fontsize-medium;
            cursor: pointer;
            &::before {
              content: '';
              position: absolute;
              left: -12px;
              top: 8px;
              width: 1px;
              height: 26px;
              background-color: @color-light-grey;
            }
          }
        }
      }
      .el-input {
        width: 255px;
        margin: 0 auto;
      }
      .btn-wrap {
        width: 204px;
        height: 36px;
        margin: 10px auto 0;
        .btn {
          height: 36px;
          line-height: 36px;
          padding: 0;
          width: 100%;
        }
      }
    }
  }
}
</style>
