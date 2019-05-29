<template>
  <el-dialog :visible.sync="boxShow" class="resetPwdbox" :close-on-click-modal="false">
    <div class="dialog-header" slot="title">
      <p class="title">修改密码</p>
    </div>
    <div class="dialog-bd">
      <el-form :model="accountForm" :rules="accountRules" ref="accountForm">
        <el-form-item prop="mobile" :rules="accountRules.mobile">
          <div class="inp-wrap">
            <span class="iconfont icon-shouji iconfont-mobile iconfont-icon"></span>
            <el-input v-model="accountForm.mobile" maxlength="11" readonly placeholder="请输入手机号"></el-input>
          </div>
        </el-form-item>
        <el-form-item prop="code" :rules="accountRules.code">
          <div class="inp-wrap inp-wrap-code">
            <span class="iconfont icon-yanzhengma iconfont-icon"></span>
            <el-input v-model="accountForm.code" placeholder="请输入验证码" maxlength="4"></el-input>
            <span class="code" @click="sendCode('accountForm')">{{verifyCodeVal}}</span>
          </div>
        </el-form-item>
        <el-form-item prop="password" :rules="accountRules.password">
          <div class="inp-wrap inp-wrap-code">
            <span class="iconfont icon-mima iconfont-icon"></span>
            <el-input type="text" v-model="accountForm.password" placeholder="密码长度不小于6位" maxlength="15" @keyup.enter.native="commitAccountInfo('accountForm')"></el-input>
          </div>
        </el-form-item>
        <el-form-item class="btn-wrap-item">
          <div class="btn-wrap">
            <el-button type="primary" @click="commitAccountInfo('accountForm')">确认</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script> 
export default {
  name: 'ResetPwdBox',
  props: {
    resetPwdBoxShow: {
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
    const validatePwd = (rule, value, callback) => {
      let reg = /\s/g
      if (!value) {
        callback(new Error('密码不能为空'))
      } else if (reg.test(value)) {
        callback(new Error('密码不能包含空格'))
      } else if (value.length < 6) {
        callback(new Error('密码的长度不能小于6位'))
      } else {
        callback()
      }
    }
    return {
      boxShow: this.resetPwdBoxShow,
      verifydisabled: false,
      verifyCodeVal: '获取验证码',
      accountForm: {
        mobile: null,
        code: null,
        password: null,
        agreement: null
      },
      accountRules: {
        code: [
          { validator: validateCode, trigger: ['blur', 'change'] }
        ],
        password: [
          { validator: validatePwd, trigger: ['blur', 'change'] }
        ]
      },
      timer: null
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:resetPwdBoxShow', newVal)
      this.resetData()
    },
    resetPwdBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      let user = JSON.parse(localStorage.getItem('user'))
      this.accountForm.mobile = user.mobile
    },
    //倒计时
    countdown (s) {
      if (s === 0) {
        this.verifydisabled = false
        this.verifyCodeVal = '重新发送'
      } else {
        this.verifydisabled = true
        this.verifyCodeVal = '已发送(' + s + ')s'
        this.timer = setTimeout(
          function () {
            this.countdown(--s)
          }.bind(this),
          1000
        )
      }
    },
    //发送验证码
    sendCode (formName) {
      if (this.verifydisabled) return
      let loading = this.$loading({ text: 'Loading' })
      let params = { mobile: this.accountForm.mobile, type: 'login' }
      this.$axios.post(this.$httpUrl.code, params).then(res => {
        loading.close()
        if (res) {
          this.countdown(60)
        }
      })
    },
    // 清空验证写入数据
    resetData () {
      clearTimeout(this.timer)
      this.timer = null
      this.$nextTick(function () {
        this.verifyCodeVal = '获取验证码'
        this.verifydisabled = false
        this.$refs.accountForm.resetFields()
      })
    },
    commitAccountInfo (formName) {
      let params = this[formName]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let loading = this.$loading({ text: 'Loading' })
          let url = this.$httpUrl.resetPassword
          this.$axios.post(url, params).then(res => {
            loading.close()
            if (res) {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.boxShow = false

            }
          })
        }
      })
    },
  }
}
</script>
<style lang="less" scoped>
.resetPwdbox {
  & /deep/ .el-dialog {
    width: 354px;
    height: 320px;
    .el-dialog__body {
      padding: 20px;
    }
    .el-form-item {
      .el-form-item__error {
        left: 66px;
        top: 110%;
      }
      .el-input__suffix {
        right: 140px;
      }
      .el-input__inner {
        padding-right: 90px;
      }
    }
    .el-input {
      .el-input__inner {
        border: none;
        border-bottom: 1px solid #cacaca;
        border-radius: 0;
        padding: 0 90px 0 8px;
        color: @color-grey;
      }
    }
  }
  .mark {
    color: @theme-color;
    text-decoration: underline;
  }
  .dialog-bd {
    .inp-wrap {
      padding-left: 60px;
      position: relative;
      border-radius: 40px;
      .iconfont {
        position: absolute;
        left: 2px;
        font-size: @fontsize-large-x;
        color: @color-light-grey;
        &.iconfont-icon {
          font-size: 22px;
          left: 30px;
        }
      }
      &.inp-wrap-code {
        position: relative;
        .code {
          position: absolute;
          right: 30px;
          top: 2px;
          color: @color-light-grey;
          z-index: 9;
          font-size: @fontsize-medium;
          cursor: pointer;
          &::before {
            content: "";
            position: absolute;
            left: -12px;
            top: 7px;
            width: 1px;
            height: 26px;
            background-color: @color-light-grey;
          }
        }
      }
    }
    .btn-wrap {
      margin: 10px auto 0;
      width: 204px;
      .el-button {
        width: 100%;
      }
    }
  }
}
</style>
